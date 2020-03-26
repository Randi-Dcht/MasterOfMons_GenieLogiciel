package be.ac.umons.mom.g02.GameStates.Menus;

import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.*;
import be.ac.umons.mom.g02.Helpers.StringHelper;
import be.ac.umons.mom.g02.Enums.Languages;
import be.ac.umons.mom.g02.Regulator.SuperviserNormally;
import be.ac.umons.mom.g02.GraphicalObjects.Controls.ColorSelector;
import be.ac.umons.mom.g02.GraphicalObjects.Controls.ScrollListChooser;
import be.ac.umons.mom.g02.GraphicalObjects.Controls.TextBox;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Objects.Settings;

import java.util.ArrayList;
import java.util.List;

/**
 * The state where the user can choose settings influencing the game.
 * @author Guillaume Cardoen
 */
public class SettingsMenuState extends MenuState {

    /**
     * @param gs The game's graphical settings
     */
    public SettingsMenuState(GraphicalSettings gs) {
        super(gs);
    }

    @Override
    public void init() {
        super.init();
        transparentBackground = false;
        setMenuItems(new MenuItem[] {
                new TitleMenuItem(gs, gs.getStringFromId("settings")),
                new ButtonMenuItem(gim, gs, gs.getStringFromId("keyChoosing"), () -> gsm.setState(KeyChoosingMenuState.class)),
                new ButtonMenuItem(gim, gs, gs.getStringFromId("colorChoosing"), () -> gsm.setState(ColorChoosingMenuState.class)),
                new NumberTextBoxMenuItem(gim, gs, gs.getStringFromId("gameResolutionWidth"), "TXT_Game_Resolution_Width"),
                new NumberTextBoxMenuItem(gim, gs, gs.getStringFromId("gameResolutionHeight"), "TXT_Game_Resolution_Height"),
                new NumberTextBoxMenuItem(gim, gs, gs.getStringFromId("maximumAutomaticSaves"), "TXT_Maximum_Automatic_Saves"),
                new ScrollListChooserMenuItem(gim, gs, gs.getStringFromId("language"), "SLC_Language"),
                new ButtonMenuItem(gim, gs, gs.getStringFromId("save"), this::save)
        });
        initDefaultValue(); // TODO : Remove the switch
    }

    /**
     * Init the default value of each <code>MenuItem</code> higher.
     */
    public void initDefaultValue() {
        Settings settings = MasterOfMonsGame.getSettings();
        for (MenuItem mi : menuItems) {
            switch (mi.getId()) {
                case "TXT_Game_Resolution_Width":
                    ((TextBox)mi.getControl()).setText("" + settings.getGameResolutionWidth());
                    break;
                case "TXT_Game_Resolution_Height":
                    ((TextBox)mi.getControl()).setText("" + settings.getGameResolutionHeight());
                    break;
                case "TXT_Maximum_Automatic_Saves":
                    ((TextBox)mi.getControl()).setText("" + settings.getMaximumAutomaticSaves());
                    break;
                case "SLC_Language":
                    List<ScrollListChooser.ScrollListItem> slil = new ArrayList<>();
                    for (Languages l : Languages.values())
                        slil.add(new ScrollListChooser.ScrollListItem(gs.getStringFromId(l.toString()), () -> {
                            MasterOfMonsGame.getSettings().setLanguage(l);
                            gs.setLanguage(l);
                            init();
                        }, l == MasterOfMonsGame.getSettings().getLanguage()));
                    ((ScrollListChooser)mi.getControl()).setScrollListItems(slil.toArray(new ScrollListChooser.ScrollListItem[0]));
                    mi.getSize().y = (int)(3 * (gs.getNormalFont().getLineHeight() + 2 * topMargin));
                    break;
            }
        }
    }

    /**
     * Save the settings and quit the state.
     */
    public void save() {
        Settings settings = MasterOfMonsGame.getSettings();
        for (MenuItem mi : menuItems) {  // No need to add SCLs because done at each click !
            switch (mi.getId()) {
                case "TXT_Game_Resolution_Width":
                    settings.setGameResolutionWidth(Integer.parseInt(((TextBox)mi.getControl()).getText()));
                    break;
                case "TXT_Game_Resolution_Height":
                    settings.setGameResolutionHeight(Integer.parseInt(((TextBox)mi.getControl()).getText()));
                    break;
                case "TXT_Maximum_Automatic_Saves":
                    settings.setMaximumAutomaticSaves(Integer.parseInt(((TextBox)mi.getControl()).getText()));
                    break;
            }
        }
        SuperviserNormally.getSupervisor().getSave().savingGraphic(settings);
        gsm.removeFirstState();
        gs.refreshColors();
    }
}
