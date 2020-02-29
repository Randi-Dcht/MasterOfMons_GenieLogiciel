package be.ac.umons.sgl.mom.GraphicalObjects;

import be.ac.umons.sgl.mom.Animations.Animation;
import be.ac.umons.sgl.mom.Animations.DoubleAnimation;
import be.ac.umons.sgl.mom.Animations.StringAnimation;
import be.ac.umons.sgl.mom.Events.SuperviserNormally;
import be.ac.umons.sgl.mom.Managers.AnimationManager;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import be.ac.umons.sgl.mom.Other.Date;
import be.ac.umons.sgl.mom.Other.TimeGame;
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

    protected boolean isBeingAnimated = false;

    protected double animationWidth;

    protected Date oldTime = SuperviserNormally.getSupervisor().getTime().getDate();
    protected String textToShow;
    protected boolean isTextBeingAnimated = false;

    /**
     * @param gs The game's graphical settings.
     */
    public TimeShower(GraphicalSettings gs) {
        this.gs = gs;
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
        sr.setColor(gs.getTransparentBackgroundColor());
        leftMargin = MasterOfMonsGame.WIDTH / 100;
        topMargin = MasterOfMonsGame.HEIGHT / 100;
        beginAnimation();
        textToShow = SuperviserNormally.getSupervisor().getTime().toString();
    }

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

    protected void updateTime() {
        TimeGame tg = SuperviserNormally.getSupervisor().getTime();
        ;
        if (! isTextBeingAnimated)
            textToShow = tg.toString();
        if (mustExpend(oldTime, tg.getDate()) && ! isBeingAnimated) {
            extendOnFullWidth();
        }
        oldTime = tg.getDate();
    }

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

    protected void beginAnimation() {
        DoubleAnimation da = new DoubleAnimation(0, 1, 500);
        da.setRunningAction(() -> setAnimationWidth(da.getActual() * getWidth()));
        da.setEndingAction(() -> isBeingAnimated = false);
        AnimationManager.getInstance().addAnAnimation("TimeShowerAnim", da);
        isBeingAnimated = true;
    }

    public void extendOnFullWidth() {
        DoubleAnimation da = new DoubleAnimation(getWidth(), MasterOfMonsGame.WIDTH - leftMargin, 2000);
        da.setRunningAction(() -> setAnimationWidth(da.getActual()));
        da.setEndingAction(() -> {
            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.schedule(this::unextendFromFullWidth, 2, TimeUnit.SECONDS);
        });
        AnimationManager.getInstance().addAnAnimation("TimeShowerAnimExtend", da);
        isBeingAnimated = true;
        String s = String.format("%s -> %s", oldTime.toString(), SuperviserNormally.getSupervisor().getTime().toString());
        StringAnimation sa = new StringAnimation(s, 500);
        sa.setRunningAction(() -> textToShow = sa.getActual());
        isTextBeingAnimated = true;
        AnimationManager.getInstance().addAnAnimation("SA_TimeShower_Extend", sa);
    }

    protected void unextendFromFullWidth() {
        DoubleAnimation da = new DoubleAnimation(MasterOfMonsGame.WIDTH - 2 * leftMargin, getWidth(), 2000);
        da.setRunningAction(() -> setAnimationWidth(da.getActual()));
        da.setEndingAction(() -> isBeingAnimated = false);
        AnimationManager.getInstance().addAnAnimation("TimeShowerAnimUnextend", da);
        isBeingAnimated = true;
        isTextBeingAnimated = false;
        updateTime();
        StringAnimation sa = new StringAnimation(textToShow, 2000);
        sa.setRunningAction(() -> textToShow = sa.getActual());
        sa.setEndingAction(() -> isTextBeingAnimated = false);
        isTextBeingAnimated = true;
        AnimationManager.getInstance().addAnAnimation("SA_TimeShower_Unextend", sa);
    }

    public void setAnimationWidth(double animationWidth) {
        this.animationWidth = animationWidth;
    }
}
