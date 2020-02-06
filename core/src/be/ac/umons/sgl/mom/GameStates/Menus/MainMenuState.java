package be.ac.umons.sgl.mom.GameStates.Menus;

import be.ac.umons.sgl.mom.GameStates.LoadState;
import be.ac.umons.sgl.mom.GameStates.LoadingState;
import be.ac.umons.sgl.mom.GameStates.SettingsState;
import be.ac.umons.sgl.mom.GraphicalObjects.Controls.ExtensionsSelector;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import be.ac.umons.sgl.mom.Objects.LoadFile;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.awt.*;

/***
 * Le menu qui s'affiche en premier lors du lancement du jeu. Il permettra, en autre, de lancer le jeu (via une sauvegarde ou non), d'activer/désactiver une extension, ainsi que de quitter le jeu.
 * @author Guillaume Cardoen
 */
public class MainMenuState extends MenuState {

    ExtensionsSelector extSel;

    /***
     * Initialise un nouveau menu.
     * @param gsm Le GameStateManager du jeu.
     * @param gim Le GameInputManager du jeu.
     * @param gs Les paramètres graphiques à utiliser.
     */
    public MainMenuState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }
    protected MainMenuState(){}

    /***
     * Initialise le menu.
     */
    @Override
    public void init() {
        super.init();
        topMargin = .1;
        setMenuItems(new MenuItem[] { new MenuItem(gs.getStringFromId("gameName"), MenuItemType.Title, false),
                new MenuItem(gs.getStringFromId("newGame"), () -> {
                    extSel.generateLoadLists();
                    for (FileHandle f : Gdx.files.internal("Tmx/").list())
                        if (f.file().getName().equals("Umons_Nimy.tmx")) // TODO : Remove the cond.
                            gsm.getGameMapManager().addMapsToLoad(f.path());
                    gsm.getGameMapManager().addMapsToLoad(extSel.getMapsToLoad().toArray(new String[0]));
                    gs.addFilesToLoad(extSel.getFilesToLoad().toArray(new LoadFile[0]));
                    gsm.setState(LoadingState.class, true);
                }),
                new MenuItem(gs.getStringFromId("load"), () -> gsm.setState(LoadState.class)),
                new MenuItem(gs.getStringFromId("settings"), () -> gsm.setState(SettingsState.class)),
                new MenuItem(gs.getStringFromId("quit"), () -> Gdx.app.exit())});
        extSel = new ExtensionsSelector(gim, gs);
    }

    @Override
    public void draw() {
        super.draw();
        extSel.draw(sb, new Point(MasterOfMonsGame.WIDTH / 3 * 2, MasterOfMonsGame.HEIGHT / 3 * 2), new Point(MasterOfMonsGame.WIDTH / 3 - 2 * (int)leftMargin, MasterOfMonsGame.HEIGHT / 3 - 2 * (int)topMargin));
    }

    @Override
    public void handleInput() {
        super.handleInput();
        extSel.handleInput();
    }
}
