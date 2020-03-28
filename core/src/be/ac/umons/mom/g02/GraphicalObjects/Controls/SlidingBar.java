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
     * The maximum value defined by the user.
     */
    protected int maxValue;
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
        actualColor = Color.WHITE;
    }

    /**
     * USES IT ONLY FOR TEST
     */
    protected SlidingBar() {}

    @Override
    public void draw(Batch batch, Point pos, Point size) {
        if (posX < 0)
            posX = pos.x + (float)size.x / 2;
        super.draw(batch, pos, size);
        sr.setColor(actualColor);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        float posY = pos.y + (float)size.y / 2;
        sr.rectLine(pos.x + size.y, posY, pos.x + size.x - size.y / 2, posY, (float)size.y / 20);
        sr.circle(posX, posY, size.y / 2);
        sr.end();
    }

    /**
     * Execute an action depending on which key was pressed.
     */
    @Override
    public void handleInput() {
        if (gim.isMouseJustClicked() &&
            new Rectangle((int)(posX - height / 2), MasterOfMonsGame.HEIGHT - (y + height / 2), (int)(posX + height / 2), MasterOfMonsGame.HEIGHT - (y - height / 2)).contains(gim.getLastMousePosition())) {
            isMoving = true;
        } else if (gim.isMouseJustUnclicked())
            isMoving = false;
        if (isMoving) {
            posX = gim.getLastMousePosition().x;
            if (posX < x + height / 2)
                posX = x + height / 2;
            else if (posX > x + width - height / 2)
                posX = x + width - height / 2;
            int tmpValue = Math.round((posX - x - height / 2) / (width - height) * maxValue);
            if (tmpValue != value && onValueChanged != null)
                onValueChanged.run();
            value = tmpValue;
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
     * @param maxValue The maximum value this bar can return
     */
    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
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
        posX = x + ((float)value / maxValue) * width;
        this.value = value;
    }

    /**
     * @param onValueChanged What to do when the value changed
     */
    public void setOnValueChanged(Runnable onValueChanged) {
        this.onValueChanged = onValueChanged;
    }
}
