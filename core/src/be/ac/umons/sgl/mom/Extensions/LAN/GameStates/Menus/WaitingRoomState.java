package be.ac.umons.sgl.mom.Extensions.LAN.GameStates.Menus;

import be.ac.umons.sgl.mom.GameStates.Menus.MenuState;

public class WaitingRoomState extends MenuState {

    @Override
    public void init() {
        super.init();
        topMargin = .1;
        transparentBackground = true;
        setMenuItems(new MenuItem[]{
                new MenuItem(gs.getStringFromId("waitingConnection"), MenuItemType.Title),
                new MenuItem(gs.getStringFromId("servInfo"), MenuItemType.Text),
                new MenuItem(gs.getStringFromId("IP : "), MenuItemType.Text), // TODO
                new MenuItem(gs.getStringFromId("Port :"), MenuItemType.Text), // TODO
        });
    }
}
