package be.ac.umons.mom.g02.Objects;

import be.ac.umons.mom.g02.Enums.Languages;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;

/**
 * Testing class for <code>GraphicalSettings</code>
 */
public class GraphicalSettingsTest {

    GraphicalSettings gs;

    public GraphicalSettingsTest() {
        Gdx.files = Mockito.mock(Files.class);
        Gdx.app = Mockito.mock(Application.class);
        File f = new File("testAssets/Maps/");
        FileHandle fh = Mockito.mock(FileHandle.class);
        Mockito.when(fh.file()).thenReturn(f);
        Mockito.when(Gdx.files.internal("Pictures/")).thenReturn(fh);
        gs = new GraphicalSettings();
    }

    /**
     * Test if changing the language is effective
     */
    @Test
    public void testBundle() { //TODO : Simulate the file handle.
        gs.setLanguage(Languages.English);
        Assertions.assertEquals("Master Of Mons", gs.getStringFromId("gameName"));
        gs.setLanguage(Languages.French);
        Assertions.assertEquals("Maître de Mons", gs.getStringFromId("gameName"));
    }

}
