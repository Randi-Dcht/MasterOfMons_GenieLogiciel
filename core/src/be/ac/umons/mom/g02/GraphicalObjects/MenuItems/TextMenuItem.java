package be.ac.umons.mom.g02.GraphicalObjects.MenuItems;

import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.awt.*;

public class TextMenuItem extends MenuItem {
    /**
     * Construct a new item.
     *
     * @param gs
     * @param header The item's name
     */
    public TextMenuItem(GraphicalSettings gs, String header) {
        super(null, gs, header);
    }

    /**
     * Draw the control associated with this item with the given parameters.
     *
     * @param batch The batch where the control must be drawn.
     * @param pos   The control's position.
     */
    @Override
    public void draw(Batch batch, Point pos) {
        size.y = (int)(gs.getNormalFont().getLineHeight() * lineNumber + 2 * topMargin);
        batch.begin();
        gs.getNormalFont().draw(batch, header, pos.x, (int)(pos.y - topMargin));
        batch.end();
    }
}
