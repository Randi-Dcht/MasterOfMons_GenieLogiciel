package be.ac.umons.sgl.mom.GameStates.Menus;

import be.ac.umons.sgl.mom.Events.SuperviserNormally;
import be.ac.umons.sgl.mom.GameStates.Dialogs.OutGameDialogState;
import be.ac.umons.sgl.mom.GraphicalObjects.Controls.TextBox;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import be.ac.umons.sgl.mom.Objects.Saving;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveMenuState extends ChooseFolderMenuState {

    /**
     * The <code>MenuItem</code> showing the name.
     */
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
        setFolder(path);
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
        save(file.getAbsolutePath());
    }

    /**
     * Initiate the saving mechanism.
     */
    protected void save() {
        save(path + "/" + ((TextBox)nameMI.control).getText());
    }

    /**
     * Initiate the saving mechanism with the given file's path.
     * @param saveFilePath The file's path where the game must be saved.
     */
    protected void save(String saveFilePath) {
        save(saveFilePath, false);
    }

    /**
     * Check if the file exists, and is not, save the game.
     * @param saveFilePath The file's path where the game must be saved.
     * @param verified If the path has already been verified or not.
     */
    protected void save(String saveFilePath, boolean verified) {
        File f = new File(saveFilePath);
        if (f.exists() && ! verified) {
            OutGameDialogState g = (OutGameDialogState) gsm.setState(OutGameDialogState.class);
            g.setText(String.format(gs.getStringFromId("saveOverwriteQuestion"), f.getName()));
            g.addAnswer(gs.getStringFromId("yes"), () -> save(saveFilePath, true));
            g.addAnswer(gs.getStringFromId("no"));
        } else {
            Saving save = SuperviserNormally.getSupervisor().getSave();
            save.setNameSave(saveFilePath);
            save.signal();
            MasterOfMonsGame.settings.setLastSavePath(saveFilePath);
            gsm.removeFirstState();
        }
    }
}
