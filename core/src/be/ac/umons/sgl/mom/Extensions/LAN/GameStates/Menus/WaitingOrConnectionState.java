package be.ac.umons.sgl.mom.Extensions.LAN.GameStates.Menus;

import be.ac.umons.sgl.mom.GameStates.Menus.MenuState;

public class WaitingOrConnectionState extends MenuState {
    @Override
    public void init() {
        super.init();
        topMargin = .1;
        transparentBackground = true;
        setMenuItems(new MenuItem[]{
                new MenuItem(gs.getStringFromId("chooseAState"), MenuItemType.Title),
                new MenuItem(gs.getStringFromId("waitingRoom"), MenuItemType.Button, () -> gsm.setState(WaitingRoomState.class, true)),
                new MenuItem(gs.getStringFromId("connectionRoom"), MenuItemType.Button, () -> gsm.setState(ConnectionRoomState.class, true)),
        });
    }
}
