package be.ac.umons.mom.g02.GraphicalObjects;

import be.ac.umons.mom.g02.Animations.DoubleAnimation;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Character;
import be.ac.umons.mom.g02.Managers.AnimationManager;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;

/**
 * The circle drawn when an attack is done.
 */
public class AttackRangeCircle {
    /**
     * The graphical's settings.
     */
    protected GraphicalSettings gs;
    /**
     * Allow to draw shapes.
     */
    protected ShapeRenderer sr;
    /**
     * The character on which the circle is bound.
     */
    protected Character character;
    /**
     * The character's attack range
     */
    protected int attackRange = 200;
    /**
     * The attack range to show when the circle is animated.
     */
    protected int animatingAttackRange = 0;
    /**
     * If the character is recovering from his attack.
     */
    protected boolean isRecovering = false;

    /**
     * @param gs The game's graphical settings.
     * @param character The character on which the circle is bound.
     */
    public AttackRangeCircle(GraphicalSettings gs, Character character) {
        this.gs = gs;
        this.character = character;
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
    }

    /**
     * Draw the circle on the given position.
     * @param pos The position.
     */
    public void draw(Point pos) {
        if (animatingAttackRange <= 0)
            return;
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
        sr.begin(ShapeRenderer.ShapeType.Filled);
//        sr.ellipse(pos.x - animatingAttackRange / 2, pos.y - animatingAttackRange / 2, animatingAttackRange, animatingAttackRange);
        sr.arc(pos.x, pos.y, animatingAttackRange / 2,  character.getOrientation().getDegrees(), 180f);
        sr.end();
        Gdx.gl.glDisable(GL30.GL_BLEND);
    }

    /**
     * Init the circle expansion.
     */
    public void expand() {
        sr.setColor(gs.getAttackRangeColor());
        DoubleAnimation da = new DoubleAnimation(0, attackRange, 200);
        isRecovering = true;
        AnimationManager.getInstance().remove("AttackRangeCircleExpandAnim" + character.getCharacteristics().getName());
        AnimationManager.getInstance().addAnAnimation("AttackRangeCircleExpandAnim" + character.getCharacteristics().getName(), da);
        da.setEndingAction(this::retract);
        da.setRunningAction(() -> {
            animatingAttackRange = da.getActual().intValue();
//            Gdx.app.log("AttackRangeCircle", "attackRange=" + animatingAttackRange);
        });
    }

    /**
     * Init the circle retractation.
     */
    public void retract() {
        DoubleAnimation da = new DoubleAnimation(attackRange, 0, 200);
        AnimationManager.getInstance().addAnAnimation("AttackRangeCircleRetractingAnim" + character.getCharacteristics().getName(), da);
        da.setEndingAction(() -> isRecovering = false);
        da.setRunningAction(() -> animatingAttackRange = da.getActual().intValue());
        sr.setColor(gs.getRecoveringAttackRangeColor());
    }

    /**
     * @return If the character is recovering.
     */
    public boolean isRecovering() {
        return isRecovering;
    }

    /**
     * @param attackRange The character attack range.
     */
    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }

    /**
     * Dispose all the resources given to this object.
     */
    public void dispose() {
        sr.dispose();
    }

}
