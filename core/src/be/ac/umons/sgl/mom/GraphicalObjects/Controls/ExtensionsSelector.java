package be.ac.umons.sgl.mom.GraphicalObjects.Controls;

import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import be.ac.umons.sgl.mom.Objects.LoadFile;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * The control where the user can choose which extensions is (de)activated.
 * Extensions's list are generated from files "extensions".
 */
public class ExtensionsSelector extends Control {

    /**
     * The list of the extensions to show.
     */
    protected List<Extension> extensions;
    /**
     * The list of <code>CheckBox</code> reserved by this control.
     */
    protected Map<Extension, CheckBox> checkBoxList;

    /**
     * A list of all the maps that need to be loaded.
     */
    protected List<String> mapsToLoad;
    /**
     * The list of files that need to be loaded.
     */
    protected List<LoadFile> filesToLoad;

    /**
     * Represents the main extension (the one to launch)
     */
    protected Extension mainExtension;

    /**
     * @param gim The game's input manager
     * @param gs The game's graphical settings
     */
    public ExtensionsSelector(GameInputManager gim, GraphicalSettings gs) {
        super(gim, gs);
        extensions = parseExtensionFile();
        checkBoxList = new HashMap<>();
        for (Extension ext : extensions) {
            CheckBox cb = new CheckBox(gim, gs, ext.extensionName);
            checkBoxList.put(ext, cb);
            cb.setOnChecked(() -> {
                boolean launch = true;
                for (Extension e : extensions) {
                    ext.activated = true;
                    if (! ext.canActivateWith.contains(e.extensionName) && ! e.canActivateWith.contains(ext.extensionName)) {
                        e.activated = false;
                        checkBoxList.get(e).setActivated(false);
                        checkBoxList.get(e).setChecked(false);
                    }

                    if (e != ext && e.activated && e.canActivateWith.contains(ext.extensionName))
                        launch = false;
                }
                if (launch) {
                    if (mainExtension != null)
                        checkBoxList.get(mainExtension).setSelected(false);
                    mainExtension = ext;
                    checkBoxList.get(ext).setSelected(true);
                }
            });
            cb.setOnUnchecked(() -> {
                checkBoxList.get(ext).setSelected(false);
                ext.activated = false;
                if (mainExtension == ext) {
                    searchMainExtension();
                }
                for (Extension e : extensions) {
                    boolean mustActivate = true;
                    for (Extension e2 : extensions) {
                        if (e2.activated && ! e2.canActivateWith.contains(e.extensionName) && ! e.canActivateWith.contains(e2.extensionName)) {
                            mustActivate = false;
                            break;
                        }
                    }
                    checkBoxList.get(e).setActivated(mustActivate);
                }
            });
        }
    }

    /**
     * Default constructor. USE ONLY FOR TEST.
     */
    protected ExtensionsSelector() {}

    @Override
    public void draw(Batch batch, Point pos, Point size) {
        for (CheckBox c : checkBoxList.values()) {
            c.draw(batch, pos, new Point(size.x, (int)gs.getSmallFont().getLineHeight()));
            pos.y -= (int)gs.getSmallFont().getLineHeight() + topMargin;
        }
    }

    @Override
    public void handleInput() {
        for (CheckBox c : checkBoxList.values()) {
            c.handleInput();
        }
    }

    @Override
    public void dispose() {
        for (CheckBox cb: checkBoxList.values())
            cb.dispose();
    }

    protected void searchMainExtension() {
        mainExtension = null;
        for (Extension e1 : extensions) {
            if (! e1.activated)
                continue;
            boolean mainOne = true;
            for (Extension e2 : extensions) {
                if (e2.activated && e2.canActivateWith.contains(e1)) {
                    mainOne = false;
                    break;
                }
            }
            if (mainOne) {
                mainExtension = e1;
                break;
            }
        }
        if (mainExtension != null)
            checkBoxList.get(mainExtension).setSelected(true);
    }

    /**
     * Return a list of all extensions present in file "extensions". Returns <code>null</code> if an error occurred.
     * @return A list of all extensions present in file "extensions"
     */
    public List<Extension> parseExtensionFile() {
        ArrayList<Extension> extensionList = new ArrayList<>();
        BufferedReader br;
        try {
            Files f = Gdx.files;
            FileHandle ef = Gdx.files.getFileHandle("extensions", Files.FileType.Internal);
            br = new BufferedReader(new FileReader(ef.file()));
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
                            Gdx.app.log("ExtensionsSelector", String.format("Error in extension's file : line %d : .load needs a file path and his type", actualLine));
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
                    case ".canActivateWith":
                        if (lineTab.length < 2)
                            Gdx.app.log("ExtensionsSelector", String.format("Error in extension's file : line %d : .canActivateWith needs another extension's name !", actualLine));
                        else {
                            ext.canActivateWith.add(lineTab[1]);
                        }
                        break;
                    default:
                        if (! line.startsWith(".")) {
                            ext = new Extension();
                            extensionList.add(ext);
                            ext.extensionName = line;
                            ext.canActivateWith.add(line);
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
     * Generate the list of map and files to load in order of loading them later.
     */
    public void generateLoadLists() {
        mapsToLoad = new ArrayList<>();
        filesToLoad = new ArrayList<>();
        for (Extension ext : checkBoxList.keySet()) {
            if (checkBoxList.get(ext).isChecked()) {
                mapsToLoad.addAll(ext.mapsToLoad);
                filesToLoad.addAll(ext.dirsFileToLoad);
            }
        }
    }

    /**
     * @return The maps that need to be loaded.
     */
    public List<String> getMapsToLoad() {
        return mapsToLoad;
    }

    /**
     * @return The files that need to be loaded.
     */
    public List<LoadFile> getFilesToLoad() {
        return filesToLoad;
    }

    /**
     * Represent the characteristics of an extension.
     */
    protected static class Extension {
        /**
         * The extension's name.
         */
        public String extensionName;
        /**
         * The list of files/dirs to load for this extension.
         */
        public ArrayList<LoadFile> dirsFileToLoad = new ArrayList<>();
        /**
         * The class to launch in first for this extension.
         */
        public String mainClass;
        /**
         * The maps to load for this extension.
         */
        public ArrayList<String> mapsToLoad = new ArrayList<>();

        public ArrayList<String> canActivateWith = new ArrayList<>();

        /**
         * If the extension is activated or not.
         */
        public boolean activated;
    }
}
