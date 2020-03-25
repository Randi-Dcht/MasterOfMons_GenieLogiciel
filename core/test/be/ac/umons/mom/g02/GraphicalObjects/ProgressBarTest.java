package be.ac.umons.mom.g02.GraphicalObjects;

import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


/**
 * Testing class for ProgressBar
 */
public class ProgressBarTest extends ProgressBar {

    public ProgressBarTest() {}

    @BeforeEach
    public void init() {
        sr = Mockito.mock(ShapeRenderer.class);
        gs = Mockito.mock(GraphicalSettings.class);
    }

    @Test
    public void percentTest() {
        setMaxValue(20);
        setValue(5);
        Assertions.assertEquals(.25, getPercent());
        setMaxValue(50);
        Assertions.assertEquals(.1, getPercent());
    }
}