package be.ac.umons.mom.g02.GameStates.Menus;

import be.ac.umons.mom.g02.GameStates.Dialogs.OutGameDialogState;
import be.ac.umons.mom.g02.GraphicalObjects.Controls.TextBox;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.ButtonMenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.MenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.TextBoxMenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.TitleMenuItem;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Objects.Saving;
import be.ac.umons.mom.g02.Regulator.Supervisor;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveMenuState extends ChoosePathMenuState {

    /**
     * The <code>MenuItem</code> showing the name.
     */
    TextBoxMenuItem nameMI;

    /**
     * @param gs The game's graphical settings.
     */
    public SaveMenuState(GraphicalSettings gs) {
        super(gs);
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
        TextBox nameTextBox = nameMI.getControl();
        nameTextBox.setOnTextChanged(() ->
                nameTextBox.setText(nameTextBox.getText().replace("/", "")));
        nameTextBox.setSuffix(".mom");
        nameTextBox.setText(String.format("MOM-%s", new SimpleDateFormat("dd_MM_yy_HH:mm:ss").format(new Date())));
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
            g.addAnswer("yes", () -> save(saveFilePath, true));
            g.addAnswer("no");
        } else {
            Saving save = Supervisor.getSupervisor().getSave();
            save.setNameSave(saveFilePath);
            save.signal();
            MasterOfMonsGame.getSettings().setLastSavePath(saveFilePath);
            gsm.removeFirstState();
        }
    }
}
