package be.ac.umons.mom.g02.Extensions.LAN.GameStates.Menus;

import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.GameStates.Dialogs.OutGameDialogState;
import be.ac.umons.mom.g02.GameStates.Menus.MenuState;
import be.ac.umons.mom.g02.GraphicalObjects.Controls.TextBox;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;

import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * The state where we wait for the second player to connect. (Broadcasting the information of this machine)
 */
public class WaitingRoomState extends MenuState {

    /**
     * The network manager
     */
    NetworkManager nm;
    /**
     * The <code>TextBox</code>'s MenuItem where the user put the server's name.
     */
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
            nm.acceptConnection();
            nm.setOnMagicNumberSent((magicNumber) -> {
                OutGameDialogState ogds = (OutGameDialogState) gsm.setState(OutGameDialogState.class);
                ogds.setText(String.format(gs.getStringFromId("magicNumber"), magicNumber));
                ogds.addAnswer("OK");
            });
            nm.setOnConnected(() -> {
                nm.startListeningForServer();
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

    @Override
    public void dispose() {
        super.dispose();
        nm.stopBroadcastingServerInfo();
    }
}
