package be.ac.umons.mom.g02.GraphicalObjects.Controls;

import be.ac.umons.mom.g02.Animations.Animation;
import be.ac.umons.mom.g02.Animations.DoubleAnimation;
import be.ac.umons.mom.g02.Managers.AnimationManager;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Objects.Course;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Enums.KeyStatus;
import be.ac.umons.mom.g02.Regulator.SuperviserNormally;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;
import java.util.ArrayList;

/**
 * This represent the control the user can draw to check its agenda.
 */
public class AgendaShower extends Control {

    /**
     * The courses to list in this control.
     */
    protected ArrayList<Course> courses;
    /**
     * A double animation used for showing and hiding.
     */
    protected DoubleAnimation da;
    /**
     * If this control is being animated
     */
    protected boolean isBeingAnimated = false;
    /**
     * If this control must be drawn to the screen.
     */
    protected boolean showed = false;
    /**
     * Allow to draw shapes.
     */
    protected ShapeRenderer sr;

    /**
     * @param gim The game's input manager
     * @param gs The game's graphical settings.
     */
    public AgendaShower(GameInputManager gim, GraphicalSettings gs) {
        super(gim, gs);
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
        sr.setColor(gs.getControlTransparentBackgroundColor());

        courses = new ArrayList<>();
    }

    /**
     * Draw this control.
     * @param batch Where to draw this control.
     */
    public void draw(Batch batch) {
        if (! showed)
            return;
        Point size = getMaximumSize(courses);
        if (isBeingAnimated) {
            size.x = (int)(size.x * da.getActual());
            size.y = (int)(size.y * da.getActual());
        }
        Point pos = new Point(leftMargin, topMargin);
        super.draw(batch, pos, size);


        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.rect(pos.x, pos.y, size.x, size.y);
        sr.end();
        Gdx.gl.glDisable(GL30.GL_BLEND);

        int alreadyUsed = topMargin;

        batch.begin();
        for (Course c : courses) {
            gs.getQuestFont().draw(batch, c.toString(), pos.x + leftMargin, pos.y + size.y - alreadyUsed);
            alreadyUsed += gs.getQuestFont().getLineHeight() + topMargin;
        }
        batch.end();
    }

    /**
     * @param courses The list of course that will be shown.
     * @return The maximum size of this control with the given courses.
     */
    protected Point getMaximumSize(ArrayList<Course> courses) {
        int maxX = 0, maxY = 2 * topMargin;
        for (Course c : courses) {
            GlyphLayout gl = new GlyphLayout();
            gl.setText(gs.getQuestFont(), c.toString());
            if (gl.width + 2 * leftMargin > maxX)
                maxX = (int)(gl.width + 2 * leftMargin);
            maxY += gs.getQuestFont().getLineHeight() + topMargin;
        }
        return new Point(maxX, maxY);
    }

    @Override
    public void handleInput() {
        if (gim.isKey(Input.Keys.A, KeyStatus.Pressed))
            show();
        else if (gim.isKey(Input.Keys.A, KeyStatus.UnPressed))
            hide();
    }

    /**
     * Begin the animation that will be showing the control.
     */
    protected void show() {
        courses = SuperviserNormally.getSupervisor().getPeople().getPlanning().get(
                SuperviserNormally.getSupervisor().getTime().getDate().getDay()
        );
        if (courses == null)
            return;
        isBeingAnimated = true;
        da = new DoubleAnimation(0, 1, 500);
        da.setEndingAction(() -> isBeingAnimated = false);
        AnimationManager.getInstance().addAnAnimation("AgendaShowingAnim", da);
        showed = true;
    }

    /**
     * Begin the animation that will be hiding the control.
     */
    protected void hide() {
        isBeingAnimated = true;
        Animation showAnim = AnimationManager.getInstance().get("AgendaShowingAnim");
        double from = (showAnim == null ? 1 : (double)showAnim.getActual());
        da = new DoubleAnimation(from, 0, 500 * from);
        AnimationManager.getInstance().remove("AgendaShowingAnim");
        da.setEndingAction(() -> { isBeingAnimated = false; showed = false; } );
        AnimationManager.getInstance().addAnAnimation("AgendaHidingAnim", da);
    }

    @Override
    public void dispose() {
        sr.dispose();
    }
}
