package be.ac.umons.sgl.mom.GameStates.Menus;

import be.ac.umons.sgl.mom.GraphicalObjects.Controls.ScrollListChooser;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LoadMenuState extends MenuState {

    protected String savePath;
    protected MenuItem chooseSaveSLC;
    protected MenuItem directoryMI;

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
        topMargin = .1;
        transparentBackground = false;
        directoryMI = new MenuItem("Directory : " + savePath, MenuItemType.Text);
        chooseSaveSLC = new MenuItem("", MenuItemType.ScrollListChooser);
        setMenuItems(new MenuItem[]{
                new MenuItem("Load a game", MenuItemType.Title),
                directoryMI,
                chooseSaveSLC
        });
        savePath = new File(".").getAbsoluteFile().getParent(); //getParent() to remove the \.
        setFolder(savePath);
    }

    /**
     * Return a list of all files "*.mom" in the <code>saveDirPath</code> directory. <code>null</code> if the directory doesn't exists/
     * @param saveDirPath The directory
     * @return A list of all files "*.mom" in the <code>saveDirPath</code> directory.
     */
    private File[] listSaveFileAndFolder(String saveDirPath) {
        return new File(saveDirPath).listFiles((dir, name) -> name.endsWith(".mom") | new File(dir, name).isDirectory());
    }

    private void setFolder(String saveDirPath) {
        savePath = saveDirPath;
        List<ScrollListChooser.ScrollListItem> slis = new ArrayList<>();
        slis.add(new ScrollListChooser.ScrollListItem("..", () -> setFolder(new File(saveDirPath).getAbsoluteFile().getParent())));
        for (File f : listSaveFileAndFolder(saveDirPath)) {
            if (f.isDirectory())
                slis.add(new ScrollListChooser.ScrollListItem(f.getName(), () -> setFolder(f.getPath())));
            else
                slis.add(new ScrollListChooser.ScrollListItem(f.getName(), () -> load(f.getPath())));
        }
        ((ScrollListChooser)chooseSaveSLC.control).setScrollListItems(slis.toArray(new ScrollListChooser.ScrollListItem[0]));
        chooseSaveSLC.size.x = -2;
        chooseSaveSLC.size.y = -2;
        directoryMI.header = "Directory : " + savePath;
    }

    /**
     * Try to load the files and quit the state.
     * @param loadFilePath The load file's path.
     */
    private void load(String loadFilePath) {
        // TODO : call the load system
        gsm.removeFirstState();
    }
}
