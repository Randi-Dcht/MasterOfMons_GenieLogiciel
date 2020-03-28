package be.ac.umons.mom.g02.Extensions.LAN.GameStates;

import be.ac.umons.mom.g02.Enums.*;
import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.*;
import be.ac.umons.mom.g02.Extensions.LAN.GameStates.Menus.DisconnectedMenuState;
import be.ac.umons.mom.g02.Extensions.LAN.GameStates.Menus.PauseMenuState;
import be.ac.umons.mom.g02.Extensions.LAN.Helpers.PlayingLANHelper;
import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.Extensions.LAN.Quests.Master.MyFirstYear;
import be.ac.umons.mom.g02.Extensions.LAN.Regulator.SupervisorLAN;
import be.ac.umons.mom.g02.Extensions.Multiplayer.Objects.Save;
import be.ac.umons.mom.g02.Extensions.Multiplayer.Regulator.SupervisorMultiPlayer;
import be.ac.umons.mom.g02.GameStates.Menus.DeadMenuState;
import be.ac.umons.mom.g02.GameStates.Menus.InGameMenuState;
import be.ac.umons.mom.g02.GameStates.Menus.MainMenuState;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Character;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.MapObject;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Player;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Objects.Characters.MovingPNJ;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.Course;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Other.Date;
import be.ac.umons.mom.g02.Regulator.Supervisor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.utils.Array;

import java.awt.*;
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
    protected boolean mazeMode;
    /**
     * If the player (one) is the one who can move in the maze
     */
    protected boolean isTheMazePlayer;
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
     * If a pause signal has been sent to the second player
     */
    protected boolean pauseSent = false;


    /**
     * @param gs The game's graphical settings.
     */
    public PlayingState(GraphicalSettings gs) {
        super(gs);
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
            gsm.removeAllStateAndAdd(MainMenuState.class);
            MasterOfMonsGame.showAnError("There was a fatal error !");
            Gdx.app.error("PlayingState", "The NetworkManager couldn't be retrieved !", e);
            return;
        }
        idCharacterMap = new HashMap<>();
        nm.whenMessageReceivedDo("CM", (objects) -> {
            String map = (String)objects[0];
            if (nm.isTheServer())
                refreshPNJsMap(gmm.getActualMapName(), gmm.getActualMapName(), secondPlayerMap, map);
            secondPlayerMap = map;
            mustDrawSecondPlayer = map.equals(gmm.getActualMapName());
        });
        nm.whenMessageReceivedDo("SPMC", (objects -> secondPlayerMap = (String)objects[0]));

        supervisor.setMustPlaceItem(nm.isTheServer());
        Supervisor.getEvent().add(this, Events.Dialog, Events.LifeChanged, Events.EnergyChanged, Events.ExperienceChanged); // Other events done in super.init()
        newParty = (MasterOfMonsGame.getGameToLoad() == null && MasterOfMonsGame.getSaveToLoad() == null);
        Supervisor.setGraphic(gs);
        supervisor.setGraphic(questShower,this);
        if (newParty)
            SupervisorLAN.getSupervisor().newParty(new MyFirstYear(Supervisor.getPeople(), null, Supervisor.getPeople().getDifficulty()),
                    SupervisorLAN.getPeople(), SupervisorLAN.getPeopleTwo());
//            SupervisorLAN.getSupervisor().newParty(new LearnToCooperate(null, Supervisor.getPeople(), Supervisor.getPeople().getDifficulty()), TODO
//                    SupervisorLAN.getPeople(), SupervisorLAN.getPeopleTwo());

        super.init();
        if (! newParty && ! nm.isTheServer())
            ((SupervisorLAN)supervisor).oldGameLAN((Save)MasterOfMonsGame.getSaveToLoad(), this, gs);

        setNetworkManagerRunnables();

        pauseButton.setOnClick(() -> {
            gsm.setState(InGameMenuState.class);
            nm.sendMessageOnTCP("Pause");
            pauseSent = true;
        });

        goodPuzzlePathColor = new Color(0x2E7D32FF);
        badPuzzlePathColor = new Color(0xD50000FF);
//        if (newParty) { TODO
//            initMap("Tmx/LAN_Puzzle.tmx");
//            secondPlayerMap = gmm.getActualMapName();
//            SupervisorLAN.getSupervisor().getRegale().push("InfoPuzzle");
//        }

        if (nm.isTheServer()) {
            nm.sendMessageOnTCP("PLAN", SupervisorLAN.getPeople().getPlanning());
        } else {
            nm.sendMessageOnTCP("getPNJsPos", gmm.getActualMapName());
            nm.sendMessageOnTCP("getItemsPos");
        }
    }

    @Override
    protected void loadOldGame() {
        ((SupervisorLAN)supervisor).oldGameLAN(MasterOfMonsGame.getGameToLoad(), this, gs);
        nm.sendMessageOnTCP("SPP", new Point(playerTwo.getPosX(), playerTwo.getPosY()));
    }

    /**
     * Set all the needed network manager's runnables except the one for setting the map changing (must be done earlier)
     */
    public void setNetworkManagerRunnables() {
        nm.whenMessageReceivedDo("PNJ", (objects) -> onCharacterDetected(
                (String)objects[0],
                (be.ac.umons.mom.g02.Objects.Characters.Character)objects[1],
                (int)objects[2], (int)objects[3]
        ));
        nm.whenMessageReceivedDo("MPNJ", (objects) ->
        {
            MovingPNJ mpnj = new MovingPNJ((Bloc)objects[1], (MobileType) objects[2], (Maps) objects[3], (Actions) objects[4]);
            mpnj.initialisation(onCharacterDetected((String) objects[0], mpnj, (int) objects[5], (int) objects[6]),
                    this, player);
        });
        nm.whenMessageReceivedDo("Item", (objects) -> addItemToMap((MapObject.OnMapItem) objects[0]));
        nm.whenMessageReceivedDo("getPNJsPos", (objects) -> sendPNJsPositions((String) objects[0]));
        nm.whenMessageReceivedDo("PP", (objects -> setSecondPlayerPosition((Point) objects[0])));
        nm.whenMessageReceivedDo("SPP", (objects) -> player.setMapPos((Point) objects[0]));
        nm.whenMessageReceivedDo("hitPNJ", (objects) -> {
            Character c = idCharacterMap.get(objects[0]);
            if (c != null) {
                c.getCharacteristics().setActualLife((double) objects[1]);
                playerTwo.expandAttackCircle();
            }
        });
        nm.whenMessageReceivedDo("PNJDeath", (objects) -> {
            String name = (String) objects[0];
            if (idCharacterMap.containsKey(name)) {
                SupervisorLAN.getSupervisor().addADeathToIgnore((Mobile)idCharacterMap.get(name).getCharacteristics());
                Supervisor.getEvent().notify(new Dead(idCharacterMap.get(name).getCharacteristics()));
            }
        });
        nm.whenMessageReceivedDo("Pause", (objects) -> gsm.setState(PauseMenuState.class));
        nm.whenMessageReceivedDo("EndPause", (objects) -> gsm.removeFirstState());
        nm.whenMessageReceivedDo("EMQ", (objects) -> {
            timeShower.extendOnFullWidth(gs.getStringFromId("secondPlayerFinishedQuest"));
            SupervisorLAN.getPeople().getQuest().passQuest();
        });
        nm.setOnDisconnected(() -> gsm.setState(DisconnectedMenuState.class));
        nm.whenMessageReceivedDo("ITMP", (objects) -> {
            boolean b = (boolean) objects[0];
            isTheMazePlayer = b;
            player.setIsATarget(! isTheMazePlayer);
            if (! b)
                player.setNoMoving(true);
        });
        nm.whenMessageReceivedDo("LVLUP", (objects) -> {
            int newLevel = (int)objects[0];
            while (newLevel > playerTwo.getCharacteristics().getLevel())
                ((People)playerTwo.getCharacteristics()).upLevel();
            timeShower.extendOnFullWidth(String.format(gs.getStringFromId("secondPlayerLVLUP"), playerTwo.getCharacteristics().getLevel()));
        });
        nm.whenMessageReceivedDo("getItemsPos", (objects ->
                PlayingLANHelper.sendItemsPositions(mapObjects)));
        nm.whenMessageReceivedDo("Death", (objects) -> {
            DeadMenuState dms = (DeadMenuState) gsm.setState(DeadMenuState.class);
            dms.setText(gs.getStringFromId("partnerDead"));
        });
        nm.whenMessageReceivedDo("IPU", (objects) -> {
            for (int i = 0; i < mapObjects.size(); i++)
                if (mapObjects.get(i).getCharacteristics().equals(objects[0]))
                    mapObjects.remove(i);
        });
        nm.whenMessageReceivedDo("TIME", (objects) -> Supervisor.getSupervisor().setDate((Date) objects[0]));
        nm.whenMessageReceivedDo("PLAN", (objects -> {
            SupervisorLAN.getSupervisor().updatePlanning((HashMap<Integer, ArrayList<Course>>) objects[0]);
            agendaShower.refreshCourses();
        }));
        nm.whenMessageReceivedDo("PL", (objects -> SupervisorLAN.getPeopleTwo().setActualLife((double) objects[0])));
        nm.whenMessageReceivedDo("PXP", objects -> SupervisorLAN.getPeopleTwo().setExperience((double) objects[0]));
        nm.whenMessageReceivedDo("PE", objects -> SupervisorLAN.getPeopleTwo().setEnergy((double) objects[0]));
        nm.whenMessageReceivedDo("AC", objects -> playerTwo.expandAttackCircle());
        nm.whenMessageReceivedDo("LVLPA", objects -> SupervisorMultiPlayer.getPeopleTwo().updateUpLevel((int) objects[0], (int) objects[1], (int) objects[2]));
    }

    /**
     * Executed when the second player sends a character characteristics. It adds the character to the map.
     * @param name The character's name
     * @param mob The character's characteristics
     * @param x The horizontal position on the map (pixel)
     * @param y The vertical position on the map (pixel)
     * @return The graphical object associated with the character
     */
    private Character onCharacterDetected(String name, be.ac.umons.mom.g02.Objects.Characters.Character mob, int x, int y) {
        Character c = new Character(gs, mob);
        pnjs.add(c);
        idCharacterMap.put(name, c);
        c.setMapPos(new Point(x, y));
        c.setMapWidth(mapWidth * tileWidth);
        c.setMapHeight(mapHeight * tileHeight);
        c.setTileWidth(tileWidth);
        c.setTileHeight(tileHeight);
        return c;
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
        if (nm.isTheServer())
            nm.sendMessageOnUDP("TIME", Supervisor.getSupervisor().getTime().getDate());
    }

    @Override
    public void initMap(String mapPath, int spawnX, int spawnY) {
        refreshPNJsMap(gmm.getActualMapName(), mapPath, secondPlayerMap, secondPlayerMap);
        if (mazeMode)
            nm.sendMessageOnTCP("SPMC", mapPath);
        super.initMap(mapPath, spawnX, spawnY);
        nm.sendMessageOnTCP("CM", mapPath);
        mustDrawSecondPlayer = gmm.getActualMapName().equals(secondPlayerMap);
        if (nm.isTheServer()) {
            for (Character pnj : pnjs) {
                if (! idCharacterMap.containsKey(pnj.getCharacteristics().getName()))
                    idCharacterMap.put(pnj.getCharacteristics().getName(), pnj);
            }
        } else {
            pnjs.clear();
            nm.sendMessageOnTCP("getPNJsPos", mapPath);
        }
        if (gmm.getActualMap().getProperties().containsKey("MazeMode"))
            mazeMode = (boolean)gmm.getActualMap().getProperties().get("MazeMode");
        else
            mazeMode = false;
        if (mazeMode) {
            mustDrawSecondPlayer = false;
            MapLayer puzzleLayer = gmm.getActualMap().getLayers().get("Puzzle");
            if (puzzleLayer != null)
                puzzleObjects = puzzleLayer.getObjects();
            if (nm.isTheServer()) {
                isTheMazePlayer = (new Random().nextInt() % 2 == 1);
                player.setIsATarget(! isTheMazePlayer);
                nm.sendMessageOnTCP("ITMP", ! isTheMazePlayer);
                if (! isTheMazePlayer)
                    player.setNoMoving(true);
            }
        } else {
            mustDrawSecondPlayer = true;
            puzzleObjects = null;
            player.setIsATarget(false);
            player.setNoMoving(false);
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
            else {
                Character c = new Character(gs, mv);
                pnjs.add(c);
                mv.initialisation(c, this, player);
            }

        return pnjs;
    }

    @Override
    public void handleInput() {
        super.handleInput();
        if (! mazeMode || isTheMazePlayer )
            nm.sendMessageOnUDP("PP", player.getMapPos());

        if (gim.isKey(Input.Keys.ESCAPE, KeyStatus.Pressed)) {
            nm.sendOnTCP("Pause");
            pauseSent = true;
        }
        if (gim.isKey("attack", KeyStatus.Pressed))
            nm.sendOnUDP("AC");
    }

    @Override
    public void initPNJsPositions(List<Character> pnjs) {
        if (nm.isTheServer()) {
            Array<RectangleMapObject> rmos = randomPNJPositions.getByType(RectangleMapObject.class);
            for (Character c : pnjs) {
                if (! idCharacterMap.containsKey(c.getCharacteristics().getName())) {
                    initPNJPosition(c, rmos);
                    idCharacterMap.put(c.getCharacteristics().getName(), c);
                }
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
    protected void sendPNJsPositions(String map) {
        initMobilesPositions(map);
        PlayingLANHelper.sendPNJsPositions(map, idCharacterMap);
    }

    @Override
    public MapObject dropSelectedObject() {
        MapObject mo = super.dropSelectedObject();
        nm.sendMessageOnTCP("Item", mo.getCharacteristics());
        return mo;
    }

    @Override
    protected void pickUpAnObject() {
        nm.sendMessageOnTCP("IPU", ((MapObject) selectedOne).getCharacteristics());
        super.pickUpAnObject();
    }

    @Override
    protected void attack(Player player) {
        if (player.isRecovering())
            return;
        player.expandAttackCircle();
        for (Character c : getPlayerInRange(player,player.getAttackRange() * player.getAttackRange(), true)) {
            supervisor.attackMethod(player.getCharacteristics(), c.getCharacteristics());
            player.setTimeBeforeAttack(player.getCharacteristics().recovery());
            nm.sendMessageOnUDP("hitPNJ", c.getCharacteristics().getName(), c.getCharacteristics().getActualLife());
        }
    }

    @Override
    protected void onPointsAttributed(int strength, int defence, int agility) {
        super.onPointsAttributed(strength, defence, agility);
        nm.sendMessageOnTCP("LVLPA", strength, defence, agility);
    }

    @Override
    public void update(Notification notify) {
        super.update(notify);
        if (notify.getEvents().equals(Events.Dead) &&
                (notify.getBuffer().equals(player.getCharacteristics()))) {
            gsm.setState(DeadMenuState.class);
            nm.sendOnTCP("Death");
        }
        else if (notify.getEvents().equals(Events.Dead)) {
            Mobile m = (Mobile) notify.getBuffer();
            nm.sendMessageOnTCP("PNJDeath", m.getName());
            idCharacterMap.remove(m.getName());
        } else if (notify.getEvents().equals(Events.UpLevel) && notify.getBuffer().equals(player.getCharacteristics()))
            nm.sendMessageOnTCP("LVLUP", player.getCharacteristics().getLevel());
        else if (notify.getEvents().equals(Events.LifeChanged) && ((LifeChanged)notify).getConcernedOne().equals(player.getCharacteristics()))
            nm.sendMessageOnUDP("PL", Supervisor.getPeople().getActualLife());
        else if (notify.getEvents().equals(Events.ExperienceChanged) && ((ExperienceChanged)notify).getConcernedOne().equals(player.getCharacteristics()))
            nm.sendMessageOnUDP("PXP", Supervisor.getPeople().getExperience());
        else if (notify.getEvents().equals(Events.EnergyChanged) && ((EnergyChanged)notify).getConcernedOne().equals(player.getCharacteristics()))
            nm.sendMessageOnUDP("PE", Supervisor.getPeople().getEnergy());
    }

    @Override
    public void getFocus() {
        super.getFocus();
        if (pauseSent) {
            nm.sendOnTCP("EndPause");
            pauseSent = false;
        }
        if (mazeMode) // If second player was disconnected
            nm.sendMessageOnTCP("ITMP", ! isTheMazePlayer);
    }

    @Override
    public void setSecondPlayerPosition(Point mapPos) {
        super.setSecondPlayerPosition(mapPos);
        if (mazeMode)
            player.setMapPos(mapPos);
    }

    public void setSecondPlayerMap(String secondPlayerMap) {
        this.secondPlayerMap = secondPlayerMap;
    }

    /**
     * @param newParty If this game is a new game or not.
     */
    public void setNewParty(boolean newParty) {
        this.newParty = newParty;
    }

    /**
     * @return The map on which the second player is
     */
    public String getSecondPlayerMap() {
        return secondPlayerMap;
    }

    /**
     * @return The current position of the second player
     */
    public Point getSecondPlayerPosition() {
        return new Point(playerTwo.getPosX(), playerTwo.getPosY());
    }

    @Override
    public void dispose() {
        super.dispose();
        nm.close();
    }
}
