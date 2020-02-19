package be.ac.umons.sgl.mom.GraphicalObjects.Controls;

import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class ColorSelectorTest extends ColorSelector {

    public ColorSelectorTest() {
        selectedColor = new Color(0x21212142);
        tb = Mockito.mock(TextBox.class);
        sr = Mockito.mock(ShapeRenderer.class);
        gs = Mockito.mock(GraphicalSettings.class);
        gim = Mockito.mock(GameInputManager.class);
    }

    @Test
    void updateSelectedColorTest() {
        Mockito.when(tb.getText()).thenReturn("00AF");
        updateSelectedColor();
        Assertions.assertEquals(new Color(0x21212142), selectedColor);
        Mockito.when(tb.getText()).thenReturn("FFFFFF");
        updateSelectedColor();
        Assertions.assertEquals(new Color(0xFFFFFFFF), selectedColor);
        Mockito.when(tb.getText()).thenReturn("000F00AF");
        updateSelectedColor();
        Assertions.assertEquals(new Color(0x000F00AF), selectedColor);
        Mockito.when(tb.getText()).thenReturn("00AF");
        updateSelectedColor();
        Assertions.assertEquals(new Color(0x000F00AF), selectedColor);
    }
}