package be.ac.umons.mom.g02.GraphicalObjects;

import be.ac.umons.mom.g02.Animations.DoubleAnimation;
import be.ac.umons.mom.g02.Managers.AnimationManager;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Random;

/**
 * Represent a life bar.
 */
public class LifeBar extends ProgressBar {
    /**
     * The color to show when the life is changing.
     */
    protected final Color changeColor = new Color(0xFB8C00FF); // TODO Put in Settings
    /**
     * The difference of life to show at this instant.
     */
    protected double differenceToShow;
    /**
     * The text to show next to the life's bar.
     */
    protected String textToShow;
    /**
     * The time that has gone since the last life's changing.
     */
    protected double timeGone;

    /**
     * @param gs The graphical's settings.
     */
    public LifeBar(GraphicalSettings gs) {
        super(gs);
    }

    /**
     * Draw the life bar with the given parameters.
     * @param batch Where to draw the text.
     * @param x Horizontal position
     * @param y Vertical position
     * @param width The width
     * @param height The height.
     */
    public void draw(Batch batch, int x, int y, int width, int height) {
        super.draw(x, y, width, height);

        float changeWidth = (float) (differenceToShow * (width - 2 * BETWEEN_BACKGROUND_FOREGROUND_MARGIN_WIDTH));
        float changeX = (float)(x + BETWEEN_BACKGROUND_FOREGROUND_MARGIN_WIDTH + (width - 2 * BETWEEN_BACKGROUND_FOREGROUND_MARGIN_WIDTH) * percent);
        float changeY = y + BETWEEN_BACKGROUND_FOREGROUND_MARGIN_HEIGHT;

        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(changeColor);
        sr.rect(changeX,
                changeY,
                changeWidth,
                height - 2 * BETWEEN_BACKGROUND_FOREGROUND_MARGIN_HEIGHT);
        sr.end();

        if (differenceToShow > 0 || (textToShow != null && timeGone < 0.6)) {
            batch.begin();
            gs.getSmallFont().draw(batch, textToShow, changeX, changeY);
            batch.end();
        }
    }

    /**
     * Update the time that has gone since the last life's changing.
     * @param dt The delta-time with the precedent call.
     */
    public void update(float dt) {
        timeGone += dt;
    }

    @Override
    public void setValue(int value) {
        int diff = this.value - value;
        super.setValue(value);
        startAnimatingDiff((double)diff / maxValue);
    }

    /**
     * Init the animation for showing the difference.
     * @param diffPercent The percentage of the difference.
     */
    protected void startAnimatingDiff(double diffPercent) {
        if (diffPercent > 0) {
            DoubleAnimation da = new DoubleAnimation(diffPercent, 0, 200);
            da.setRunningAction(() -> {
                differenceToShow = da.getActual();
                textToShow = "-" + (int)((diffPercent - da.getActual()) * maxValue);
            });
            da.setEndingAction(() -> differenceToShow = 0);
            AnimationManager.getInstance().addAnAnimation(da); // random for multiple bar possibility
            timeGone = 0;
        }
    }
}
