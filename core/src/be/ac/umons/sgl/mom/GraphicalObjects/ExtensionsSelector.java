package be.ac.umons.sgl.mom.GraphicalObjects;

import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import be.ac.umons.sgl.mom.Objects.LoadFile;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Représente le sélectionneur d'extensions. La liste des extensions qu'il affiche se génére en fonction du fichier "extensions".
 */
public class ExtensionsSelector extends Control {

    /**
     * La liste des extensions à afficher.
     */
    List<Extension> extensions;
    /**
     * La liste des cases à cocher.
     */
    List<CheckBox> checkBoxList;

    /**
     * Crée un nouveau selecteur d'extension.
     *
     * @param gim Le GameInputManager du jeu.
     * @param gs  Les paramètres graphiques du jeu.
     */
    public ExtensionsSelector(GameInputManager gim, GraphicalSettings gs) {
        super(gim, gs);
        extensions = parseExtensionFile();
        checkBoxList = new ArrayList<>();
        for (Extension ext : extensions) {
            checkBoxList.add(new CheckBox(gim, gs, ext.extensionName));
        }
    }

    @Override
    public void draw(Batch batch, Point pos, Point size) {
        for (CheckBox c : checkBoxList) {
            c.draw(batch, pos, new Point(size.x, (int)gs.getSmallFont().getLineHeight()));
            pos.y -= (int)gs.getSmallFont().getLineHeight() + topMargin;
        }
    }

    @Override
    public void handleInput() {
        for (CheckBox c : checkBoxList)
            c.handleInput();
    }

    @Override
    public void dispose() {
        for (CheckBox cb: checkBoxList)
            cb.dispose();
    }

    /**
     * Lis le fichier "extensions" et retourne la liste d'<code>Extension</code> lu dans ce fichier. Retourne <code>null</code> si une erreur se produit.
     * @return La liste d'<code>Extension</code> lu dans le fichier "extensions".
     */
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
                        break;
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
                        break;
                    case ".map":
                        if (lineTab.length < 2)
                            Gdx.app.log("ExtensionsSelector", String.format("Error in extension's file : line %d : .map needs a path to a tmx file !", actualLine));
                        else {
                            if (new File(lineTab[1]).exists())
                                ext.mapsToLoad.add(lineTab[1]);
                            else
                                Gdx.app.log("ExtensionsSelector", String.format("Error in extension's file : line %d : the given file (%s) doesn't exist !", actualLine, lineTab[1]));
                        }
                        break;
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
            return null;
        }

        return extensionList;
    }

    /**
     * Représente les caractéristiques d'extension.
     */
    protected class Extension {
        /**
         * Le nom de l'extension.
         */
        String extensionName;
        /**
         * Les fichiers/dossiers à charger pour cette extension.
         */
        ArrayList<LoadFile> dirsFileToLoad = new ArrayList<>();
        /**
         * La classe à lancer pour l'extension.
         */
        String mainClass;
        /**
         * Les cartes à charger pour cette extension.
         */
        ArrayList<String> mapsToLoad = new ArrayList<>();
        /**
         * Si l'extension est activée ou non.
         */
        boolean activated;
    }
}
