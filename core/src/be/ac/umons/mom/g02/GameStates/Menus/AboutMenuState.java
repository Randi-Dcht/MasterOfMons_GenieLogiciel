package be.ac.umons.mom.g02.GameStates.Menus;

import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.ButtonMenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.MenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.TextMenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.TitleMenuItem;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;

public class AboutMenuState extends MenuState {

    /**
     * @param gs The graphical settings
     */
    public AboutMenuState(GraphicalSettings gs) {
        super(gs);
    }

    @Override
    public void init() {
        super.init();
        transparentBackground = false;
        handleEscape = true;
        setMenuItems(new MenuItem[]{
                new TitleMenuItem(gs, gs.getStringFromId("about")),
                new TextMenuItem(gs, gs.getStringFromId("aboutText")),
                new ButtonMenuItem(gim, gs, gs.getStringFromId("quit"), () -> gsm.removeFirstState())
        });
    }
}
