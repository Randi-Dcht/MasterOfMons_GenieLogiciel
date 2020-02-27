package be.ac.umons.sgl.mom.Extensions.LAN.GameStates.Menus;

import be.ac.umons.sgl.mom.GameStates.Menus.MenuState;

public class ConnectionRoomState extends MenuState {

    @Override
    public void init() {
        super.init();
        topMargin = .1;
        transparentBackground = true;
        setMenuItems(new MenuItem[]{
                new MenuItem(gs.getStringFromId("automaticDetect"), MenuItemType.Title),
                new MenuItem(gs.getStringFromId("enterServerInfo"), MenuItemType.Title),
                new MenuItem(gs.getStringFromId("servInfo"), MenuItemType.Text),
                new MenuItem(gs.getStringFromId("IP : "), MenuItemType.TextBox, "TXT_IP"),
                new MenuItem(gs.getStringFromId("Port :"), MenuItemType.NumberTextBox, "TXT_Port"),
        });
    }
}
