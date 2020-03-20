package be.ac.umons.mom.g02.Managers;

import be.ac.umons.mom.g02.GameStates.GameState;
import be.ac.umons.mom.g02.GameStates.LoadingState;
import be.ac.umons.mom.g02.GameStates.Menus.CreatePlayerMenuState;
import be.ac.umons.mom.g02.Objects.LoadFile;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExtensionsManager {

    protected static ExtensionsManager instance;

    public static ExtensionsManager getInstance() {
        if (instance == null)
            instance = new ExtensionsManager();
        return instance;
    }

    /**
     * The list of the extensions to show.
     */
    protected List<Extension> extensions;
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

    protected ExtensionsManager() {
        extensions = parseExtensionFile();
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
    }


    /**
     * Return a list of all extensions present in file "extensions". Returns <code>null</code> if an error occurred.
     * @return A list of all extensions present in file "extensions"
     */
    public List<Extension> parseExtensionFile() {
        ArrayList<Extension> extensionList = new ArrayList<>();
        BufferedReader br;
        try {
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
                    case ".mainClassBeforeLoading":
                        if (lineTab.length < 2)
                            Gdx.app.log("ExtensionsSelector", String.format("Error in extension's file : line %d : .mainClassBeforeLoading needs a class", actualLine));
                        else
                            ext.mainClassBeforeLoading = lineTab[1];
                        break;
                    case ".mainClassBeforeCharacterCreation":
                        if (lineTab.length < 2)
                            Gdx.app.log("ExtensionsSelector", String.format("Error in extension's file : line %d : .mainClassBeforeCharacterCreation needs a class", actualLine));
                        else
                            ext.mainClassBeforeCharacterCreation = lineTab[1];
                        break;
                    case ".classBeforeOldGameSelection":
                        if (lineTab.length < 2)
                            Gdx.app.log("ExtensionsSelector", String.format("Error in extension's file : line %d : .classBeforeOldGameSelection needs a class", actualLine));
                        else
                            ext.classBeforeOldGameSelection = lineTab[1];
                        break;
                    case ".classAfterOldGameSelection":
                        if (lineTab.length < 2)
                            Gdx.app.log("ExtensionsSelector", String.format("Error in extension's file : line %d : .classAfterOldGameSelection needs a class", actualLine));
                        else
                            ext.classAfterOldGameSelection = lineTab[1];
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
                    case ".isMultiplayer":
                        ext.isMultiplayer = true;
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
        for (Extension ext : extensions) {
            if (ext.activated) {
                mapsToLoad.addAll(ext.mapsToLoad);
                filesToLoad.addAll(ext.dirsFileToLoad);
            }
        }
    }

    public void onExtensionActivated(Extension activatedExtension) {
        boolean launch = true;
        for (Extension e : extensions) {
            activatedExtension.activated = true;
            if (! activatedExtension.canActivateWith.contains(e.extensionName) && ! e.canActivateWith.contains(activatedExtension.extensionName)) {
                e.activated = false;
                e.canBeActivated = false;
            }

            if (e != activatedExtension && e.activated && e.canActivateWith.contains(activatedExtension.extensionName))
                launch = false;
        }
        if (launch) {
            mainExtension = activatedExtension;
        }
    }

    public void onExtensionDeactivated(Extension deactivatedExtension) {
        deactivatedExtension.activated = false;
        if (mainExtension == deactivatedExtension) {
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
            e.canBeActivated = mustActivate;
        }
    }

    public void initGame(GameStateManager gsm) {
        ExtensionsManager.Extension mainExt = getMainExtension();
        if (mainExt != null && mainExt.mainClassBeforeCharacterCreation != null) {
            try {
                gsm.setState(mainExt.getMainClassBeforeCharacterCreation());
            } catch (ClassNotFoundException e) {
                Gdx.app.error("ExtensionsFile", String.format("The class %s wasn't found", mainExt.mainClassBeforeCharacterCreation), e);
            }
        } else {
            CreatePlayerMenuState cpms = (CreatePlayerMenuState) gsm.setState(CreatePlayerMenuState.class, false);
            for (ExtensionsManager.Extension ext : getExtensions()) {
                if (ext.activated && ext.isMultiplayer)
                    cpms.setMustUseMultiplayer(true);
            }
            try {
                if (mainExt != null) {
                    Class<? extends GameState> gs = mainExt.getMainClassBeforeLoading();
                    if (gs != null)
                        cpms.setAfterCreationState(gs);
                    gs = mainExt.getMainClass();
                    if (gs != null)
                        cpms.setAfterLoadingState(gs);

                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                Gdx.app.error("Critical error", e.getMessage());
            }
        }
    }

    public void initGameFromLoad(GameStateManager gsm) {
        ExtensionsManager.Extension mainExt = getMainExtension();
        try {
            if (mainExt != null && mainExt.getClassAfterOldGameSelection() != null) {
                gsm.setState(mainExt.getClassAfterOldGameSelection());
            } else if (mainExt != null && mainExt.mainClassBeforeCharacterCreation != null) {
                gsm.setState(mainExt.getMainClassBeforeCharacterCreation());
            } else if (mainExt != null) {
                Class<? extends GameState> gs = mainExt.getMainClassBeforeLoading();
                if (gs != null)
                    gsm.setState(gs);
                else if ((gs = mainExt.getMainClass()) != null) {
                    LoadingState ls = (LoadingState) gsm.setState(LoadingState.class);
                    ls.setAfterLoadingState(gs);
                }
                else
                    gsm.setState(LoadingState.class);
            } else
                gsm.setState(LoadingState.class);
        } catch (ClassNotFoundException e) {
            Gdx.app.error("ExtensionsFile", String.format("The class %s wasn't found", mainExt.mainClassBeforeCharacterCreation), e);
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

    public Extension getMainExtension() {
        return mainExtension;
    }

    public List<Extension> getExtensions() {
        return extensions;
    }

    /**
     * Represent the characteristics of an extension.
     */
    public static class Extension {
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
         * The class to launch in first for this extension.
         */
        public String mainClassBeforeLoading;
        /**
         * The class to launch in first for this extension (before the character creation).
         */
        public String mainClassBeforeCharacterCreation;

        public String classAfterOldGameSelection;

        public String classBeforeOldGameSelection;
        /**
         * The maps to load for this extension.
         */
        public ArrayList<String> mapsToLoad = new ArrayList<>();

        public ArrayList<String> canActivateWith = new ArrayList<>();

        public boolean isMultiplayer = false;

        /**
         * If the extension is activated or not.
         */
        public boolean activated = false;

        public boolean canBeActivated = true;

        public Class<? extends GameState> getMainClass() throws ClassNotFoundException {
            return mainClass == null ? null : (Class<? extends GameState>) Class.forName(mainClass);
        }

        public Class<? extends GameState> getMainClassBeforeLoading() throws ClassNotFoundException {
            return mainClassBeforeLoading == null ? null : (Class<? extends GameState>) Class.forName(mainClassBeforeLoading);
        }

        public Class<? extends GameState> getMainClassBeforeCharacterCreation() throws ClassNotFoundException {
            return mainClassBeforeCharacterCreation == null ? null : (Class<? extends GameState>) Class.forName(mainClassBeforeCharacterCreation);
        }
        public Class<? extends GameState> getClassAfterOldGameSelection() throws ClassNotFoundException {
            return classAfterOldGameSelection == null ? null : (Class<? extends GameState>) Class.forName(classAfterOldGameSelection);
        }

        public Class<? extends GameState> getClassBeforeOldGameSelection() throws ClassNotFoundException {
            return classBeforeOldGameSelection == null ? null : (Class<? extends GameState>) Class.forName(classBeforeOldGameSelection);
        }
    }
}
