package be.ac.umons.mom.g02.GraphicalObjects.Controls;

import be.ac.umons.mom.g02.Enums.KeyStatus;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;

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
    protected int selectedPosition=0;
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
     * If the <code>TextBox</code> accepts ONLY characters corresponding the hexa-decimal encodage(1-9, A-F)
     */
    protected boolean acceptOnlyHexadecimal = false;

    protected boolean mustShowVerticalBar = false;
    protected double timeGone = 0;

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
        GlyphLayout gl = new GlyphLayout();
        gl.setText(gs.getNormalFont(), actualText.substring(0, selectedPosition));
        float lineX = x + gl.width + leftMargin;
        if (mustShowVerticalBar) {
            sr.setColor(gs.getNormalFont().getColor());
            sr.line(lineX, y, lineX, y + height);
        }
        sr.end();
        batch.begin();
        gs.getNormalFont().draw(batch, actualText + suffix, x + leftMargin, y + gs.getNormalFont().getLineHeight());
        batch.end();
        Gdx.gl.glDisable(GL30.GL_BLEND);

    }

    public void update(double dt) {
        timeGone += dt;
        if (timeGone > .5) {
            timeGone = 0;
            if (isSelected)
                mustShowVerticalBar = ! mustShowVerticalBar;
        }
    }

    @Override
    public void handleInput() {
        for (Point click : gim.getRecentClicks()) {
            isSelected = new Rectangle(x, MasterOfMonsGame.HEIGHT - y - height, width, height).contains(click);
            if (isSelected) {
                mustShowVerticalBar = true;
                selectedPosition = actualText.length();
            }
            else
                mustShowVerticalBar = false;
        }

        if (gim.isKey(Input.Keys.RIGHT, KeyStatus.Pressed))
            if (selectedPosition < actualText.length())
                selectedPosition++;
        if (gim.isKey(Input.Keys.LEFT, KeyStatus.Pressed))
            if (selectedPosition > 0)
                selectedPosition--;

        if (isSelected) {
            for (char c : gim.getLastChars()) {
                if (acceptOnlyNumbers && (c < '0' || c > '9'))
                    continue;
                if (acceptOnlyHexadecimal && ((c < '0' || c > '9') && (c < 'a' || c > 'f') && (c < 'A' || c > 'F')))
                    continue;
                if (Character.isLetterOrDigit(c)) {
                    if (selectedPosition == actualText.length())
                        actualText += c;
                    else if (selectedPosition == 0)
                        actualText = c + actualText;
                    else {
                        actualText = actualText.substring(0, selectedPosition) + c + actualText.substring(selectedPosition);
                    }
                    selectedPosition++;
                }
            }
            if (gim.isKey(Input.Keys.BACKSPACE, KeyStatus.Pressed) && actualText.length() > 0) {
                if (selectedPosition == 0)
                    return;
                else if (selectedPosition == 1)
                    actualText = actualText.substring(1);
                else if (selectedPosition == actualText.length())
                    actualText = actualText.substring(0, actualText.length() - 1);
                else
                    actualText = actualText.substring(0, selectedPosition - 1) + actualText.substring(selectedPosition);
                selectedPosition--;
                if (selectedPosition > actualText.length())
                    selectedPosition = actualText.length();
                else if (selectedPosition < 0)
                    selectedPosition = 0;

            }
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
     * @param acceptOnlyNumbers If the <code>TextBox</code> accepts only numbers.
     */
    public void setAcceptOnlyNumbers(boolean acceptOnlyNumbers) {
        this.acceptOnlyNumbers = acceptOnlyNumbers;
    }

    /**
     * @param acceptOnlyHexadecimal If the <code>TextBox</code> accepts ONLY characters corresponding the hexa-decimal encodage(1-9, A-F)
     */
    public void setAcceptOnlyHexadecimal(boolean acceptOnlyHexadecimal) {
        this.acceptOnlyHexadecimal = acceptOnlyHexadecimal;
    }
}
