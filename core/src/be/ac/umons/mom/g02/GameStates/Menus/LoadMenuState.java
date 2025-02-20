package be.ac.umons.mom.g02.GameStates.Menus;

import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.ButtonMenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.MenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.TitleMenuItem;
import be.ac.umons.mom.g02.Managers.ExtensionsManager;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;

import java.io.File;

public class LoadMenuState extends ChoosePathMenuState {

    /**
     * @param gs The game's graphical settings.
     */
    public LoadMenuState(GraphicalSettings gs) {
        super(gs);
    }

    @Override
    public void init() {
        super.init();
        setMenuItems(new MenuItem[] {
                new TitleMenuItem(gs, GraphicalSettings.getStringFromId("load")),
                directoryMI,
                new ButtonMenuItem(gim, gs, GraphicalSettings.getStringFromId("cancel"), () -> gsm.removeFirstState()),
                chooseSaveSLC,
        });
        setFolder(path);
    }

    @Override
    protected void onFolderClick(File folder) {
        setFolder(folder.getAbsolutePath());
    }

    @Override
    protected void onFileClick(File file) {
        load(file.getAbsolutePath());
    }

    /**
     * Try to load the files and quit the state.
     * @param loadFilePath The load file's path.
     */
    public void load(String loadFilePath) {
        ExtensionsManager.getInstance().initGameFromLoad();
        MasterOfMonsGame.setGameToLoad(loadFilePath);
    }
}
