package be.ac.umons.sgl.mom.GraphicalObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ProgressBar {
    protected int BETWEEN_BACKGROUND_FOREGROUND_MARGIN_WIDTH = 7;
    protected int BETWEEN_BACKGROUND_FOREGROUND_MARGIN_HEIGHT = 2;

    protected Color backgroundColor = new Color(21f / 255, 21f/255, 21f/255, .5f);
    protected Color foregroundColor = new Color(42f / 255, 42f/255, 42f/255, .8f);

    protected int value = 50;
    protected int maxValue = 100;
    protected double percent = .5f;

    protected ShapeRenderer sr;

    public ProgressBar() {
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
    }


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

    public void setValue(int value) {
        this.value = value;
        percent = (double)value / maxValue;
    }

    public int getValue() {
        return value;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setForegroundColor(Color foregroundColor) {
        this.foregroundColor = foregroundColor;
    }
}
