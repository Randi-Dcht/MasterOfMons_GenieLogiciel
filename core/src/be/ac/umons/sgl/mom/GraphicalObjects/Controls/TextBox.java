package be.ac.umons.sgl.mom.GraphicalObjects.Controls;

import be.ac.umons.sgl.mom.Enums.KeyStatus;
import be.ac.umons.sgl.mom.GraphicalObjects.Controls.Control;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;
import java.util.stream.IntStream;

/**
 * A personal text field adapted to the purpose of the game.
 * @author Guillaume Cardoen
 */
public class TextBox extends Control {

    /**
     * Allow to draw shapes
     */
    protected ShapeRenderer sr;
    /**
     * The actual text entered
     */
    protected String actualText = "";
    /**
     * The suffix that will be placed at the end of the text
     */
    protected String suffix = "";
    /**
     * Is the TextBox selected
     */
    protected boolean isSelected = false;

    /**
     * If the <code>TextBox</code> accepts ONLY numbers or not.
     */
    protected boolean acceptOnlyNumbers = false;

    /**
     * @param gim The game's input manager
     * @param gs The game's graphical settings.
     */
    public TextBox(GameInputManager gim, GraphicalSettings gs) {
        super(gim, gs);
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
    }

    /**
     * Default constructor. USE IT ONLY FOR TEST
     */
    protected TextBox() {}

    @Override
    public void draw(Batch batch, Point pos, Point size) {
        super.draw(batch, pos, size);
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
        if (isSelected)
            sr.setColor(new Color(0x0288D1));
        else
            sr.setColor(Color.WHITE);
        sr.begin();
        sr.rect(x, y, width, height);
        sr.end();
        batch.begin();
        gs.getNormalFont().draw(batch, actualText + suffix, x + leftMargin, y + gs.getNormalFont().getLineHeight());
        batch.end();
        Gdx.gl.glDisable(GL30.GL_BLEND);

    }

    @Override
    public void handleInput() {
        for (Point click : gim.getRecentClicks())
            isSelected = new Rectangle(x, MasterOfMonsGame.HEIGHT - y - height, width, height).contains(click);
        if (isSelected) {
            for (char c : gim.getLastChars()) {
                if (acceptOnlyNumbers && (c < '0' || c > '9'))
                    continue;
                if (Character.isLetterOrDigit(c))
                    actualText += c;
            }
            if (gim.isKey(Input.Keys.BACKSPACE, KeyStatus.Pressed))
                actualText = actualText.substring(0, actualText.length() - 1);
        }
    }

    @Override
    public void dispose() {
        sr.dispose();
    }

    /**
     * @return The text entered with the previously given suffix.
     */
    public String getText() {
        return actualText + suffix;
    }

    /**
     * Set the text showed in the <code>TextBox</code>
     * @param text The text that will be showed in the <code>TextBox</code>
     */
    public void setText(String text) {
        this.actualText = text;
    }

    /**
     * @param suffix Set the suffix to add at the end of the actual text.
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    /**
     * Define if the <code>TextBox</code> accepts only numbers.
     * @param acceptOnlyNumbers
     */
    public void setAcceptOnlyNumbers(boolean acceptOnlyNumbers) {
        this.acceptOnlyNumbers = acceptOnlyNumbers;
    }
}
