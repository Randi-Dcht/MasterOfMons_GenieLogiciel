package be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects;

import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Represent a object that must be draw on the map.
 */
public abstract class OnMapObject {

    /**
     * If the object is the nearest of the player.
     */
    protected boolean isSelected;
    /**
     * Allow to draw shapes.
     */
    protected ShapeRenderer sr;

    /**
     * The game's graphical settings.
     */
    protected GraphicalSettings gs;

    /**
     * @param gs The game's graphical object.
     */
    public OnMapObject(GraphicalSettings gs) {
        this.gs = gs;
        sr = new ShapeRenderer();
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
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
            sr.setColor(gs.getTransparentBackgroundColor());
            sr.rect(x, y - gs.getSmallFont().getLineHeight(), gl.width, gs.getSmallFont().getLineHeight());
            sr.end();
            Gdx.gl.glDisable(GL30.GL_BLEND);
            if (isSelected) {
                batch.begin();
                gs.getSmallFont().draw(batch, String.format(gs.getStringFromId("pressToInteract"), "E"), x, y); //TODO Check for touch
                batch.end();
            }
        }
    }

    /**
     * Set if the object is the nearest of the player.
     * @param selected If the object is the nearest of the player.
     */
    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    /**
     * @return The horizontal position of the object.
     */
    public abstract int getPosX();

    /**
     * @return The vertical position of the object.
     */
    public abstract int getPosY();
}
