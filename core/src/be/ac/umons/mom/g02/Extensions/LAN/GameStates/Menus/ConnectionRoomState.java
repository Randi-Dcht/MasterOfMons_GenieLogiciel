package be.ac.umons.mom.g02.Extensions.LAN.GameStates.Menus;

import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.Extensions.LAN.Objects.ServerInfo;
import be.ac.umons.mom.g02.GameStates.Dialogs.OutGameDialogState;
import be.ac.umons.mom.g02.GameStates.Menus.MenuState;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.*;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represent the state where the user can wait the detection of a server or enter the server's informations.
 */
public class ConnectionRoomState extends MenuState {

    /**
     * The network manager
     */
    protected NetworkManager nm;

    /**
     * If we must send the player's characteristics to the second player
     */
    protected boolean sendPlayer;

    /**
     * @param gs The game's graphical settings.
     */
    public ConnectionRoomState(GraphicalSettings gs) {
        super(gs);
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
                ogds.setText(GraphicalSettings.getStringFromId("chooseTheMagicNumber"));
                ogds.addNonInternationalizedAnswer("" + i, () -> checkMagicNumber(i));
                ogds.addNonInternationalizedAnswer("" + j, () -> checkMagicNumber(j));
                ogds.addNonInternationalizedAnswer("" + k, () -> checkMagicNumber(k));
                ogds.addNonInternationalizedAnswer("" + l, () -> checkMagicNumber(l));
                ogds.addNonInternationalizedAnswer("" + m, () -> checkMagicNumber(m));
            });
            nm.setOnConnected(() -> {
                FinalisingConnectionState fcs = (FinalisingConnectionState) gsm.removeAllStateAndAdd(FinalisingConnectionState.class);
                fcs.setSendPlayer(sendPlayer);
            });
            nm.setOnWrongMagicNumber(() -> {
                OutGameDialogState ogds = (OutGameDialogState) gsm.setState(OutGameDialogState.class);
                ogds.setText(GraphicalSettings.getStringFromId("wrongOne"));
                ogds.addAnswer("OK");
            });

            FinalisingConnectionState.setNetworkManagerRunnable();

        } catch (SocketException e) {
            e.printStackTrace();
            return;
        }

        transparentBackground = false;

        refresh();
    }

    /**
     * Refresh the state with the detected server.
     */
    protected void refresh() {
        TextBoxMenuItem tbmi;
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new TitleMenuItem(gs, GraphicalSettings.getStringFromId("automaticDetect")));
        for (ServerInfo si : nm.getDetectedServers()) {
            menuItems.add(new ButtonMenuItem(gim, gs,
                    String.format("%s (%s)", si.getName(), si.getIp().toString().replace("/", "")),
                    () -> connectToServer(si)));
        }
        menuItems.add(new TitleMenuItem(gs, GraphicalSettings.getStringFromId("enterServerInfo")));
        menuItems.add(new TextMenuItem(gs, GraphicalSettings.getStringFromId("servInfo")));
        menuItems.add(tbmi = new TextBoxMenuItem(gim, gs, "IP : ", "TXT_IP"));
        menuItems.add(new ButtonMenuItem(gim, gs, GraphicalSettings.getStringFromId("connect"), () -> manualConnect(tbmi.getControl().getText())));
        setMenuItems(menuItems.toArray(new MenuItem[0]));
    }

    protected void manualConnect(String ip) {
        for (InetAddress ia : nm.getAddressToBroadcast().keySet()) {
            if (ia.toString().contains(ip)) {
                onIncorrectIP();
                return;
            }
        }
        if (ip.equals(""))
            onIncorrectIP();

        try {
            connectToServer(
                    new ServerInfo(InetAddress.getByName(ip))
            );
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    protected void onIncorrectIP() {
        OutGameDialogState ogds = (OutGameDialogState) gsm.setStateWithoutAnimation(OutGameDialogState.class);
        ogds.setText(GraphicalSettings.getStringFromId("ipIncorrect"));
        ogds.addAnswer("OK");
    }

    /**
     * Try to connect to a server and go to <code>FinalisingConnectionState</code>
     * @param serverInfo The server's informations on which the connection should be established.
     */
    protected void connectToServer(ServerInfo serverInfo) {
        nm.selectAServer(serverInfo);
        nm.tryToConnect();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    /**
     * Check if the chosen magic number is the good one or not
     * @param magicNumber The chosen magic number
     */
    protected void checkMagicNumber(int magicNumber) {
        if (! nm.checkMagicNumber(magicNumber)) {
            OutGameDialogState ogds = (OutGameDialogState) gsm.setState(OutGameDialogState.class);
            ogds.setText("");
        }
    }

    /**
     * @param sendPlayer If we must send the player's characteristics to the second player
     */
    public void setSendPlayer(boolean sendPlayer) {
        this.sendPlayer = sendPlayer;
    }
}
