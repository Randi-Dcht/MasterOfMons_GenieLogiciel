package be.ac.umons.sgl.mom.GraphicalObjects.Controls;

import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;

/**
 * A checkbox.
 * @author Guillaume Cardoen
 */
public class CheckBox extends Control {

    /**
     * Allows to draw shapes.
     */
    private ShapeRenderer sr;
    /**
     * If the checkbox is checked or not.
     */
    protected boolean checked = false;
    /**
     * The text associated with the checkbox.
     */
    private String text;

    /**
     * The color if the checkbox is checked.
     */
    private Color checkedColor;
    /**
     * The color if the checkbox isn't checked.
     */
    private Color uncheckedColor;

    private Color deactivatedColor;

    private Runnable onChecked;

    private Runnable onUnchecked;

    private boolean isActivated = true;

    /**
     * @param gim The game's input manager
     * @param gs The game's graphical settings
     */
    protected CheckBox(GameInputManager gim, GraphicalSettings gs) {
        this(gim, gs, "");
    }
    /**
     * Default constructor. USE IT ONLY FOR TEST.
     */
    protected CheckBox() {}
    /**
     * @param gim The game's input manager
     * @param gs The game's graphical settings
     * @param text The text associated with the checkbox.
     */
    protected CheckBox(GameInputManager gim, GraphicalSettings gs, String text) {
        super(gim, gs);
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
        this.text = text;
        checkedColor = new Color(0x2E7D32FF);
        uncheckedColor = new Color(0xC62828FF);
        deactivatedColor = new Color(0x424242FF);
    }

    @Override
    public void draw(Batch batch, Point pos, Point size) {
        super.draw(batch, pos, size);
        if (checked)
            sr.setColor(checkedColor);
        else
            sr.setColor(uncheckedColor);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.ellipse(pos.x, pos.y - gs.getSmallFont().getLineHeight(), size.y, size.y);
        sr.end();
        Color oldFontColor = null;
        if (! isActivated) {
            oldFontColor = gs.getSmallFont().getColor().cpy();
            gs.getSmallFont().setColor(deactivatedColor);
        }
        batch.begin();
        gs.getSmallFont().draw(batch,text, pos.x + size.y + leftMargin, pos.y);
        batch.end();
        if (oldFontColor != null)
            gs.getSmallFont().setColor(oldFontColor);
    }

    @Override
    public void handleInput() {
        if (! isActivated)
            return;
        for (Point click : gim.getRecentClicks()) {
            if (new Rectangle(x, MasterOfMonsGame.HEIGHT - y, width, height).contains(click)) {
                checked = !checked;
                if (checked)
                    onChecked.run();
                else
                    onUnchecked.run();
            }
        }
    }

    @Override
    public void dispose() {
        sr.dispose();
    }

    /**
     * @return If the checkbox is checked or not.
     */
    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    /**
     * Set the text associated with this checkbox.
     * @param text The text associated with this checkbox.
     */
    public void setText(String text) {
        this.text = text;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }

    public void setOnChecked(Runnable onChecked) {
        this.onChecked = onChecked;
    }

    public void setOnUnchecked(Runnable onUnchecked) {
        this.onUnchecked = onUnchecked;
    }
}
