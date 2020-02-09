package be.ac.umons.sgl.mom.GameStates.Menus;
import be.ac.umons.sgl.mom.Enums.KeyStatus;
import be.ac.umons.sgl.mom.GameStates.LoadState;
import be.ac.umons.sgl.mom.GameStates.SaveState;
import be.ac.umons.sgl.mom.GameStates.SettingsState;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL30;
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
                new MenuItem(gs.getStringFromId("continue"), () -> gsm.removeFirstState()),
                new MenuItem(gs.getStringFromId("player"), () -> gsm.setState(PlayerMenuState.class)),
                new MenuItem(gs.getStringFromId("save"), () -> gsm.setState(SaveState.class)),
                new MenuItem(gs.getStringFromId("load"), () -> gsm.setState(LoadState.class)),
                new MenuItem(gs.getStringFromId("quickSave")), // TODO : Call save system with default name.
                new MenuItem(gs.getStringFromId("quickLoad")), // TODO : Call load system with last save (automatic or not).
                new MenuItem(gs.getStringFromId("settings"), () -> gsm.setState(SettingsState.class)),
                new MenuItem(gs.getStringFromId("quit"), () -> Gdx.app.exit())});
    }

    @Override
    public void dispose() {
        super.dispose();
        sr.dispose();
    }

    @Override
    public void handleInput() {
        super.handleInput();
        if (gim.isKey(Input.Keys.ESCAPE, KeyStatus.Pressed))
            gsm.removeFirstState();
    }
}
