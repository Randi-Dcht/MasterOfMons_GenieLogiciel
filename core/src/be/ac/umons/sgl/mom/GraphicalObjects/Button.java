package be.ac.umons.sgl.mom.GraphicalObjects;

import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;

/**
 * Un bouton avec du texte.
 */
public class Button extends Control {

    /**
     * L'action a éffectué quand le bouton reçoit un clique.
     */
    private Runnable onClick;
    /**
     * Permet de dessiner les formes comme les rectangles.
     */
    private ShapeRenderer sr;
    /**
     * Le texte affiché sur le bouton.
     */
    private String textToShow = "";
    /**
     * Si la souris est au dessus du bouton ou non.
     */
    private boolean isMouseOver;

    /**
     * Crée un nouveau bouton.
     * @param gim Le GameInputManager du jeu.
     * @param gs Les paramètres graphiques à utiliser.
     */
    public Button(GameInputManager gim, GraphicalSettings gs) {
        super(gim, gs);
        sr = new ShapeRenderer();
    }

    @Override
    public void draw(Batch batch, int x, int y, int width, int height) {
        super.draw(batch, x, y, width, height);
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        if (isMouseOver)
            sr.setColor(new Color(0x21212142));
        else
            sr.setColor(new Color(0x21212121));
        sr.rect(x, y, width, height);
        sr.end();
        batch.begin();
        gs.getNormalFont().draw(batch, textToShow, x + leftMargin, y + gs.getNormalFont().getLineHeight());
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
}
