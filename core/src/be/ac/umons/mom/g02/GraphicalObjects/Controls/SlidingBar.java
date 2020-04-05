package be.ac.umons.mom.g02.GraphicalObjects.Controls;

import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;
import java.util.Random;

public class SlidingBar extends Control {

    /**
     * Allow to draw shapes
     */
    protected ShapeRenderer sr;
    /**
     * The horizontal position of the circle
     */
    protected float posX = -1;
    /**
     * The actual color to be drawn
     */
    protected Color actualColor;
    /**
     * If the circle is moving or not
     */
    protected boolean isMoving = false;
    /**
     * The minimum value defined by the user.
     */
    protected int minValue;
    /**
     * The maximum value defined by the user.
     */
    protected int maxValue;
    /**
     * The maximum value defined by the user on which this bar can go.
     */
    protected int maxSlidingValue;
    /**
     * The actual value of this bar
     */
    protected int value;
    /**
     * The runnable to run when the value changed
     */
    protected Runnable onValueChanged;

    /**
     * @param gs The graphical settings
     */
    public SlidingBar(GraphicalSettings gs) {
        super(gs);
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
        actualColor = gcm.getColorFor("foreground");
    }

    /**
     * USES IT ONLY FOR TEST
     */
    protected SlidingBar() {}

    @Override
    public void draw(Batch batch, Point pos, Point size) {
        if (posX < 0)
            posX = pos.x + (float)size.y / 2;
        super.draw(batch, pos, size);
        sr.setColor(actualColor);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        float posY = pos.y + (float)size.y / 2;
        sr.rectLine(pos.x + size.y / 2, posY, pos.x + size.x - size.y / 2, posY, (float)size.y / 20);
        sr.circle(posX, posY, size.y / 2);
        sr.end();
    }

    /**
     * Execute an action depending on which key was pressed.
     */
    @Override
    public void handleInput() {
        if (gim.isMouseJustClicked() &&
            new Rectangle((int)(posX - height / 2), MasterOfMonsGame.HEIGHT - (y + height), height, height).contains(gim.getLastMousePosition())) {
            isMoving = true;
        } else if (gim.isMouseJustUnclicked())
            isMoving = false;
        if (isMoving) {
            float oldPosX = posX;
            posX = gim.getLastMousePosition().x;
            if (posX < x + (float)height / 2)
                posX = x + (float)height / 2;
            else if (posX > x + width - (float)height / 2)
                posX = x + width - (float)height / 2;
            double tmpValue = minValue + (posX - x - (float)height / 2) / (width - (float)height) * (maxValue - minValue);

            if (tmpValue > maxSlidingValue) {
                posX = oldPosX;
                return;
            }

            int tmp = minValue + value;
            value = (int)tmpValue + (tmpValue - (int)tmpValue > 0.5 ? 1 : 0); // Math.round -> +inf (from doc)
            if (tmp != value && onValueChanged != null)
                onValueChanged.run();
        }
    }

    /**
     * Dispose all resources reserved by this control.
     */
    @Override
    public void dispose() {
        sr.dispose();
    }

    /**
     * @param minValue The minimum value this bar can return
     */
    public void setMinValue(int minValue) {
        this.minValue = minValue;
        if (value < minValue)
            value = minValue;
    }

    /**
     * @param maxValue The maximum value this bar can return
     */
    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
        this.maxSlidingValue = maxValue;
        if (value > maxValue)
            value = maxValue;
    }

    /**
     * @param maxValue The maximum value this bar can go to
     */
    public void setMaxSlidingValue(int maxValue) {
        this.maxSlidingValue = maxValue;
        if (value > maxValue)
            value = maxValue;
    }

    /**
     * @return The actual value of this bar. A maximum value have to be set before.
     */
    public int getActualValue() {
        return value;
    }

    /**
     * Set the round position at the position at which the value is represented.
     * @param value The actual value of this bar
     */
    public void setActualValue(int value) {
        if (value < minValue)
            value = minValue;
        else if (value > maxValue)
            value = maxValue;
        posX = x + height / 2 + ((float)value / maxValue) * (width - height);
        this.value = value;
    }

    /**
     * @param onValueChanged What to do when the value changed
     */
    public void setOnValueChanged(Runnable onValueChanged) {
        this.onValueChanged = onValueChanged;
    }
}
