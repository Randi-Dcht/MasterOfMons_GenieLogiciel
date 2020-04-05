package be.ac.umons.mom.g02.GraphicalObjects.MenuItems;

import be.ac.umons.mom.g02.GraphicalObjects.Controls.CheckBox;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.awt.*;

public class CheckBoxMenuItem extends MenuItem<CheckBox> {

    /**
     * What to do if the state on the CheckBox change.
     */
    protected CheckBox.OnStateChangedRunnable onStateChanged;

    /**
     * Construct a new item.
     *
     * @param gs The graphical settings to use
     * @param header The item's name
     */
    public CheckBoxMenuItem(GameInputManager gim, GraphicalSettings gs, String header) {
        super(gim, gs, header);
    }

    /**
     * Construct a new item.
     *
     * @param gs The graphical settings to use
     * @param header         The item's name
     * @param onStateChanged The action to do if the item is selected.
     */
    public CheckBoxMenuItem(GameInputManager gim, GraphicalSettings gs, String header, CheckBox.OnStateChangedRunnable onStateChanged) {
        this(gim, gs, header, onStateChanged, true);
    }

    /**
     * Construct a new item.
     *
     * @param gs The graphical settings to use
     * @param header               The item's name
     * @param onStateChanged       The action to do if the item is selected.
     * @param drawUnderPreviousOne If this item must be drawn under the previous one (=true) or next to it (=false).
     */
    public CheckBoxMenuItem(GameInputManager gim, GraphicalSettings gs, String header, CheckBox.OnStateChangedRunnable onStateChanged, boolean drawUnderPreviousOne) {
        this(gim, gs, header, onStateChanged, drawUnderPreviousOne, "");
    }

    /**
     * Construct a new item.
     *
     * @param gs The graphical settings to use
     * @param header The item's name
     * @param id     The item's id.
     */
    public CheckBoxMenuItem(GameInputManager gim, GraphicalSettings gs, String header, String id) {
        this(gim, gs, header, null, true, id);
    }

    /**
     * Construct a new item.
     *
     * @param gs The graphical settings to use
     * @param header         The item's name
     * @param id             The item's id
     * @param onStateChanged The action to do if the item is selected.
     */
    public CheckBoxMenuItem(GameInputManager gim, GraphicalSettings gs, String header, CheckBox.OnStateChangedRunnable onStateChanged, boolean drawUnderPreviousOne, String id) {
        super(gim, gs, header, id);
        this.onStateChanged = onStateChanged;
        this.drawUnderPreviousOne = drawUnderPreviousOne;
        control = new CheckBox(gs);
        control.setOnStateChanged(onStateChanged);
        control.setText(header);
    }

    /**
     * Draw the control associated with this item with the given parameters.
     *
     * @param batch The batch where the control must be drawn.
     * @param pos   The control's position.
     */
    @Override
    public void draw(Batch batch, Point pos) {
        super.draw(batch, pos);
        pos.y -= gs.getNormalFont().getLineHeight() + topMargin;
        drawIfNotNull(batch, pos, size);
    }
}
