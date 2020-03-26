package be.ac.umons.mom.g02.GraphicalObjects;

import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Represent a progress bar.
 */
public class ProgressBar {
    /**
     * The horizontal margin between the background and the foreground.
     */
    protected int BETWEEN_BACKGROUND_FOREGROUND_MARGIN_WIDTH = 7;

    /**
     * The vertical margin between the background and the foreground.
     */
    protected int BETWEEN_BACKGROUND_FOREGROUND_MARGIN_HEIGHT = 2;

    /**
     * The background's color
     */
    protected Color backgroundColor;
    /**
     * The foreground's color
     */
    protected Color foregroundColor = new Color(42f / 255, 42f/255, 42f/255, .8f);

    /**
     * The actual value of the bar.
     */
    protected int value = 0;
    /**
     * The maximum value of the bar.
     */
    protected int maxValue = 100;
    /**
     * The ratio between the value and the maximum value/
     */
    protected double percent = .5f;
    /**
     * Allow to draw shapes.
     */
    protected ShapeRenderer sr;

    /**
     * The graphical settings.
     */
    protected GraphicalSettings gs;

    /**
     * @param gs The graphical settings.
     */
    public ProgressBar(GraphicalSettings gs) {
        this.gs = gs;
        init();
    }

    protected ProgressBar() {}

    /**
     * Initialize a new progress bar.
     */
    public void init() {
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
        backgroundColor = gs.getControlTransparentBackgroundColor();
    }

    /**
     * Draw the progress bar with the given parameters.
     * @param x Horizontal position
     * @param y Vertical position
     * @param width The width
     * @param height The height.
     */
    public void draw(int x, int y, int width, int height) {
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);

        sr.begin(ShapeRenderer.ShapeType.Filled);

        sr.setColor(backgroundColor);
        sr.rect(x, y, width, height);

        sr.setColor(foregroundColor);
        sr.rect(x + BETWEEN_BACKGROUND_FOREGROUND_MARGIN_WIDTH, y + BETWEEN_BACKGROUND_FOREGROUND_MARGIN_HEIGHT, (int)((width - 2 * BETWEEN_BACKGROUND_FOREGROUND_MARGIN_WIDTH) * percent), height - 2 * BETWEEN_BACKGROUND_FOREGROUND_MARGIN_HEIGHT);

        sr.end();

        Gdx.gl.glDisable(GL30.GL_BLEND);
    }

    /**
     * Set the actual value of the bar.
     * @param value The actual value of the bar.
     */
    public void setValue(int value) {
        this.value = value;
        percent = (double)value / maxValue;
    }

    /**
     * Set the maximum value of the bar.
     * @param maxValue Bar's maximum value
     */
    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
        this.percent = (double)value / maxValue;
    }

    /**
     * Set bar's background color
     * @param backgroundColor The bar's background color
     */
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * Set bar's foreground color.
     * @param foregroundColor Bar's foreground color
     */
    public void setForegroundColor(Color foregroundColor) {
        this.foregroundColor = foregroundColor;
    }

    /**
     * @return The ration between the actual value and the maximum value.
     */
    public double getPercent() {
        return percent;
    }

    /**
     * Dispose all resources allowed by this bar.
     */
    public void dispose() {
        sr.dispose();
    }
}
