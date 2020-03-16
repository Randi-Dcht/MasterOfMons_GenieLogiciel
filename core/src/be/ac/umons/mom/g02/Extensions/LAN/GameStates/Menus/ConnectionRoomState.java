package be.ac.umons.mom.g02.Extensions.LAN.GameStates.Menus;

import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.Extensions.LAN.Objects.ServerInfo;
import be.ac.umons.mom.g02.GameStates.Dialogs.OutGameDialogState;
import be.ac.umons.mom.g02.GameStates.Menus.MenuState;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represent the state where the user can wait the detection of a server or enter the server's informations.
 */
public class ConnectionRoomState extends MenuState {

    /**
     * The network manager
     */
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
            nm.setOnMagicNumberReceived((i, j, k, l, m) -> {
                OutGameDialogState ogds = (OutGameDialogState) gsm.setState(OutGameDialogState.class);
                ogds.setText(gs.getStringFromId("chooseTheMagicNumber"));
                ogds.addNonInternationalizedAnswer("" + i, () -> checkMagicNumber(i));
                ogds.addNonInternationalizedAnswer("" + j, () -> checkMagicNumber(j));
                ogds.addNonInternationalizedAnswer("" + k, () -> checkMagicNumber(k));
                ogds.addNonInternationalizedAnswer("" + l, () -> checkMagicNumber(l));
                ogds.addNonInternationalizedAnswer("" + m, () -> checkMagicNumber(m));
            });
            nm.setOnConnected(() -> gsm.removeAllStateAndAdd(FinalisingConnectionState.class));
            nm.setOnWrongMagicNumber(() -> {
                OutGameDialogState ogds = (OutGameDialogState) gsm.setState(OutGameDialogState.class);
                ogds.setText(gs.getStringFromId("wrongOne"));
                ogds.addAnswer("OK");
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

    /**
     * Refresh the state with the detected server.
     */
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

    /**
     * Try to connect to a server and go to <code>FinalisingConnectionState</code>
     * @param serverInfo The server's informations on which the connection should be established.
     */
    protected void connectToServer(ServerInfo serverInfo) {
        nm.selectAServer(serverInfo);
        nm.tryToConnect();
        gsm.removeAllStateAndAdd(FinalisingConnectionState.class);
    }

    @Override
    public void dispose() {
        super.dispose();
    }


    protected void checkMagicNumber(int magicNumber) {
        if (! nm.checkMagicNumber(magicNumber)) {
            OutGameDialogState ogds = (OutGameDialogState) gsm.setState(OutGameDialogState.class);
            ogds.setText("");
        }
    }

}
