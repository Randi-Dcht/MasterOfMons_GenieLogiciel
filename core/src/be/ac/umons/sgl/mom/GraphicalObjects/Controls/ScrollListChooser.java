package be.ac.umons.sgl.mom.GraphicalObjects.Controls;

import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ScrollListChooser extends Control {

    protected ScrollListItem[] scrollListItems;
    protected List<Button> buttons;
    protected int mouseScrolled = 0;
    protected boolean canSelectMultipleItems = false;

    public ScrollListChooser(GameInputManager gim, GraphicalSettings gs) {
        super(gim, gs);
        buttons = new ArrayList<>();
    }

    @Override
    public void draw(Batch batch, Point pos, Point size) {
        super.draw(batch, pos, size);
        int alreadyUsed = 0;
        int ySize = (int)Math.floor(gs.getNormalFont().getLineHeight()) + 2 * topMargin;
        int maxScrolled = (ySize + 2 * topMargin) * buttons.size();
        if (mouseScrolled > maxScrolled)
            mouseScrolled = maxScrolled;
        for (Button b : buttons) {
            if (alreadyUsed + ySize - mouseScrolled < 0)
                continue;
            else if (alreadyUsed + ySize > size.y)
                break;
            b.draw(batch, new Point(pos.x, pos.y - alreadyUsed - topMargin), new Point(size.x, ySize));
            alreadyUsed += ySize;
        }
    }

    @Override
    public void handleInput() {
        mouseScrolled += gim.getScrolledAmount();
        for (Button b : buttons)
            b.handleInput();
    }

    @Override
    public void dispose() {
        for (Button b : buttons)
            b.dispose();
    }

    public void setScrollListItems(ScrollListItem[] scrollListItems) {
        this.scrollListItems = scrollListItems;
        for (ScrollListItem sli : scrollListItems) {
            Button b = new Button(gim, gs);
            b.setText(sli.header);
            b.setOnClick(() -> {
                b.setSelected(! b.isSelected());
                if (sli.onClick != null)
                    sli.onClick.run();
                sli.isSelected = ! sli.isSelected;
                if (sli.isSelected && ! canSelectMultipleItems) {
                    for (Button b2 : buttons) {
                        if (b != b2) {
                            b2.setSelected(false);
                            sli.isSelected = false;
                        }
                    }
                }
            });
            b.setSelected(sli.isSelected);
            buttons.add(b);
        }
    }

    public void setCanSelectMultipleItems(boolean canSelectMultipleItems) {
        this.canSelectMultipleItems = canSelectMultipleItems;
    }

    public static class ScrollListItem {
        public String header;
        public boolean isSelected;
        public Runnable onClick;

        public ScrollListItem(String header) {
            this(header, null);
        }
        public ScrollListItem(String header, Runnable onClick) {
            this(header, onClick, false);
        }
        public ScrollListItem(String header, boolean isSelected) {
            this(header, null, false);
        }
        public ScrollListItem(String header, Runnable onClick, boolean isSelected) {
            this.header = header;
            this.onClick = onClick;
            this.isSelected = isSelected;
        }
    }
}
