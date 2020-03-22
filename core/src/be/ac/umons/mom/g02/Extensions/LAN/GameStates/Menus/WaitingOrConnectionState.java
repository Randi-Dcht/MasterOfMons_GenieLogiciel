package be.ac.umons.mom.g02.Extensions.LAN.GameStates.Menus;

import be.ac.umons.mom.g02.GameStates.Menus.MenuState;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.ButtonMenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.MenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.TitleMenuItem;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;

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
        setMenuItems(new MenuItem[]{
                new TitleMenuItem(gs, gs.getStringFromId("chooseAState")),
                new ButtonMenuItem(gim, gs, gs.getStringFromId("waitingRoom"), () -> {
                    WaitingRoomState wrs = (WaitingRoomState) gsm.setState(WaitingRoomState.class);
                    wrs.setSendPlayer(true);
                }),
                new ButtonMenuItem(gim, gs, gs.getStringFromId("connectionRoom"), () -> {
                    ConnectionRoomState crs = (ConnectionRoomState) gsm.setState(ConnectionRoomState.class);
                    crs.setSendPlayer(true);
                }),
        });
    }
}
