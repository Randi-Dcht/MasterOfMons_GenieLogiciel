package be.ac.umons.mom.g02.GraphicalObjects.Controls;

import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;

public class KeySelector extends Control {

    protected boolean isSelected = false;
    protected ShapeRenderer sr;
    protected String actualKey;
    protected int actualKeyCode;

    /**
     * @param gim The game's input manager
     * @param gs The game's graphical settings.
     */
    public KeySelector(GameInputManager gim, GraphicalSettings gs) {
        super(gim, gs);
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
        actualKeyCode = -1;
        actualKey = "";
    }

    public KeySelector() {}

    @Override
    public void draw(Batch batch, Point pos, Point size) {
        super.draw(batch, pos, size);
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
        if (isSelected)
            sr.setColor(new com.badlogic.gdx.graphics.Color(0x0288D1)); // TODO Put in Settings
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

}
