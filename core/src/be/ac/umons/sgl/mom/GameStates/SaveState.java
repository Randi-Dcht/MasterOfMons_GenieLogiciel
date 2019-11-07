package be.ac.umons.sgl.mom.GameStates;

import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class SaveState extends GameState {

    ShapeRenderer sr;
    SpriteBatch sb;

    protected int topMargin;

    public SaveState(GameStateManager gsm, GameInputManager gim) {
        super(gsm, gim);
    }

    @Override
    public void init() {
        sb = new SpriteBatch();
        sr = new ShapeRenderer();
        topMargin = MasterOfMonsGame.HEIGHT / 100;
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
        sr.rect(MasterOfMonsGame.WIDTH / 4,  MasterOfMonsGame.HEIGHT / 2 - MasterOfMonsGame.HEIGHT/ 8, MasterOfMonsGame.WIDTH / 2, MasterOfMonsGame.HEIGHT / 4);
        sr.end();
        sb.begin();
        String str = "Save";
        MasterOfMonsGame.gs.getNormalFont().draw(sb, str, MasterOfMonsGame.WIDTH / 2 - str.length() * MasterOfMonsGame.gs.getNormalFont().getXHeight() / 2, MasterOfMonsGame.HEIGHT / 2 + MasterOfMonsGame.HEIGHT/ 8 - topMargin);
        sb.end();
        Gdx.gl.glDisable(GL30.GL_BLEND);
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void dispose() {

    }
}
