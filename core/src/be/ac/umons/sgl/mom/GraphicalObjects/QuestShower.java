package be.ac.umons.sgl.mom.GraphicalObjects;

import be.ac.umons.sgl.mom.Animations.DoubleAnimation;
import be.ac.umons.sgl.mom.Managers.AnimationManager;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import be.ac.umons.sgl.mom.Quests.Quest;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Allow to show the current quest (and under-quests) and their progress.
 * @author Guillaume Cardoen
 */
public class QuestShower {
    /**
     * The margin between the text and the background's rectangle.
     */
    public static final int TEXT_AND_RECTANGLE_MARGIN = 7;
    /**
     * The vertical margin between two quests.
     */
    public static final int BETWEEN_QUEST_MARGIN_HEIGHT = 7;
    /**
     * The horizontal margin between one quest and its under-quest(s).
     */
    public static final int BETWEEN_QUEST_MARGIN_WIDTH = 7;
    /**
     * The margin between the circle and the text.
     */
    public static final int BETWEEN_CIRCLE_AND_TEXT_MARGIN = 7;
    /**
     * Allow to draw shapes.
     */
    private ShapeRenderer sr;
    /**
     * The game's graphical settings.
     */
    protected GraphicalSettings gs;
    /**
     * The size while animating.
     */
    protected float duringAnimationQuestShowerHeight, duringAnimationQuestShowerWidth;
    /**
     * Text's opacity while animating.
     */
    protected double duringAnimationForegroundOpacity;
    /**
     * Is the support being animated.
     */
    protected boolean isBeingAnimated;
    /**
     * The size of the object.
     */
    protected int questShowerWidth, questShowerHeight;
    /**
     * The progress circle's radius.
     */
    protected int circleRadius;
    /**
     * The quest to show.
     */
    private Quest questToShow;

    /**
     * The progress circle mapped to their corresponding quest (under-quest).
     */
    protected Map<Quest, QuestProgressCircle> questProgressCircleMap;
    /**
     * The game's animation manager.
     */
    protected AnimationManager am;

    /**
     * @param gs Game's graphical settings.
     */
    public QuestShower(GraphicalSettings gs) {
        this.gs = gs;
        this.am = AnimationManager.getInstance();
        init();
    }

    /**
     * Default constructor. USE IT ONLY FOR TEST
     */
    protected QuestShower() {}

    /**
     * Initialize the QuestShower.
     */
    protected void init() {
        circleRadius = (int)gs.getQuestFont().getLineHeight() / 2;
        questProgressCircleMap = new HashMap<>();

        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
    }

    /**
     * Draw the object with the given parameters.
     * Code inspired from https://gamedev.stackexchange.com/a/115483 by NuttyBunny
     * and https://stackoverflow.com/a/14721570 by UVM
     * @param batch Where the object must be drawn.
     * @param x Horizontal position.
     * @param y Vertical position.
     */
    public void draw(Batch batch, int x, int y) {
        if (questToShow == null)
            return;

        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);

        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(21f / 255, 21f / 255, 21f / 255, .5f);
        if (isBeingAnimated)
            sr.rect(x - TEXT_AND_RECTANGLE_MARGIN, y  - duringAnimationQuestShowerHeight + TEXT_AND_RECTANGLE_MARGIN, duringAnimationQuestShowerWidth, duringAnimationQuestShowerHeight);
        else
            sr.rect(x - TEXT_AND_RECTANGLE_MARGIN, y  - questShowerHeight + TEXT_AND_RECTANGLE_MARGIN, questShowerWidth, questShowerHeight);
        sr.end();
        drawQuestCircles(questToShow, x + TEXT_AND_RECTANGLE_MARGIN, y - circleRadius, circleRadius);

        Gdx.gl.glDisable(GL30.GL_BLEND);
        batch.begin();
        if (isBeingAnimated)
            printQuest(questToShow, batch, x + 2 * circleRadius + BETWEEN_CIRCLE_AND_TEXT_MARGIN, y, (float)duringAnimationForegroundOpacity);
        else
            printQuest(questToShow, batch, x + 2 * circleRadius + BETWEEN_CIRCLE_AND_TEXT_MARGIN, y, 1);
        batch.end();
    }

    /**
     * Set the quest to show.
     * @param q Quest to show.
     */
    public void setQuest(Quest q) {
        if (questToShow == null) {
            questToShow = q;
            questProgressCircleMap.put(q, new QuestProgressCircle(gs, q));
            questShowerWidth = getMaximumQuestNameWidth(q, 2 * circleRadius + BETWEEN_CIRCLE_AND_TEXT_MARGIN) + 2 * TEXT_AND_RECTANGLE_MARGIN;
            questShowerHeight = getMaximumQuestHeight(q) + TEXT_AND_RECTANGLE_MARGIN * 2;
            for (Quest q2 : q.getSubQuests()) {
                questProgressCircleMap.put(q2, new QuestProgressCircle(gs, q2));
            }
            animateQuestRectangle(0, 1, 750);
            animateQuestProgressCircle(0,1,1500);
        } else {
            animateQuestRectangle(1, 0, 1500, () -> {
                questToShow = null;
                setQuest(q);
            });
            animateQuestProgressCircle(1,0,1500);
        }
    }


    /**
     * Show the quest <code>q</code> and its under-quests from position (<code>beginningX</code>, <code>beginningY</code>) with the opacity <code>textOpacity</code>.
     * @param q The quest to show.
     * @param batch Where its must be drawn
     * @param beginningX The beginning horizontal position.
     * @param beginningY The beginning vertical position.
     * @param textOpacity The text's opacity.
     */
    protected void printQuest(Quest q, Batch batch, int beginningX, int beginningY, float textOpacity) {
        gs.getQuestFont().setColor(1, 1, 1, textOpacity);
        gs.getQuestFont().draw(batch, q.getName() + '\n', beginningX, beginningY);
        gs.getQuestFont().setColor(1, 1, 1, 1); // Si jamais il est utilisé entre temps
        for (Quest q2 : q.getSubQuests()) {
            beginningY -= (gs.getQuestFont().getLineHeight() + BETWEEN_QUEST_MARGIN_HEIGHT);
            printQuest(q2, batch, beginningX + BETWEEN_QUEST_MARGIN_WIDTH, beginningY, textOpacity);
        }
    }

    /**
     * Draw the circles corresponding the to given quest and under-quest.
     * @param q The quest
     * @param beginningX The beginning horizontal position.
     * @param beginningY The beginning vertical position.
     * @param radius The radius of the circle to draw.
     */
    protected void drawQuestCircles(Quest q, int beginningX, int beginningY, float radius) {
//        sr.setColor(21f / 255, 21f / 255, 21f / 255, 1f);

        questProgressCircleMap.get(q).draw(beginningX, beginningY, radius);
        for (Quest q2 : q.getSubQuests()) {
            beginningY -= (questProgressCircleMap.get(q).getHeight() + BETWEEN_QUEST_MARGIN_HEIGHT);
            drawQuestCircles(q2, beginningX + BETWEEN_QUEST_MARGIN_WIDTH, beginningY, radius);
        }
    }

    /**
     * Animate every circles.
     * @param from The beginning percent.
     * @param to The ending percent.
     * @param time The duration of the animation.
     */
    public void animateQuestProgressCircle(double from, double to, int time) {
        for (QuestProgressCircle qpc : questProgressCircleMap.values()) {
            qpc.beginAnimation();
            DoubleAnimation da = new DoubleAnimation(from, to, time);
            da.setRunningAction(() -> {
                qpc.setDuringAnimationProgressPercent(da.getActual());
                qpc.setDuringAnimationOpacity((float)da.getActual().doubleValue());
            });
            am.addAnAnimation("QuestCircleRectangleAnimation" + qpc.toString(), da); // Evite de remplacer les animations :)
            da.setEndingAction(qpc::finishAnimation);
        }
    }

    /**
     * Animate the background's rectangle.
     * @param from The beginning percent.
     * @param to The ending percent.
     * @param time The duration of the animation.
     * @return The generated animation.
     */
    public DoubleAnimation animateQuestRectangle(double from, double to, int time) {
        beginAnimation();
        DoubleAnimation da = new DoubleAnimation(from, to, time);
        da.setRunningAction(() -> {
            setDuringAnimationQuestShowerWidth((int)((double)getWidth() * da.getActual()));
            setDuringAnimationQuestShowerHeight((int)((double)getHeight() * da.getActual()));
            setDuringAnimationForegroundOpacity(da.getActual());
        });
        am.addAnAnimation("QuestRectangleAnimation", da);
        da.setEndingAction(this::finishAnimation);
        return da;
    }
    /**
     * Animate the background's rectangle.
     * @param from The beginning percent.
     * @param to The ending percent.
     * @param time The duration of the animation.
     * @param toDoAfter The action to do when the animation is finished.
     */
    public void animateQuestRectangle(double from, double to, int time, Runnable toDoAfter) {
        DoubleAnimation da = animateQuestRectangle(from, to, time);
        Runnable run = da.getEndingAction();
        da.setEndingAction(() ->  {
            run.run();
            toDoAfter.run();
        });
    }


    /**
     * Return the maximum size used by a quest and its under-quests.
     * @param mainQuest The quest
     * @param defaultMargin The default margin to use.
     * @return The maximum size used by a quest and its under-quests.
     */
    protected int getMaximumQuestNameWidth(Quest mainQuest, int defaultMargin) {
        int max = getTextSize(mainQuest.getName()).x + defaultMargin;
        for (Quest q: mainQuest.getSubQuests()) {
            int i = getMaximumQuestNameWidth(q, max + BETWEEN_QUEST_MARGIN_WIDTH);
            if (i > max)
                max = i;
        }
        return max;
    }

    /**
     * Return the maximum height used by this object.
     * @param mainQuest The quest to show.
     * @return The maximum height used by this object.
     */
    protected int getMaximumQuestHeight(Quest mainQuest) {
        return (int)(mainQuest.getTotalSubQuestsNumber() * gs.getQuestFont().getLineHeight() + BETWEEN_QUEST_MARGIN_HEIGHT * mainQuest.getTotalSubQuestsNumber());
    }

    /**
     * Return the size used by the text <code>text</code> with the quest's font.
     * Code inspired from https://stackoverflow.com/a/16606599 by BennX
     * @param text The text.
     * @return The size used by the text <code>text</code> with the quest's font.
     */
    protected Point getTextSize(String text) {
        GlyphLayout layout = new GlyphLayout();
        layout.setText(gs.getQuestFont(), text);
        return new Point((int)layout.width, (int)layout.height);// contains the width of the current set text
    }

    /**
     * @return The width.
     */
    public int getWidth() {
        return questShowerWidth;
    }
    /**
     * @return The height.
     */
    public int getHeight() {
        return questShowerHeight;
    }

    /**
     * Set the height while animating.
     * @param duringAnimationQuestShowerHeight The height while animating.
     */
    public void setDuringAnimationQuestShowerHeight(float duringAnimationQuestShowerHeight) {
        this.duringAnimationQuestShowerHeight = duringAnimationQuestShowerHeight;
    }

    /**
     * Set the width while animating.
     * @param duringAnimationQuestShowerWidth The width while animating.
     */
    public void setDuringAnimationQuestShowerWidth(float duringAnimationQuestShowerWidth) {
        this.duringAnimationQuestShowerWidth = duringAnimationQuestShowerWidth;
    }

    /**
     * Set the foreground's opacity while animating.
     * @param duringAnimationForegroundOpacity The foreground's opacity while animating.
     */
    public void setDuringAnimationForegroundOpacity(double duringAnimationForegroundOpacity) {
        this.duringAnimationForegroundOpacity = duringAnimationForegroundOpacity;
    }

    /**
     * Mark this object as being animating.
     */
    public void beginAnimation() {
        isBeingAnimated = true;
    }

    /**
     * Mark this object as not being animated.
     */
    public void finishAnimation() {
        isBeingAnimated = false;
    }

    /**
     * Dispose resources that this object has.
     */
    public void dispose() {
        sr.dispose();
    }
}
