package be.ac.umons.sgl.mom.GraphicalObjects;

import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import java.awt.*;

public class CheckBox extends Control {

    private ShapeRenderer sr;
    private boolean checked = false;
    private String text;

    private Color checkedColor;
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
        checkedColor = new Color(0xFF2E7D32);
        uncheckedColor = new Color(0xFFC62828);
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
            if (new Rectangle(x, MasterOfMonsGame.HEIGHT - y - height, width, height).contains(click.x, click.y))
                checked = !checked;
    }

    @Override
    public void dispose() {
        sr.dispose();
    }

    public boolean isChecked() {
        return checked;
    }

    public void setText(String text) {
        this.text = text;
    }
}
