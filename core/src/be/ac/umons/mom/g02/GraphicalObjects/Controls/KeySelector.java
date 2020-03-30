package be.ac.umons.mom.g02.GraphicalObjects.Controls;

import be.ac.umons.mom.g02.Managers.GameColorManager;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;

/**
 * Represent a <code>TextBox</code> reacting for choosing only one key.
 */
public class KeySelector extends Control {

    /**
     * If the <code>KeySelector</code> is selected.
     */
    protected boolean isSelected = false;
    /**
     * Allow to draw shapes.
     */
    protected ShapeRenderer sr;
    /**
     * The string representing which key is selected.
     */
    protected String actualKey;
    /**
     * The code of the key which is selected.
     */
    protected int actualKeyCode;

    /**
     * @param gs The game's graphical settings.
     */
    public KeySelector(GraphicalSettings gs) {
        super(gs);
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
        actualKeyCode = -1;
        actualKey = "";
    }

    /**
     * USES ONLY FOR TESTS
     */
    public KeySelector() {}

    @Override
    public void draw(Batch batch, Point pos, Point size) {
        super.draw(batch, pos, size);
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
        if (isSelected)
            sr.setColor(gcm.getColorFor("controlSelected"));
        else
            sr.setColor(Color.WHITE);
        sr.begin();
        sr.rect(x, y, width, height);
        sr.end();
        batch.begin();
        gs.getNormalFont().draw(batch, actualKey, x + leftMargin, y + gs.getNormalFont().getLineHeight());
        batch.end();
        Gdx.gl.glDisable(GL30.GL_BLEND);
    }

    @Override
    public void handleInput() {
        if (isSelected && gim.getLastKeyPressedCode() != -1) {
            actualKeyCode = gim.getLastKeyPressedCode();
            actualKey = Input.Keys.toString(actualKeyCode);
            isSelected = false;
        }
        for (Point click : gim.getRecentClicks())
            isSelected = new Rectangle(x, MasterOfMonsGame.HEIGHT - y - height, width, height).contains(click);
    }

    @Override
    public void dispose() {
        sr.dispose();
    }

    /**
     * @return The code of the key which is selected.
     */
    public int getActualKeyCode() {
        return actualKeyCode;
    }

    /**
     * @param actualKeyCode The code of the key which is selected.
     */
    public void setActualKeyCode(int actualKeyCode) {
        this.actualKeyCode = actualKeyCode;
        actualKey = Input.Keys.toString(actualKeyCode);
    }
}
