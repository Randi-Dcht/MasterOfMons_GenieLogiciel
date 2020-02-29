package be.ac.umons.sgl.mom.Extensions.LAN.GameStates.Menus;

import be.ac.umons.sgl.mom.GameStates.Menus.MenuState;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;

public class WaitingOrConnectionState extends MenuState {

    /**
     * @param gsm The game's state manager
     * @param gim The game's input manager
     * @param gs The game's graphical settings.
     */
    public WaitingOrConnectionState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }

    @Override
    public void init() {
        super.init();
        topMargin = .1;
        setMenuItems(new MenuItem[]{
                new MenuItem(gs.getStringFromId("chooseAState"), MenuItemType.Title),
                new MenuItem(gs.getStringFromId("waitingRoom"), MenuItemType.Button, () -> gsm.setState(WaitingRoomState.class, true)),
                new MenuItem(gs.getStringFromId("connectionRoom"), MenuItemType.Button, () -> gsm.setState(ConnectionRoomState.class, true)),
        });
    }
}
