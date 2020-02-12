package be.ac.umons.sgl.mom.GameStates.Menus;

import be.ac.umons.sgl.mom.GraphicalObjects.Controls.TextBox;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveMenuState extends ChooseFolderMenuState {

    MenuItem nameMI;

    /**
     * @param gsm The game's state manager
     * @param gim The game's input manager
     * @param gs The game's graphical settings.
     */
    public SaveMenuState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }

    /**
     * Initialize the state.
     * A part of this code is from (https://www.mkyong.com/java/java-how-to-get-current-date-time-date-and-calender/)
     */
    @Override
    public void init() {
        super.init();
        nameMI = new MenuItem(gs.getStringFromId("name"), MenuItemType.TextBox);
        setMenuItems(new MenuItem[]{
                new MenuItem(gs.getStringFromId("save"), MenuItemType.Title),
                directoryMI,
                nameMI,
                new MenuItem(gs.getStringFromId("save"), MenuItemType.Button, this::save),
                chooseSaveSLC
        });
        setFolder(savePath);
        ((TextBox)nameMI.control).setSuffix(".mom");
        ((TextBox)nameMI.control).setText(String.format("MOM - %s", new SimpleDateFormat("dd/MM/yy HH:mm:ss").format(new Date())));
        nameMI.size.x = -2;
    }


    @Override
    protected void onFolderClick(File folder) {
        setFolder(folder.getAbsolutePath());
    }

    @Override
    protected void onFileClick(File file) {
        save(file.getAbsolutePath()); // TODO : Ask for file overwrite.
    }

    protected void save() {
        save(savePath + "/" + ((TextBox)nameMI.control).getText());
    }

    protected void save(String saveFilePath) {
        // TODO : Call the save system
        gsm.removeFirstState();
    }
}
