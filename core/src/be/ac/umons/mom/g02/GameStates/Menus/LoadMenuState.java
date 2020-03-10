package be.ac.umons.mom.g02.GameStates.Menus;

import be.ac.umons.mom.g02.Regulator.SuperviserNormally;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;

import java.io.File;

public class LoadMenuState extends ChooseFolderMenuState {

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
                new MenuItem(gs.getStringFromId("load"), MenuItemType.Title),
                directoryMI,
                new MenuItem(gs.getStringFromId("cancel"), MenuItemType.Button, () -> gsm.removeFirstState()),
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
        SuperviserNormally.getSupervisor().getSave().playOldParty(loadFilePath,gs);
        gsm.removeFirstState();
    }
}
