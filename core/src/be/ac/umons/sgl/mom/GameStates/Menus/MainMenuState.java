package be.ac.umons.sgl.mom.GameStates.Menus;

import be.ac.umons.sgl.mom.GameStates.LoadingState;
import be.ac.umons.sgl.mom.GraphicalObjects.Controls.ExtensionsSelector;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameMapManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import be.ac.umons.sgl.mom.Objects.LoadFile;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.awt.*;

/**
 * The first menu presented in the game. It allow to start a party in other things.
 * @author Guillaume Cardoen
 */
public class MainMenuState extends MenuState {

    /**
     * Allows to activate/deactivate an extension.
     */
    ExtensionsSelector extSel;

    /**
     * @param gsm The game's state manager
     * @param gim The game's input manager
     * @param gs The game's graphical settings.
     */
    public MainMenuState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }

    /**
     * The default constructor. USE IT ONLY FOR TESTS.
     */
    protected MainMenuState(){}

    @Override
    public void init() {
        super.init();
        topMargin = .1;
        handleEscape = false;
        leftMargin = .05 * MasterOfMonsGame.WIDTH;
        setMenuItems(new MenuItem[] { new MenuItem(gs.getStringFromId("gameName"), MenuItemType.Title),
                new MenuItem(gs.getStringFromId("newGame"), () -> {
                    extSel.generateLoadLists();
                    for (FileHandle f : Gdx.files.internal("Tmx/").list())
                        if (f.file().getName().equals("Umons_Nimy.tmx")) // TODO : Remove the cond.
                            GameMapManager.getInstance().addMapsToLoad(f.path());
                    GameMapManager.getInstance().addMapsToLoad(extSel.getMapsToLoad().toArray(new String[0]));
                    gs.addFilesToLoad(extSel.getFilesToLoad().toArray(new LoadFile[0]));
                    gsm.setState(LoadingState.class, true);
                }),
                new MenuItem(gs.getStringFromId("load"), MenuItemType.Button, () -> gsm.setState(LoadMenuState.class)),
                new MenuItem(gs.getStringFromId("settings"), MenuItemType.Button, () -> gsm.setState(SettingsMenuState.class)),
                new MenuItem(gs.getStringFromId("quit"), MenuItemType.Button, () -> Gdx.app.exit())});
        extSel = new ExtensionsSelector(gim, gs);
    }

    @Override
    public void draw() {
        super.draw();
        extSel.draw(sb, new Point(MasterOfMonsGame.WIDTH / 3 * 2, MasterOfMonsGame.HEIGHT / 3 - 2 * (int)topMargin), new Point(MasterOfMonsGame.WIDTH / 3 - 2 * (int)leftMargin, MasterOfMonsGame.HEIGHT / 3 - 2 * (int)topMargin));
    }

    @Override
    public void handleInput() {
        super.handleInput();
        extSel.handleInput();
    }

    @Override
    public void getFocus() {
        init();
    }
}
