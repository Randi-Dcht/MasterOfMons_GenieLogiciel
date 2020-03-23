package be.ac.umons.mom.g02.GameStates.Menus;

import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.ButtonMenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.MenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.TitleMenuItem;
import be.ac.umons.mom.g02.Managers.ExtensionsManager;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;

import java.io.File;

public class LoadMenuState extends ChoosePathMenuState {

    /**
     * @param gsm The game's state manager
     * @param gim The game's input manager
     * @param gs The game's graphical settings.
     */
    public LoadMenuState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }

    @Override
    public void init() {
        super.init();
        setMenuItems(new MenuItem[] {
                new TitleMenuItem(gs, gs.getStringFromId("load")),
                directoryMI,
                new ButtonMenuItem(gim, gs, gs.getStringFromId("cancel"), () -> gsm.removeFirstState()),
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
    private void load(String loadFilePath) {
        ExtensionsManager.getInstance().initGameFromLoad(gsm);
        MasterOfMonsGame.setGameToLoad(loadFilePath);
    }
}
