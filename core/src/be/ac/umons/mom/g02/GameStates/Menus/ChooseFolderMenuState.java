package be.ac.umons.mom.g02.GameStates.Menus;

import be.ac.umons.mom.g02.GraphicalObjects.Controls.ScrollListChooser;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class ChooseFolderMenuState extends MenuState {

    /**
     * The path represented by the state.
     */
    protected String path;
    /**
     * The <code>MenuItem</code> representing the <code>ScrollListChooser</code> that contains all the folder and files buttons.
     */
    protected MenuItem chooseSaveSLC;
    /**
     * The <code>MenuItem</code> representing the <code>TextBox</code> with the directory.
     */
    protected MenuItem directoryMI;

    /**
     * @param gsm The game's state manager
     * @param gim The game's input manager
     * @param gs The game's graphical settings.
     */
    public ChooseFolderMenuState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }

    @Override
    public void init() {
        super.init();
        transparentBackground = false;
        directoryMI = new MenuItem(gs.getStringFromId("directory") + " : " + path, MenuItemType.Text);
        chooseSaveSLC = new MenuItem("", MenuItemType.ScrollListChooser);
        path = new File(".").getAbsoluteFile().getParent(); //getParent() to remove the \.
    }

    /**
     * Return a list of all files "*.mom" in the <code>saveDirPath</code> directory. <code>null</code> if the directory doesn't exists/
     * @param saveDirPath The directory
     * @return A list of all files "*.mom" in the <code>saveDirPath</code> directory.
     */
    protected File[] listSaveFileAndFolder(String saveDirPath) {
        return new File(saveDirPath).listFiles((dir, name) -> name.endsWith(".mom") | new File(dir, name).isDirectory());
    }

    /**
     * Set the folder to show, and initiate add all needed buttons to the state.
     * @param dirPath The folder to show.
     */
    protected void setFolder(String dirPath) {
        path = dirPath;
        List<ScrollListChooser.ScrollListItem> slis = new ArrayList<>();
        slis.add(new ScrollListChooser.ScrollListItem("..", () -> setFolder(new File(dirPath).getAbsoluteFile().getParent())));
        for (File f : listSaveFileAndFolder(dirPath)) {
            if (f.isDirectory())
                slis.add(new ScrollListChooser.ScrollListItem(f.getName(), () -> onFolderClick(f)));
            else
                slis.add(new ScrollListChooser.ScrollListItem(f.getName(), () -> onFileClick(f)));
        }
        ((ScrollListChooser)chooseSaveSLC.control).setScrollListItems(slis.toArray(new ScrollListChooser.ScrollListItem[0]));
        chooseSaveSLC.size.x = -2;
        chooseSaveSLC.size.y = -2;
        directoryMI.setHeader(gs.getStringFromId("directory") + " : " + path);
        directoryMI.size.y = -1;
    }

    /**
     * Represent the action to do when a click on a folder is detected.
     * @param folder The clicked folder.
     */
    protected abstract void onFolderClick(File folder);
    /**
     * Represent the action to do when a click on a file is detected.
     * @param file The clicked file.
     */
    protected abstract void onFileClick(File file);
}
