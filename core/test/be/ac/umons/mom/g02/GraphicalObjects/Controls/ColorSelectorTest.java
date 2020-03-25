package be.ac.umons.mom.g02.GraphicalObjects.Controls;

import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing class for the ColorSelector
 */
class ColorSelectorTest extends ColorSelector {

    public ColorSelectorTest() {
        selectedColor = new Color(0x21212142);
        tb = Mockito.mock(TextBox.class);
        sr = Mockito.mock(ShapeRenderer.class);
        gs = Mockito.mock(GraphicalSettings.class);
        gim = Mockito.mock(GameInputManager.class);
    }

    /**
     * Test if the ColorSelector updates itself in the good case (not when color is incorrect)
     */
    @Test
    void updateSelectedColorTest() {
        Mockito.when(tb.getText()).thenReturn("00AF");
        updateSelectedColor();
        assertEquals(new Color(0x21212142), selectedColor);
        Mockito.when(tb.getText()).thenReturn("FFFFFF");
        updateSelectedColor();
        assertEquals(new Color(0xFFFFFFFF), selectedColor);
        Mockito.when(tb.getText()).thenReturn("000F00AF");
        updateSelectedColor();
        assertEquals(new Color(0x000F00AF), selectedColor);
        Mockito.when(tb.getText()).thenReturn("00AF");
        updateSelectedColor();
        assertEquals(new Color(0x000F00AF), selectedColor);
    }
}