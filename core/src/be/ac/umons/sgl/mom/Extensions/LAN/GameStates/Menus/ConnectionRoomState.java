package be.ac.umons.sgl.mom.Extensions.LAN.GameStates.Menus;

import be.ac.umons.sgl.mom.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.sgl.mom.GameStates.Menus.MenuState;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;

import java.net.SocketException;

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
        } catch (SocketException e) {
            e.printStackTrace();
            return;
        }

        topMargin = .1;
        transparentBackground = false;
        nm.startBroadcastingMessage("MOMServer/");
        setMenuItems(new MenuItem[]{
                new MenuItem(gs.getStringFromId("automaticDetect"), MenuItemType.Title),
                new MenuItem(gs.getStringFromId("enterServerInfo"), MenuItemType.Title),
                new MenuItem(gs.getStringFromId("servInfo"), MenuItemType.Text),
                new MenuItem("IP : ", MenuItemType.TextBox, "TXT_IP"),
                new MenuItem("Port :", MenuItemType.NumberTextBox, "TXT_Port"),
        });
    }
}
