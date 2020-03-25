package be.ac.umons.mom.g02.GraphicalObjects.Controls;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * Testing class for <code>ScrollListChooser</code>
 */
public class ScrollListChooserTest extends ScrollListChooser {

    Button b1,b2;
    @BeforeEach
    public void init() {
        buttons = new ArrayList<>();
        buttons.add(b1 = new ButtonTest());
        buttons.add(b2 = new ButtonTest());
        scrollListItems = new ScrollListItem[] {
                new ScrollListItem("b1"),
                new ScrollListItem("b2")
        };
        scrollListItems[0].button = b1;
        scrollListItems[1].button = b2;
    }

    /**
     * Check if the ScrollListChooser selects the good item in of them.
     */
    @Test
    public void checkSelected() {
        canSelectMultipleItems = false;
        Assertions.assertEquals(2, buttons.size());
        b1.setSelected(true);
        b2.setSelected(true);
        scrollListItems[0].isSelected = true;
        scrollListItems[1].isSelected = true;
        checkSelected(scrollListItems[0]);
        checkB1Selected();
        b1.setSelected(true);
        b2.setSelected(true);
        scrollListItems[0].isSelected = true;
        scrollListItems[1].isSelected = true;
        checkSelected(scrollListItems[1]);
        checkB2Selected();
        b1.setSelected(true);
        b2.setSelected(false);
        scrollListItems[0].isSelected = true;
        scrollListItems[1].isSelected = false;
        checkSelected(scrollListItems[0]);
        checkB1Selected();
    }

    /**
     * CHeck if the others items unselect themselves when another one is selected
     */
    public void checkB1Selected() {
        Assertions.assertTrue(buttons.get(0).isSelected());
        Assertions.assertFalse(buttons.get(1).isSelected());
        Assertions.assertTrue(scrollListItems[0].isSelected);
        Assertions.assertFalse(scrollListItems[1].isSelected);
    }
    /**
     * CHeck if the others items unselect themselves when another one is selected
     */
    public void checkB2Selected() {
        Assertions.assertFalse(buttons.get(0).isSelected());
        Assertions.assertTrue(buttons.get(1).isSelected());
        Assertions.assertTrue(scrollListItems[1].isSelected);
        Assertions.assertFalse(scrollListItems[0].isSelected);
    }
}
