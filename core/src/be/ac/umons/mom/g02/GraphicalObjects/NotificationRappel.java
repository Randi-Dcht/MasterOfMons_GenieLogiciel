package be.ac.umons.mom.g02.GraphicalObjects;

import be.ac.umons.mom.g02.Animations.DoubleAnimation;
import be.ac.umons.mom.g02.Animations.StringAnimation;
import be.ac.umons.mom.g02.Managers.AnimationManager;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;

public class NotificationRappel {

    protected int ANIM_TIME = 1500;

    GraphicalSettings gs;
    ShapeRenderer sr;
    /**
     * The horizontal margin
     */
    protected int leftMargin;
    /**
     * The vertical margin
     */
    protected int topMargin;
    protected String textToShow;

    protected boolean isBeingAnimated = false;
    protected boolean isHided = true;
    protected boolean mustHide = false;

    protected double animatedWidth = 0;


    public NotificationRappel(GraphicalSettings gs) {
        this.gs = gs;
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
        sr.setColor(gs.getTransparentBackgroundColor());
        leftMargin = MasterOfMonsGame.WIDTH / 100;
        topMargin = MasterOfMonsGame.HEIGHT / 100;
        textToShow = "";
    }

    public void draw(Batch batch, Point pos, Point size) {
        if (isHided)
            return;
        if (isBeingAnimated) {
            pos = new Point((int)(pos.x - animatedWidth + size.x), pos.y);
            size = new Point((int)animatedWidth, size.y);
        }
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.rect(pos.x, pos.y, size.x, size.y);
        sr.end();
        Gdx.gl.glDisable(GL30.GL_BLEND);
        batch.begin();
        gs.getSmallFont().draw(batch, textToShow, pos.x + leftMargin + (size.x - getWidth()) / 2, pos.y + gs.getSmallFont().getLineHeight() + topMargin);
        batch.end();
    }

    protected void expend() {
        isBeingAnimated = true;
        isHided = false;
        DoubleAnimation da = new DoubleAnimation(0, getWidth(), ANIM_TIME);
        da.setRunningAction(() -> animatedWidth = da.getActual());
        da.setEndingAction(() -> isBeingAnimated = false);
        StringAnimation sa = new StringAnimation(textToShow, ANIM_TIME);
        sa.setRunningAction(() -> textToShow = sa.getActual());
        AnimationManager.getInstance().addAnAnimation("DA_NotifRappelExpend", da);
        AnimationManager.getInstance().addAnAnimation("SA_NotifRappelExpend", sa);
    }

    protected void unexpend() {
        isBeingAnimated = true;
        DoubleAnimation da = new DoubleAnimation(getWidth(), 0, ANIM_TIME);
        da.setRunningAction(() -> animatedWidth = da.getActual());
        da.setEndingAction(() -> {
            isBeingAnimated = false;
            isHided = true;
            if (textToShow != null && ! mustHide)
                Gdx.app.postRunnable(this::expend);
            mustHide = false;
        });
        StringAnimation sa = new StringAnimation(textToShow, ANIM_TIME);
        sa.setRunningAction(() -> textToShow = sa.getActual());
        sa.invert();
        AnimationManager.getInstance().addAnAnimation("DA_NotifRappelUnexpend", da);
        AnimationManager.getInstance().addAnAnimation("SA_NotifRappelUnexpend", sa);
    }

    /**
     * @return The maximum width of this clock.
     */
    public float getWidth() {
        GlyphLayout gl = new GlyphLayout();
        gl.setText(gs.getSmallFont(), textToShow);
        return gl.width + 2 * leftMargin;
    }

    public void setTextToShow(String textToShow) {
        if (this.textToShow.equals("") && !textToShow.equals("")) {
            this.textToShow = textToShow;
            expend();
        } else if (!this.textToShow.equals("") && !textToShow.equals("")) {
            unexpend();
            this.textToShow = textToShow;
        } else { // textToShow == ""
            mustHide = true;
            unexpend();
        }
    }
}
