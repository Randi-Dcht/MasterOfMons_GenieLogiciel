package be.ac.umons.mom.g02.Extensions.LAN.GameStates.Menus;

import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.GameStates.Menus.MenuState;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;

import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class WaitingRoomState extends MenuState {

    NetworkManager nm;

    /**
     * @param gsm The game's state manager
     * @param gim The game's input manager
     * @param gs The game's graphical settings.
     */
    public WaitingRoomState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }

    @Override
    public void init() {
        super.init();

        try {
            nm = NetworkManager.getInstance();
            nm.startBroadcastingMessage("MOMServer/");
        } catch (SocketException e) {
            e.printStackTrace();
            return;
        }

        topMargin = .1;
        transparentBackground = false;
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(gs.getStringFromId("waitingConnection"), MenuItemType.Title));
        menuItems.add(new MenuItem(gs.getStringFromId("servInfo"), MenuItemType.Text));

        for (InetAddress ia : nm.getAddressToBroadcast().keySet()) {
            menuItems.add(new MenuItem("IP : " + ia.toString().replace("/", ""), MenuItemType.Text));
        }
        menuItems.add(new MenuItem(gs.getStringFromId("multiplesIPInfo"), MenuItemType.Text));

        setMenuItems(menuItems.toArray(new MenuItem[0]));
    }
}
