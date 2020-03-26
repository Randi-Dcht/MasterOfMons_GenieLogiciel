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
public class ButtonTest extends Button {

    private boolean clicked = false;

    @BeforeEach
    public void init() {
        gim = Mockito.mock(GameInputManager.class);
        gs = Mockito.mock(GraphicalSettings.class);
        sr = Mockito.mock(ShapeRenderer.class);
        MasterOfMonsGame.HEIGHT = 50; // Redéfini pour les biens du tests
    }

    /**
     * Test if the button monitor the click correctly
     */
    @Test
    public void clickTest() {
        setOnClick(() -> clicked = true);
        x = 1; y = 1;
        width = 10; height = 10;
        ArrayList<Point> l = new ArrayList<>();
        l.add(new Point(5,45)); // Dedans car le y est inversé
        Mockito.when(gim.getRecentClicks()).thenReturn(l);
        Mockito.when(gim.getLastMousePosition()).thenReturn(new Point(0,0)); // Sans importance ici vu l'implémentation
        handleInput();
        Assertions.assertTrue(clicked);
        l.clear();
        clicked = false;
        l.add(new Point(0,5)); // Dehors
        handleInput();
        Assertions.assertFalse(clicked);
        l.clear();
        clicked = false;
        l.add(new Point(5,45)); // Dedans
        l.add(new Point(0,5)); // Dehors
        handleInput();
        Assertions.assertTrue(clicked);
        l.clear();
        clicked = false;
        l.add(new Point(5,0)); // Dehors
        handleInput();
        Assertions.assertFalse(clicked);
        l.clear();
        clicked = false;
        l.add(new Point(20,45)); // Dehors
        handleInput();
        Assertions.assertFalse(clicked);
    }

    /**
     * Test if the method to check if the mouse is over works correctly
     */
    @Test
    public void isMouseOverTest() {
        x = 1; y = 1;
        width = 10; height = 10;
        Mockito.when(gim.getLastMousePosition()).thenReturn(new Point(0,0));
        Mockito.when(gim.getRecentClicks()).thenReturn(new ArrayList<>()); // Sans importance ici.
        Assertions.assertFalse(isMouseOver);
        Mockito.when(gim.getLastMousePosition()).thenReturn(new Point(5,45)); // Dedans (y inversé)
        handleInput();
        Assertions.assertTrue(isMouseOver);
        Mockito.when(gim.getLastMousePosition()).thenReturn(new Point(15,45)); // Dehors
        handleInput();
        Assertions.assertFalse(isMouseOver);
        Mockito.when(gim.getLastMousePosition()).thenReturn(new Point(5,5)); // Dehors
        handleInput();
        Assertions.assertFalse(isMouseOver);
    }
}
