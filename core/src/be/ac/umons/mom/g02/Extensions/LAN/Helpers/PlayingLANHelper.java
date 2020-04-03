package be.ac.umons.mom.g02.Extensions.LAN.Helpers;

import be.ac.umons.mom.g02.Enums.*;
import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.*;
import be.ac.umons.mom.g02.Events.Observer;
import be.ac.umons.mom.g02.Extensions.LAN.GameStates.Menus.PauseMenuState;
import be.ac.umons.mom.g02.Extensions.LAN.Interfaces.NetworkReady;
import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.Extensions.Multiplayer.Regulator.SupervisorMultiPlayer;
import be.ac.umons.mom.g02.GameStates.PlayingState;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Character;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.MapObject;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameMapManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Objects.Characters.MovingPNJ;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Objects.Items.Items;
import be.ac.umons.mom.g02.Other.Date;
import be.ac.umons.mom.g02.Regulator.Supervisor;
import com.badlogic.gdx.Input;

import java.awt.*;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class PlayingLANHelper {
    public static boolean ignoreEMQ;
    public static boolean pauseSent;

    public static void init(Observer observer) {
        Supervisor.getEvent().add(observer, Events.Dead, Events.UpLevel, Events.LifeChanged, Events.ExperienceChanged, Events.EnergyChanged, Events.InventoryChanged, Events.PNJMoved, Events.Attack);
    }

    /**
     * Send all the PNJs positions to the second player.
     * @param map The map asked.
     */
    public static void sendPNJsPositions(String map, HashMap<String, Character> idCharacterMap) {
        NetworkManager nm = null;
        try {
            nm = NetworkManager.getInstance();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        for (Mobile mob : Supervisor.getSupervisor().getMobile(Supervisor.getSupervisor().getMaps(map))) {
            Character c = idCharacterMap.get(mob.getName());
            if (c != null)
                nm.sendMessageOnTCP("PNJ", mob.getName(), mob.getPlayerBloc(), mob.getMobileType(), mob.getMaps(), mob.getAction(), c.getPosX(), c.getPosY());
        }
        for (MovingPNJ mob : Supervisor.getSupervisor().getMovingPnj(Supervisor.getSupervisor().getMaps(map))) {
            Character c = idCharacterMap.get(mob.getName());
            if (c != null)
                nm.sendMessageOnTCP("MPNJ", mob.getName(), mob.getPlayerBloc(), mob.getMobileType(), mob.getMaps(), mob.getAction(), c.getPosX(), c.getPosY());
        }
    }

    /**
     * Send the given items to the second player
     * @param mapObjects The items to send
     */
    public static void sendItemsPositions(List<MapObject> mapObjects) {
        NetworkManager nm = null;
        try {
            nm = NetworkManager.getInstance();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        for (MapObject mo : mapObjects) {
            nm.sendMessageOnTCP("Item", mo.getCharacteristics());
        }
    }

    /**
     * Set all the needed network manager's runnables except the one for setting the map changing (must be done earlier)
     * <code>ps</code> needs to be a PlayingState !
     */
    public static void setNetworkManagerRunnable(NetworkReady ps) {
        NetworkManager nm = null;
        try {
            nm = NetworkManager.getInstance();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        GameStateManager gsm = GameStateManager.getInstance();
        nm.whenMessageReceivedDo("PP", (objects -> ps.setSecondPlayerPosition((Point) objects[0])));
        nm.whenMessageReceivedDo("SPP", (objects) -> ps.setPlayerPosition((Point) objects[0]));
        nm.whenMessageReceivedDo("Pause", (objects) -> gsm.setState(PauseMenuState.class));
        nm.whenMessageReceivedDo("EndPause", (objects) -> gsm.removeFirstState());
        nm.setOnDisconnected(() -> gsm.setState(be.ac.umons.mom.g02.Extensions.LAN.GameStates.Menus.DisconnectedMenuState.class));
        nm.whenMessageReceivedDo("PNJ", (objects) -> {
            Character c = ps.onCharacterDetected(
                    (String)objects[0],
                    (be.ac.umons.mom.g02.Objects.Characters.Character)objects[1],
                    (int)objects[2], (int)objects[3]
            );
            Supervisor.getSupervisor().addMobile((Mobile)objects[1], Supervisor.getSupervisor().getMaps(GameMapManager.getInstance().getActualMapName()), c);
        });
        nm.whenMessageReceivedDo("MPNJ", (objects) ->
        {
            Character c;
            MovingPNJ mpnj = new MovingPNJ((Bloc)objects[1], (MobileType) objects[2], (Maps) objects[3], (Actions) objects[4]);
            mpnj.initialisation(c = ps.onCharacterDetected((String) objects[0], mpnj, (int) objects[5], (int) objects[6]),
                    (PlayingState)ps, ps.getPlayer());
            Supervisor.getSupervisor().addMoving(mpnj, Supervisor.getSupervisor().getMaps(GameMapManager.getInstance().getActualMapName()), c);
        });
        nm.whenMessageReceivedDo("hitPNJ", (objects) -> {
            if (ps.getIdCharacterMap() != null) {
                Character c = ps.getIdCharacterMap().get(objects[0]);
                if (c != null && (double) objects[1] > 0) // Check > to not give the items when he dies (death is took into account lower)
                    c.getCharacteristics().setActualLife((double) objects[1]);
            }
        });
        nm.whenMessageReceivedDo("PNJDeath", (objects) -> {
            if (ps.getIdCharacterMap() != null) {
                String name = (String) objects[0];
                if (ps.getIdCharacterMap().containsKey(name)) {
                    SupervisorMultiPlayer.getSupervisor().addADeathToIgnore((Mobile)ps.getIdCharacterMap().get(name).getCharacteristics());
                    Supervisor.getEvent().notify(new Dead(ps.getIdCharacterMap().get(name).getCharacteristics()));
                }
            }
        });
        nm.whenMessageReceivedDo("getItemsPos", (objects ->
                sendItemsPositions(ps.getMapObjects())));
        nm.whenMessageReceivedDo("getPNJsPos", (objects) ->
                PlayingLANHelper.sendPNJsPositions((String) objects[0], ps.getIdCharacterMap()));
        nm.whenMessageReceivedDo("PL", (objects -> SupervisorMultiPlayer.getPeopleTwo().setActualLife((double)objects[0])));
        nm.whenMessageReceivedDo("PXP", objects -> SupervisorMultiPlayer.getPeopleTwo().setExperience((double) objects[0]));
        nm.whenMessageReceivedDo("PE", objects -> SupervisorMultiPlayer.getPeopleTwo().setEnergy((double) objects[0]));
        nm.whenMessageReceivedDo("IPU", (objects) -> {
            for (int i = 0; i < ps.getMapObjects().size(); i++)
                if (ps.getMapObjects().get(i).getCharacteristics().equals(objects[0]))
                    ps.getMapObjects().remove(i);
        });
        nm.whenMessageReceivedDo("EMQ", (objects) -> {
            if (! ignoreEMQ) {
                ps.getTimeShower().extendOnFullWidth(GraphicalSettings.getStringFromId("secondPlayerFinishedQuest"));
                SupervisorMultiPlayer.getPeople().getQuest().passQuest();
                SupervisorMultiPlayer.getPeopleTwo().newQuest(Supervisor.getPeople().getQuest(), false);
            }
            ignoreEMQ = ! ignoreEMQ;
        });
        nm.whenMessageSentDo("EMQ", () -> {
            ignoreEMQ = true;
            SupervisorMultiPlayer.getPeopleTwo().newQuest(Supervisor.getPeople().getQuest(), false);
        });
        nm.whenMessageReceivedDo("LVLUP", (objects) -> {
            int newLevel = (int)objects[0];
            while (newLevel > ps.getSecondPlayer().getCharacteristics().getLevel())
                ((People)ps.getSecondPlayer().getCharacteristics()).upLevel();
            ps.getTimeShower().extendOnFullWidth(String.format(GraphicalSettings.getStringFromId("secondPlayerLVLUP"),
                    ps.getSecondPlayer().getCharacteristics().getLevel()));
        });
        nm.whenMessageReceivedDo("Item", (objects) -> ps.addItemToMap((MapObject.OnMapItem) objects[0]));
        nm.whenMessageReceivedDo("AC", objects -> ps.getSecondPlayer().expandAttackCircle());
        nm.whenMessageReceivedDo("LVLPA", objects -> SupervisorMultiPlayer.getPeopleTwo().updateUpLevel((int) objects[0], (int) objects[1], (int) objects[2]));
        nm.whenMessageReceivedDo("TIME", (objects) -> Supervisor.getSupervisor().setDate((Date) objects[0]));
        nm.whenMessageReceivedDo("PNJMove", objects -> {
            String pnj = (String) objects[0];
            Point position = (Point) objects[1];
            if (ps.getIdCharacterMap().containsKey(pnj))
                ps.getIdCharacterMap().get(pnj).setMapPos(position);
        });
        nm.whenMessageReceivedDo("PA", objects -> {
            for (ArrayList<MovingPNJ> mvl : Supervisor.getSupervisor().getListMoving().values())
                for (MovingPNJ mv : mvl)
                    mv.setVictim(ps.getSecondPlayer());
            Supervisor.getSupervisor().setVictimPlayer((People)ps.getSecondPlayer().getCharacteristics());
        });
        nm.whenMessageSentDo("PA", () -> {
            for (ArrayList<MovingPNJ> mvl : Supervisor.getSupervisor().getListMoving().values())
                for (MovingPNJ mv : mvl)
                    mv.setVictim(ps.getPlayer());
            Supervisor.getSupervisor().setVictimPlayer((People)ps.getPlayer().getCharacteristics());
        });
        nm.whenMessageReceivedDo("PO", (objects) -> ps.getSecondPlayer().setOrientation((Orientation) objects[0]));
        nm.whenMessageReceivedDo("IC", objects -> {
            Items it = (Items)objects[0];
            InventoryChanged.Type type = (InventoryChanged.Type)objects[1];
            if (type == InventoryChanged.Type.Added)
                ((People)ps.getSecondPlayer().getCharacteristics()).pushObject(it);
            else if (type == InventoryChanged.Type.Removed)
                ps.getSecondPlayer().getCharacteristics().removeObject(it);
        });
    }

    public static void handleInput() {
        GameInputManager gim = GameInputManager.getInstance();
        NetworkManager nm = null;
        try {
            nm = NetworkManager.getInstance();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        if (gim.isKey(Input.Keys.ESCAPE, KeyStatus.Pressed)) {
            nm.sendOnTCP("Pause");
            pauseSent = true;
        }
        if (gim.isKey("attack", KeyStatus.Pressed))
            nm.sendOnTCP("AC"); // TODO Don't work on UDP (?)
    }

    public static void update(NetworkReady ps, Notification notify) {
        NetworkManager nm = null;
        try {
            nm = NetworkManager.getInstance();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        if (notify.getEvents().equals(Events.Dead) && notify.getBuffer().getClass().equals(Mobile.class)) {
            Mobile m = (Mobile) notify.getBuffer();
            nm.sendMessageOnTCP("PNJDeath", m.getName());
            ps.getIdCharacterMap().remove(m.getName());
        } else if (notify.getEvents().equals(Events.UpLevel) && notify.getBuffer().equals(Supervisor.getPeople()))
            nm.sendMessageOnTCP("LVLUP", Supervisor.getPeople().getLevel());
        else if (notify.getEvents().equals(Events.LifeChanged) && ((LifeChanged)notify).getInvolvedOne().equals(Supervisor.getPeople()))
            nm.sendMessageOnTCP("PL", Supervisor.getPeople().getActualLife());
        else if (notify.getEvents().equals(Events.ExperienceChanged) && ((ExperienceChanged)notify).getInvolvedOne().equals(Supervisor.getPeople()))
            nm.sendMessageOnTCP("PXP", Supervisor.getPeople().getExperience());
        else if (notify.getEvents().equals(Events.EnergyChanged) && ((EnergyChanged)notify).getInvolvedOne().equals(Supervisor.getPeople()))
            nm.sendMessageOnTCP("PE", Supervisor.getPeople().getEnergy());
        else if (notify.getEvents().equals(Events.PNJMoved) && notify.bufferNotEmpty()) {
            PNJMoved notif = (PNJMoved)notify;
            nm.sendMessageOnUDP("PNJMove", notif.getConcernedOne().getName(), notif.getBuffer());
        } else if (notify.getEvents().equals(Events.Attack) && notify.bufferNotEmpty() && notify.getBuffer().equals(ps.getPlayer().getCharacteristics()))
            nm.sendMessageOnUDP("PA"); // Player attack
        else if (notify.getEvents().equals(Events.InventoryChanged) && notify.bufferNotEmpty() && notify.getBuffer().equals(ps.getPlayer().getCharacteristics()))
            nm.sendMessageOnTCP("IC", ((InventoryChanged)notify).getItem(), ((InventoryChanged)notify).getType()); // Player attack
    }

    public static void getFocus() {
        NetworkManager nm = null;
        try {
            nm = NetworkManager.getInstance();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        if (pauseSent) {
            nm.sendOnTCP("EndPause");
            pauseSent = false;
        }
    }

    public static List<Character> getPNJsOnMap(String mapName, NetworkReady ps, GraphicalSettings gs) {
        NetworkManager nm = null;
        try {
            nm = NetworkManager.getInstance();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        if (! nm.isTheServer())
            return new LinkedList<>();
        Maps map = Supervisor.getSupervisor().getMaps(mapName);
        List<Character> pnjs = new ArrayList<>();
        for (Mobile mob : Supervisor.getSupervisor().getMobile(map)) {
            if (ps.getIdCharacterMap().containsKey(mob.getName()))
                pnjs.add(ps.getIdCharacterMap().get(mob.getName()));
            else {
                Character c = new Character(gs, mob);
                pnjs.add(c);
                Supervisor.getSupervisor().init(mob, c);
                ps.getIdCharacterMap().put(mob.getName(), c);
            }
        }

        for (MovingPNJ mv : Supervisor.getSupervisor().getMovingPnj(map)) {
            if (ps.getIdCharacterMap().containsKey(mv.getName()))
                pnjs.add(ps.getIdCharacterMap().get(mv.getName()));
            else {
                Character c = new Character(gs, mv);
                pnjs.add(c);
                mv.initialisation(c, (PlayingState)ps, ps.getPlayer());
                ps.getIdCharacterMap().put(mv.getName(), c);
            }
        }

        return pnjs;
    }
}
