package be.ac.umons.sgl.mom.GameStates.Dialogs;

import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class InGameDialogState extends DialogState {

    protected Color backgroundColor = new Color(21f / 255, 21f/255, 21f/255, .8f);

    public InGameDialogState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }

    @Override
    public void update(float dt) {
        super.update(dt);
    }

    @Override
    public void draw() {
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
        float height = (float)MasterOfMonsGame.HEIGHT / 4;
        float rectY = (float)topMargin;
        sr.setColor(backgroundColor);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.rect((float)leftMargin, rectY, (float)(MasterOfMonsGame.WIDTH - 2 * leftMargin), height);
        sr.end();
        sb.begin();
        gs.getSmallFont().draw(sb, "Testtttttttttttttttttttttttttttttttttttttttttttttttttttt ttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt", (float)(2 * leftMargin), (float)(rectY + height - topMargin));
        sb.end();
        Gdx.gl.glDisable(GL30.GL_BLEND);
    }

    @Override
    public void handleInput() {
        super.handleInput();
    }
}
