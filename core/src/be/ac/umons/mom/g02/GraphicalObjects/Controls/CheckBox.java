package be.ac.umons.mom.g02.GraphicalObjects.Controls;

import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
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
    /**
     * The color if the checkbox isn't activated.
     */
    private Color deactivatedColor;
    /**
     * The color if the checkbox is selected.
     */
    private Color selectedColor;
    /**
     * What to do the moment it's checked.
     */
    private OnStateChangedRunnable onStateChanged;
    /**
     * If it is activated.
     */
    private boolean isActivated = true;
    /**
     * If it is selected
     */
    private boolean isSelected = false;

    /**
     * @param gs The game's graphical settings
     */
    public CheckBox(GraphicalSettings gs) {
        this(gs, "");
    }
    /**
     * Default constructor. USE IT ONLY FOR TEST.
     */
    protected CheckBox() {}
    /**
     * @param gs The game's graphical settings
     * @param text The text associated with the checkbox.
     */
    protected CheckBox(GraphicalSettings gs, String text) {
        super(gs);
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
        this.text = text;
        checkedColor = new Color(0x2E7D32FF);
        uncheckedColor = new Color(0xC62828FF);
        deactivatedColor = new Color(0x424242FF);
        selectedColor = new Color(0x388E3CFF);
    }

    @Override
    public void draw(Batch batch, Point pos, Point size) {
        super.draw(batch, pos, size);
        if (checked)
            sr.setColor(checkedColor);
        else
            sr.setColor(uncheckedColor);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.ellipse(pos.x, pos.y, size.y, size.y);
        sr.end();
        Color oldFontColor = null;
        if (isSelected) {
            oldFontColor = gs.getNormalFont().getColor().cpy();
            gs.getNormalFont().setColor(selectedColor);
        }
        if (! isActivated) {
            oldFontColor = gs.getNormalFont().getColor().cpy();
            gs.getNormalFont().setColor(deactivatedColor);
        }
        batch.begin();
        gs.getNormalFont().draw(batch,text, pos.x + size.y + leftMargin, pos.y + gs.getNormalFont().getLineHeight());
        batch.end();
        if (oldFontColor != null)
            gs.getNormalFont().setColor(oldFontColor);
    }

    @Override
    public void handleInput() {
        if (! isActivated)
            return;
        for (Point click : gim.getRecentClicks()) {
            if (new Rectangle(x, MasterOfMonsGame.HEIGHT - y - height, width, height).contains(click)) {
                checked = !checked;
                if (onStateChanged != null)
                    onStateChanged.run(checked);
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

    /**
     * @param checked If the checkbox is checked or not.
     */
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

    /**
     * @param activated If it's activated.
     */
    public void setActivated(boolean activated) {
        isActivated = activated;
    }


    public void setOnStateChanged(OnStateChangedRunnable onStateChanged) {
        this.onStateChanged = onStateChanged;
    }

    /**
     * @param selected If it's selected.
     */
    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    /**
     * Represent an runnable taking a boolean as parameter.
     */
    public interface OnStateChangedRunnable {
        void run(boolean newState);
    }
}
