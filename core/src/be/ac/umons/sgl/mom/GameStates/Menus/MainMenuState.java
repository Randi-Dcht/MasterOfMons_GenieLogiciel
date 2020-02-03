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
        betweenItemMargin = .01;
        menuItems = new MenuItem[] { new MenuItem("Master Of Mons", MenuItemType.Title, false),
                new MenuItem("Start a new game", () -> {
                    extSel.generateLoadLists();
                    gsm.getGameMapManager().addMapsToLoad("Tmx/NimyTest.tmx");
                    gsm.getGameMapManager().addMapsToLoad(extSel.getMapsToLoad().toArray(new String[0]));
                    gs.addFilesToLoad(extSel.getFilesToLoad().toArray(new LoadFile[0]));
                    gsm.setState(LoadingState.class, true);
                }),
                new MenuItem("Load", () -> gsm.setState(LoadState.class)),
                new MenuItem("Settings", () -> gsm.setState(SettingsState.class)),
                new MenuItem("Quit", () -> Gdx.app.exit())};
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
