package be.ac.umons.sgl.mom.GameStates;

import be.ac.umons.sgl.mom.Enums.GameStates;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class LoadingState extends GameState {
    protected static final int CIRCLE_MARGIN_X = MasterOfMonsGame.WIDTH / 15;
    protected static final int CIRCLE_MARGIN_Y = MasterOfMonsGame.HEIGHT / 10;
    protected static final double CIRCLE_SPEED_RAD_SEC = Math.PI;

    protected SpriteBatch sb;
    protected ShapeRenderer sr;
    protected boolean assetsLoaded = false;

    protected double actualAngle = 0;

    public LoadingState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
        sb = new SpriteBatch();
        sr = new ShapeRenderer();
    }

    @Override
    public void init() {}

    @Override
    public void update(float dt) {
        actualAngle += CIRCLE_SPEED_RAD_SEC * dt;
    }

    @Override
    public void draw() {
        sb.begin();
        String txt = "Loading...";
        gs.getTitleFont().draw(sb, txt, MasterOfMonsGame.WIDTH / 2 - gs.getTitleFont().getXHeight() * txt.length() / 2, MasterOfMonsGame.HEIGHT / 2 + gs.getTitleFont().getLineHeight() / 2);
        sb.end();

        int fromCenterX = (int)((gs.getTitleFont().getXHeight() * txt.length() / 2 + CIRCLE_MARGIN_X) * Math.cos(actualAngle));
        int fromCenterY = (int)((gs.getTitleFont().getLineHeight() / 2 + CIRCLE_MARGIN_Y) * Math.sin(actualAngle));

        if (! assetsLoaded)
            assetsLoaded = gs.getAssetManager().update();
        else
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
        sb.dispose();
    }
}
