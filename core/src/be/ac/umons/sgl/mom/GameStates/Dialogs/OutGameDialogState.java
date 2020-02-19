package be.ac.umons.sgl.mom.GameStates.Dialogs;

import be.ac.umons.sgl.mom.GraphicalObjects.Controls.Button;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;

public class OutGameDialogState extends DialogState {
    /**
     * Create a new dialog.
     *
     * @param gsm Game's state manager
     * @param gim Game's input manager
     * @param gs  Game's graphical settings
     */
    public OutGameDialogState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }

    @Override
    public void draw() {
        GlyphLayout gl = new GlyphLayout();
        gl.setText(gs.getNormalFont(), text);
        float width = (float) (gl.width + 2 * leftMargin);
        float height = (float) (whenSelectedActions.keySet().size() * (gs.getNormalFont().getLineHeight() + 2 * topMargin) + 2 * topMargin) + gl.height + (float)topMargin;
        float x = (MasterOfMonsGame.WIDTH - gl.width) / 2;
        float y = ((float)MasterOfMonsGame.HEIGHT - height) / 2;
        sr.setColor(gs.getBackgroundColor());
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.rect(x, y, width, height);
        sr.end();
        sb.begin();
        gs.getNormalFont().draw(sb, text, (float)(x + leftMargin), MasterOfMonsGame.HEIGHT - (float)(y + topMargin));
        sb.end();
        int alreadyUsed = 0;
        for (int i = buttons.size() - 1; i >= 0; i--) {
            Button b = buttons.get(i);
            int bHeight = (int)(gs.getNormalFont().getLineHeight());
            b.draw(sb, new Point((int)(x + leftMargin), (int)y + alreadyUsed), new Point((int)(width - 2 * leftMargin), bHeight));
            alreadyUsed += (int)(bHeight + topMargin);
        }
    }
}
