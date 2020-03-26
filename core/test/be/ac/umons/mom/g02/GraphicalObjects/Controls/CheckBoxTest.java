package be.ac.umons.mom.g02.GraphicalObjects.Controls;

import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.*;
import java.util.ArrayList;

/**
 * Testing class for CheckBox
 */
public class CheckBoxTest {

    CheckBox cb;

    @BeforeEach
    public void init() {
        cb = new CheckBox();
        cb.gim = Mockito.mock(GameInputManager.class);
        MasterOfMonsGame.HEIGHT = 50; // Redefined for tests purposes
    }

    /**
     * Test if the method <code>handleInput</code> works as expected
     */
    @Test
    public void handleInputTest() {
        cb.x = 1; cb.y = 10;
        cb.width = 10; cb.height = 10;
        ArrayList<Point> l = new ArrayList<>();
        l.add(new Point(5,35)); // In because the y is inverted with the mouse
        Mockito.when(cb.gim.getRecentClicks()).thenReturn(l);
        Mockito.when(cb.gim.getLastMousePosition()).thenReturn(new Point(0,0)); // Without any importance here
        cb.handleInput();
        Assertions.assertTrue(cb.checked);
        l.clear();
        cb.checked = false;
        l.add(new Point(0,5)); // Out
        cb.handleInput();
        Assertions.assertFalse(cb.checked);
        l.clear();
        cb.checked = false;
        l.add(new Point(5,35)); // In
        l.add(new Point(0,5)); // Out
        cb.handleInput();
        Assertions.assertTrue(cb.checked);
        l.clear();
        cb.checked = false;
        l.add(new Point(5,0)); // Out
        cb.handleInput();
        Assertions.assertFalse(cb.checked);
        l.clear();
        cb.checked = false;
        l.add(new Point(20,45)); // Out
        cb.handleInput();
        Assertions.assertFalse(cb.checked);
    }
}
