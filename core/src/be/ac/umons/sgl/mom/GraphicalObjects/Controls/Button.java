package be.ac.umons.sgl.mom.GraphicalObjects.Controls;

import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;

/**
 * Un bouton avec du texte.
 * @author Guillaume Cardoen
 */
public class Button extends Control {
    protected Color backgroundColor = new Color(0x21212163);

    protected Color isMouseOverBackgroundColor = new Color(0x21212142);

    protected Color isSelectedBackgroundColor = new Color(0x512DA8FF);

    /**
     * L'action a éffectué quand le bouton reçoit un clique.
     */
    protected Runnable onClick;
    /**
     * Permet de dessiner les formes comme les rectangles.
     */
    protected ShapeRenderer sr;
    /**
     * Le texte affiché sur le bouton.
     */
    protected String textToShow = "";
    /**
     * Si la souris est au dessus du bouton ou non.
     */
    protected boolean isMouseOver;

    protected BitmapFont font;

    protected boolean selected;

    /**
     * Crée un nouveau bouton.
     * @param gim Le GameInputManager du jeu.
     * @param gs Les paramètres graphiques à utiliser.
     */
    public Button(GameInputManager gim, GraphicalSettings gs) {
        super(gim, gs);
        sr = new ShapeRenderer();
        font = gs.getNormalFont();
    }
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
     * Retourne le texte affiché.
     * @return Le texte affiché.
     */
    public String getText() {
        return textToShow;
    }

    /**
     * Défini le texte à afficher.
     * @param text Le texte à afficher.
     */
    public void setText(String text) {
        this.textToShow = text;
    }

    /**
     * Défini l'action à éxécuter si l'on clique sur le bouton.
     * @param onClick L'action à éxécuter en cas de clique.
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

    public void setFont(BitmapFont font) {
        this.font = font;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }
}
