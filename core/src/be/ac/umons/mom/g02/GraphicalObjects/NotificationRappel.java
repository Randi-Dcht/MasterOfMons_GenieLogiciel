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
import java.util.HashMap;
import java.util.Iterator;

public class NotificationRappel {

    protected final int NOTIFICATION_TIME = 20;

    protected final int ANIM_TIME = 1500;

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
    protected String showedText;

    protected HashMap<String, String> notificationsToShow;
    Iterator<String> notificationsKeyIterator;

    protected boolean isBeingAnimated = false;
    protected boolean isHided = true;
    protected boolean mustHide = false;

    protected double animatedWidth = 0;

    protected double timeBetweenNotification = NOTIFICATION_TIME;


    public NotificationRappel(GraphicalSettings gs) {
        this.gs = gs;
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
        sr.setColor(gs.getTransparentBackgroundColor());
        leftMargin = MasterOfMonsGame.WIDTH / 100;
        topMargin = MasterOfMonsGame.HEIGHT / 100;
        textToShow = "";
        showedText = "";
        notificationsToShow = new HashMap<>();
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
        gs.getSmallFont().draw(batch, showedText, pos.x + leftMargin + (size.x - getActualWidth()) / 2, pos.y + gs.getSmallFont().getLineHeight() + topMargin);
        batch.end();
    }

    public void update(double dt) {
        timeBetweenNotification -= dt;
        if (timeBetweenNotification <= 0)
            showNextNotification();
    }

    protected void expend() {
        AnimationManager.getInstance().remove("DA_NotifRappelUnexpend");
        AnimationManager.getInstance().remove("SA_NotifRappelUnexpend");
        isBeingAnimated = true;
        isHided = false;
        DoubleAnimation da = new DoubleAnimation(0, getWidth(), ANIM_TIME);
        da.setRunningAction(() -> animatedWidth = da.getActual());
        da.setEndingAction(() -> isBeingAnimated = false);
        StringAnimation sa = new StringAnimation(textToShow, ANIM_TIME);
        sa.setRunningAction(() -> showedText = sa.getActual());
        AnimationManager.getInstance().addAnAnimation("DA_NotifRappelExpend", da);
        AnimationManager.getInstance().addAnAnimation("SA_NotifRappelExpend", sa);
    }

    protected void unexpend() {
        AnimationManager.getInstance().remove("DA_NotifRappelExpend");
        AnimationManager.getInstance().remove("SA_NotifRappelExpend");
        isBeingAnimated = true;
        DoubleAnimation da = new DoubleAnimation(getActualWidth(), 0, ANIM_TIME);
        da.setRunningAction(() -> animatedWidth = da.getActual());
        da.setEndingAction(() -> {
            isBeingAnimated = false;
            isHided = true;
            if (! textToShow.equals("") && ! mustHide)
                Gdx.app.postRunnable(this::expend);
            mustHide = false;
        });
        StringAnimation sa = new StringAnimation(showedText, ANIM_TIME);
        sa.setRunningAction(() -> showedText = sa.getActual());
        sa.invert();
        AnimationManager.getInstance().addAnAnimation("DA_NotifRappelUnexpend", da);
        AnimationManager.getInstance().addAnAnimation("SA_NotifRappelUnexpend", sa);
    }

    /**
     * @return The width of this clock.
     */
    public float getWidth() {
        GlyphLayout gl = new GlyphLayout();
        gl.setText(gs.getSmallFont(), textToShow);
        return gl.width + 2 * leftMargin;
    }
    /**
     * @return The of this clock.
     */
    public float getActualWidth() {
        GlyphLayout gl = new GlyphLayout();
        gl.setText(gs.getSmallFont(), showedText);
        return gl.width + 2 * leftMargin;
    }

    public void addANotification(String notificationName, String notificationContent) {
        notificationsToShow.put(notificationName, notificationContent);
        setTextToShow(notificationContent);
        notificationsKeyIterator = notificationsToShow.keySet().iterator();
        timeBetweenNotification = NOTIFICATION_TIME;
    }

    public void removeANotification(String notificationName) {
        notificationsToShow.remove(notificationName);
        if (notificationsToShow.size() == 0)
            setTextToShow("");
    }

    protected void showNextNotification() {
        if (notificationsToShow.size() < 2)
            return;
        if (! notificationsKeyIterator.hasNext())
            notificationsKeyIterator = notificationsToShow.keySet().iterator();
        String next = notificationsKeyIterator.next();
        setTextToShow(notificationsToShow.get(next));
        timeBetweenNotification = NOTIFICATION_TIME;
    }

    protected void setTextToShow(String textToShow) {
        if (this.textToShow.equals("") && ! textToShow.equals("")) {
            this.textToShow = textToShow;
            expend();
        } else if (! this.textToShow.equals("") && ! textToShow.equals("")) {
            unexpend();
            this.textToShow = textToShow;
        } else { // textToShow == ""
            mustHide = true;
            unexpend();
        }
    }
}
