package be.ac.umons.mom.g02.GraphicalObjects.Controls;

import be.ac.umons.mom.g02.Managers.ExtensionsManager;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;

public class ExtensionsSelectorTest {
    ExtensionsManager em;

    @BeforeEach
    public void init() {
        Gdx.files = Mockito.mock(Files.class);
        FileHandle fh = Mockito.mock(FileHandle.class);
        Mockito.when(fh.file()).thenReturn(new File("testAssets/extensions"));
        Mockito.when(Gdx.files.getFileHandle("extensions", Files.FileType.Internal)).thenReturn(fh);
        Gdx.app = Mockito.mock(Application.class);

        em = ExtensionsManager.getInstance();
    }

    @Test
    public void parseExtensionFileTest() {
        Assertions.assertEquals(2, em.getExtensions().size());
        ExtensionsManager.Extension ext1 = em.getExtensions().get(0);
        ExtensionsManager.Extension ext2 = em.getExtensions().get(1);
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

    @Test
    public void searchMainExtensionTest() {
        ExtensionsManager.Extension ext1 = em.getExtensions().get(0);
        ExtensionsManager.Extension ext2 = em.getExtensions().get(1);
        Assertions.assertNull(em.getMainExtension());
        em.onExtensionActivated(ext2);
        Assertions.assertEquals(ext2, em.getMainExtension());
        em.onExtensionActivated(ext1);
        Assertions.assertEquals(ext1, em.getMainExtension());
    }
}
