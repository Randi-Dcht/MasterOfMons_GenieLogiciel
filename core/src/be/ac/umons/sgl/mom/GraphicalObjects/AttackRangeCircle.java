package be.ac.umons.sgl.mom.GraphicalObjects;

import be.ac.umons.sgl.mom.Animations.DoubleAnimation;
import be.ac.umons.sgl.mom.GraphicalObjects.OnMapObjects.Character;
import be.ac.umons.sgl.mom.Managers.AnimationManager;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;

public class AttackRangeCircle {

    GraphicalSettings gs;
    ShapeRenderer sr;
    Character character;
    public int attackRange = 200;
    public int animatingAttackRange = 0;
    public boolean isRecovering = false;

    public AttackRangeCircle(GraphicalSettings gs, Character character) {
        this.gs = gs;
        this.character = character;
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
    }

    public void draw(Batch batch, Point pos) {
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
        sr.begin(ShapeRenderer.ShapeType.Filled);
//        sr.ellipse(pos.x - animatingAttackRange / 2, pos.y - animatingAttackRange / 2, animatingAttackRange, animatingAttackRange);
        sr.arc(pos.x, pos.y, animatingAttackRange / 2,  character.getOrientation().getDegrees(), 180f);
        sr.end();
        Gdx.gl.glDisable(GL30.GL_BLEND);
    }

    public void expand() {
        sr.setColor(gs.getAttackRangeColor());
        DoubleAnimation da = new DoubleAnimation(0, attackRange, 200);
        isRecovering = true;
        AnimationManager.getInstance().remove("AttackRangeCircleExpandAnim");
        AnimationManager.getInstance().addAnAnimation("AttackRangeCircleExpandAnim", da);
        da.setEndingAction(() -> unexpand());
        da.setRunningAction(() -> setAnimatingAttackRange(da.getActual().intValue()));
    }

    public void unexpand() {
        DoubleAnimation da = new DoubleAnimation(attackRange, 0, 200);
        AnimationManager.getInstance().addAnAnimation("AttackRangeCircleUnexpandAnim", da);
        da.setEndingAction(() -> isRecovering = false);
        da.setRunningAction(() -> setAnimatingAttackRange(da.getActual().intValue()));
        sr.setColor(gs.getRecoveringAttackRangeColor());
    }

    public boolean isRecovering() {
        return isRecovering;
    }

    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }

    public void setAnimatingAttackRange(int animatingAttackRange) {
        this.animatingAttackRange = animatingAttackRange;
    }

    public void dispose() {
        sr.dispose();
    }

}
