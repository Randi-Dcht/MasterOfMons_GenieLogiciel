package be.ac.umons.sgl.mom.GameStates.Menus;

import be.ac.umons.sgl.mom.GraphicalObjects.Controls.ScrollListChooser;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LoadMenuState extends MenuState {

    protected final String SAVE_PATH = "D:\\Users\\Guillaume\\Documents\\Test\\MOM"; // TODO : Define it itself
    protected MenuItem chooseSaveSLC;

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
        chooseSaveSLC = new MenuItem("", MenuItemType.ScrollListChooser);
        setMenuItems(new MenuItem[]{
                new MenuItem("Load a game", MenuItemType.Title),
                new MenuItem("Directory : " + SAVE_PATH, MenuItemType.Text),
                new MenuItem("Choose", MenuItemType.Button, this::chooseFolder, false),
                chooseSaveSLC
        });
        chooseFolder();
    }

    protected void chooseFolder() {
        setFolder(SAVE_PATH);
        //TODO
    }

    /**
     * Return a list of all files "*.mom" in the <code>saveDirPath</code> directory. <code>null</code> if the directory doesn't exists/
     * @param saveDirPath The directory
     * @return A list of all files "*.mom" in the <code>saveDirPath</code> directory.
     */
    private File[] listSaveFile(String saveDirPath) {
        return new File(saveDirPath).listFiles((dir, name) -> name.endsWith(".mom"));
    }

    private void setFolder(String saveDirPath) {
        List<ScrollListChooser.ScrollListItem> slis = new ArrayList<>();
        for (File f : listSaveFile(saveDirPath))
            slis.add(new ScrollListChooser.ScrollListItem(f.getName(), () -> load(f.getPath())));
        ((ScrollListChooser)chooseSaveSLC.control).setScrollListItems(slis.toArray(new ScrollListChooser.ScrollListItem[0]));
        chooseSaveSLC.size.x = -2;
        chooseSaveSLC.size.y = -2;
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
