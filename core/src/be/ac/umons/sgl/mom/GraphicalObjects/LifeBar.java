package be.ac.umons.sgl.mom.GraphicalObjects;

import be.ac.umons.sgl.mom.Animations.DoubleAnimation;
import be.ac.umons.sgl.mom.Managers.AnimationManager;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Random;

public class LifeBar extends ProgressBar {

    protected final Color changeColor = new Color(0xFB8C00FF);

    protected String textToShow;

    protected double differenceToShow;

    public LifeBar(GraphicalSettings gs) {
        super(gs);
    }

    @Override
    public void draw(int x, int y, int width, int height) {
        super.draw(x, y, width, height);

        float changeWidth = (float) (differenceToShow * (width - 2 * BETWEEN_BACKGROUND_FOREGROUND_MARGIN_WIDTH));

        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(changeColor);
        sr.rect((float)(x + BETWEEN_BACKGROUND_FOREGROUND_MARGIN_WIDTH + (width - 2 * BETWEEN_BACKGROUND_FOREGROUND_MARGIN_WIDTH) * percent),
                y + BETWEEN_BACKGROUND_FOREGROUND_MARGIN_HEIGHT,
                changeWidth,
                height - 2 * BETWEEN_BACKGROUND_FOREGROUND_MARGIN_HEIGHT);
        sr.end();
    }

    @Override
    public void setValue(int value) {
        int diff = this.value - value;
        textToShow = (diff > 0 ? "+" : "") + diff;
        super.setValue(value);
        startAnimatingDiff((double)diff / maxValue);
    }

    protected void startAnimatingDiff(double diffPercent) {
        if (diffPercent > 0) {
            DoubleAnimation da = new DoubleAnimation(diffPercent, 0, 200);
            da.setRunningAction(() -> differenceToShow = da.getActual());
            da.setEndingAction(() -> differenceToShow = 0);
            AnimationManager.getInstance().addAnAnimation("LifeBarAnim" + new Random().nextInt(), da); // random for multiple bar possibility
        }
    }
}
