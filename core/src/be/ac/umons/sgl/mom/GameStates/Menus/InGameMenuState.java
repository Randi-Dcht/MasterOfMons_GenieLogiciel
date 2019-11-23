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
 * Un menu "in-game". Il permet entre autre de continuer le jeu, d'accéder aux paramètres, de sauvegarder, de charger une partie, ...
 * @author Guillaume Cardoen
 */
public class InGameMenuState extends MenuState {

    /***
     * Permet de dessiner les formes comme les rectangles.
     */
    protected ShapeRenderer sr;

    /***
     * Initialise un menu "in-game".
     * @param gsm Le GameStateManager du jeu.
     * @param gim Le GameInputManager du jeu.
     * @param gs Les paramètres graphiques à utiliser.
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
        betweenItemMargin = .01;
        menuItems = new MenuItem[] { new MenuItem("Master Of Mons", MenuItemType.Title, false),
                new MenuItem("Continue", () -> gsm.removeFirstState()),
                new MenuItem("Save", () -> gsm.setState(SaveState.class)),
                new MenuItem("Load", () -> gsm.setState(LoadState.class)),
                new MenuItem("Quick Save"), // TODO : Call save system with default name.
                new MenuItem("Quick Load"), // TODO : Call load system with last save (automatic or not).
                new MenuItem("Settings", () -> gsm.setState(SettingsState.class)),
                new MenuItem("Quit", () -> Gdx.app.exit())};
    }


    /***
     * Dessine les éléments du menu avec un fond transparent.
     * Code was inspired by CNIAngel from gamedev.stackexchange.com (https://gamedev.stackexchange.com/a/67837)
     */
    @Override
    public void draw() {
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(21f / 255, 21f / 255, 21f / 255, .5f);
        sr.rect(0, 0, MasterOfMonsGame.WIDTH, MasterOfMonsGame.HEIGHT);
        sr.end();
        super.draw();
        Gdx.gl.glDisable(GL30.GL_BLEND);
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
