package be.ac.umons.mom.g02.GraphicalObjects.MenuItems;

import be.ac.umons.mom.g02.GraphicalObjects.Controls.ScrollListChooser;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.awt.*;

public class ScrollListChooserMenuItem extends MenuItem<ScrollListChooser> {
    /**
     * Construct a new item.
     *
     * @param gs
     * @param header The item's name
     */
    public ScrollListChooserMenuItem(GameInputManager gim, GraphicalSettings gs, String header) {
        this(gim, gs, header, "");
    }

    /**
     * Construct a new item.
     *
     * @param gs
     * @param header The item's name
     * @param id     The item's id.
     */
    public ScrollListChooserMenuItem(GameInputManager gim, GraphicalSettings gs, String header, String id) {
        super(gim, gs, header, id);
        control = new ScrollListChooser(gs);
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
        drawNextToHeader(batch, pos);
    }
}
