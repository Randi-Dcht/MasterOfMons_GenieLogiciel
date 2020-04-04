package be.ac.umons.mom.g02.GameStates.Menus;

import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.ButtonMenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.MenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.TitleMenuItem;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;

public class DeadMenuState extends MenuState {

    protected static String text;

    /**
     * @param gs The game's graphical settings
     */
    public DeadMenuState(GraphicalSettings gs) {
        super(gs);
        text = GraphicalSettings.getStringFromId("dead");
    }

    @Override
    public void init() {
        super.init();
        transparentBackground = true;
        handleEscape = false;
        setText(text);
    }

    public void setText(String text) {
        DeadMenuState.text = text;
        if (buttons == null) // Not initialised
            return;
        setMenuItems(new MenuItem[] {
                new TitleMenuItem(gs, text),
                new ButtonMenuItem(gim, gs, GraphicalSettings.getStringFromId("loadPreviousGame"), () -> gsm.setState(LoadMenuState.class)),
                new ButtonMenuItem(gim, gs, GraphicalSettings.getStringFromId("gpBackMainMenu"), () -> gsm.removeAllStateAndAdd(MainMenuState.class)),
                new ButtonMenuItem(gim, gs, GraphicalSettings.getStringFromId("quitTheGame"), () -> Gdx.app.exit())
        });
    }
}
