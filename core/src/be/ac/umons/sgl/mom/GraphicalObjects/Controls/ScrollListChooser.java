package be.ac.umons.sgl.mom.GraphicalObjects.Controls;

import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Propose a list of item that the user can (un)select
 */
public class ScrollListChooser extends Control {
    /**
     * The items to show.
     */
    protected ScrollListItem[] scrollListItems;
    /**
     * The list of buttons used in this state.
     */
    protected List<Button> buttons;
    /**
     * At which point the user want to go down.
     */
    protected int mouseScrolled = 0;
    /**
     * If the user can select multiple items.
     */
    protected boolean canSelectMultipleItems = false;

    /**
     * If the items haves changed since last update.
     */
    protected boolean newItems = false;

    /**
     * @param gim The game's input manager
     * @param gs The game's graphical settings.
     */
    public ScrollListChooser(GameInputManager gim, GraphicalSettings gs) {
        super(gim, gs);
        buttons = new ArrayList<>();
    }

    /**
     * Default constructor. USE IT ONLY FOR TEST
     */
    protected ScrollListChooser() {}

    @Override
    public void draw(Batch batch, Point pos, Point size) {
        super.draw(batch, pos, size);
        int alreadyUsed = 0;
        int ySize = (int)Math.floor(gs.getNormalFont().getLineHeight()) + 2 * topMargin;
        int maxScrolled = ySize * buttons.size() - size.y;
        if (maxScrolled < 0)
            maxScrolled = 0;
        if (mouseScrolled > maxScrolled)
            mouseScrolled = maxScrolled;
        else if (mouseScrolled < 0)
            mouseScrolled = 0;
        for (Button b : buttons) {
            if (! (alreadyUsed - mouseScrolled < 0))
                b.draw(batch, new Point(pos.x, pos.y - alreadyUsed - topMargin + mouseScrolled), new Point(size.x, ySize));
            else if (alreadyUsed + ySize - mouseScrolled > size.y)
                break;
            alreadyUsed += ySize;
        }
    }

    @Override
    public void handleInput() {
        mouseScrolled += gim.getScrolledAmount() * 10;
        for (int i = 0; i < buttons.size(); i++) {
            if (newItems) {
                newItems = false;
                break;
            }
            buttons.get(i).handleInput();
        }
    }

    @Override
    public void dispose() {
        for (Button b : buttons)
            b.dispose();
    }

    /**
     * Set the items to show and create the needed resources.
     * @param scrollListItems The items to show
     */
    public void setScrollListItems(ScrollListItem[] scrollListItems) {
        this.scrollListItems = scrollListItems;
        buttons.clear();
        for (ScrollListItem sli : scrollListItems) {
            Button b = new Button(gim, gs);
            b.setText(sli.header);
            b.setOnClick(() -> {
                b.setSelected(! b.isSelected());
                if (sli.onClick != null)
                    sli.onClick.run();
                sli.isSelected = ! sli.isSelected;
                checkSelected(sli);
            });
            sli.button = b;
            b.setSelected(sli.isSelected);
            buttons.add(b);
        }
        newItems = true;
    }

    /**
     * Check if multiple items are selected and unselect the precedent one if there are.
     * @param sli The last item selected.
     */
    public void checkSelected(ScrollListItem sli) {
        if (sli.isSelected && ! canSelectMultipleItems) {
            for (ScrollListItem sli2 : scrollListItems) {
                if (sli != sli2) {
                    sli2.button.setSelected(false);
                    sli2.isSelected = false;
                }
            }
        }
    }

    /**
     * Set if multiple items can be selected.
     * @param canSelectMultipleItems If multiple items can be selected.
     */
    public void setCanSelectMultipleItems(boolean canSelectMultipleItems) {
        this.canSelectMultipleItems = canSelectMultipleItems;
    }

    /**
     * Represent an item.
     */
    public static class ScrollListItem {
        /**
         * The item's name
         */
        public String header;
        /**
         * If the item is selected.
         */
        public boolean isSelected;
        /**
         * What to do when a click is detected on this item.
         */
        public Runnable onClick;
        /**
         * The button associated with this item.
         */
        public Button button;

        /**
         * @param header The item name.
         */
        public ScrollListItem(String header) {
            this(header, null);
        }

        /**
         * @param header The item's name
         * @param onClick What to do when a click is detected on this item.
         */
        public ScrollListItem(String header, Runnable onClick) {
            this(header, onClick, false);
        }

        /**
         * @param header The item's name
         * @param isSelected If the item is selected
         */
        public ScrollListItem(String header, boolean isSelected) {
            this(header, null, false);
        }

        /**
         * @param header The item's name
         * @param onClick What to do when a click is detected on this item.
         * @param isSelected If the item is selected
         */
        public ScrollListItem(String header, Runnable onClick, boolean isSelected) {
            this.header = header;
            this.onClick = onClick;
            this.isSelected = isSelected;
        }
    }
}
