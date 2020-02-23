package be.ac.umons.sgl.mom.GraphicalObjects;

import be.ac.umons.sgl.mom.Animations.DoubleAnimation;
import be.ac.umons.sgl.mom.Managers.AnimationManager;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Random;

public class LifeBar extends ProgressBar {

    protected final Color changeColor = new Color(0xFB8C00FF);

    protected double differenceToShow;

    protected String textToShow;

    protected double timePassed;

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

        if (differenceToShow > 0 || (textToShow != null && timePassed < 0.6)) {
            batch.begin();
            gs.getSmallFont().draw(batch, textToShow, changeX, changeY);
            batch.end();
        }
    }

    public void update(float dt) {
        timePassed += dt;
    }

    @Override
    public void setValue(int value) {
        int diff = this.value - value;
        super.setValue(value);
        startAnimatingDiff((double)diff / maxValue);
    }

    protected void startAnimatingDiff(double diffPercent) {
        if (diffPercent > 0) {
            DoubleAnimation da = new DoubleAnimation(diffPercent, 0, 200);
            da.setRunningAction(() -> {
                differenceToShow = da.getActual();
                textToShow = "-" + (int)((diffPercent - da.getActual()) * maxValue);
            });
            da.setEndingAction(() -> differenceToShow = 0);
            AnimationManager.getInstance().addAnAnimation("LifeBarAnim" + new Random().nextInt(), da); // random for multiple bar possibility
            timePassed = 0;
        }
    }
}
