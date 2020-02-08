package be.ac.umons.sgl.mom.GraphicalObjects;

import be.ac.umons.sgl.mom.GraphicalObjects.Controls.Button;
import be.ac.umons.sgl.mom.GraphicalObjects.Controls.ScrollListChooser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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

    public void checkB1Selected() {
        Assertions.assertTrue(buttons.get(0).isSelected());
        Assertions.assertFalse(buttons.get(1).isSelected());
        Assertions.assertTrue(scrollListItems[0].isSelected);
        Assertions.assertFalse(scrollListItems[1].isSelected);
    }

    public void checkB2Selected() {
        Assertions.assertFalse(buttons.get(0).isSelected());
        Assertions.assertTrue(buttons.get(1).isSelected());
        Assertions.assertTrue(scrollListItems[1].isSelected);
        Assertions.assertFalse(scrollListItems[0].isSelected);
    }
}
