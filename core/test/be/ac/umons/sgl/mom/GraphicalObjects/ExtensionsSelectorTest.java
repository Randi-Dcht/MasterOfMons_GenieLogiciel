package be.ac.umons.sgl.mom.GraphicalObjects;

import be.ac.umons.sgl.mom.GraphicalObjects.Controls.ExtensionsSelector;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExtensionsSelectorTest extends ExtensionsSelector {

    @Test
    public void parseExtensionFileTest() {
        Files f = Gdx.files;
        extensions = parseExtensionFile();
        Assertions.assertEquals(2, extensions.size());
        Extension ext1 = extensions.get(0);
        Extension ext2 = extensions.get(1);
        Assertions.assertEquals("TestClass", ext1.extensionName);
        Assertions.assertEquals("Test2Class", ext2.extensionName);
        Assertions.assertEquals(2, ext1.dirsFileToLoad.size());
        Assertions.assertTrue(ext1.dirsFileToLoad.contains("File1"));
        Assertions.assertTrue(ext1.dirsFileToLoad.contains("File2"));
        Assertions.assertFalse(ext1.dirsFileToLoad.contains("File3"));
        Assertions.assertFalse(ext2.dirsFileToLoad.contains("File1"));
        Assertions.assertFalse(ext2.dirsFileToLoad.contains("File2"));
        Assertions.assertTrue(ext2.dirsFileToLoad.contains("File3"));
        Assertions.assertFalse(ext2.dirsFileToLoad.contains("File4"));
        Assertions.assertTrue(ext1.mapsToLoad.contains("Maps/Map1"));
        Assertions.assertTrue(ext1.dirsFileToLoad.contains("Maps/Map2"));
        Assertions.assertFalse(ext1.mapsToLoad.contains("Maps/Map3"));
        Assertions.assertFalse(ext2.mapsToLoad.contains("Maps/Map1"));
        Assertions.assertFalse(ext2.dirsFileToLoad.contains("Maps/Map2"));
        Assertions.assertTrue(ext2.mapsToLoad.contains("Maps/Map3"));
    }
}
