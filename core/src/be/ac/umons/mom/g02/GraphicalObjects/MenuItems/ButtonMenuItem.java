package be.ac.umons.mom.g02.GraphicalObjects.MenuItems;

import be.ac.umons.mom.g02.GraphicalObjects.Controls.Button;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.awt.*;

public class ButtonMenuItem extends MenuItem<Button> {

    /**
     * The action to do if the item is selected.
     */
    protected Runnable toDoIfExecuted;

    /**
     * Construct a new item.
     *
     * @param gs The graphical settings to use
     * @param header The item's name
     */
    public ButtonMenuItem(GameInputManager gim, GraphicalSettings gs, String header) {
        this(gim, gs, header, "");
    }

    /**
     * Construct a new item.
     *
     * @param gs The graphical settings to use
     * @param header         The item's name
     * @param toDoIfExecuted The action to do if the item is selected.
     */
    public ButtonMenuItem(GameInputManager gim, GraphicalSettings gs, String header, Runnable toDoIfExecuted) {
        this(gim, gs, header, toDoIfExecuted, true);
    }

    /**
     * Construct a new item.
     *
     * @param gs The graphical settings to use
     * @param header               The item's name
     * @param toDoIfExecuted       The action to do if the item is selected.
     * @param drawUnderPreviousOne If this item must be drawn under the previous one (=true) or next to it (=false).
     */
    public ButtonMenuItem(GameInputManager gim, GraphicalSettings gs, String header, Runnable toDoIfExecuted, boolean drawUnderPreviousOne) {
        this(gim, gs, header, toDoIfExecuted, drawUnderPreviousOne, "");
    }

    /**
     * Construct a new item.
     *
     * @param gs The graphical settings to use
     * @param header The item's name
     * @param id     The item's id.
     */
    public ButtonMenuItem(GameInputManager gim, GraphicalSettings gs, String header, String id) {
        this(gim, gs, header, null, true, id);
    }

    /**
     * Construct a new item.
     *
     * @param gs The graphical settings to use
     * @param header         The item's name
     * @param id             The item's id
     * @param toDoIfExecuted The action to do if the item is selected.
     */
    public ButtonMenuItem(GameInputManager gim, GraphicalSettings gs, String header, Runnable toDoIfExecuted, boolean drawUnderPreviousOne, String id) {
        super(gim, gs, header, id);
        this.toDoIfExecuted = toDoIfExecuted;
        this.drawUnderPreviousOne = drawUnderPreviousOne;
        control = new Button(gs);
        control.setText(header);
        control.setOnClick(toDoIfExecuted);
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
