package be.ac.umons.mom.g02.Extensions.LAN.GameStates.Menus;

import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.GameStates.Dialogs.OutGameDialogState;
import be.ac.umons.mom.g02.GameStates.LoadingState;
import be.ac.umons.mom.g02.GameStates.Menus.MenuState;
import be.ac.umons.mom.g02.GraphicalObjects.Controls.TextBox;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.*;
import be.ac.umons.mom.g02.Managers.ExtensionsManager;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.MasterOfMonsGame;
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
    protected NetworkManager nm;
    /**
     * The <code>TextBox</code>'s MenuItem where the user put the server's name.
     */
    protected TextBoxMenuItem TXT_ServerName;
    /**
     * If we must send the player's characteristics to the second player
     */
    protected boolean sendPlayer;

    /**
     * @param gs The game's graphical settings.
     */
    public WaitingRoomState(GraphicalSettings gs) {
        super(gs);
    }

    @Override
    public void init() {
        super.init();

        try {
            if (ExtensionsManager.getInstance().getExtensionsMap().get("Dual").activated)
                nm = be.ac.umons.mom.g02.Extensions.DualLAN.Managers.NetworkManager.getInstance();
            else
                nm = NetworkManager.getInstance();
            nm.acceptConnection();
            nm.setOnMagicNumberSent((magicNumber) -> {
                OutGameDialogState ogds = (OutGameDialogState) gsm.setState(OutGameDialogState.class);
                ogds.setText(String.format(gs.getStringFromId("magicNumber"), magicNumber));
                ogds.addAnswer("OK");
            });
            nm.setOnConnected(() -> {
                FinalisingConnectionState fcs = (FinalisingConnectionState) gsm.removeAllStateAndAdd(FinalisingConnectionState.class);
                fcs.setSendPlayer(sendPlayer);
            });
        } catch (SocketException e) {
            e.printStackTrace();
            return;
        }

        transparentBackground = false;
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new TitleMenuItem(gs, gs.getStringFromId("waitingConnection")));
        menuItems.add(new TextMenuItem(gs, gs.getStringFromId("servInfo")));
        menuItems.add(TXT_ServerName =
                new TextBoxMenuItem(gim, gs, gs.getStringFromId("servName")));
        menuItems.add(new ButtonMenuItem(gim, gs, gs.getStringFromId("setName"),
                () -> nm.startBroadcastingMessage(
                    TXT_ServerName.getControl().getText()
        )));

        for (InetAddress ia : nm.getAddressToBroadcast().keySet()) {
            menuItems.add(new TextMenuItem(gs, "IP : " + ia.toString().replace("/", "")));
        }
        menuItems.add(new TextMenuItem(gs, gs.getStringFromId("multiplesIPInfo")));

        setMenuItems(menuItems.toArray(new MenuItem[0]));
    }

    @Override
    public void dispose() {
        super.dispose();
        nm.stopBroadcastingServerInfo();
    }

    /**
     * @param sendPlayer If we must send the player's characteristics to the second player
     */
    public void setSendPlayer(boolean sendPlayer) {
        this.sendPlayer = sendPlayer;
    }
}
