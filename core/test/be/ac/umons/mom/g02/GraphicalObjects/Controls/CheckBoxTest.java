package be.ac.umons.mom.g02.GraphicalObjects.Controls;

import be.ac.umons.mom.g02.GraphicalObjects.Controls.CheckBox;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.*;
import java.util.ArrayList;

/**
 * Cette classe représente les tests pour CheckBox.
 */
public class CheckBoxTest extends CheckBox {
    @BeforeEach
    public void init() {
        gim = Mockito.mock(GameInputManager.class);
        MasterOfMonsGame.HEIGHT = 50; // Redéfini pour les biens du tests
    }

    @Test
    public void handleInputTest() {
        x = 1; y = 10;
        width = 10; height = 10;
        ArrayList<Point> l = new ArrayList<>();
        l.add(new Point(5,45)); // Dedans car le y est inversé
        Mockito.when(gim.getRecentClicks()).thenReturn(l);
        Mockito.when(gim.getLastMousePosition()).thenReturn(new Point(0,0)); // Sans importance ici vu l'implémentation
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
