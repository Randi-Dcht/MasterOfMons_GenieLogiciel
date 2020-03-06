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

public class KeySelectorTest extends KeySelector {

    public KeySelectorTest() {
        sr = Mockito.mock(ShapeRenderer.class);
        gim = Mockito.mock(GameInputManager.class);
        width = 20;
        height = 20;
        MasterOfMonsGame.WIDTH = 50;
        MasterOfMonsGame.HEIGHT = 50;
        actualKeyCode = -1;
        actualKey = "";
    }

    @Test
    public void handleInputTest() {
        isSelected = true;
        Mockito.when(gim.getLastKeyPressedCode()).thenReturn(Input.Keys.E);
        handleInput();
        Assertions.assertEquals(actualKeyCode, Input.Keys.E);
        Mockito.when(gim.getLastKeyPressedCode()).thenReturn(Input.Keys.F);
        handleInput();
        Assertions.assertEquals(actualKeyCode, Input.Keys.E); // Not selected
        isSelected = true;
        Mockito.when(gim.getLastKeyPressedCode()).thenReturn(Input.Keys.R);
        handleInput();
        Assertions.assertEquals(actualKeyCode, Input.Keys.R); // Not selected
        Assertions.assertEquals(actualKey, "R");
    }

    /**
     * Test if isSelected works correctly.
     */
    @Test
    public void isSelectedTest() {
        handleInput();
        Assertions.assertFalse(isSelected);
        ArrayList<Point> l = new ArrayList<>();
        Point c = new Point(15, 35); // IN
        l.add(c);
        Mockito.when(gim.getRecentClicks()).thenReturn(l);
        handleInput();
        Assertions.assertTrue(isSelected);
        Point c2 = new Point(15, 20); // OUT
        l.add(c2);
        handleInput();
        Assertions.assertFalse(isSelected);
    }

}
