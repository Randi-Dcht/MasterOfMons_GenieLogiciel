package be.ac.umons.sgl.mom.GraphicalObjects;

import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.awt.*;
import java.util.ArrayList;

/**
 * Cette classe représente les tests pour CheckBox.
 */
public class CheckBoxTest {
    private boolean checked;
    @Mock
    private GameInputManager gim;
    /**
     * Les coordonées où le contrôle doit être déssiné.
     * Rajouté pour le bien du test.
     */
    protected int x, y;
    /**
     * La taille du contrôle.
     * Rajouté pour le bien du test.
     */
    protected int width, height;

    @BeforeEach
    private void init() {
        MockitoAnnotations.initMocks(this);
    }

    public void handleInput() {
        for (Point click : gim.getRecentClicks())
            if (new Rectangle(x, MasterOfMonsGame.HEIGHT - y, width, height).contains(click))
                checked = !checked;
    }

    @Test
    private void handleInputTest() {
        x = 1; y = 1;
        width = 10; height = 10;
        ArrayList<Point> l = new ArrayList<Point>();
        l.add(new Point(5,45)); // Dedans car le y est inversé
        Mockito.when(gim.getRecentClicks()).thenReturn(l);
//        Mockito.when(gim.getLastMousePosition()).thenReturn(new Point(0,0)); // Sans importance ici vu l'implémentation
        handleInput();
        Assertions.assertTrue(checked);
        l.clear();
        checked = false;
        l.add(new Point(0,5)); // Dehors
        handleInput();
        Assertions.assertFalse(checked);
        l.clear();
        checked = false;
        l.add(new Point(5,45)); // Dedans
        l.add(new Point(0,5)); // Dehors
        handleInput();
        Assertions.assertTrue(checked);
        l.clear();
        checked = false;
        l.add(new Point(5,0)); // Dehors
        handleInput();
        Assertions.assertFalse(checked);
        l.clear();
        checked = false;
        l.add(new Point(20,45)); // Dehors
        handleInput();
        Assertions.assertFalse(checked);

    }
}
