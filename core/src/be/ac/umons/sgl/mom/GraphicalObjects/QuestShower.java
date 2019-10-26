package be.ac.umons.sgl.mom.GraphicalObjects;

import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import be.ac.umons.sgl.mom.Objects.Quest;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;

public class QuestShower {
    public static final int TEXT_AND_RECTANGLE_MARGIN = 7;
    public static final int BETWEEN_QUEST_MARGIN_HEIGHT = 7;
    public static final int BETWEEN_QUEST_MARGIN_WIDTH = 7;
    public static final int BETWEEN_CIRCLE_AND_TEXT_MARGIN = 7;

    protected static final Color ACTIVATED_QUEST_CIRCLE_COLOR = Color.valueOf("FF5722");
    protected static final Color FINISHED_QUEST_CIRCLE_COLOR = Color.valueOf("00C853");
    protected static final Color UNACTIVATED_QUEST_CIRCLE_COLOR = Color.valueOf("616161");

    protected Batch batch;
    private ShapeRenderer sr;
    protected GraphicalSettings gs;

    protected float x, y;
    protected float duringAnimationQuestShowerHeight, duringAnimationQuestShowerWidth;
    protected double duringAnimationTextOpacity;
    protected boolean isBeingAnimated;
    protected int questShowerWidth;
    protected int questShowerHeight;
    protected int circleRadius;


    public QuestShower(GraphicalSettings gs, Batch batch, float x, float y) {
        this.gs = gs;
        this.batch = batch;
        this.x = x;
        this.y = y;
        init();
    }

    public void draw() {
        showQuest(questToShow, x, y);
    }

    protected void init() {
        circleRadius = getTextSize("A").y / 2;

        sr = new ShapeRenderer();
        sr.setProjectionMatrix(batch.getProjectionMatrix());
        sr.setAutoShapeType(true);
    }


    int maximumQuestHeight = 0;
    Quest questToShow;

    public void setQuest(Quest q) {
        questToShow = q;
        maximumQuestHeight = getMaximumQuestHeight(q);
        questShowerWidth = getMaximumQuestNameWidth(q, 2 * circleRadius + BETWEEN_CIRCLE_AND_TEXT_MARGIN) + 2 * TEXT_AND_RECTANGLE_MARGIN;
        questShowerHeight = getMaximumQuestHeight(q) + TEXT_AND_RECTANGLE_MARGIN * 2;
    }

    // https://gamedev.stackexchange.com/a/115483
    // https://stackoverflow.com/a/14721570
    protected void showQuest(Quest quest, float x, float y) {
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);

        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(21f / 255, 21f / 255, 21f / 255, .5f);
        if (isBeingAnimated)
            sr.rect(x - TEXT_AND_RECTANGLE_MARGIN, y  - duringAnimationQuestShowerHeight + TEXT_AND_RECTANGLE_MARGIN, duringAnimationQuestShowerWidth, duringAnimationQuestShowerHeight);
        else
            sr.rect(x - TEXT_AND_RECTANGLE_MARGIN, y  - questShowerHeight + TEXT_AND_RECTANGLE_MARGIN, questShowerWidth, questShowerHeight);
        drawQuestCircles(quest, x + TEXT_AND_RECTANGLE_MARGIN, y - (float)circleRadius, circleRadius);
        sr.end();

        Gdx.gl.glDisable(GL30.GL_BLEND);
        batch.begin();
        if (isBeingAnimated)
            printQuest(quest, x + 2 * circleRadius + BETWEEN_CIRCLE_AND_TEXT_MARGIN, y, (float)duringAnimationTextOpacity);
        else
            printQuest(quest, x + 2 * circleRadius + BETWEEN_CIRCLE_AND_TEXT_MARGIN, y, 1);
        batch.end();
    }

    protected void printQuest(Quest q, float beginningX, float beginningY, float textOpacity) {
        gs.getQuestFont().setColor(1, 1, 1, textOpacity);
        gs.getQuestFont().draw(batch, q.getName() + '\n', beginningX, beginningY);
        gs.getQuestFont().setColor(1, 1, 1, 1); // Si jamais il est utilisé entre temps
        for (Quest q2 : q.getSubQuests()) {
            beginningY -= (getTextSize(q.getName()).y + BETWEEN_QUEST_MARGIN_HEIGHT);
            printQuest(q2, beginningX + BETWEEN_QUEST_MARGIN_WIDTH, beginningY, textOpacity);
        }
    }

    protected void drawQuestCircles(Quest q, float beginningX, float beginningY, float radius) {
//        sr.setColor(21f / 255, 21f / 255, 21f / 255, 1f);
        float degrees = q.getProgress() * 360;

        sr.setColor(UNACTIVATED_QUEST_CIRCLE_COLOR);
        if (q.isFinished())
            sr.setColor(FINISHED_QUEST_CIRCLE_COLOR);
        else if (q.isActive() || degrees != 0)
            sr.setColor(ACTIVATED_QUEST_CIRCLE_COLOR);

        if (q.isActive() && degrees == 0) {
            sr.set(ShapeRenderer.ShapeType.Line); // Ce n'est pas dérangeant qu'il s'éxécute en dernier comme le end est appelé juste après.
            sr.circle(beginningX, beginningY, radius); // Evite une ligne en plein milieu de tout !!!
        }
        else {
            sr.set(ShapeRenderer.ShapeType.Filled);
            sr.arc(beginningX, beginningY, radius, 0, (degrees == 0 ? 360 : degrees));
        }
        for (Quest q2 : q.getSubQuests()) {
            beginningY -= (getTextSize(q.getName()).y + BETWEEN_QUEST_MARGIN_HEIGHT);
            drawQuestCircles(q2, beginningX + BETWEEN_QUEST_MARGIN_WIDTH, beginningY, radius);
        }
    }

    protected int getMaximumQuestNameWidth(Quest mainQuest, double defaultMargin) {
        int max = getTextSize(mainQuest.getName()).x + (int)defaultMargin;
        for (Quest q: mainQuest.getSubQuests()) {
            int i = getMaximumQuestNameWidth(q, defaultMargin) + BETWEEN_QUEST_MARGIN_WIDTH;
            if (i > max)
                max = i;
        }
        return max;
    }

    protected int getMaximumQuestHeight(Quest mainQuest) {
        return mainQuest.getTotalSubQuestsNumber() * getTextSize("A").y + BETWEEN_QUEST_MARGIN_HEIGHT * mainQuest.getTotalSubQuestsNumber();
    }

    // https://stackoverflow.com/a/16606599
    protected Point getTextSize(String text) {
        GlyphLayout layout = new GlyphLayout();
        layout.setText(gs.getQuestFont(), text);
        return new Point((int)layout.width, (int)layout.height);// contains the width of the current set text
    }

    public int getQuestShowerWidth() {
        return questShowerWidth;
    }

    public int getQuestShowerHeight() {
        return questShowerHeight;
    }

    public float getDuringAnimationQuestShowerHeight() {
        return duringAnimationQuestShowerHeight;
    }

    public float getDuringAnimationQuestShowerWidth() {
        return duringAnimationQuestShowerWidth;
    }

    public void setDuringAnimationQuestShowerHeight(float duringAnimationQuestShowerHeight) {
        this.duringAnimationQuestShowerHeight = duringAnimationQuestShowerHeight;
    }

    public void setDuringAnimationQuestShowerWidth(float duringAnimationQuestShowerWidth) {
        this.duringAnimationQuestShowerWidth = duringAnimationQuestShowerWidth;
    }

    public double getDuringAnimationTextOpacity() {
        return duringAnimationTextOpacity;
    }

    public void setDuringAnimationTextOpacity(double duringAnimationTextOpacity) {
        this.duringAnimationTextOpacity = duringAnimationTextOpacity;
    }

    public void beginAnimation() {
        isBeingAnimated = true;
    }

    public void finishAnimation() {
        isBeingAnimated = false;
    }
}
