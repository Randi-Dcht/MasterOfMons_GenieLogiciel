package be.ac.umons.mom.g02.GameStates.Menus;

import be.ac.umons.mom.g02.GameStates.Dialogs.OutGameDialogState;
import be.ac.umons.mom.g02.GameStates.GameState;
import be.ac.umons.mom.g02.GameStates.PlayingState;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/***
 * A menu representing the pause in the game. It allows to change settings, save, ...
 * @author Guillaume Cardoen
 */
public class InGameMenuState extends MenuState {

    /**
     * Allow to draw shapes.
     */
    protected ShapeRenderer sr;

    /**
     * @param gsm The game's state manager
     * @param gim The game's input manager
     * @param gs The game's graphical settings.
     */
    public InGameMenuState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }


    @Override
    public void init() {
        super.init();
        sr = new ShapeRenderer();
        sr.setProjectionMatrix(sb.getProjectionMatrix());
        sr.setAutoShapeType(true);
        topMargin = .1;
        transparentBackground = true;
        setMenuItems(new MenuItem[] { new MenuItem(gs.getStringFromId("gameName"), MenuItemType.Title),
                new MenuItem(gs.getStringFromId("continue"), MenuItemType.Button, () -> gsm.removeFirstState()),
                new MenuItem(gs.getStringFromId("player"), MenuItemType.Button, () -> gsm.setState(LevelUpMenuState.class)),
                new MenuItem(gs.getStringFromId("save"), MenuItemType.Button, () -> gsm.setState(SaveMenuState.class)),
                new MenuItem(gs.getStringFromId("load"), MenuItemType.Button, () -> gsm.setState(LoadMenuState.class)),
                new MenuItem(gs.getStringFromId("quickSave"), MenuItemType.Button, PlayingState::quickSave),
                new MenuItem(gs.getStringFromId("quickLoad"), MenuItemType.Button, PlayingState::quickLoad),
                new MenuItem(gs.getStringFromId("settings"), MenuItemType.Button, () -> gsm.setState(SettingsMenuState.class)),
                new MenuItem(gs.getStringFromId("quit"), MenuItemType.Button, this::exit)});
    }

    @Override
    public void dispose() {
        super.dispose();
        sr.dispose();
    }

    @Override
    public void handleInput() {
        super.handleInput();
    }

    public void exit() {
        GameState g = gsm.setState(OutGameDialogState.class);
        ((OutGameDialogState)g).setText(gs.getStringFromId("sureQuitGame"));
        ((OutGameDialogState)g).addAnswer(gs.getStringFromId("yes"), () -> Gdx.app.exit());
        ((OutGameDialogState)g).addAnswer(gs.getStringFromId("no"), () -> gsm.removeFirstState());
    }
}
