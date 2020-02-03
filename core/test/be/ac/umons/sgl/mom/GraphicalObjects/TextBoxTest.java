package be.ac.umons.sgl.mom.GraphicalObjects;

import be.ac.umons.sgl.mom.Enums.KeyStatus;
import be.ac.umons.sgl.mom.GraphicalObjects.Controls.TextBox;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import com.badlogic.gdx.Input;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.*;
import java.util.ArrayList;

/**
 * Cette classe représente les tests de la classe TextBox.
 */
public class TextBoxTest extends TextBox {

    @BeforeEach
    public void init() {
        gim = Mockito.mock(GameInputManager.class);
        Mockito.when(gim.getRecentClicks()).thenReturn(new ArrayList<>());
    }

    /**
     * Test si la variable isSelected se met à jour comme attendu.
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

    /**
     * Test si la variable actualText se met à jour comme attendu.
     */
    @Test
    public void actualTextTest() {
        Mockito.when(gim.getRecentClicks()).thenReturn(new ArrayList<>());
        suffix = ".txt";
        handleInput();
        isSelected = true;
        Assertions.assertEquals("", actualText);
        Mockito.when(gim.isKey(Input.Keys.A, KeyStatus.Pressed)).thenReturn(true);
        handleInput();
        Assertions.assertEquals("a", actualText);
        Mockito.when(gim.isKey(Input.Keys.SHIFT_LEFT, KeyStatus.Pressed)).thenReturn(true);
        handleInput();
        Assertions.assertEquals("aA", actualText);
        Mockito.when(gim.isKey(Input.Keys.A, KeyStatus.Pressed)).thenReturn(false);
        Mockito.when(gim.isKey(Input.Keys.BACKSPACE, KeyStatus.Pressed)).thenReturn(true);
        handleInput();
        Assertions.assertEquals("a", actualText);
        isSelected = false;
        Assertions.assertEquals("a", actualText);
        Mockito.when(gim.isKey(Input.Keys.A, KeyStatus.Pressed)).thenReturn(true);
        handleInput();
        Assertions.assertEquals("a", actualText);
        Mockito.when(gim.isKey(Input.Keys.SHIFT_LEFT, KeyStatus.Pressed)).thenReturn(true);
        handleInput();
        Assertions.assertEquals("a", actualText);
        Mockito.when(gim.isKey(Input.Keys.A, KeyStatus.Pressed)).thenReturn(false);
        Mockito.when(gim.isKey(Input.Keys.BACKSPACE, KeyStatus.Pressed)).thenReturn(true);
        handleInput();
        Assertions.assertEquals("a", actualText);
    }
}
