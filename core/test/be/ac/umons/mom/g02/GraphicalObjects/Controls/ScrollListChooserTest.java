package be.ac.umons.mom.g02.GraphicalObjects.Controls;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * Testing class for <code>ScrollListChooser</code>
 */
public class ScrollListChooserTest {

    Button b1,b2;
    ScrollListChooser slc;

    @BeforeEach
    public void init() {
        slc = new ScrollListChooser();
        slc.buttons = new ArrayList<>();
        slc.buttons.add(b1 = new Button());
        slc.buttons.add(b2 = new Button());
        slc.scrollListItems = new ScrollListChooser.ScrollListItem[] {
                new ScrollListChooser.ScrollListItem("b1"),
                new ScrollListChooser.ScrollListItem("b2")
        };
        slc.scrollListItems[0].button = b1;
        slc.scrollListItems[1].button = b2;
    }

    /**
     * Check if the ScrollListChooser selects the good item in of them.
     */
    @Test
    public void checkSelected() {
        slc.canSelectMultipleItems = false;
        Assertions.assertEquals(2, slc.buttons.size());
        b1.setSelected(true);
        b2.setSelected(true);
        slc.scrollListItems[0].isSelected = true;
        slc.scrollListItems[1].isSelected = true;
        slc.checkSelected(slc.scrollListItems[0]);
        checkB1Selected();
        b1.setSelected(true);
        b2.setSelected(true);
        slc.scrollListItems[0].isSelected = true;
        slc.scrollListItems[1].isSelected = true;
        slc.checkSelected(slc.scrollListItems[1]);
        checkB2Selected();
        b1.setSelected(true);
        b2.setSelected(false);
        slc.scrollListItems[0].isSelected = true;
        slc.scrollListItems[1].isSelected = false;
        slc.checkSelected(slc.scrollListItems[0]);
        checkB1Selected();
    }

    /**
     * CHeck if the others items unselect themselves when another one is selected
     */
    public void checkB1Selected() {
        Assertions.assertTrue(slc.buttons.get(0).isSelected());
        Assertions.assertFalse(slc.buttons.get(1).isSelected());
        Assertions.assertTrue(slc.scrollListItems[0].isSelected);
        Assertions.assertFalse(slc.scrollListItems[1].isSelected);
    }
    /**
     * CHeck if the others items unselect themselves when another one is selected
     */
    public void checkB2Selected() {
        Assertions.assertFalse(slc.buttons.get(0).isSelected());
        Assertions.assertTrue(slc.buttons.get(1).isSelected());
        Assertions.assertTrue(slc.scrollListItems[1].isSelected);
        Assertions.assertFalse(slc.scrollListItems[0].isSelected);
    }
}
