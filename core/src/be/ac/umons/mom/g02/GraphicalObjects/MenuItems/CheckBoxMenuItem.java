package be.ac.umons.mom.g02.GraphicalObjects.MenuItems;

import be.ac.umons.mom.g02.GraphicalObjects.Controls.CheckBox;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.awt.*;

public class CheckBoxMenuItem extends MenuItem<CheckBox> {

    protected CheckBox.OnStateChangedRunnable toDoIfStateChanged;

    /**
     * Construct a new item.
     *
     * @param gs
     * @param header The item's name
     */
    public CheckBoxMenuItem(GameInputManager gim, GraphicalSettings gs, String header) {
        super(gim, gs, header);
    }

    /**
     * Construct a new item.
     *
     * @param gs
     * @param header         The item's name
     * @param toDoIfStateChanged The action to do if the item is selected.
     */
    public CheckBoxMenuItem(GameInputManager gim, GraphicalSettings gs, String header, CheckBox.OnStateChangedRunnable toDoIfStateChanged) {
        this(gim, gs, header, toDoIfStateChanged, true);
    }

    /**
     * Construct a new item.
     *
     * @param gs
     * @param header               The item's name
     * @param toDoIfStateChanged       The action to do if the item is selected.
     * @param drawUnderPreviousOne If this item must be drawn under the previous one (=true) or next to it (=false).
     */
    public CheckBoxMenuItem(GameInputManager gim, GraphicalSettings gs, String header, CheckBox.OnStateChangedRunnable toDoIfStateChanged, boolean drawUnderPreviousOne) {
        this(gim, gs, header, toDoIfStateChanged, drawUnderPreviousOne, "");
    }

    /**
     * Construct a new item.
     *
     * @param gs
     * @param header The item's name
     * @param id     The item's id.
     */
    public CheckBoxMenuItem(GameInputManager gim, GraphicalSettings gs, String header, String id) {
        this(gim, gs, header, null, true, id);
    }

    /**
     * Construct a new item.
     *
     * @param gs
     * @param header         The item's name
     * @param id             The item's id
     * @param toDoIfStateChanged The action to do if the item is selected.
     */
    public CheckBoxMenuItem(GameInputManager gim, GraphicalSettings gs, String header, CheckBox.OnStateChangedRunnable toDoIfStateChanged, boolean drawUnderPreviousOne, String id) {
        super(gim, gs, header, id);
        this.toDoIfStateChanged = toDoIfStateChanged;
        this.drawUnderPreviousOne = drawUnderPreviousOne;
        control = getControl(CheckBox.class);
        control.setOnStateChanged(toDoIfStateChanged);
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
        pos.y -= gs.getNormalFont().getLineHeight() + 4 * topMargin;
        drawIfNonNull(batch, pos, size);
    }
}
