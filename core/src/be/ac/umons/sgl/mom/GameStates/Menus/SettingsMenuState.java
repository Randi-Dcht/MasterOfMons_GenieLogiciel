package be.ac.umons.sgl.mom.GameStates.Menus;

import be.ac.umons.sgl.mom.Enums.Difficulty;
import be.ac.umons.sgl.mom.Enums.Languages;
import be.ac.umons.sgl.mom.GameStates.Menus.MenuState;
import be.ac.umons.sgl.mom.GraphicalObjects.Controls.ColorSelector;
import be.ac.umons.sgl.mom.GraphicalObjects.Controls.ScrollListChooser;
import be.ac.umons.sgl.mom.GraphicalObjects.Controls.TextBox;
import be.ac.umons.sgl.mom.Helpers.StringHelper;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import be.ac.umons.sgl.mom.Objects.Settings;
import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * The state where the user can choose settings influencing the game.
 * @author Guillaume Cardoen
 */
public class SettingsMenuState extends MenuState {

    /**
     * @param gsm The game's state manager
     * @param gim The game's input manager
     * @param gs The game's graphical settings
     */
    public SettingsMenuState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }

    @Override
    public void init() {
        super.init();
        topMargin = .1;
        transparentBackground = false;
        setMenuItems(new MenuItem[] {
                new MenuItem(gs.getStringFromId("settings"), MenuItemType.Title),
                new MenuItem(gs.getStringFromId("gameResolutionWidth"), MenuItemType.NumberTextBox, "TXT_Game_Resolution_Width"),
                new MenuItem(gs.getStringFromId("gameResolutionHeight"), MenuItemType.NumberTextBox, "TXT_Game_Resolution_Height"),
                new MenuItem(gs.getStringFromId("maximumAutomaticSaves"), MenuItemType.NumberTextBox, "TXT_Maximum_Automatic_Saves"),
                new MenuItem(gs.getStringFromId("language"), MenuItemType.ScrollListChooser, "SLC_Language"),
                new MenuItem(gs.getStringFromId("difficulty"), MenuItemType.ScrollListChooser, "SLC_Difficulty"),
                new MenuItem(gs.getStringFromId("backgroundColor"), MenuItemType.ColorChooser, "CS_Background"),
                new MenuItem(gs.getStringFromId("save"), MenuItemType.Button, this::save)
        });
        Settings settings = MasterOfMonsGame.settings;
        for (MenuItem mi : menuItems) {
            switch (mi.id) {
                case "TXT_Game_Resolution_Width":
                    ((TextBox)mi.control).setText("" + settings.getGameResolutionWidth());
                    break;
                case "TXT_Game_Resolution_Height":
                    ((TextBox)mi.control).setText("" + settings.getGameResolutionHeight());
                    break;
                case "TXT_Maximum_Automatic_Saves":
                    ((TextBox)mi.control).setText("" + settings.getMaximumAutomaticSaves());
                    break;
                case "SLC_Language":
                    List<ScrollListChooser.ScrollListItem> slil = new ArrayList<>();
                    for (Languages l : Languages.values())
                        slil.add(new ScrollListChooser.ScrollListItem(gs.getStringFromId(l.toString()), () -> {
                            MasterOfMonsGame.settings.setLanguage(l);
                            gs.setLanguage(l);
                            init();
                        }, l == MasterOfMonsGame.settings.getLanguage()));
                    ((ScrollListChooser)mi.control).setScrollListItems(slil.toArray(new ScrollListChooser.ScrollListItem[0]));
                    mi.size.y = (int)(3 * (gs.getNormalFont().getLineHeight() + 2 * topMargin));
                    break;
                case "SLC_Difficulty":
                    List<ScrollListChooser.ScrollListItem> l = new ArrayList<>();
                    for (Difficulty d : Difficulty.values())
                        l.add(new ScrollListChooser.ScrollListItem(gs.getStringFromId(d.toString()),
                                () -> MasterOfMonsGame.settings.setDifficulty(d), d == MasterOfMonsGame.settings.getDifficulty()));
                    ((ScrollListChooser)mi.control).setScrollListItems(l.toArray(new ScrollListChooser.ScrollListItem[0]));
                    mi.size.y = (int)(4 * (gs.getNormalFont().getLineHeight() + 3 * topMargin));
                    break;
                case "CS_Background":
                    ((ColorSelector)mi.control).setSelectedColor(StringHelper.getColorFromString(settings.getBackgroundColor()));
            }
        }
    }

    /**
     * Save the settings and quit the state.
     */
    public void save() {
        Settings settings = new Settings();
        for (MenuItem mi : menuItems) {
            switch (mi.id) {
                case "TXT_Game_Resolution_Width":
                    settings.setGameResolutionWidth(Integer.parseInt(((TextBox)mi.control).getText()));
                    break;
                case "TXT_Game_Resolution_Height":
                    settings.setGameResolutionHeight(Integer.parseInt(((TextBox)mi.control).getText()));
                    break;
                case "TXT_Maximum_Automatic_Saves": // No need to add SCLs because done at each click !
                    settings.setMaximumAutomaticSaves(Integer.parseInt(((TextBox)mi.control).getText()));
                    break;
                case "CS_Background":
                    Color backgroundColor = ((ColorSelector)mi.control).getSelectedColor();
                    settings.setBackgroundColor(backgroundColor.toString());
            }
        }
        // TODO : Save the settings object
        MasterOfMonsGame.settings = settings;
        gsm.removeFirstState();
    }
}
