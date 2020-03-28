package be.ac.umons.mom.g02.Extensions.LAN.Helpers;

import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Character;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.MapObject;
import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Objects.Characters.MovingPNJ;
import be.ac.umons.mom.g02.Regulator.Supervisor;

import java.net.SocketException;
import java.util.HashMap;
import java.util.List;

public class PlayingLANHelper {

    protected static NetworkManager nm;

    /**
     * Send all the PNJs positions to the second player.
     * @param map The map asked.
     */
    public static void sendPNJsPositions(String map, HashMap<String, Character> idCharacterMap) {
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

    public static void sendItemsPositions(List<MapObject> mapObjects) {
        try {
            nm = NetworkManager.getInstance();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        for (MapObject mo : mapObjects) {
            nm.sendMessageOnTCP("Item", mo.getCharacteristics());
        }
    }
}
