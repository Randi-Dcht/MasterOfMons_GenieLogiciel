package be.ac.umons.mom.g02.Extensions.LAN.GameStates;

import be.ac.umons.mom.g02.Enums.KeyStatus;
import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.Dead;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Extensions.LAN.GameStates.Menus.PauseMenuState;
import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.GameStates.Menus.InGameMenuState;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Character;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Player;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.utils.Array;

import java.awt.*;
import java.io.IOException;
import java.net.SocketException;
import java.util.HashMap;
import java.util.List;

/**
 * The playing state. This state suppose that a connection has already been established.
 * @see be.ac.umons.mom.g02.GameStates.PlayingState
 * @author Guillaume Cardoen
 */
public class PlayingState extends be.ac.umons.mom.g02.Extensions.Multiplayer.GameStates.PlayingState {

    /**
     * The network manager.
     */
    private NetworkManager nm;
    /**
     * The map associating an id (his name) and a character.
     */
    private HashMap<String, Character> idCharacterMap;


    /**
     * @param gsm The game's state manager
     * @param gim The game's input manager
     * @param gs The game's graphical settings.
     */
    public PlayingState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
//        supervisor = SupervisorMultiPlayer.getSupervisor();  //TODO
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
//        supervisor.getPeople().newQuest(new MyFirstYear(supervisor.getPeople(), null, Difficulty.Easy));
        super.init();
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
        if (nm.isTheServer()) {
            if (nm.getMustSendPNJPos() != null) {
                sendPNJsPositions(nm.getMustSendPNJPos());
            } else
                nm.setOnGetPNJ(this::sendPNJsPositions);
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
                supervisor.getEvent().notify(new Dead(idCharacterMap.get(name).getCharacteristics()));
        });

        pauseButton.setOnClick(() -> {
            gsm.setState(InGameMenuState.class);
            nm.sendPause();
        });
        nm.setOnPause(() -> gsm.setState(PauseMenuState.class));
        nm.setOnEndPause(() -> gsm.removeFirstState());
    }

    @Override
    public void initMap(String mapPath, int spawnX, int spawnY) {
        super.initMap(mapPath, spawnX, spawnY);
        if (nm.isTheServer()) {
            for (Character pnj : pnjs) {
                if (! idCharacterMap.containsKey(pnj.getCharacteristics().getName()))
                    idCharacterMap.put(pnj.getCharacteristics().getName(), pnj);
            }
        } else {
            pnjs.clear();
            nm.askPNJsPositions(mapPath);
        }
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
            super.initPNJsPositions(pnjs);
        }
    }

    /**
     * Put a position to all mobiles and add it to the map.
     * @param mobs The list of mobiles to initiaite.
     */
    protected void initMobilesPositions(List<Mobile> mobs) {
        Array<RectangleMapObject> rmos = randomPNJPositions.getByType(RectangleMapObject.class);
        for (Mobile mob : mobs) {
            if ( ! idCharacterMap.containsKey(mob.getName())) {
                Character c = new Character(gs, mob);
                initPNJPosition(c, rmos);
                idCharacterMap.put(mob.getName(), c);
            }
        }
    }

    /**
     * Send all the PNJs positions to the second player.
     * @param map The map asked.
     */
    protected void sendPNJsPositions(String map) { // TODO MovingPNJ
        initMobilesPositions(supervisor.getMobile(supervisor.getMaps(map)));
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
        }
    }

    @Override
    public void getFocus() {
        super.getFocus();
        nm.sendEndPause();
    }
}
