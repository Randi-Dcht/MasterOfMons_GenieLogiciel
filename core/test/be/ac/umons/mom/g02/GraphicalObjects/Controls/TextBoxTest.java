package be.ac.umons.mom.g02.GraphicalObjects.Controls;

import be.ac.umons.mom.g02.Enums.KeyStatus;
import be.ac.umons.mom.g02.GraphicalObjects.Controls.TextBox;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import com.badlogic.gdx.Input;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Testing class for <code>TextBox</code>
 */
public class TextBoxTest extends TextBox {

    @BeforeEach
    public void init() {
        gim = Mockito.mock(GameInputManager.class);
        Mockito.when(gim.getRecentClicks()).thenReturn(new ArrayList<>());
        width = 20;
        height = 20;
        MasterOfMonsGame.WIDTH = 50;
        MasterOfMonsGame.HEIGHT = 50;
    }

    /**
     * Test if the attribute <code>isSelected</code> is updated correctly
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
     * Test if the attribute <code>actualText</code> is updated correctly
     */
    @Test
    public void actualTextTest() {
        LinkedList<java.lang.Character> ll = new LinkedList<>();
        Mockito.when(gim.getRecentClicks()).thenReturn(new ArrayList<>());
        Mockito.when(gim.getLastChars()).thenReturn(ll);
        suffix = ".txt";
        handleInput();
        isSelected = true;
        Assertions.assertEquals("", actualText);
        ll.add('a');
        handleInput();
        Assertions.assertEquals("a", actualText);
        ll.clear();
        ll.add('A');
        handleInput();
        Assertions.assertEquals("aA", actualText);
        Mockito.when(gim.isKey(Input.Keys.BACKSPACE, KeyStatus.Pressed)).thenReturn(true);
        ll.clear();
        handleInput();
        Assertions.assertEquals("a", actualText);
        isSelected = false;
        Assertions.assertEquals("a", actualText);
        ll.add('A');
        handleInput();
        ll.clear();
        Assertions.assertEquals("a", actualText);
        Mockito.when(gim.isKey(Input.Keys.BACKSPACE, KeyStatus.Pressed)).thenReturn(true);
        handleInput();
        Assertions.assertEquals("a", actualText);
    }

    /**
     * Test if only numbers are accepted when <code>acceptOnlyNumbers</code> is true
     */
    @Test
    public void acceptOnlyNumberTest() {
        isSelected = true;
        acceptOnlyNumbers = true;
        LinkedList<java.lang.Character> ll = new LinkedList<>();
        Mockito.when(gim.getRecentClicks()).thenReturn(new ArrayList<>());
        Mockito.when(gim.getLastChars()).thenReturn(ll);
        ll.add('a');
        handleInput();
        Assertions.assertEquals("", actualText);
        ll.add('2');
        handleInput();
        Assertions.assertEquals("2", actualText);
    }

    /**
     * Test if only hexa-decimal characters are accepted when <code>acceptOnlyHexadecimal</code> is true
     */
    @Test
    public void acceptOnlyHexaTest() {
        isSelected = true;
        acceptOnlyHexadecimal = true;
        LinkedList<java.lang.Character> ll = new LinkedList<>();
        Mockito.when(gim.getRecentClicks()).thenReturn(new ArrayList<>());
        Mockito.when(gim.getLastChars()).thenReturn(ll);
        ll.add('A');
        handleInput();
        Assertions.assertEquals("A", actualText);
        ll.clear();
        ll.add('1');
        ll.add('9');
        handleInput();
        ll.clear();
        Assertions.assertEquals("A19", actualText);
        ll.add('G');
        handleInput();
        Assertions.assertEquals("A19", actualText);
    }

    /**
     * Test if the attribute <code>selectedPosition</code> is updated correctly
     */
    @Test
    public void selectedPositionTest() {
        isSelected = true;
        actualText = "ABCD";
        selectedPosition = 4;
        LinkedList<java.lang.Character> ll = new LinkedList<>();
        Mockito.when(gim.getRecentClicks()).thenReturn(new ArrayList<>());
        Mockito.when(gim.getLastChars()).thenReturn(ll);
        ll.add('E');
        handleInput();
        Assertions.assertEquals("ABCDE", actualText);
        Mockito.when(gim.isKey(Input.Keys.LEFT, KeyStatus.Pressed)).thenReturn(true);
        ll.clear();
        for (int i = 0; i < 3; i++)
            handleInput();
        ll.add('B');
        Mockito.when(gim.isKey(Input.Keys.LEFT, KeyStatus.Pressed)).thenReturn(false);
        handleInput();
        Assertions.assertEquals("ABBCDE", actualText);
        ll.clear();
        Mockito.when(gim.isKey(Input.Keys.LEFT, KeyStatus.Pressed)).thenReturn(true);
        for (int i = 0; i < 10; i++)
            handleInput();
        Assertions.assertEquals(0, selectedPosition);
        Mockito.when(gim.isKey(Input.Keys.LEFT, KeyStatus.Pressed)).thenReturn(false);
        Mockito.when(gim.isKey(Input.Keys.RIGHT, KeyStatus.Pressed)).thenReturn(true);
        for (int i = 0; i < 10; i++)
            handleInput();
        Assertions.assertEquals(6, selectedPosition);
    }
}
