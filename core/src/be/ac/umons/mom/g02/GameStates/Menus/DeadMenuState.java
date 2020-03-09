package be.ac.umons.mom.g02.GameStates.Menus;

import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;

public class DeadMenuState extends MenuState {

    /**
     * @param gsm The game's state manager
     * @param gim The game's input manager
     * @param gs The game's graphical settings
     */
    public DeadMenuState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }

    @Override
    public void init() {
        super.init();
        transparentBackground = true;
        handleEscape = false;
        setMenuItems(new MenuItem[] {
                new MenuItem(gs.getStringFromId("dead"), MenuItemType.Title),
                new MenuItem(gs.getStringFromId("loadPreviousGame"), MenuItemType.Button, () -> gsm.setState(LoadMenuState.class)),
                new MenuItem(gs.getStringFromId("gpBackMainMenu"), MenuItemType.Button, () -> gsm.removeAllStateAndAdd(MainMenuState.class)),
                new MenuItem(gs.getStringFromId("quitTheGame"), MenuItemType.Button, () -> Gdx.app.exit())
        });
    }
}
