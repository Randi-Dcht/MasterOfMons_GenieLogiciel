package be.ac.umons.sgl.mom.GraphicalObjects.Controls;

import be.ac.umons.sgl.mom.Animations.DoubleAnimation;
import be.ac.umons.sgl.mom.Enums.KeyStatus;
import be.ac.umons.sgl.mom.Enums.Lesson;
import be.ac.umons.sgl.mom.Events.SuperviserNormally;
import be.ac.umons.sgl.mom.Managers.AnimationManager;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Objects.Course;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;
import java.util.ArrayList;

public class AgendaShower extends Control {

    protected ArrayList<Course> courses;

    protected DoubleAnimation da;
    protected boolean isBeingAnimated = false;
    protected boolean showed = false;

    protected ShapeRenderer sr;

    public AgendaShower(GameInputManager gim, GraphicalSettings gs) {
        super(gim, gs);
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
        sr.setColor(new Color(0x21212142));

        courses = new ArrayList<>();
    }

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

    protected void hide() {
        isBeingAnimated = true;
        da = new DoubleAnimation(1, 0, 500);
        da.setEndingAction(() -> { isBeingAnimated = false; showed = false; } );
        AnimationManager.getInstance().addAnAnimation("AgendaHidingAnim", da);
    }

    @Override
    public void dispose() {
        sr.dispose();
    }
}
