package be.ac.umons.mom.g02.GraphicalObjects.MenuItems;

import be.ac.umons.mom.g02.Helpers.StringHelper;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.awt.*;

public class TitleMenuItem extends MenuItem {
    /**
     * Construct a new item.
     *
     * @param gs
     * @param header The item's name
     */
    public TitleMenuItem(GraphicalSettings gs, String header) {
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
        batch.begin();
        gs.getTitleFont().draw(batch, header, pos.x, pos.y);
        batch.end();
    }

    @Override
    public void setHeader(String header) {
        if (gs.getTitleFont() != null) { // Test case
            this.header = StringHelper.adaptTextToWidth(gs.getTitleFont(), header, (int)(MasterOfMonsGame.WIDTH - 2 * leftMargin));
            lineNumber = this.header.split("\n").length;
        }
    }
}
