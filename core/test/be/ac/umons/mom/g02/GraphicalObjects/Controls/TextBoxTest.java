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
public class TextBoxTest {

    TextBox tb;

    @BeforeEach
    public void init() {
        tb = new TextBox();
        tb.gim = Mockito.mock(GameInputManager.class);
        Mockito.when(tb.gim.getRecentClicks()).thenReturn(new ArrayList<>());
        tb.width = 20;
        tb.height = 20;
        MasterOfMonsGame.WIDTH = 50;
        MasterOfMonsGame.HEIGHT = 50;
    }

    /**
     * Test if the attribute <code>isSelected</code> is updated correctly
     */
    @Test
    public void isSelectedTest() {
        tb.handleInput();
        Assertions.assertFalse(tb.isSelected);
        ArrayList<Point> l = new ArrayList<>();
        Point c = new Point(15, 35); // IN
        l.add(c);
        Mockito.when(tb.gim.getRecentClicks()).thenReturn(l);
        tb.handleInput();
        Assertions.assertTrue(tb.isSelected);
        Point c2 = new Point(15, 20); // OUT
        l.add(c2);
        tb.handleInput();
        Assertions.assertFalse(tb.isSelected);
    }

    /**
     * Test if the attribute <code>actualText</code> is updated correctly
     */
    @Test
    public void actualTextTest() {
        LinkedList<java.lang.Character> ll = new LinkedList<>();
        Mockito.when(tb.gim.getRecentClicks()).thenReturn(new ArrayList<>());
        Mockito.when(tb.gim.getLastChars()).thenReturn(ll);
        tb.suffix = ".txt";
        tb.handleInput();
        tb.isSelected = true;
        Assertions.assertEquals("", tb.actualText);
        ll.add('a');
        tb.handleInput();
        Assertions.assertEquals("a", tb.actualText);
        ll.clear();
        ll.add('A');
        tb.handleInput();
        Assertions.assertEquals("aA", tb.actualText);
        Assertions.assertEquals("aA.txt", tb.getText());
        Mockito.when(tb.gim.isKey(Input.Keys.BACKSPACE, KeyStatus.Pressed)).thenReturn(true);
        ll.clear();
        tb.handleInput();
        Assertions.assertEquals("a", tb.actualText);
        Assertions.assertEquals("a.txt", tb.getText());
        tb.isSelected = false;
        Assertions.assertEquals("a", tb.actualText);
        ll.add('A');
        tb.handleInput();
        ll.clear();
        Assertions.assertEquals("a", tb.actualText);
        Mockito.when(tb.gim.isKey(Input.Keys.BACKSPACE, KeyStatus.Pressed)).thenReturn(true);
        tb.handleInput();
        Assertions.assertEquals("a", tb.actualText);
        Assertions.assertEquals("a.txt", tb.getText());
    }

    /**
     * Test if only numbers are accepted when <code>acceptOnlyNumbers</code> is true
     */
    @Test
    public void acceptOnlyNumberTest() {
        tb.isSelected = true;
        tb.acceptOnlyNumbers = true;
        LinkedList<java.lang.Character> ll = new LinkedList<>();
        Mockito.when(tb.gim.getRecentClicks()).thenReturn(new ArrayList<>());
        Mockito.when(tb.gim.getLastChars()).thenReturn(ll);
        ll.add('a');
        tb.handleInput();
        Assertions.assertEquals("", tb.actualText);
        ll.add('2');
        tb.handleInput();
        Assertions.assertEquals("2", tb.actualText);
    }

    /**
     * Test if only hexa-decimal characters are accepted when <code>acceptOnlyHexadecimal</code> is true
     */
    @Test
    public void acceptOnlyHexaTest() {
        tb.isSelected = true;
        tb.acceptOnlyHexadecimal = true;
        LinkedList<java.lang.Character> ll = new LinkedList<>();
        Mockito.when(tb.gim.getRecentClicks()).thenReturn(new ArrayList<>());
        Mockito.when(tb.gim.getLastChars()).thenReturn(ll);
        ll.add('A');
        tb.handleInput();
        Assertions.assertEquals("A", tb.actualText);
        ll.clear();
        ll.add('1');
        ll.add('9');
        tb.handleInput();
        ll.clear();
        Assertions.assertEquals("A19", tb.actualText);
        ll.add('G');
        tb.handleInput();
        Assertions.assertEquals("A19", tb.actualText);
    }

    /**
     * Test if the attribute <code>selectedPosition</code> is updated correctly
     */
    @Test
    public void selectedPositionTest() {
        tb.isSelected = true;
        tb.actualText = "ABCD";
        tb.selectedPosition = 4;
        LinkedList<java.lang.Character> ll = new LinkedList<>();
        Mockito.when(tb.gim.getRecentClicks()).thenReturn(new ArrayList<>());
        Mockito.when(tb.gim.getLastChars()).thenReturn(ll);
        ll.add('E');
        tb.handleInput();
        Assertions.assertEquals("ABCDE", tb.actualText);
        Mockito.when(tb.gim.isKey(Input.Keys.LEFT, KeyStatus.Pressed)).thenReturn(true);
        ll.clear();
        for (int i = 0; i < 3; i++)
            tb.handleInput();
        ll.add('B');
        Mockito.when(tb.gim.isKey(Input.Keys.LEFT, KeyStatus.Pressed)).thenReturn(false);
        tb.handleInput();
        Assertions.assertEquals("ABBCDE", tb.actualText);
        ll.clear();
        Mockito.when(tb.gim.isKey(Input.Keys.LEFT, KeyStatus.Pressed)).thenReturn(true);
        for (int i = 0; i < 10; i++)
            tb.handleInput();
        Assertions.assertEquals(0, tb.selectedPosition);
        Mockito.when(tb.gim.isKey(Input.Keys.LEFT, KeyStatus.Pressed)).thenReturn(false);
        Mockito.when(tb.gim.isKey(Input.Keys.RIGHT, KeyStatus.Pressed)).thenReturn(true);
        for (int i = 0; i < 10; i++)
            tb.handleInput();
        Assertions.assertEquals(6, tb.selectedPosition);
    }
}
