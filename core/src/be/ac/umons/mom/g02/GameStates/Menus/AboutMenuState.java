package be.ac.umons.mom.g02.GameStates.Menus;

import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;

public class AboutMenuState extends MenuState {

    /**
     * @param gsm The game's state manager
     * @param gim The game's input manager
     * @param gs The graphical settings
     */
    public AboutMenuState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }

    @Override
    public void init() {
        super.init();
        transparentBackground = false;
        handleEscape = true;
        topMargin = .1;
        setMenuItems(new MenuItem[]{
                new MenuItem(gs.getStringFromId("about"), MenuItemType.Title),
                new MenuItem(gs.getStringFromId("aboutText"), MenuItemType.Text),
                new MenuItem(gs.getStringFromId("quit"), MenuItemType.Button, () -> gsm.removeFirstState())
        });
    }
}
