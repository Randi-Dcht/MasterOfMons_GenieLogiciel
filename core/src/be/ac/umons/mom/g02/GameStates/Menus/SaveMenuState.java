package be.ac.umons.mom.g02.GameStates.Menus;

import be.ac.umons.mom.g02.GameStates.Dialogs.OutGameDialogState;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.ButtonMenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.MenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.TextBoxMenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.TitleMenuItem;
import be.ac.umons.mom.g02.Regulator.SuperviserNormally;
import be.ac.umons.mom.g02.GraphicalObjects.Controls.TextBox;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Objects.Saving;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveMenuState extends ChooseFolderMenuState {

    /**
     * The <code>MenuItem</code> showing the name.
     */
    TextBoxMenuItem nameMI;

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
        nameMI = new TextBoxMenuItem(gim, gs, gs.getStringFromId("name"));
        setMenuItems(new MenuItem[]{
                new TitleMenuItem(gs, gs.getStringFromId("save")),
                directoryMI,
                nameMI,
                new ButtonMenuItem(gim, gs, gs.getStringFromId("save"), this::save),
                new ButtonMenuItem(gim, gs, gs.getStringFromId("cancel"), () -> gsm.removeFirstState()),
                chooseSaveSLC
        });
        setFolder(path);
        nameMI.getControl().setSuffix(".mom");
        nameMI.getControl().setText(String.format("MOM-%s", new SimpleDateFormat("dd_MM_yy_HH:mm:ss").format(new Date())));
        nameMI.getSize().x = -2;
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
        save(path + "/" + nameMI.getControl().getText());
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
