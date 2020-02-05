package be.ac.umons.sgl.mom.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;

public class GraphicalSettingsTest {

    GraphicalSettings gs;

    public GraphicalSettingsTest() {
        gs = new GraphicalSettings();
    }

    @Test
    public void testBundle() {
        Assertions.assertEquals("Master Of Mons", gs.getStringFromId("gameName"));
        gs.setLocale(Locale.FRENCH);
        Assertions.assertEquals("Maître de Mons", gs.getStringFromId("gameName"));
    }

}
