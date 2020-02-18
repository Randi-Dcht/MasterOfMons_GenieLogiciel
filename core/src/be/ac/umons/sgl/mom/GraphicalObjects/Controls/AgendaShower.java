package be.ac.umons.sgl.mom.GraphicalObjects.Controls;

import be.ac.umons.sgl.mom.Animations.DoubleAnimation;
import be.ac.umons.sgl.mom.Enums.KeyStatus;
import be.ac.umons.sgl.mom.Enums.Lesson;
import be.ac.umons.sgl.mom.GraphicalObjects.Controls.Control;
import be.ac.umons.sgl.mom.Managers.AnimationManager;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
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

    protected ArrayList<Lesson> lessons;

    protected DoubleAnimation da;
    protected boolean isBeingAnimated = false;

    protected ShapeRenderer sr;

    public AgendaShower(GameInputManager gim, GraphicalSettings gs) {
        super(gim, gs);
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
        sr.setColor(new Color(0x21212142));

        lessons = new ArrayList<>();
        lessons.add(Lesson.nbComplexe);
        lessons.add(Lesson.statistique);
    }

    public void draw(Batch batch) {
        Point size = getMaximumSize(lessons);
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
        for (Lesson l : lessons) {
            gs.getQuestFont().draw(batch, l.toString(), pos.x + leftMargin, pos.y + size.y - alreadyUsed);
            alreadyUsed += gs.getQuestFont().getLineHeight() + topMargin;
        }
        batch.end();
    }

    protected Point getMaximumSize(ArrayList<Lesson> lessons) {
        int maxX = 0, maxY = 2 * topMargin;
        for (Lesson l : lessons) {
            GlyphLayout gl = new GlyphLayout();
            gl.setText(gs.getQuestFont(), l.toString());
            if (gl.width + 2 * leftMargin > maxX)
                maxX = (int)(gl.width + 2 * leftMargin);
            maxY += gs.getQuestFont().getLineHeight() + topMargin;
        }
        return new Point(maxX, maxY);
    }

    @Override
    public void handleInput() {
        if (gim.isKey(Input.Keys.A, KeyStatus.Pressed))
            beginShowing();
    }

    protected void beginShowing() {
//        da = new DoubleAnimation(0, 1, 2000);
//        AnimationManager.getInstance().addAnAnimation("AgendaShowingAnim", da);
    }

    @Override
    public void dispose() {

    }
}
