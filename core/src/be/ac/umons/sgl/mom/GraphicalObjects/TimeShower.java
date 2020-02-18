package be.ac.umons.sgl.mom.GraphicalObjects;

import be.ac.umons.sgl.mom.Events.SuperviserNormally;
import be.ac.umons.sgl.mom.GameStates.PlayingState;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;

/**
 * Represent a small clock in the game.
 */
public class TimeShower {

    /**
     * The game's graphical settings.
     */
    protected GraphicalSettings gs;
    /**
     * Allow to draw shape.
     */
    protected ShapeRenderer sr;
    /**
     * The horizontal margin
     */
    protected int leftMargin;
    /**
     * The vertical margin
     */
    protected int topMargin;

    /**
     * @param gs The game's graphical settings.
     */
    public TimeShower(GraphicalSettings gs) {
        this.gs = gs;
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
        sr.setColor(new Color(0x21212142));
        leftMargin = MasterOfMonsGame.WIDTH / 100;
        topMargin = MasterOfMonsGame.HEIGHT / 100;
    }

    /**
     * Draw the clock with the given parameters.
     * @param batch Where to draw the character
     * @param pos The position of the clock.
     * @param size The size of the clock.
     */
    public void draw(Batch batch, Point pos, Point size) {
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.rect(pos.x, pos.y, size.x, size.y);
        sr.end();
        Gdx.gl.glDisable(GL30.GL_BLEND);
        batch.begin();
        gs.getSmallFont().draw(batch, SuperviserNormally.getSupervisor().getTime().toString(), pos.x + leftMargin,  pos.y + gs.getSmallFont().getLineHeight() + topMargin);
        batch.end();
    }

    /**
     * @return The maximum width of this clock.
     */
    public float getWidth() {
        String s = SuperviserNormally.getSupervisor().getTime().toString();
        GlyphLayout gl = new GlyphLayout();
        gl.setText(gs.getSmallFont(), s);
        return gl.width;
    }

}
