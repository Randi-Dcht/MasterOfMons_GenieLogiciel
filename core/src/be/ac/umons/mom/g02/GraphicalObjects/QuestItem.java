package be.ac.umons.mom.g02.GraphicalObjects;

import be.ac.umons.mom.g02.Animations.DoubleAnimation;
import be.ac.umons.mom.g02.Animations.StringAnimation;
import be.ac.umons.mom.g02.Managers.AnimationManager;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Quests.Quest;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.awt.*;

/**
 * Represent a line/item of the QuestShower
 */
public class QuestItem {
    /**
     * The circle showing the quest's progress
     */
    protected QuestProgressCircle progressCircle;
    /**
     * The game's graphical settings.
     */
    protected GraphicalSettings gs;
    /**
     * The quest represented by this item.
     */
    protected Quest quest;
    /**
     * The text to show next to the circle (as a variable for animation).
     */
    protected String textToShow;

    /**
     * @param gs The game's graphical settings.
     * @param q The quest represented by this item.
     */
    public QuestItem(GraphicalSettings gs, Quest q) {
        this.gs = gs;
        this.quest = q;
        progressCircle = new QuestProgressCircle(gs, q);
        textToShow = "";
    }

    /**
     * Draw this item with the given parameters?
     * @param batch Where to draw the item.
     * @param pos The position of this item.
     * @param radius The radius of the circle in this item.
     */
    public void draw(Batch batch, Point pos, float radius) {
        progressCircle.draw(pos.x, pos.y, radius);
        batch.begin();
        gs.getQuestFont().draw(batch, textToShow, pos.x +  2 * radius, pos.y + radius / 2);
        batch.end();
    }

    /**
     * @return The height of this item once drawn.
     */
    public int getHeight() {
        return (int)Math.max(progressCircle.getHeight(), gs.getQuestFont().getLineHeight());
    }

    /**
     * Animate the circle.
     * @param from The progress' percentage to begin with.
     * @param to The progress' percentage to end with.
     * @param time The duration of the animation (in ms)
     */
    public void animateCircle(double from, double to, int time) {
        progressCircle.beginAnimation();
        DoubleAnimation da = new DoubleAnimation(from, to, time);
        da.setRunningAction(() -> {
            progressCircle.beginAnimation();
            progressCircle.setDuringAnimationProgressPercent(da.getActual());
        });
        AnimationManager.getInstance().addAnAnimation("QuestCircleRectangleAnimation" + quest.toString(), da); // Evite de remplacer les animations :)
        da.setEndingAction(progressCircle::finishAnimation);
    }

    /**
     * Animate the showed text. If <code>to</code> > 0, the text begin to show. If <code>to</code> == 0, the text disappears.
     * @param to
     * @param time
     */
    public void animateText(double to, int time) {
        StringAnimation sa = new StringAnimation(quest.getName(), time);
        sa.setRunningAction(() -> textToShow = sa.getActual());
        if (Double.compare(to, 0) == 0)
            sa.invert();
        AnimationManager.getInstance().addAnAnimation("SA_QuestItem_" + quest.getName(), sa);
    }
}
