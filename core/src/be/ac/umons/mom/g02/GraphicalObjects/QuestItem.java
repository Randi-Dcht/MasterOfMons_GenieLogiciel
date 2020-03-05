package be.ac.umons.mom.g02.GraphicalObjects;

import be.ac.umons.mom.g02.Animations.DoubleAnimation;
import be.ac.umons.mom.g02.Animations.StringAnimation;
import be.ac.umons.mom.g02.Managers.AnimationManager;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Quests.Quest;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.awt.*;

public class QuestItem {

    protected QuestProgressCircle progressCircle;
    protected GraphicalSettings gs;
    protected Quest quest;
    protected String textToShow;

    public QuestItem(GraphicalSettings gs, Quest q) {
        this.gs = gs;
        this.quest = q;
        progressCircle = new QuestProgressCircle(gs, q);
        textToShow = "";
    }

    public void draw(Batch batch, Point pos, float radius) {
        progressCircle.draw(pos.x, pos.y, radius);
        batch.begin();
        gs.getQuestFont().draw(batch, textToShow, pos.x +  2 * radius, pos.y + radius / 2);
        batch.end();
    }

    public int getHeight() {
        return (int)Math.max(progressCircle.getHeight(), gs.getQuestFont().getLineHeight());
    }

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

    public void animateText(double to, int time) {
        StringAnimation sa = new StringAnimation(quest.getName(), time);
        sa.setRunningAction(() -> textToShow = sa.getActual());
        if (Double.compare(to, 0) == 0)
            sa.invert();
        AnimationManager.getInstance().addAnAnimation("SA_QuestItem_" + quest.getName(), sa);
    }
}
