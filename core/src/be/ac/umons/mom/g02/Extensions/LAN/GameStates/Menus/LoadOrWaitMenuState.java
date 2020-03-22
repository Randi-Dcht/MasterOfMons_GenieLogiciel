package be.ac.umons.mom.g02.Extensions.LAN.GameStates.Menus;

import be.ac.umons.mom.g02.GameStates.Menus.LoadMenuState;
import be.ac.umons.mom.g02.GameStates.Menus.MenuState;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.ButtonMenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.MenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.TitleMenuItem;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;

/**
 * Represent the state where the player have to choose between loading an old game or connect to a server
 */
public class LoadOrWaitMenuState extends MenuState {

    /**
     * @param gsm The game's state manager
     * @param gim The game's input manager
     * @param gs The game's graphical settings.
     */
    public LoadOrWaitMenuState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }

    @Override
    public void init() {
        super.init();
        setMenuItems(new MenuItem[]{
                new TitleMenuItem(gs, gs.getStringFromId("chooseAState")),
                new ButtonMenuItem(gim, gs, gs.getStringFromId("load"), () -> gsm.setState(LoadMenuState.class)),
                new ButtonMenuItem(gim, gs, gs.getStringFromId("connectionRoom"), () -> gsm.setState(ConnectionRoomState.class)),
        });
    }

}
