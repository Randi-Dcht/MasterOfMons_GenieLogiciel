package be.ac.umons.mom.g02.GraphicalObjects.Controls;

import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;

/**
 * A button.
 * @author Guillaume Cardoen
 */
public class Button extends Control {
    /**
     * The background's color.
     */
    protected Color backgroundColor;
    /**
     * The background's color when the mouse is over it.
     */
    protected Color isMouseOverBackgroundColor = new Color(0x21212142);
    /**
     * The background color when the button is selected.
     */
    protected Color isSelectedBackgroundColor = new Color(0x512DA8FF);
    /**
     * Action to do when the button is clicked.
     */
    protected Runnable onClick;
    /**
     * Allow to draw shapes.
     */
    protected ShapeRenderer sr;
    /**
     * The text showed in the button.
     */
    protected String textToShow = "";
    /**
     * If the mouse is over the button.
     */
    protected boolean isMouseOver;
    /**
     * The font to use.
     */
    protected BitmapFont font;
    /**
     * If the button is selected.
     */
    protected boolean selected;

    /**
     * @param gim The game's input manager
     * @param gs The game's graphical settings
     */
    public Button(GameInputManager gim, GraphicalSettings gs) {
        super(gim, gs);
        sr = new ShapeRenderer();
        font = gs.getNormalFont();
        backgroundColor = gs.getControlTransparentBackgroundColor();
    }

    /**
     * Default constructor. USE IT ONLY FOR TEST.
     */
    protected Button() {}

    @Override
    public void draw(Batch batch, Point pos, Point size) {
        super.draw(batch, pos, size);
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);

        sr.begin(ShapeRenderer.ShapeType.Filled);
        if (selected)
            sr.setColor(isSelectedBackgroundColor);
        else if (isMouseOver)
            sr.setColor(isMouseOverBackgroundColor);
        else
            sr.setColor(backgroundColor);
        sr.rect(x, y, width, height);
        sr.end();
        GlyphLayout gl = new GlyphLayout();
        gl.setText(font, textToShow);
        batch.begin();
        font.draw(batch, textToShow, (int)(x + (double)width / 2 - gl.width / 2), (int)(y + (double)height / 2 + gl.height / 2));
        batch.end();
        Gdx.gl.glDisable(GL30.GL_BLEND);
    }

    @Override
    public void handleInput() {
        Rectangle buttonRectangle = new Rectangle(x, MasterOfMonsGame.HEIGHT - y - height, width, height);
        for (Point click : gim.getRecentClicks())
            if (onClick != null && buttonRectangle.contains(click))
                onClick.run();
        isMouseOver = buttonRectangle.contains(gim.getLastMousePosition());

    }

    @Override
    public void dispose() {
        sr.dispose();
    }

    /**
     * @return The showed text.
     */
    public String getText() {
        return textToShow;
    }

    /**
     * @param text The text to show.
     */
    public void setText(String text) {
        this.textToShow = text;
    }

    /**
     * Set the action to do when the button is clicked.
     * @param onClick Action to do when the button is clicked.
     */
    public void setOnClick(Runnable onClick) {
        this.onClick = onClick;
    }

    /**
     * Return the runnable to execute if a click is detected.
     * @return The runnable to execute if a click is detected.
     */
    public Runnable getOnClick() {
        return onClick;
    }

    /**
     * Set the font to use.
     * @param font The font to use.
     */
    public void setFont(BitmapFont font) {
        this.font = font;
    }

    /**
     * Set if the button is selected or not.
     * @param selected If the button is selected or not.
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * @return If the button is selected or not.
     */
    public boolean isSelected() {
        return selected;
    }
}
