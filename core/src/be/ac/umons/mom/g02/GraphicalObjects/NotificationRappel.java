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

/**
 * Represent a small notification shower (for reminding them)
 */
public class NotificationRappel {
    /**
     * The time between each notification (in s).
     */
    protected final int NOTIFICATION_TIME = 20;
    /**
     * The time of the animations (in ms).
     */
    protected final int ANIM_TIME = 1500;
    /**
     * The game's graphical settings.
     */
    GraphicalSettings gs;
    /**
     * Allow to draw shapes.
     */
    ShapeRenderer sr;
    /**
     * The horizontal margin
     */
    protected int leftMargin;
    /**
     * The vertical margin
     */
    protected int topMargin;
    /**
     * The text that need to be showed when all the animations are finished.
     */
    protected String textToShow;
    /**
     * The text shown at the moment.
     */
    protected String showedText;
    /**
     * The link between the notification's name and it's content.
     */
    protected HashMap<String, String> notificationsToShow;
    /**
     * The iterator to remember where we are in the list.
     */
    Iterator<String> notificationsKeyIterator;
    /**
     * If the object is being animated.
     */
    protected boolean isBeingAnimated = false;
    /**
     * If the object is hided.
     */
    protected boolean isHided = true;
    /**
     * If the object must be hided after the animations.
     */
    protected boolean mustHide = false;
    /**
     * The width of the object during an animation
     */
    protected double animatedWidth = 0;
    /**
     * The remaining time before changing the notification (in s).
     */
    protected double timeBetweenNotification = NOTIFICATION_TIME;

    /**
     * @param gs The game's graphical settings.
     */
    public NotificationRappel(GraphicalSettings gs) {
        this.gs = gs;
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
        sr.setColor(gs.getControlTransparentBackgroundColor());
        leftMargin = MasterOfMonsGame.WIDTH / 100;
        topMargin = MasterOfMonsGame.HEIGHT / 100;
        textToShow = "";
        showedText = "";
        notificationsToShow = new HashMap<>();
    }

    /**
     * Draw the object on <code>batch</code> with the given parameters.
     * @param batch The batch where to draw the control.
     * @param pos The control's position.
     * @param size The control's size.
     */
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

    /**
     * Update the timing of the object.
     * @param dt The delta-time with precedent call.
     */
    public void update(double dt) {
        timeBetweenNotification -= dt;
        if (timeBetweenNotification <= 0)
            showNextNotification();
    }

    /**
     * Animate the object's width (and text) from 0 to it's maximal width.
     */
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

    /**
     * Animate the object's width (and text) from it's maximal width to 0.
     */
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

    /**
     * Add a notification to show.
     * @param notificationName The name of the notification (non-shown)
     * @param notificationContent The content of the notification (shown)
     */
    public void addANotification(String notificationName, String notificationContent) {
        notificationsToShow.put(notificationName, notificationContent);
        setTextToShow(notificationContent);
        notificationsKeyIterator = notificationsToShow.keySet().iterator();
        timeBetweenNotification = NOTIFICATION_TIME;
    }

    /**
     * Remove a notification.
     * @param notificationName The notification's name.
     */
    public void removeANotification(String notificationName) {
        notificationsToShow.remove(notificationName);
        if (notificationsToShow.size() == 0)
            setTextToShow("");
    }

    /**
     * Switch the current notification with the next one.
     */
    protected void showNextNotification() {
        if (notificationsToShow.size() < 2)
            return;
        if (! notificationsKeyIterator.hasNext())
            notificationsKeyIterator = notificationsToShow.keySet().iterator();
        String next = notificationsKeyIterator.next();
        setTextToShow(notificationsToShow.get(next));
        timeBetweenNotification = NOTIFICATION_TIME;
    }

    /**
     * Set the text that must be shown and animate the object.
     * @param textToShow The text that must be shown.
     */
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
