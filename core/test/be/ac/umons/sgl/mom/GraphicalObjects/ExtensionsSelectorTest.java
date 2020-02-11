package be.ac.umons.sgl.mom.GraphicalObjects;

import be.ac.umons.sgl.mom.GraphicalObjects.Controls.ExtensionsSelector;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.util.logging.FileHandler;

public class ExtensionsSelectorTest extends ExtensionsSelector {

    @Test
    public void parseExtensionFileTest() {
        Gdx.files = Mockito.mock(Files.class);
        FileHandle fh = Mockito.mock(FileHandle.class);
        Mockito.when(fh.file()).thenReturn(new File("testAssets/extensions"));
        Mockito.when(Gdx.files.getFileHandle("extensions", Files.FileType.Internal)).thenReturn(fh);
        Gdx.app = Mockito.mock(Application.class);
        extensions = parseExtensionFile();
        Assertions.assertEquals(2, extensions.size());
        Extension ext1 = extensions.get(0);
        Extension ext2 = extensions.get(1);
        Assertions.assertEquals("Test", ext1.extensionName);
        Assertions.assertEquals("Test2", ext2.extensionName);
        Assertions.assertEquals(1, ext1.dirsFileToLoad.size());
        Assertions.assertEquals(1, ext2.dirsFileToLoad.size());
        Assertions.assertEquals("testAssets/File1", ext1.dirsFileToLoad.get(0).getFile().getPath().replace("\\", "/"));
        Assertions.assertEquals("testAssets/File3", ext2.dirsFileToLoad.get(0).getFile().getPath().replace("\\", "/"));
        Assertions.assertTrue(ext1.mapsToLoad.contains("testAssets/Maps/Map1"));
        Assertions.assertFalse(ext1.mapsToLoad.contains("Maps/Map2"));
        Assertions.assertTrue(ext2.mapsToLoad.contains("testAssets/Maps/Map1"));
        Assertions.assertFalse(ext2.mapsToLoad.contains("testAssets/Maps/Map2"));
    }
}
