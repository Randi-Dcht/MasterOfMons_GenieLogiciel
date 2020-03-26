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
public class ProgressBarTest {

    ProgressBar pb;

    @BeforeEach
    public void init() {
        pb = new ProgressBar();
        pb.sr = Mockito.mock(ShapeRenderer.class);
        pb.gs = Mockito.mock(GraphicalSettings.class);
    }

    @Test
    public void percentTest() {
        pb.setMaxValue(20);
        pb.setValue(5);
        Assertions.assertEquals(.25, pb.getPercent());
        pb.setMaxValue(50);
        Assertions.assertEquals(.1, pb.getPercent());
    }
}