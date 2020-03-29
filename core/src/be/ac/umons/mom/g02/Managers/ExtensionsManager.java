package be.ac.umons.mom.g02.Managers;

import be.ac.umons.mom.g02.GameStates.GameState;
import be.ac.umons.mom.g02.GameStates.LoadingState;
import be.ac.umons.mom.g02.GameStates.Menus.CreatePlayerMenuState;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.LoadFile;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ExtensionsManager {

    /**
     * The instance of ExtensionsManager
     */
    protected static ExtensionsManager instance;

    /**
     * @return The instance of ExtensionsManager
     */
    public static ExtensionsManager getInstance() {
        if (instance == null)
            instance = new ExtensionsManager();
        return instance;
    }

    /**
     * The list of the extensions to show.
     */
    protected HashMap<String, Extension> extensions;

    protected Extension baseGame;

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

    /**
     * Search the main extension in the activated one
     */
    protected void searchMainExtension() {
        mainExtension = null;
        for (Extension e1 : getExtensions()) {
            if (! e1.activated)
                continue;
            boolean mainOne = true;
            for (Extension e2 : getExtensions()) {
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
    public HashMap<String, Extension> parseExtensionFile() {
        HashMap<String, Extension> extensionList = new HashMap<>();
        BufferedReader br;
        try {
            FileHandle ef = Gdx.files.getFileHandle("extensions", Files.FileType.Internal);
            br = new BufferedReader(ef.reader());
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
                    case ".file":
                        if (lineTab.length < 3)
                            Gdx.app.log("ExtensionsSelector", String.format("Error in extension's file : line %d : .file needs a file path and his type", actualLine));
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
                        if (! line.startsWith(".") && ! line.trim().equals("")) {
                            ext = new Extension();
                            if (line.equals("MasterOfMonsGame")) {
                                baseGame = ext;
                            } else {
                                ext.extensionName = line;
                                ext.canActivateWith.add(line);
                                extensionList.put(ext.extensionName, ext);
                            }
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
        mapsToLoad.addAll(baseGame.mapsToLoad);
        filesToLoad.addAll(baseGame.dirsFileToLoad);
        for (Extension ext : getExtensions()) {
            if (ext.activated) {
                mapsToLoad.addAll(ext.mapsToLoad);
                filesToLoad.addAll(ext.dirsFileToLoad);
            }
        }
    }

    /**
     * Do the necessary action when an extension is activated
     * @param activatedExtension The activated extension
     */
    public void onExtensionActivated(Extension activatedExtension) {
        boolean launch = true;
        for (Extension e : getExtensions()) {
            activatedExtension.activated = true;
            if ((! activatedExtension.canActivateWith.contains(e.extensionName) && ! e.canActivateWith.contains(activatedExtension.extensionName))) {
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

    /**
     * Do the necessary actions when an extension is deactivated
     * @param deactivatedExtension The deactivated extension
     */
    public void onExtensionDeactivated(Extension deactivatedExtension) {
        deactivatedExtension.activated = false;
        if (mainExtension == deactivatedExtension) {
            searchMainExtension();
        }
        for (Extension e : getExtensions()) {
            boolean mustActivate = true;
            for (Extension e2 : getExtensions()) {
                if (e2.activated && ! e2.canActivateWith.contains(e.extensionName) && ! e.canActivateWith.contains(e2.extensionName)) {
                    mustActivate = false;
                    break;
                }
            }
            e.canBeActivated = mustActivate;
        }
    }

    /**
     * Change the state and set its settings to correspond to the activated extensions one
     * @param gsm The game's state manager
     */
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

    /**
     * Change the state and set its settings to correspond to the activated extensions one when we want to load an old game
     * @param gsm The game's state manager
     */
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
            MasterOfMonsGame.showAnError(String.format("The extension \"%s\" couldn't be loaded !", mainExt.extensionName));
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

    /**
     * @return The extension to launch between all the activated one.
     */
    public Extension getMainExtension() {
        return mainExtension;
    }

    /**
     * @return A list of all detected extensions
     */
    public List<Extension> getExtensions() {
        return Arrays.asList(extensions.values().toArray(new Extension[0]));
    }

    public HashMap<String, Extension> getExtensionsMap() {
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
         * The state to launch in first for this extension.
         */
        public String mainClass;
        /**
         * The state to launch before the loading the state
         */
        public String mainClassBeforeLoading;
        /**
         * The state to launch in first for this extension (before the character creation).
         */
        public String mainClassBeforeCharacterCreation;
        /**
         * The state to be in when an old game is about to be loaded
         */
        public String classAfterOldGameSelection;
        /**
         * The state to launch before we select an old game file
         */
        public String classBeforeOldGameSelection;
        /**
         * The maps to load for this extension.
         */
        public ArrayList<String> mapsToLoad = new ArrayList<>();
        /**
         * Represent with with extensions this extension can be active with
         */
        public ArrayList<String> canActivateWith = new ArrayList<>();
        /**
         * If the extension is a multiplayer one or not
         */
        public boolean isMultiplayer = false;
        /**
         * If the extension is activated or not.
         */
        public boolean activated = false;
        /**
         * If this extension can be activated following other activated extension
         */
        public boolean canBeActivated = true;

        /**
         * @return The state to launch in first for this extension.
         * @throws ClassNotFoundException If the specified class wasn't found
         */
        public Class<? extends GameState> getMainClass() throws ClassNotFoundException {
            return mainClass == null ? null : (Class<? extends GameState>) Class.forName(mainClass);
        }

        /**
         * @return The state to launch before the loading the state
         * @throws ClassNotFoundException If the specified class wasn't found
         */
        public Class<? extends GameState> getMainClassBeforeLoading() throws ClassNotFoundException {
            return mainClassBeforeLoading == null ? null : (Class<? extends GameState>) Class.forName(mainClassBeforeLoading);
        }

        /**
         * @return The state to launch in first for this extension (before the character creation).
         * @throws ClassNotFoundException If the specified class wasn't found
         */
        public Class<? extends GameState> getMainClassBeforeCharacterCreation() throws ClassNotFoundException {
            return mainClassBeforeCharacterCreation == null ? null : (Class<? extends GameState>) Class.forName(mainClassBeforeCharacterCreation);
        }

        /**
         * @return The state to be in when an old game is about to be loaded
         * @throws ClassNotFoundException If the specified class wasn't found
         */
        public Class<? extends GameState> getClassAfterOldGameSelection() throws ClassNotFoundException {
            return classAfterOldGameSelection == null ? null : (Class<? extends GameState>) Class.forName(classAfterOldGameSelection);
        }

        /**
         * @return The state to launch before we select an old game file
         * @throws ClassNotFoundException If the specified class wasn't found
         */
        public Class<? extends GameState> getClassBeforeOldGameSelection() throws ClassNotFoundException {
            return classBeforeOldGameSelection == null ? null : (Class<? extends GameState>) Class.forName(classBeforeOldGameSelection);
        }
    }
}
