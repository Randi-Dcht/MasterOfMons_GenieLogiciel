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
class ColorSelectorTest {

    ColorSelector cs;

    public ColorSelectorTest() {
        cs = new ColorSelector();
        cs.selectedColor = new Color(0x21212142);
        cs.tb = Mockito.mock(TextBox.class);
        cs.sr = Mockito.mock(ShapeRenderer.class);
        cs.gs = Mockito.mock(GraphicalSettings.class);
        cs.gim = Mockito.mock(GameInputManager.class);
    }

    /**
     * Test if the ColorSelector updates itself in the good case (not when color is incorrect)
     */
    @Test
    void updateSelectedColorTest() {
        Mockito.when(cs.tb.getText()).thenReturn("00AF");
        cs.updateSelectedColor();
        assertEquals(new Color(0x21212142), cs.selectedColor);
        Mockito.when(cs.tb.getText()).thenReturn("FFFFFF");
        cs.updateSelectedColor();
        assertEquals(new Color(0xFFFFFFFF), cs.selectedColor);
        Mockito.when(cs.tb.getText()).thenReturn("000F00AF");
        cs.updateSelectedColor();
        assertEquals(new Color(0x000F00AF), cs.selectedColor);
        Mockito.when(cs.tb.getText()).thenReturn("00AF");
        cs.updateSelectedColor();
        assertEquals(new Color(0x000F00AF), cs.selectedColor);
    }
}