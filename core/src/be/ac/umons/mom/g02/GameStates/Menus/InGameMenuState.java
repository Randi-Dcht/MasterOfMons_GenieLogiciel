package be.ac.umons.mom.g02.GameStates.Menus;

import be.ac.umons.mom.g02.GameStates.Dialogs.OutGameDialogState;
import be.ac.umons.mom.g02.GameStates.GameState;
import be.ac.umons.mom.g02.GameStates.PlayingState;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.ButtonMenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.MenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.TitleMenuItem;
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
     * The <code>PlayingState</code> that asked to in-game menu.
     */
    protected PlayingState ps;

    /**
     * @param gs The game's graphical settings.
     */
    public InGameMenuState(GraphicalSettings gs) {
        super(gs);
    }


    @Override
    public void init() {
        super.init();
        sr = new ShapeRenderer();
        sr.setProjectionMatrix(sb.getProjectionMatrix());
        sr.setAutoShapeType(true);
        transparentBackground = true;
        setMenuItems(new MenuItem[] {
                new TitleMenuItem(gs, GraphicalSettings.getStringFromId("gameName")),
                new ButtonMenuItem(gim, gs, GraphicalSettings.getStringFromId("continue"), () -> gsm.removeFirstState()),
                new ButtonMenuItem(gim, gs, GraphicalSettings.getStringFromId("levelUp"), () -> ps.goToLevelUpState()),
                new ButtonMenuItem(gim, gs, GraphicalSettings.getStringFromId("save"), () -> gsm.setState(SaveMenuState.class)),
                new ButtonMenuItem(gim, gs, GraphicalSettings.getStringFromId("load"), () -> gsm.setState(LoadMenuState.class)),
                new ButtonMenuItem(gim, gs, GraphicalSettings.getStringFromId("quickSave"), PlayingState::quickSave),
                new ButtonMenuItem(gim, gs, GraphicalSettings.getStringFromId("quickLoad"), () -> ps.quickLoad()),
                new ButtonMenuItem(gim, gs, GraphicalSettings.getStringFromId("settings"), () -> gsm.setState(SettingsMenuState.class)),
                new ButtonMenuItem(gim, gs, GraphicalSettings.getStringFromId("return"), this::goMainMenu),
                new ButtonMenuItem(gim, gs, GraphicalSettings.getStringFromId("quit"), this::exit)
        });
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

    /**
     * Executed when the "return" option is chosen
     */
    public void goMainMenu() {
        GameState g = gsm.setState(OutGameDialogState.class);
        ((OutGameDialogState)g).setText(GraphicalSettings.getStringFromId("sureQuitGame"));
        ((OutGameDialogState)g).addAnswer("yes", () -> gsm.removeAllStateAndAdd(MainMenuState.class));
        ((OutGameDialogState)g).addAnswer("no");
    }

    /**
     * Executed when the "exit" option is chosen
     */
    public void exit() {
        GameState g = gsm.setState(OutGameDialogState.class);
        ((OutGameDialogState)g).setText(GraphicalSettings.getStringFromId("sureQuitGame"));
        ((OutGameDialogState)g).addAnswer("yes", () -> Gdx.app.exit());
        ((OutGameDialogState)g).addAnswer("no");
    }

    /**
     * @param ps The <code>PlayingState</code> that asked to in-game menu.
     */
    public void setPlayingState(PlayingState ps) {
        this.ps = ps;
    }
}
