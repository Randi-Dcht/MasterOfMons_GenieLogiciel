package be.ac.umons.sgl.mom.GameStates.Menus;

import be.ac.umons.sgl.mom.Events.SuperviserNormally;
import be.ac.umons.sgl.mom.GameStates.Dialogs.OutGameDialogState;
import be.ac.umons.sgl.mom.GameStates.GameState;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import be.ac.umons.sgl.mom.Objects.Saving;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

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
                new MenuItem(gs.getStringFromId("quickSave"), MenuItemType.Button, this::quickSave),
                new MenuItem(gs.getStringFromId("quickLoad"), MenuItemType.Button, this::quickLoad),
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

    protected void quickSave() {
        String name = String.format("MOM QS - %s", new SimpleDateFormat("dd/MM/yy HH:mm:ss").format(new Date()));
        String newName = getNonExistingFilePath(name);
        Saving save = SuperviserNormally.getSupervisor().getSave();
        save.setNameSave(newName);
        save.signal();
        MasterOfMonsGame.settings.setLastSavePath(newName);
    }

    protected void quickLoad() {
        String path = MasterOfMonsGame.settings.getLastSavePath();
        // TODO : Call load system with last save (automatic or not).
    }

    private String getNonExistingFilePath(String name) {
        String newName = name;
        int i = 1;
        while (new File(new File(".").getAbsoluteFile().getParent(), newName + ".mom").exists()) {
            newName = String.format("%s(%d)", name, i);
            i++;
        }
        return newName;
    }
}
