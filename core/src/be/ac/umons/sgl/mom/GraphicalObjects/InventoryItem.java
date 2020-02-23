package be.ac.umons.sgl.mom.GraphicalObjects;

import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import be.ac.umons.sgl.mom.Objects.Items.Items;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Represent an inventory's item
 * @author Guillaume Cardoen
 */
public class InventoryItem {
    /**
     * The background's opacity
     */
    protected static float BACKGROUND_RECTANGLE_OPACITY = .8f;

    /**
     * The object to show.
     */
    protected Items go;
    /**
     * The game's graphical settings.
     */
    protected GraphicalSettings gs;
    /**
     * Allow to draw shapes.
     */
    protected ShapeRenderer sr;
    /**
     * If the item is being animated
     */
    private boolean isBeingAnimated;
    /**
     * The foreground's opacity when animating
     */
    private float duringAnimationForegroundOpacity;
    /**
     * If this inventory's item is selected.
     */
    private boolean isSelected = false;
    /**
     * The background's color
     */
    private Color backgroundColor;
    /**
     * The background's color when the item is selected
     */
    private Color selectedBackgroundColor;

    /**
     * The background color to use at this moment.
     */
    private Color backgroundColorToUse;

    /**
     * @param gs The game's graphical settings.
     * @param go The item to show.
     */
    public InventoryItem(GraphicalSettings gs, Items go) {
        this.go = go;
        this.gs = gs;
        init();
    }

    /**
     * Initialize the item.
     */
    public void init() {
        sr = new ShapeRenderer();
        backgroundColor = new Color(gs.getTransparentBackgroundColor().r, gs.getTransparentBackgroundColor().g, gs.getTransparentBackgroundColor().b, BACKGROUND_RECTANGLE_OPACITY);
        selectedBackgroundColor = new Color(69f / 255, 39f / 255, 160f / 255, BACKGROUND_RECTANGLE_OPACITY);
        backgroundColorToUse = backgroundColor;
    }

    /**
     * Draw the item on <code>batch</code> at (<code>x</code>, <code>y</code>) with the width <code>width</code> and the height <code>y</code>.
     * @param batch Where this item will be drawn.
     * @param x Horizontal position
     * @param y Vertical position
     * @param width The width
     * @param height The height
     */
    public void draw(Batch batch, int x, int y, int width, int height) {
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        if (isBeingAnimated)
            backgroundColorToUse.a = duringAnimationForegroundOpacity * BACKGROUND_RECTANGLE_OPACITY;
        sr.setColor(backgroundColorToUse);
        sr.rect(x, y, width, height);
        sr.end();

        if (gs.getAssetManager().contains("Pictures/Objects/" + go.toString() + ".png")) {
            Texture t = gs.getAssetManager().get("Pictures/Objects/" + go.toString() + ".png");
            batch.begin();
            Color c = batch.getColor();
            batch.setColor(c.r, c.g, c.b, isBeingAnimated ? duringAnimationForegroundOpacity : 1);
            batch.draw(t, x, y, width, height);
            batch.end();
        }
        Gdx.gl.glDisable(GL30.GL_BLEND);
    }

    /**
     * Select this item.
     */
    public void select() {
        backgroundColorToUse = selectedBackgroundColor;
        isSelected = true;
    }
    /**
     * Unselect this item.
     */
    public void unselect() {
        backgroundColorToUse = backgroundColor;
        isSelected = false;
    }

    /**
     * Set Foreground's opacity when animating.
     * @param duringAnimationForegroundOpacity Foreground's opacity when animating
     */
    public void setDuringAnimationForegroundOpacity(float duringAnimationForegroundOpacity) {
        this.duringAnimationForegroundOpacity = duringAnimationForegroundOpacity;
    }

    /**
     * Mark the item as being animated.
     */
    public void beginAnimation() {
        isBeingAnimated = true;
    }

    /**
     * Mark this item as non-animated.
     */
    public void finishAnimation() {
        backgroundColor.a = BACKGROUND_RECTANGLE_OPACITY;
        isBeingAnimated = false;
    }

    public Items getItem() {
        return go;
    }

    /**
     * Dispose all resources allowed to this item.
     */
    public void dispose() {
        sr.dispose();
    }

}
