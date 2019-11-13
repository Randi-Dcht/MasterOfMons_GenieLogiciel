package be.ac.umons.sgl.mom.GameStates;

import be.ac.umons.sgl.mom.Enums.GameStates;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * L'état de chargement avant de lancer l'état de jeu. Il charge toutes les images nécessaires aux bon déroulement du jeu.
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
     * L'angle actuel des cercles.
     */
    protected double actualAngle = 0;

    /**
     * Initialise un nouvel état de chargement.
     * @param gsm Le GameStateManager du jeu.
     * @param gim Le GameInputManager du jeu.
     * @param gs Les paramètres graphiques à utiliser.
     */
    public LoadingState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }

    @Override
    public void init() {
        super.init();
        sb = new SpriteBatch();
        sr = new ShapeRenderer();
    }

    @Override
    public void update(float dt) {
        actualAngle += (actualAngle + CIRCLE_SPEED_RAD_SEC * dt) % (2 * Math.PI);
    }

    @Override
    public void draw() {
        sb.begin();
        String txt = "Loading...";
        gs.getTitleFont().draw(sb, txt, MasterOfMonsGame.WIDTH / 2 - gs.getTitleFont().getXHeight() * txt.length() / 2, MasterOfMonsGame.HEIGHT / 2 + gs.getTitleFont().getLineHeight() / 2);
        sb.end();

        int fromCenterX = (int)((gs.getTitleFont().getXHeight() * txt.length() / 2 + CIRCLE_MARGIN_X) * Math.cos(actualAngle));
        int fromCenterY = (int)((gs.getTitleFont().getLineHeight() / 2 + CIRCLE_MARGIN_Y) * Math.sin(actualAngle));

        assetsLoaded = gs.getAssetManager().update();
        if (assetsLoaded)
            gsm.setState(GameStates.Play);

        float progress = gs.getAssetManager().getProgress();
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
