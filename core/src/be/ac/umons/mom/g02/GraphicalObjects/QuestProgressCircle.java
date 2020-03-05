package be.ac.umons.mom.g02.GraphicalObjects;

import be.ac.umons.mom.g02.Animations.DoubleAnimation;
import be.ac.umons.mom.g02.Managers.AnimationManager;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import be.ac.umons.mom.g02.Quests.Quest;

public class QuestProgressCircle {
    /**
     * The circle's color when the quest is active.
     */
    protected static final Color ACTIVATED_QUEST_CIRCLE_COLOR = Color.valueOf("FF5722");
    /**
     * Circle's color when the quest is finished.
     */
    protected static final Color FINISHED_QUEST_CIRCLE_COLOR = Color.valueOf("00C853");
    /**
     * The circle's color when the quest isn't active.
     */
    protected static final Color UNACTIVATED_QUEST_CIRCLE_COLOR = Color.valueOf("616161");

    /**
     * Game's graphical settings
     */
    protected GraphicalSettings gs;
    /**
     * Allow to draw shapes.
     */
    protected ShapeRenderer sr;
    /**
     * If the circle is being animated.
     */
    protected boolean isBeingAnimated;
    /**
     * Drawn degrees when animated.
     */
    protected float duringAnimationCircleDegrees;
    /**
     * The quest associated with this circle.
     */
    protected Quest quest;

    /**
     * The last radius given on the last drawing.
     */
    protected float lastRadius;
    protected float lastDegrees;

    /**
     * @param gs Game's graphical settings.
     * @param quest The quest associated with this quest.
     */
    public QuestProgressCircle(GraphicalSettings gs, Quest quest) {
        this.gs = gs;
        this.quest = quest;
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
    }

    /**
     * Draw the circle at (<code>x</code>, <code>y</code>) with the radius <code>radius</code>.
     * @param x Horizontal position
     * @param y Vertical position
     * @param radius Radius of the circle.
     */
    public void draw(int x, int y, float radius) {
        float degrees;
        if (isBeingAnimated)
            degrees = getDuringAnimationCircleDegrees();
        else
            degrees = (float)quest.getProgress() * 360;

        if (! isBeingAnimated && degrees - lastDegrees > 1E-2) {
            beginAnimation();
            duringAnimationCircleDegrees = lastDegrees;
            degrees = lastDegrees;
            DoubleAnimation da = new DoubleAnimation(lastDegrees, degrees, 2 * degrees - lastDegrees);
            da.setRunningAction(() -> setDuringAnimationCircleDegrees(da.getActual().floatValue()));
            da.setEndingAction(this::finishAnimation);
            AnimationManager.getInstance().addAnAnimation("QuestProgressCircleDegreesAnimation_" + quest.getName(), da);
        }

        lastDegrees = degrees;
        lastRadius = radius;
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);

        sr.begin();

        sr.setColor(UNACTIVATED_QUEST_CIRCLE_COLOR);
        if (quest.isFinished())
            sr.setColor(FINISHED_QUEST_CIRCLE_COLOR);
        else if (quest.isActive() || degrees != 0)
            sr.setColor(ACTIVATED_QUEST_CIRCLE_COLOR);

        if (quest.isActive() && quest.getProgress() == 0) {
            sr.set(ShapeRenderer.ShapeType.Line); // Ce n'est pas dérangeant qu'il s'éxécute en dernier comme le end est appelé juste après.
            if (isBeingAnimated)
                sr.arc(x, y, radius, 0, degrees); // Evite une ligne en plein milieu de tout !!!
            else
                sr.circle(x, y, radius);
        }
        else {
            sr.set(ShapeRenderer.ShapeType.Filled);
            sr.arc(x, y, radius, 0, (degrees == 0 ? 360 : degrees));
        }
        sr.end();

        Gdx.gl.glDisable(GL30.GL_BLEND);
    }

    /**
     * Dispose all resources allowed to this element.
     */
    public void dispose() {
        sr.dispose();
    }

    /**
     * Set the drawn circle's degrees during an animation.
     * @param duringAnimationCircleDegrees The drawn circle's degrees during an animation.
     */
    public void setDuringAnimationCircleDegrees(float duringAnimationCircleDegrees) {
        this.duringAnimationCircleDegrees = duringAnimationCircleDegrees;
    }

    /**
     * @return The drawn circle's degrees during an animation.
     */
    public float getDuringAnimationCircleDegrees() {
        return duringAnimationCircleDegrees;
    }

    /**
     * Set this object as animated.
     */
    public void beginAnimation() {
        isBeingAnimated = true;
    }

    /**
     * Set this object as non-animated.
     */
    public void finishAnimation() {
        isBeingAnimated = false;
    }

    /**
     * @return Circle's height
     */
    public int getHeight() {
        return Math.round(lastRadius * 2);
    }
    /**
     * @return Circle's width.
     */
    public int getWidth() {
        return Math.round(lastRadius * 2);
    }

    /**
     * Set the percent of the drawn circle's degree.
     * @param percent The percent of the drawn circle's degree.
     */
    public void setDuringAnimationProgressPercent(double percent) {
        setDuringAnimationCircleDegrees((float)(percent * (quest.getProgress() == 0 ? 1 : quest.getProgress()) * 360));
    }

    /**
     * @return The quest associated with this circle.
     */
    public Quest getQuest() {
        return quest;
    }
}
