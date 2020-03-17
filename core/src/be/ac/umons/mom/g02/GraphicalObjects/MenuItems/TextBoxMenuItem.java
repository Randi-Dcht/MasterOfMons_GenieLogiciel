package be.ac.umons.mom.g02.GraphicalObjects.MenuItems;

import be.ac.umons.mom.g02.GraphicalObjects.Controls.TextBox;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.awt.*;

public class TextBoxMenuItem extends MenuItem<TextBox> {
    /**
     * Construct a new item.
     *
     * @param gs
     * @param header The item's name
     */
    public TextBoxMenuItem(GameInputManager gim, GraphicalSettings gs, String header) {
        this(gim, gs, header, "");
    }

    /**
     * Construct a new item.
     *
     * @param gs
     * @param header The item's name
     * @param id     The item's id.
     */
    public TextBoxMenuItem(GameInputManager gim, GraphicalSettings gs, String header, String id) {
        super(gim, gs, header, id);
        control = getControl(TextBox.class);
    }

    /**
     * Draw the control associated with this item with the given parameters.
     *
     * @param batch The batch where the control must be drawn.
     * @param pos   The control's position.
     */
    @Override
    public void draw(Batch batch, Point pos) {
        pos.y -= gs.getNormalFont().getLineHeight() + 4 * topMargin;
        drawNextToHeader(batch, pos);
    }
}
