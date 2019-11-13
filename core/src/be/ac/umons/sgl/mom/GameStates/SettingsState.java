package be.ac.umons.sgl.mom.GameStates;

import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * L'état où l'utilisateur peut choisir les différents paramètres du jeu.
 */
public class SettingsState extends GameState {

    /**
     * Utilisé afin de dessiner en autre le texte.
     */
    protected SpriteBatch sb;
    /**
     * Permet de dessiner les formes comme les rectangles.
     */
    protected ShapeRenderer sr;



    public SettingsState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
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

    }

    @Override
    public void draw() {
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(21f / 255, 21f / 255, 21f / 255, .5f);
        sr.rect(0, 0, MasterOfMonsGame.WIDTH, MasterOfMonsGame.HEIGHT);
        sr.end();
        sb.begin();
        gs.getTitleFont().draw(sb, "Settings", (int)leftMargin, MasterOfMonsGame.HEIGHT - (int)topMargin);
        sb.end();
        Gdx.gl.glDisable(GL30.GL_BLEND);
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void dispose() {
        sr.dispose();
        sb.dispose();
    }
}
