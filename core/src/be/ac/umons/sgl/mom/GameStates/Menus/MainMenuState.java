package be.ac.umons.sgl.mom.GameStates.Menus;

import be.ac.umons.sgl.mom.GameStates.LoadState;
import be.ac.umons.sgl.mom.GameStates.LoadingState;
import be.ac.umons.sgl.mom.GameStates.SettingsState;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;

/***
 * Le menu qui s'affiche en premier lors du lancement du jeu. Il permettra, en autre, de lancer le jeu (via une sauvegarde ou non), d'activer/désactiver une extension, ainsi que de quitter le jeu.
 * @author Guillaume Cardoen
 */
public class MainMenuState extends MenuState {

    /***
     * Initialise un nouveau menu.
     * @param gsm Le GameStateManager du jeu.
     * @param gim Le GameInputManager du jeu.
     * @param gs Les paramètres graphiques à utiliser.
     */
    public MainMenuState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }

    /***
     * Initialise le menu.
     */
    @Override
    public void init() {
        super.init();
        topMargin = .1;
        betweenItemMargin = .01;
        menuItems = new MenuItem[] { new MenuItem("Master Of Mons", MenuItemType.Title, false),
                new MenuItem("Start a new game"),
                new MenuItem("Load"),
                new MenuItem("Settings"),
                new MenuItem("Quit")};
    }


    @Override
    protected void executeSelectedItem() {
        switch (selectedItem) {
            case 1:
                gsm.getGameMapManager().addMapsToLoad("Map/isoTest.tmx");
                gsm.setState(LoadingState.class, true);
                break;
            case 2:
                gsm.setState(LoadState.class);
                break;
            case 3:
                gsm.setState(SettingsState.class);
                break;
            case 4:
                Gdx.app.exit();
                break;
            default:
                break;
        }
    }
}
