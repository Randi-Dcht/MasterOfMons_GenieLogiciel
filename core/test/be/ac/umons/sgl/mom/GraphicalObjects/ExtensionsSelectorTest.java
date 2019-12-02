package be.ac.umons.sgl.mom.GraphicalObjects;

import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import be.ac.umons.sgl.mom.Objects.LoadFile;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExtensionsSelectorTest {

    List<Extension> extensions;

    /**
     * Crée un nouveau selecteur d'extension.
     *
     * @param gim Le GameInputManager du jeu.
     * @param gs  Les paramètres graphiques du jeu.
     */
    public ExtensionsSelectorTest(GameInputManager gim, GraphicalSettings gs) {
        extensions = parseExtensionFile();
    }

    @BeforeEach
    private void init() {
    }

    public List<Extension> parseExtensionFile() {
        ArrayList<Extension> extensionList = new ArrayList<>();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(Gdx.files.getFileHandle("extensions", Files.FileType.Internal).file()));
            Extension ext = null;
            int actualLine = 0;
            String line;
            while ((line = br.readLine()) != null) {
                actualLine++;
                String[] lineTab = line.split(" ");
                switch (lineTab[0]) {
                    case ".mainClass":
                        if (lineTab.length < 2)
                            Gdx.app.log("ExtensionsSelector", String.format("Error in extension's file : line %d : .mainClass needs a class", actualLine));
                        else
                            ext.mainClass = lineTab[1];
                    case ".load":
                        if (lineTab.length < 3)
                            Gdx.app.log("ExtensionsSelector", String.format("Error in extension's file : line %d : .mainClass needs a class", actualLine));
                        else {
                            try {
                                ext.dirsFileToLoad.add(new LoadFile(lineTab[1], lineTab[2]));
                            } catch (ClassNotFoundException e) {
                                Gdx.app.log("ExtensionsSelector", String.format("Error in extension's file : line %d : class %s doesn't exist !", actualLine, lineTab[2]), e);
                            } catch (FileNotFoundException e) {
                                Gdx.app.log("ExtensionsSelector", String.format("Error in extension's file : line %d : file %s doesn't exist !", actualLine, lineTab[1]), e);
                            }
                        }
                    case ".map":
                        if (lineTab.length < 2)
                            Gdx.app.log("ExtensionsSelector", String.format("Error in extension's file : line %d : .map needs a path to a tmx file !", actualLine));
                        else {
                            if (new File(lineTab[1]).exists())
                                ext.mapsToLoad.add(lineTab[1]);
                            else
                                Gdx.app.log("ExtensionsSelector", String.format("Error in extension's file : line %d : the given file (%s) doesn't exist !", actualLine, lineTab[1]));
                        }
                    default:
                        if (! line.startsWith(".")) {
                            ext = new Extension();
                            extensionList.add(ext);
                            ext.extensionName = line;
                        }
                }
            }
        } catch (FileNotFoundException e) {
            Gdx.app.log("ExtensionsSelector", "File extensions wasn't found !", e);
            return null;
        } catch (IOException e) {
            Gdx.app.log("ExtensionsSelector", "An error has occurred while reading the extension's file !", e);
        }

        return extensionList;
    }

    @Test
    private void parseExtensionFileTest() throws IOException {
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

    protected class Extension {
        String extensionName;
        ArrayList<LoadFile> dirsFileToLoad = new ArrayList<>();
        String mainClass;
        ArrayList<String> mapsToLoad = new ArrayList<>();
        boolean activated;
    }
}
