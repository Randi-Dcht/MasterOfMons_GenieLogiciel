package be.ac.umons.sgl.mom.GameStates;

import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameMapManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * L'état de chargement avant de lancer l'état de jeu. Il charge toutes les images nécessaires aux bon déroulement du jeu.
 * @author Guillaume Cardoen
 */
public class LoadingState extends GameState {
    /**
     * La marge des cercles par rapport au bord gauche (et droit) de l'écran.
     */
    protected static final int CIRCLE_MARGIN_X = MasterOfMonsGame.WIDTH / 15;

    /**
     * La marge des cercles par rapport au haut (et bas) de l'écran.
     */
    protected static final int CIRCLE_MARGIN_Y = MasterOfMonsGame.HEIGHT / 10;
    /**
     * La vitesse des cercles (en rad/s).
     */
    protected static final double CIRCLE_SPEED_RAD_SEC = Math.PI;

    /**
     * Utilisé afin de dessiner en autre le texte.
     */
    protected SpriteBatch sb;
    /**
     * Permet de dessiner les formes comme les rectangles.
     */
    protected ShapeRenderer sr;
    /**
     * Défini si tout les fichiers nécéssaires au jeu ont été chargés.
     */
    protected boolean assetsLoaded = false;
    /**
     * Défini si toutes les cartes nécéssaires au jeu ont été chargées.
     */
    protected boolean mapsLoaded = false;

    /**
     * L'angle actuel des cercles.
     */
    protected double actualAngle = 0;

    /**
     * Le gestionnaire de carte du jeu.
     */
    protected GameMapManager gmm;
    /**
     * Le gestionnaire de ressources du jeu.
     */
    protected AssetManager am;

    /**
     * Initialise un nouvel état de chargement.
     * @param gsm Le GameStateManager du jeu.
     * @param gim Le GameInputManager du jeu.
     * @param gs Les paramètres graphiques à utiliser.
     */
    public LoadingState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }
    protected LoadingState(){}

    @Override
    public void init() {
        super.init();
        sb = new SpriteBatch();
        sr = new ShapeRenderer();
        gmm = gsm.getGameMapManager();
        am = gs.getAssetManager();
    }

    @Override
    public void update(float dt) {
        actualAngle = (actualAngle + CIRCLE_SPEED_RAD_SEC * dt) % (2 * Math.PI);
    }

    @Override
    public void draw() {
        sb.begin();
        String txt = "Loading...";
        gs.getTitleFont().draw(sb, txt, MasterOfMonsGame.WIDTH / 2 - gs.getTitleFont().getXHeight() * txt.length() / 2, MasterOfMonsGame.HEIGHT / 2 + gs.getTitleFont().getLineHeight() / 2);
        sb.end();

        int fromCenterX = (int)((gs.getTitleFont().getXHeight() * txt.length() / 2 + CIRCLE_MARGIN_X) * Math.cos(actualAngle));
        int fromCenterY = (int)((gs.getTitleFont().getLineHeight() / 2 + CIRCLE_MARGIN_Y) * Math.sin(actualAngle));

        float progress = (float)((am.getProgress() + gmm.getProgress()) / 2);

        if (assetsLoaded) {
            mapsLoaded = gmm.loadNextMap();
            if (mapsLoaded) {
                sr.end();
                gsm.setState(PlayingState.class, true);
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

    @Override
    public void handleInput() {}

    @Override
    public void dispose() {
        sr.dispose();
        sb.dispose();
    }
}
