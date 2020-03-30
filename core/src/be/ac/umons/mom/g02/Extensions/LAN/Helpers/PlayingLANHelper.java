package be.ac.umons.mom.g02.Extensions.LAN.Helpers;

import be.ac.umons.mom.g02.Enums.Actions;
import be.ac.umons.mom.g02.Enums.Bloc;
import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Enums.MobileType;
import be.ac.umons.mom.g02.Events.Notifications.Dead;
import be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.Menus.DisconnectedMenuState;
import be.ac.umons.mom.g02.Extensions.LAN.Interfaces.NetworkReady;
import be.ac.umons.mom.g02.Extensions.LAN.GameStates.Menus.PauseMenuState;
import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.Extensions.LAN.Regulator.SupervisorLAN;
import be.ac.umons.mom.g02.Extensions.Multiplayer.Regulator.SupervisorMultiPlayer;
import be.ac.umons.mom.g02.GameStates.PlayingState;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Character;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.MapObject;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Objects.Characters.MovingPNJ;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Other.Date;
import be.ac.umons.mom.g02.Regulator.Supervisor;

import java.awt.*;
import java.net.SocketException;
import java.util.HashMap;
import java.util.List;

public class PlayingLANHelper {
    protected static boolean ignoreEMQ;

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
            nm.sendMessageOnTCP("PNJ", c.getCharacteristics().getName(), c.getCharacteristics(), c.getPosX(), c.getPosY());
        }
        for (MovingPNJ mob : Supervisor.getSupervisor().getMovingPnj(Supervisor.getSupervisor().getMaps(map))) {
            Character c = idCharacterMap.get(mob.getName());
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
        nm.whenMessageReceivedDo("PNJ", (objects) -> ps.onCharacterDetected(
                (String)objects[0],
                (be.ac.umons.mom.g02.Objects.Characters.Character)objects[3],
                (int)objects[1], (int)objects[2]
        ));
        nm.whenMessageReceivedDo("MPNJ", (objects) ->
        {
            MovingPNJ mpnj = new MovingPNJ((Bloc)objects[1], (MobileType) objects[2], (Maps) objects[3], (Actions) objects[4]);
            mpnj.initialisation(ps.onCharacterDetected((String) objects[0], mpnj, (int) objects[5], (int) objects[6]),
                    (PlayingState)ps, ps.getPlayer());
        });
        nm.whenMessageReceivedDo("hitPNJ", (objects) -> {
            if (ps.getIdCharacterMap() != null) {
                Character c = ps.getIdCharacterMap().get(objects[0]);
                if (c != null)
                    c.getCharacteristics().setActualLife((double) objects[1]);
            }
        });
        nm.whenMessageReceivedDo("PNJDeath", (objects) -> {
            if (ps.getIdCharacterMap() != null) {
                String name = (String) objects[0];
                if (ps.getIdCharacterMap().containsKey(name)) {
                    SupervisorLAN.getSupervisor().addADeathToIgnore((Mobile)ps.getIdCharacterMap().get(name).getCharacteristics());
                    Supervisor.getEvent().notify(new Dead(ps.getIdCharacterMap().get(name).getCharacteristics()));
                }
            }
        });
        nm.whenMessageReceivedDo("getItemsPos", (objects ->
                sendItemsPositions(ps.getMapObjects())));
        nm.whenMessageReceivedDo("getPNJsPos", (objects) ->
                PlayingLANHelper.sendPNJsPositions((String) objects[0], ps.getIdCharacterMap()));
        nm.whenMessageReceivedDo("PL", (objects -> SupervisorMultiPlayer.getPeopleTwo().setActualLife((int)(objects[0]))));
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
                SupervisorLAN.getPeople().getQuest().passQuest();
            }
            ignoreEMQ = ! ignoreEMQ;
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
    }
}
