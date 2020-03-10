package be.ac.umons.mom.g02.Extensions.LAN.GameStates.Menus;

import be.ac.umons.mom.g02.Regulator.SuperviserNormally;
import be.ac.umons.mom.g02.Extensions.LAN.GameStates.PlayingState;
import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.GameStates.LoadingState;
import be.ac.umons.mom.g02.GameStates.Menus.MenuState;
import be.ac.umons.mom.g02.GraphicalObjects.Controls.TextBox;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;

import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class WaitingRoomState extends MenuState {

    NetworkManager nm;
    MenuItem TXT_ServerName;

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
            nm.startListeningForServer(); // Only for MOMConnect message
            nm.acceptConnection();
            nm.setOnConnected(() -> {
                gsm.removeAllStateAndAdd(FinalisingConnectionState.class);
            });
        } catch (SocketException e) {
            e.printStackTrace();
            return;
        }

        transparentBackground = false;
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(gs.getStringFromId("waitingConnection"), MenuItemType.Title));
        menuItems.add(new MenuItem(gs.getStringFromId("servInfo"), MenuItemType.Text));
        menuItems.add(TXT_ServerName = new MenuItem(gs.getStringFromId("servName"), MenuItemType.TextBox));
        menuItems.add(new MenuItem(gs.getStringFromId("setName"), MenuItemType.Button, () -> nm.startBroadcastingMessage(
                ((TextBox)TXT_ServerName.control).getText()
        )));

        for (InetAddress ia : nm.getAddressToBroadcast().keySet()) {
            menuItems.add(new MenuItem("IP : " + ia.toString().replace("/", ""), MenuItemType.Text));
        }
        menuItems.add(new MenuItem(gs.getStringFromId("multiplesIPInfo"), MenuItemType.Text));

        setMenuItems(menuItems.toArray(new MenuItem[0]));
    }
}
