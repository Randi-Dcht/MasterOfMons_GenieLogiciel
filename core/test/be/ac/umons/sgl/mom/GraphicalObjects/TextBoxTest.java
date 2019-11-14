package be.ac.umons.sgl.mom.GraphicalObjects;

import be.ac.umons.sgl.mom.Enums.KeyStatus;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import com.badlogic.gdx.Input;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.awt.*;
import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 * Cette classe représente les tests de la classe TextBox.
 */
public class TextBoxTest {
    /**
     * Le texte actuellement entré.
     */
    private String actualText = "";
    /**
     * Le suffixe à placer en fin de texte.
     */
    private String suffix = "";
    /**
     * Est-ce que le support est selectionné.
     */
    private boolean isSelected = false;

    /**
     * Le GameInputManager du jeu normalement donné par la classe mère de TextBox.
     */
    @Mock
    private GameInputManager gim;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(gim.getRecentClicks()).thenReturn(new ArrayList<>());
    }

    /**
     * Prends en compte les entrées utilisateur.
     */
    public void handleInput() {
        for (Point click : gim.getRecentClicks())
            isSelected = new Rectangle(10, 30, 20, 20).contains(click); // Changer afin de simplifier le test. (30 = 50 (Window's height) - 20)
        if (isSelected) {
            boolean upper = false;
            if (gim.isKey(Input.Keys.SHIFT_LEFT, KeyStatus.Pressed) || gim.isKey(Input.Keys.SHIFT_RIGHT, KeyStatus.Pressed))
                upper = true;
            for (int key : IntStream.rangeClosed(Input.Keys.A, Input.Keys.Z).toArray())
                if (gim.isKey(key, KeyStatus.Pressed))
                    actualText += upper ? Input.Keys.toString(key) : Input.Keys.toString(key).toLowerCase();
            if (gim.isKey(Input.Keys.BACKSPACE, KeyStatus.Pressed))
                actualText = actualText.substring(0, actualText.length() - 1);
        }
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
