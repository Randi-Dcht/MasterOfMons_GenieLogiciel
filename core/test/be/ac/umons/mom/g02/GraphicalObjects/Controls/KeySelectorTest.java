package be.ac.umons.mom.g02.GraphicalObjects.Controls;

import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.*;
import java.util.ArrayList;

/**
 * Testing class for <code>KeySelector</code>
 */
public class KeySelectorTest {

    KeySelector ks;

    public KeySelectorTest() {
        ks = new KeySelector();
        ks.sr = Mockito.mock(ShapeRenderer.class);
        ks.gim = Mockito.mock(GameInputManager.class);
        ks.width = 20;
        ks.height = 20;
        MasterOfMonsGame.WIDTH = 50;
        MasterOfMonsGame.HEIGHT = 50;
        ks.actualKeyCode = -1;
        ks.actualKey = "";
    }

    /**
     * Test if the method <code>handleInput</code> is working as expected
     */
    @Test
    public void handleInputTest() {
        ks.isSelected = true;
        Mockito.when(ks.gim.getLastKeyPressedCode()).thenReturn(Input.Keys.E);
        ks.handleInput();
        Assertions.assertEquals(ks.actualKeyCode, Input.Keys.E);
        Mockito.when(ks.gim.getLastKeyPressedCode()).thenReturn(Input.Keys.F);
        ks.handleInput();
        Assertions.assertEquals(ks.actualKeyCode, Input.Keys.E); // Not selected
        ks.isSelected = true;
        Mockito.when(ks.gim.getLastKeyPressedCode()).thenReturn(Input.Keys.R);
        ks.handleInput();
        Assertions.assertEquals(ks.actualKeyCode, Input.Keys.R); // Not selected
        Assertions.assertEquals(ks.actualKey, "R");
    }

    /**
     * Test if isSelected works correctly.
     */
    @Test
    public void isSelectedTest() {
        ks.handleInput();
        Assertions.assertFalse(ks.isSelected);
        ArrayList<Point> l = new ArrayList<>();
        Point c = new Point(15, 35); // IN
        l.add(c);
        Mockito.when(ks.gim.getRecentClicks()).thenReturn(l);
        ks.handleInput();
        Assertions.assertTrue(ks.isSelected);
        Point c2 = new Point(15, 20); // OUT
        l.add(c2);
        ks.handleInput();
        Assertions.assertFalse(ks.isSelected);
    }

}
