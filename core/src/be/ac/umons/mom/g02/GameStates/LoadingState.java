package be.ac.umons.mom.g02.GameStates;

import be.ac.umons.mom.g02.GameStates.Menus.CreatePlayerMenuState;
import be.ac.umons.mom.g02.Managers.ExtensionsManager;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameMapManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Objects.LoadFile;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * The loading state where all resources are loaded for the further playing state.
 * @author Guillaume Cardoen
 */
public class LoadingState extends GameState {
    /**
     * The margin the circles needs to have horizontally.
     */
    protected static final int CIRCLE_MARGIN_X = MasterOfMonsGame.WIDTH / 15;

    /**
     * The margin the circles needs to have vertically.
     */
    protected static final int CIRCLE_MARGIN_Y = MasterOfMonsGame.HEIGHT / 10;
    /**
     * The circle's rotation's speed.
     */
    protected static final double CIRCLE_SPEED_RAD_SEC = Math.PI;

    /**
     * Allow to draw
     */
    protected SpriteBatch sb;
    /**
     * Allow to draw shapes.
     */
    protected ShapeRenderer sr;
    /**
     * Represent if all the necessary resources have been loaded.
     */
    protected boolean assetsLoaded = false;
    /**
     * Represent if all the necessary maps have been loaded.
     */
    protected boolean mapsLoaded = false;

    /**
     * The actual circle's angle.
     */
    protected double actualAngle = 0;

    /**
     * The game's map manager.
     */
    protected GameMapManager gmm;
    /**
     * Allow to load the resources.
     */
    protected AssetManager am;
    /**
     * The font to use.
     */
    protected BitmapFont font;
    /**
     * The state to launch when the loading is done.
     */
    protected Class<? extends GameState> afterLoadingState;

    private boolean changedCalled = false;

    /**
     * @param gsm The game's state manager
     * @param gim The game's input manager
     * @param gs The game's graphical settings
     */
    public LoadingState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
        afterLoadingState = PlayingState.class;
    }

    /**
     * Default constructor. USE IT ONLY FOR TEST.
     */
    protected LoadingState(){}

    @Override
    public void init() {
        super.init();
        sb = new SpriteBatch();
        sr = new ShapeRenderer();
        gmm = GameMapManager.getInstance();
        am = gs.getAssetManager();
        font = gs.getTitleFont();
        initGame();
    }

    @Override
    public void update(float dt) {
        actualAngle = (actualAngle + CIRCLE_SPEED_RAD_SEC * dt) % (2 * Math.PI);
    }

    @Override
    public void draw() {
        Gdx.gl.glClearColor(gs.getBackgroundColor().r,gs.getBackgroundColor().g,gs.getBackgroundColor().b,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.begin();
        String txt = gs.getStringFromId("loading");
        font.draw(sb, txt, MasterOfMonsGame.WIDTH / 2 - font.getXHeight() * txt.length() / 2, MasterOfMonsGame.HEIGHT / 2 + font.getLineHeight() / 2);
        sb.end();

        int fromCenterX = (int)((font.getXHeight() * txt.length() / 2 + CIRCLE_MARGIN_X) * Math.cos(actualAngle));
        int fromCenterY = (int)((font.getLineHeight() / 2 + CIRCLE_MARGIN_Y) * Math.sin(actualAngle));

        float progress = (float)((am.getProgress() + gmm.getProgress()) / 2);

        if (assetsLoaded) {
            mapsLoaded = gmm.loadNextMap();
            if (mapsLoaded) {
                sr.end();
                if (! changedCalled) {
                    Gdx.app.postRunnable(() ->
                            gsm.removeAllStateAndAdd(afterLoadingState));
                    changedCalled = true;
                }
                return;
            }
        } else
            assetsLoaded = am.update();

        sr.setColor(1 - 217f / 255 * progress, 1 - 113f / 255 * progress, 1 - 195f / 255 * progress, 1);

        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.circle(MasterOfMonsGame.WIDTH / 2 + fromCenterX, MasterOfMonsGame.HEIGHT / 2 + fromCenterY, 10);
        sr.circle(MasterOfMonsGame.WIDTH / 2 - fromCenterX, MasterOfMonsGame.HEIGHT / 2 - fromCenterY, 10);
        sr.end();
    }

    public void initGame() {
        ExtensionsManager em = ExtensionsManager.getInstance();
        em.generateLoadLists();
        for (FileHandle f : Gdx.files.internal("Tmx/").list())
            GameMapManager.getInstance().addMapsToLoad(f.path());
        GameMapManager.getInstance().addMapsToLoad(em.getMapsToLoad().toArray(new String[0]));
        gs.addFilesToLoad(em.getFilesToLoad().toArray(new LoadFile[0]));
    }

    @Override
    public void handleInput() {}

    @Override
    public void dispose() {
        sr.dispose();
        sb.dispose();
    }
    /**
     * @param state The state to launch when the loading is done.
     */
    public void setAfterLoadingState(Class<? extends GameState> state) {
        this.afterLoadingState = state;
    }
}
