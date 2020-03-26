package be.ac.umons.mom.g02.GraphicalObjects.Controls;

import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.*;
import java.util.ArrayList;

/**
 * Testing class for Button
 */
public class ButtonTest {

    Button b;

    private boolean clicked = false;

    @BeforeEach
    public void init() {
        b = new Button();
        b.gim = Mockito.mock(GameInputManager.class);
        b.gs = Mockito.mock(GraphicalSettings.class);
        b.sr = Mockito.mock(ShapeRenderer.class);
        MasterOfMonsGame.HEIGHT = 50; // Redefined for tests purposes
    }

    /**
     * Test if the button monitor the click correctly
     */
    @Test
    public void clickTest() {
        b.setOnClick(() -> clicked = true);
        b.x = 1; b.y = 1;
        b.width = 10; b.height = 10;
        ArrayList<Point> l = new ArrayList<>();
        l.add(new Point(5,45));
        Mockito.when(b.gim.getRecentClicks()).thenReturn(l);
        Mockito.when(b.gim.getLastMousePosition()).thenReturn(new Point(0,0)); // Without any importance here
        b.handleInput();
        Assertions.assertTrue(clicked);
        l.clear();
        clicked = false;
        l.add(new Point(0,5)); // Out
        b.handleInput();
        Assertions.assertFalse(clicked);
        l.clear();
        clicked = false;
        l.add(new Point(5,45)); // In
        l.add(new Point(0,5)); // Out
        b.handleInput();
        Assertions.assertTrue(clicked);
        l.clear();
        clicked = false;
        l.add(new Point(5,0)); // Out
        b.handleInput();
        Assertions.assertFalse(clicked);
        l.clear();
        clicked = false;
        l.add(new Point(20,45)); // Out
        b.handleInput();
        Assertions.assertFalse(clicked);
    }

    /**
     * Test if the method to check if the mouse is over works correctly
     */
    @Test
    public void isMouseOverTest() {
        b.x = 1; b.y = 1;
        b.width = 10; b.height = 10;
        Mockito.when(b.gim.getLastMousePosition()).thenReturn(new Point(0,0));
        Mockito.when(b.gim.getRecentClicks()).thenReturn(new ArrayList<>()); // Sans importance ici.
        Assertions.assertFalse(b.isMouseOver);
        Mockito.when(b.gim.getLastMousePosition()).thenReturn(new Point(5,45)); // Dedans (y inversé)
        b.handleInput();
        Assertions.assertTrue(b.isMouseOver);
        Mockito.when(b.gim.getLastMousePosition()).thenReturn(new Point(15,45)); // Dehors
        b.handleInput();
        Assertions.assertFalse(b.isMouseOver);
        Mockito.when(b.gim.getLastMousePosition()).thenReturn(new Point(5,5)); // Dehors
        b.handleInput();
        Assertions.assertFalse(b.isMouseOver);
    }
}
