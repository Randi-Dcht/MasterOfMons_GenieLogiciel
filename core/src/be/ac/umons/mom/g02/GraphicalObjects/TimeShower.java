package be.ac.umons.mom.g02.GraphicalObjects;

import be.ac.umons.mom.g02.Animations.DoubleAnimation;
import be.ac.umons.mom.g02.Animations.StringAnimation;
import be.ac.umons.mom.g02.Regulator.SuperviserNormally;
import be.ac.umons.mom.g02.Managers.AnimationManager;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Other.Date;
import be.ac.umons.mom.g02.Other.TimeGame;
import be.ac.umons.mom.g02.Regulator.Supervisor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Represent a small clock in the game.
 */
public class TimeShower {
    /**
     * The time the object take to go from it's width to the screen's width.
     */
    protected final int EXPEND_TIME = 1500;

    /**
     * The game's graphical settings.
     */
    protected GraphicalSettings gs;
    /**
     * Allow to draw shape.
     */
    protected ShapeRenderer sr;
    /**
     * The horizontal margin
     */
    protected int leftMargin;
    /**
     * The vertical margin
     */
    protected int topMargin;
    /**
     * If the object is being animated.
     */
    protected boolean isBeingAnimated = false;
    /**
     * The width of the object when animated.
     */
    protected double animationWidth;
    /**
     * The time before the update call.
     */
    protected Date oldTime;
    /**
     * The text to show in this object.
     */
    protected String textToShow;
    /**
     * If the text is being animated.
     */
    protected boolean isTextBeingAnimated = false;

    /**
     * @param gs The game's graphical settings.
     */
    public TimeShower(GraphicalSettings gs) {
        this.gs = gs;
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
        sr.setColor(gs.getControlTransparentBackgroundColor());
        leftMargin = MasterOfMonsGame.WIDTH / 100;
        topMargin = MasterOfMonsGame.HEIGHT / 100;
        beginAnimation();
        textToShow = Supervisor.getSupervisor().getTime().toString();
        oldTime = Supervisor.getSupervisor().getTime().getDate();
    }

    protected TimeShower() {}

    /**
     * Draw the clock with the given parameters.
     * @param batch Where to draw the character
     * @param pos The position of the clock.
     * @param size The size of the clock.
     */
    public void draw(Batch batch, Point pos, Point size) {
        if (isBeingAnimated) {
            pos.x = (int)(pos.x - animationWidth + size.x);
            size.x = (int)animationWidth;
        }
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.rect(pos.x, pos.y, size.x, size.y);
        sr.end();
        Gdx.gl.glDisable(GL30.GL_BLEND);
        batch.begin();
        updateTime();
        gs.getSmallFont().draw(batch, textToShow, pos.x + leftMargin + (size.x - getWidth()) / 2,  pos.y + gs.getSmallFont().getLineHeight() + topMargin);
        batch.end();
    }

    /**
     * Update the time and expend to full-width if more than a minute has passed.
     */
    protected void updateTime() {
        TimeGame tg = Supervisor.getSupervisor().getTime();
        if (tg != null) {
            if (! isTextBeingAnimated)
                textToShow = tg.toString();
            if (mustExpend(oldTime, tg.getDate()) && ! isBeingAnimated) {
                extendOnFullWidth();
            }
            oldTime = tg.getDate();
        }
    }

    /**
     * Check if the object must go to full-width or not.
     * @param date1 The in-game date before the update.
     * @param date2 The in-game date now.
     * @return If the object must go to full-width or not.
     */
    protected boolean mustExpend(Date date1, Date date2) {
        int time1 = date1.getMin() + 60 * date1.getHour() + 24 * 60 * date1.getDay();
        int time2 = date2.getMin() + 60 * date2.getHour() + 24 * 60 * date2.getDay();

        return Math.abs(time2 - time1) > 1 || date1.getMonth() != date2.getMonth() || date1.getYear() != date2.getYear();
    }

    /**
     * @return The maximum width of this clock.
     */
    public float getWidth() {
//        String s = SuperviserNormally.getSupervisor().getTime().toString();
        GlyphLayout gl = new GlyphLayout();
        gl.setText(gs.getSmallFont(), textToShow);
        return gl.width + 2 * leftMargin;
    }

    /**
     * Start the animation for make the object appear.
     */
    protected void beginAnimation() {
        DoubleAnimation da = new DoubleAnimation(0, 1, 500);
        da.setRunningAction(() -> animationWidth = da.getActual() * getWidth());
        da.setEndingAction(() -> isBeingAnimated = false);
        AnimationManager.getInstance().addAnAnimation("TimeShowerAnim", da);
        isBeingAnimated = true;
    }

    /**
     * Start the animation for make the object appear on the whole screen.
     * @param text The text to show.
     */
    public void extendOnFullWidth(String text) {
        DoubleAnimation da = new DoubleAnimation(getWidth(), MasterOfMonsGame.WIDTH - leftMargin, EXPEND_TIME);
        da.setRunningAction(() -> animationWidth = da.getActual());
        da.setEndingAction(() -> {
            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.schedule(this::unextendFromFullWidth, 5, TimeUnit.SECONDS);
        });
        AnimationManager.getInstance().addAnAnimation("TimeShowerAnimExtend", da);
        isBeingAnimated = true;
        StringAnimation sa = new StringAnimation(text, EXPEND_TIME);
        sa.setRunningAction(() -> textToShow = sa.getActual());
        isTextBeingAnimated = true;
        AnimationManager.getInstance().addAnAnimation("SA_TimeShower_Extend", sa);
    }

    /**
     * Start the animation for make the object appear on the whole screen and show the time gone by.
     */
    public void extendOnFullWidth() {
        extendOnFullWidth(
                String.format("%s -> %s", oldTime.toString(), Supervisor.getSupervisor().getTime().toString()));
    }

    /**
     * Animate the object to make it go back to it's normal width.
     */
    protected void unextendFromFullWidth() {
        isBeingAnimated = true;
        isTextBeingAnimated = false;
        updateTime();
        DoubleAnimation da = new DoubleAnimation(MasterOfMonsGame.WIDTH - 2 * leftMargin, getWidth(), EXPEND_TIME);
        da.setRunningAction(() -> animationWidth = da.getActual());
        da.setEndingAction(() -> isBeingAnimated = false);
        AnimationManager.getInstance().addAnAnimation("TimeShowerAnimUnextend", da);
        StringAnimation sa = new StringAnimation(textToShow, EXPEND_TIME);
        sa.setRunningAction(() -> textToShow = sa.getActual());
        sa.setEndingAction(() -> isTextBeingAnimated = false);
        isTextBeingAnimated = true;
        AnimationManager.getInstance().addAnAnimation("SA_TimeShower_Unextend", sa);
    }
}
