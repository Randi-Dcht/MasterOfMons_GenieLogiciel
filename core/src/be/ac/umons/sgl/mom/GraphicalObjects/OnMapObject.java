package be.ac.umons.sgl.mom.GraphicalObjects;

import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class OnMapObject {

    protected boolean isSelected;

    protected ShapeRenderer sr;

    /**
     * The game's graphical settings.
     */
    protected GraphicalSettings gs;

    public OnMapObject(GraphicalSettings gs) {
        this.gs = gs;
        sr = new ShapeRenderer();
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
        sr.setColor(new Color(0x21212142));
    }

    protected OnMapObject() {}

    /**
     * Draw the object with the given parameters.
     * @param batch Where to draw the character
     * @param x The horizontal position
     * @param y The vertical position
     * @param width The width of the character
     * @param height The height of the character
     */
    public void draw(Batch batch, int x, int y, int width, int height) {
        if (isSelected) {
            Gdx.gl.glEnable(GL30.GL_BLEND);
            Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
            GlyphLayout gl = new GlyphLayout();
            gl.setText(gs.getSmallFont(), String.format(gs.getStringFromId("pressToInteract"), "E"));
            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.rect(x, y + height - gs.getSmallFont().getLineHeight(), gl.width, gs.getSmallFont().getLineHeight());
            sr.end();
            Gdx.gl.glDisable(GL30.GL_BLEND);
            if (isSelected) {
                batch.begin();
                gs.getSmallFont().draw(batch, String.format(gs.getStringFromId("pressToInteract"), "E"), x, y + height); //TODO Check for touch
                batch.end();
            }
        }
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public abstract int getPosX();
    public abstract int getPosY();
}
