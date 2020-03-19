package be.ac.umons.mom.g02.Extensions.LAN.GameStates;

import be.ac.umons.mom.g02.Enums.KeyStatus;
import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.Dead;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Extensions.LAN.GameStates.Menus.DisconnectedMenuState;
import be.ac.umons.mom.g02.Extensions.LAN.GameStates.Menus.PauseMenuState;
import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.Extensions.LAN.Quests.Master.LearnToCooperate;
import be.ac.umons.mom.g02.Extensions.LAN.Regulator.SupervisorLAN;
import be.ac.umons.mom.g02.GameStates.Menus.InGameMenuState;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Character;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Player;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Objects.Characters.MovingPNJ;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Regulator.Supervisor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.utils.Array;

import java.awt.*;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * The playing state. This state suppose that a connection has already been established.
 * @see be.ac.umons.mom.g02.Extensions.Multiplayer.GameStates.PlayingState
 * @author Guillaume Cardoen
 */
public class PlayingState extends be.ac.umons.mom.g02.Extensions.Multiplayer.GameStates.PlayingState {

    /**
     * The network manager.
     */
    protected NetworkManager nm;
    /**
     * The map associating an id (his name) and a character.
     */
    protected HashMap<String, Character> idCharacterMap;

    /**
     * The map on which the second player is.
     */
    protected String secondPlayerMap;
    /**
     * If we must call <code>newParty</code> method or not
     * @see SupervisorLAN
     */
    protected boolean newParty = true;
    /**
     * If the state must applied the maze rule or not.
     */
    protected boolean mazeMode = false;
    /**
     * If the player (one) is the one who can move in the maze
     */
    protected boolean isTheMazePlayer = false;
    /**
     * The objects that are in the layer puzzle on the map.
     */
    protected MapObjects puzzleObjects;

    /**
     * The color when we are on the good path in a puzzle.
     */
    protected Color goodPuzzlePathColor;
    /**
     * The color when we are on the bad path in a puzzle.
     */
    protected Color badPuzzlePathColor;


    /**
     * @param gsm The game's state manager
     * @param gim The game's input manager
     * @param gs The game's graphical settings.
     */
    public PlayingState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }

    /**
     * Default constructor. USE IT ONLY FOR TESTS PURPOSES
     */
    protected PlayingState() { }

    @Override
    protected void setSupervisor() {
        supervisor = SupervisorLAN.getSupervisor();
    }

    @Override
    public void init() {
        try {
            nm = NetworkManager.getInstance();
        } catch (SocketException e) {
            Gdx.app.error("PlayingState", "The NetworkManager couldn't be retrieved !", e);
            // TODO Go to an error page
        }
        idCharacterMap = new HashMap<>();
        nm.setOnMapChanged((map) -> {
            if (nm.isTheServer())
                refreshPNJsMap(gmm.getActualMapName(), gmm.getActualMapName(), secondPlayerMap, map);
            secondPlayerMap = map;
            mustDrawSecondPlayer = map.equals(gmm.getActualMapName());
        });

        supervisor.setMustPlaceItem(false);
        if (newParty)
            SupervisorLAN.getSupervisor().newParty(new LearnToCooperate(null, Supervisor.getPeople(), Supervisor.getPeople().getDifficulty()),
                    SupervisorLAN.getPeople(), SupervisorLAN.getPeopleTwo());
        super.init();
        secondPlayerMap = gmm.getActualMapName();

        setNetworkManagerRunnables();

        pauseButton.setOnClick(() -> {
            gsm.setState(InGameMenuState.class);
            nm.sendPause();
        });

        goodPuzzlePathColor = new Color(0x2E7D32);
        badPuzzlePathColor = new Color(0xB71C1C);
    }

    @Override
    protected void loadOldGame() {
        ((SupervisorLAN)supervisor).oldGameLAN(MasterOfMonsGame.getGameToLoad(), this, gs);
    }

    /**
     * Set all the needed network manager's runnables except the one for setting the map changing (must be done earlier)
     */
    protected void setNetworkManagerRunnables() {
        nm.setOnPNJDetected((name, mob, x, y) -> {
            Character c = new Character(gs, mob);
            pnjs.add(c);
            idCharacterMap.put(name, c);
            c.setMapPos(new Point(x, y));
            c.setMapWidth(mapWidth * tileWidth);
            c.setMapHeight(mapHeight * tileHeight);
            c.setTileWidth(tileWidth);
            c.setTileHeight(tileHeight);
        });
        nm.setOnItemDetected((item, x, y) -> addItemToMap(item, new Point(x, y)));
        if (nm.isTheServer()) {
            if (nm.getMustSendPNJPos() != null) {
                sendPNJsPositions(nm.getMustSendPNJPos());
            } else {
                nm.setOnGetPNJ(this::sendPNJsPositions);
            }
        } else
            nm.askPNJsPositions(gmm.getActualMapName());
        nm.setOnPositionDetected(this::setSecondPlayerPosition);
        nm.setOnHitPNJ((name, life) -> {
            Character c = idCharacterMap.get(name);
            if (c != null) {
                c.getCharacteristics().setActualLife(life);
                playerTwo.expandAttackCircle();
            }
        });
        nm.setOnPNJDeath((name) -> {
            if (idCharacterMap.containsKey(name))
                Supervisor.getEvent().notify(new Dead(idCharacterMap.get(name).getCharacteristics()));
        });
        nm.setOnPause(() -> gsm.setState(PauseMenuState.class));
        nm.setOnEndPause(() -> gsm.removeFirstState());
        nm.setOnMasterQuestFinished(() -> {
            timeShower.extendOnFullWidth(gs.getStringFromId("secondPlayerFinishedQuest"));
            SupervisorLAN.getPeople().getQuest().passQuest();
        });
        nm.setOnDisconnected(() -> {
            DisconnectedMenuState dms = (DisconnectedMenuState) gsm.setState(DisconnectedMenuState.class);
            dms.setPlayingState(this);
        });
        nm.setOnMazePlayerDetected((b) -> {
            isTheMazePlayer = b;
            if (! b)
                player.setNoMoving(true);
        });
        nm.setOnLevelUp(() -> {
            ((People)playerTwo.getCharacteristics()).upLevel();
            timeShower.extendOnFullWidth(String.format(gs.getStringFromId("secondPlayerLVLUP"), playerTwo.getCharacteristics().getLevel()));
        });
        nm.setOnGetItem((map) -> {
            // TODO
        });
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        nm.update(dt);
        if (mazeMode) {
            boolean goodPath = checkForPuzzleCollision();
            if (goodPath)
                player.setIsATargetColor(goodPuzzlePathColor);
            else
                player.setIsATargetColor(badPuzzlePathColor);
        }
    }

    @Override
    public void initMap(String mapPath, int spawnX, int spawnY) {
        refreshPNJsMap(gmm.getActualMapName(), mapPath, secondPlayerMap, secondPlayerMap);
        super.initMap(mapPath, spawnX, spawnY);
        nm.sendMapChanged(mapPath);
        mustDrawSecondPlayer = gmm.getActualMapName().equals(secondPlayerMap);
        if (nm.isTheServer()) {
            for (Character pnj : pnjs) {
                if (! idCharacterMap.containsKey(pnj.getCharacteristics().getName()))
                    idCharacterMap.put(pnj.getCharacteristics().getName(), pnj);
            }
        } else {
            pnjs.clear();
            nm.askPNJsPositions(mapPath);
        }
        if (gmm.getActualMap().getProperties().containsKey("MazeMode"))
            mazeMode = (boolean)gmm.getActualMap().getProperties().get("MazeMode");
        else
            mazeMode = false;
        if (mazeMode) {
            mustDrawSecondPlayer = false;
            player.setIsATarget(true);
            MapLayer puzzleLayer = gmm.getActualMap().getLayers().get("Puzzle");
            if (puzzleLayer != null)
                puzzleObjects = puzzleLayer.getObjects();
            if (nm.isTheServer())
                isTheMazePlayer = (new Random().nextInt() % 2 == 1);
            nm.sendIsTheMazePlayer(! isTheMazePlayer);
            if (! isTheMazePlayer)
                player.setNoMoving(true);
        }
    }

    /**
     * @return If the player is in collision with an object on the puzzle layer.
     */
    protected boolean checkForPuzzleCollision() {
        return checkForCollision(puzzleObjects, player) == null;
    }

    @Override
    protected List<Character> getPNJsOnMap(String mapName) {
        Maps map = supervisor.getMaps(mapName);
        List<Character> pnjs = new ArrayList<>();
        supervisor.init(player.getCharacteristics(), player);
        for (Mobile mob : supervisor.getMobile(map)) {
            if (idCharacterMap.containsKey(mob.getName()))
                pnjs.add(idCharacterMap.get(mob.getName()));
            else {
                Character c = new Character(gs, mob);
                pnjs.add(c);
                supervisor.init(mob, c);
            }
        }

        for (MovingPNJ mv : supervisor.getMovingPnj(map))
            if (idCharacterMap.containsKey(mv.getName()))
                pnjs.add(idCharacterMap.get(mv.getName()));
            else
                pnjs.add(mv.initialisation(gs, this, player));

        return pnjs;
    }

    @Override
    public void handleInput() {
        super.handleInput();
        nm.sendPlayerPosition(player);

        if (gim.isKey(Input.Keys.ESCAPE, KeyStatus.Pressed))
            nm.sendPause();
    }

    @Override
    public void initPNJsPositions(List<Character> pnjs) {
        if (nm.isTheServer()) {
            Array<RectangleMapObject> rmos = randomPNJPositions.getByType(RectangleMapObject.class);
            for (Character c : pnjs) {
                if (! idCharacterMap.containsKey(c.getCharacteristics().getName()))
                    initPNJPosition(c, rmos);
            }
        }
    }

    /**
     * Put a position to all mobiles and add it to the map.
     * @param mapName The map's name where the mobiles must be
     */
    protected void initMobilesPositions(String mapName) {
        List<Mobile> mobs = supervisor.getMobile(supervisor.getMaps(mapName));
        MapLayer pnjLayer = gmm.getMap(mapName).getLayers().get("RandomPNJ");
        if (pnjLayer != null) {
            MapObjects randomPNJPositions = pnjLayer.getObjects();

            Array<RectangleMapObject> rmos = randomPNJPositions.getByType(RectangleMapObject.class);
            for (Mobile mob : mobs) {
                if ( ! idCharacterMap.containsKey(mob.getName())) {
                    Character c = new Character(gs, mob);
                    initPNJPosition(c, rmos);
                    idCharacterMap.put(mob.getName(), c);
                }
            }
        }
    }

    /**
     * Remove unnecessary PNJs from idCharacterMap for refreshing the PNJs.
     * @param p1Old The first player's old map
     * @param p1New The first player's new map
     * @param p2Old The second player's old map
     * @param p2New The second player's new map
     */
    protected void refreshPNJsMap(String p1Old, String p1New, String p2Old, String p2New) {
        if ( p1Old != null && p2New != null && ! p1Old.equals(p1New) && ! p2New.equals(p1Old))
            removeAllPNJsFromMap(p1Old);
        else if (p2Old != null && p2New != null && ! p2Old.equals(p1New) && ! p2New.equals(p2Old))
            removeAllPNJsFromMap(p2Old);
    }

    /**
     * Remove all PNJs from map <code>mapName</code> from idCharacterMap.
     * @param mapName The name of a map.
     */
    protected void removeAllPNJsFromMap(String mapName) {
        for (Mobile mob : supervisor.getMobile(supervisor.getMaps(mapName)))
            idCharacterMap.remove(mob.getName());
    }

    /**
     * Send all the PNJs positions to the second player.
     * @param map The map asked.
     */
    protected void sendPNJsPositions(String map) { // TODO MovingPNJ
        initMobilesPositions(map);
        for (Mobile mob : supervisor.getMobile(supervisor.getMaps(map))) {
            try {
                nm.sendPNJInformation(idCharacterMap.get(mob.getName()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void attack(Player player) {
        if (player.isRecovering())
            return;
        player.expandAttackCircle();
        for (Character c : getPlayerInRange(player,player.getAttackRange() * player.getAttackRange(), true)) {
            supervisor.attackMethod(player.getCharacteristics(), c.getCharacteristics());
            player.setTimeBeforeAttack(player.getCharacteristics().recovery());
            nm.sendHit(c);
        }
    }

    @Override
    public void update(Notification notify) {
        super.update(notify);
        if (notify.getEvents().equals(Events.Dead)) {
            Mobile m = (Mobile) notify.getBuffer();
            nm.sendPNJDeath(m.getName());
            idCharacterMap.remove(m.getName());
        } else if (notify.getEvents().equals(Events.UpLevel)) {
            nm.sendLevelUp();
        }
    }

    @Override
    public void getFocus() {
        super.getFocus();
        nm.sendEndPause();
    }

    @Override
    public void setSecondPlayerPosition(Point mapPos) {
        super.setSecondPlayerPosition(mapPos);
        if (mazeMode)
            player.setMapPos(mapPos);
    }

    /**
     * @param newParty If this game is a new game or not.
     */
    public void setNewParty(boolean newParty) {
        this.newParty = newParty;
    }

    public String getSecondPlayerMap() {
        return secondPlayerMap;
    }

    public Point getSecondPlayerPosition() {
        return new Point(playerTwo.getPosX(), playerTwo.getPosY());
    }

    @Override
    public void dispose() {
        super.dispose();
        nm.close();
    }
}
