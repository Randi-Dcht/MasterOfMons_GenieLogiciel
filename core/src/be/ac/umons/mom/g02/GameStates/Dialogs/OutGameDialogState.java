package be.ac.umons.mom.g02.GameStates.Dialogs;

import be.ac.umons.mom.g02.GraphicalObjects.Controls.Button;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;

/**
 * Represent a dialog that appears in a small box in the center of the screen.
 */
public class OutGameDialogState extends DialogState {
    /**
     * Create a new dialog.
     *
     * @param gs  Game's graphical settings
     */
    public OutGameDialogState(GraphicalSettings gs) {
        super(gs);
    }

    @Override
    public void draw() {
        GlyphLayout gl = new GlyphLayout();
        gl.setText(gs.getNormalFont(), text);
        float width = (float) (gl.width + 2 * leftMargin);
        float height = (float) (whenSelectedActions.keySet().size() * (gs.getNormalFont().getLineHeight() + 2 * topMargin) + 2 * topMargin) + gl.height + (float)topMargin;
        float x = (MasterOfMonsGame.WIDTH - width) / 2;
        float y = ((float)MasterOfMonsGame.HEIGHT - height) / 2;
        sr.setColor(gcm.getColorFor("background"));
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
