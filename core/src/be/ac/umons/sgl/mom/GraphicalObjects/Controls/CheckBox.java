package be.ac.umons.sgl.mom.GraphicalObjects.Controls;

import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;

/**
 * Cette classe représente une case à cocher.
 */
public class CheckBox extends Control {

    /**
     * Permet de dessiner les formes comme les rectangles.
     */
    private ShapeRenderer sr;
    /**
     * Si la case est cochée ou non.
     */
    protected boolean checked = false;
    /**
     * Le texte à afficher à côté de la case.
     */
    private String text;

    /**
     * La couleur si la case est cochée.
     */
    private Color checkedColor;
    /**
     * La couleur si la case n'est pas cochée.
     */
    private Color uncheckedColor;

    /**
     * Crée une nouvelle case à cocher.
     *
     * @param gim Le GameInputManager du jeu.
     * @param gs  Les paramètres graphiques du jeu.
     */
    protected CheckBox(GameInputManager gim, GraphicalSettings gs) {
        this(gim, gs, "");
    }
    protected CheckBox() {}
    /**
     * Crée une nouvelle case à cocher .
     *
     * @param gim Le GameInputManager du jeu.
     * @param gs  Les paramètres graphiques du jeu.
     * @param text Le texte à afficher.
     */
    protected CheckBox(GameInputManager gim, GraphicalSettings gs, String text) {
        super(gim, gs);
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
        this.text = text;
        checkedColor = new Color(0x2E7D32FF);
        uncheckedColor = new Color(0xC62828FF);
    }

    @Override
    protected void draw(Batch batch, Point pos, Point size) {
        super.draw(batch, pos, size);
        if (checked)
            sr.setColor(checkedColor);
        else
            sr.setColor(uncheckedColor);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.ellipse(pos.x, pos.y - gs.getSmallFont().getLineHeight(), size.y, size.y);
        sr.end();
        batch.begin();
        gs.getSmallFont().draw(batch,text, pos.x + size.y + leftMargin, pos.y);
        batch.end();
    }

    @Override
    public void handleInput() {
        for (Point click : gim.getRecentClicks())
            if (new Rectangle(x, MasterOfMonsGame.HEIGHT - y, width, height).contains(click))
                checked = !checked;
    }

    @Override
    public void dispose() {
        sr.dispose();
    }

    /**
     * Retourne si la case est cochée ou non.
     * @return Si la case est cochée ou non.
     */
    public boolean isChecked() {
        return checked;
    }

    /**
     * Défini le texte à afficher.
     * @param text Le texte à afficher.
     */
    public void setText(String text) {
        this.text = text;
    }
}
