package be.ac.umons.mom.g02.GraphicalObjects;

import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;

public class NotificationRappel {

    GraphicalSettings gs;
    ShapeRenderer sr;
    /**
     * The horizontal margin
     */
    protected int leftMargin;
    /**
     * The vertical margin
     */
    protected int topMargin;
    protected String textToShow;

    public NotificationRappel(GraphicalSettings gs) {
        this.gs = gs;
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
        sr.setColor(gs.getTransparentBackgroundColor());
        leftMargin = MasterOfMonsGame.WIDTH / 100;
        topMargin = MasterOfMonsGame.HEIGHT / 100;
        textToShow = "";
    }

    public void draw(Batch batch, Point pos, Point size) {
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.rect(pos.x, pos.y, size.x, size.y);
        sr.end();
        Gdx.gl.glDisable(GL30.GL_BLEND);
        batch.begin();
        gs.getSmallFont().draw(batch, textToShow, pos.x + leftMargin + (size.x - getWidth()) / 2, pos.y + gs.getSmallFont().getLineHeight() + topMargin);
        batch.end();
    }

    /**
     * @return The maximum width of this clock.
     */
    public float getWidth() {
        GlyphLayout gl = new GlyphLayout();
        gl.setText(gs.getSmallFont(), textToShow);
        return gl.width + 2 * leftMargin;
    }

    public void setTextToShow(String textToShow) {
        this.textToShow = textToShow;
    }
}
