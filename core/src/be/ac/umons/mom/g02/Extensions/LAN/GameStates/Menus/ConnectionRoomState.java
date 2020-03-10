package be.ac.umons.mom.g02.Extensions.LAN.GameStates.Menus;

import be.ac.umons.mom.g02.Regulator.SuperviserNormally;
import be.ac.umons.mom.g02.Extensions.LAN.GameStates.PlayingState;
import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.Extensions.LAN.Objects.ServerInfo;
import be.ac.umons.mom.g02.GameStates.LoadingState;
import be.ac.umons.mom.g02.GameStates.Menus.MenuState;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionRoomState extends MenuState {

    NetworkManager nm;

    /**
     * @param gsm The game's state manager
     * @param gim The game's input manager
     * @param gs The game's graphical settings.
     */
    public ConnectionRoomState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }

    @Override
    public void init() {
        super.init();

        try {
            nm = NetworkManager.getInstance();
            nm.startListeningForServer();
            nm.setOnServerDetected(this::refresh);
            nm.setOnConnected(() -> {
                gsm.removeAllStateAndAdd(FinalisingConnectionState.class);
            });
        } catch (SocketException e) {
            e.printStackTrace();
            return;
        }

        transparentBackground = false;
        setMenuItems(new MenuItem[]{
                new MenuItem(gs.getStringFromId("automaticDetect"), MenuItemType.Title),
                new MenuItem(gs.getStringFromId("enterServerInfo"), MenuItemType.Title),
                new MenuItem(gs.getStringFromId("servInfo"), MenuItemType.Text),
                new MenuItem("IP : ", MenuItemType.TextBox, "TXT_IP"),
        });
    }

    protected void refresh() {
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(gs.getStringFromId("automaticDetect"), MenuItemType.Title));
        for (ServerInfo si : nm.getDetectedServers()) {
            menuItems.add(new MenuItem(
                    String.format("%s (%s)", si.getName(), si.getIp().toString().replace("/", "")),
                    MenuItemType.Button, () -> connectToServer(si)));
        }
        menuItems.add(new MenuItem(gs.getStringFromId("enterServerInfo"), MenuItemType.Title));
        menuItems.add(new MenuItem(gs.getStringFromId("servInfo"), MenuItemType.Text));
        menuItems.add(new MenuItem("IP : ", MenuItemType.TextBox, "TXT_IP"));
        setMenuItems(menuItems.toArray(new MenuItem[0]));
    }

    protected void connectToServer(ServerInfo serverInfo) {
        nm.selectAServer(serverInfo);
        nm.tryToConnect();
        gsm.removeAllStateAndAdd(FinalisingConnectionState.class);
    }
}
