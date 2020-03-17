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
        transparentBackground = false;
        setMenuItems(new MenuItem[] {
                new TitleMenuItem(gs, gs.getStringFromId("settings")),
                new ButtonMenuItem(gim, gs, gs.getStringFromId("keyChoosing"), () -> gsm.setState(KeyChoosingMenuState.class)),
                new NumberTextBoxMenuItem(gim, gs, gs.getStringFromId("gameResolutionWidth"), "TXT_Game_Resolution_Width"),
                new NumberTextBoxMenuItem(gim, gs, gs.getStringFromId("gameResolutionHeight"), "TXT_Game_Resolution_Height"),
                new NumberTextBoxMenuItem(gim, gs, gs.getStringFromId("maximumAutomaticSaves"), "TXT_Maximum_Automatic_Saves"),
                new ScrollListChooserMenuItem(gim, gs, gs.getStringFromId("language"), "SLC_Language"),
                new ColorSelectorMenuItem(gim, gs, gs.getStringFromId("foregroundColor"), "CS_Foreground"),
                new ColorSelectorMenuItem(gim, gs, gs.getStringFromId("backgroundColor"), "CS_Background"),
                new ColorSelectorMenuItem(gim, gs, gs.getStringFromId("transparentBackgroundColor"), "CS_Transparent_Background"),
                new ColorSelectorMenuItem(gim, gs, gs.getStringFromId("controlBackgroundColor"), "CS_Control_Background"),
                new ColorSelectorMenuItem(gim, gs, gs.getStringFromId("controlTransparentBackgroundColor"), "CS_Control_Transparent_Background"),
                new ColorSelectorMenuItem(gim, gs, gs.getStringFromId("controlSelectedBackgroundColor"), "CS_Control_Selected_Background"),
                new ColorSelectorMenuItem(gim, gs, gs.getStringFromId("attackRangeColor"), "CS_Attack_Range_Color"),
                new ColorSelectorMenuItem(gim, gs, gs.getStringFromId("recoveringAttackRangeColor"), "CS_Recovering_Attack_Range_Color"),
                new ColorSelectorMenuItem(gim, gs, gs.getStringFromId("lifeBarColor"), "CS_Life_Bar_Color"),
                new ColorSelectorMenuItem(gim, gs, gs.getStringFromId("experienceBarColor"), "CS_Experience_Bar_Color"),
                new ColorSelectorMenuItem(gim, gs, gs.getStringFromId("energyBarColor"), "CS_Energy_Bar_Color"),
                new ButtonMenuItem(gim, gs, gs.getStringFromId("save"), this::save)
        });
        initDefaultValue(); // TODO : Remove the switch
    }

    /**
     * Init the default value of each <code>MenuItem</code> higher.
     */
    public void initDefaultValue() {
        Settings settings = MasterOfMonsGame.settings;
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
                            MasterOfMonsGame.settings.setLanguage(l);
                            gs.setLanguage(l);
                            init();
                        }, l == MasterOfMonsGame.settings.getLanguage()));
                    ((ScrollListChooser)mi.getControl()).setScrollListItems(slil.toArray(new ScrollListChooser.ScrollListItem[0]));
                    mi.getSize().y = (int)(3 * (gs.getNormalFont().getLineHeight() + 2 * topMargin));
                    break;
                case "CS_Foreground":
                    setColorSelectorDefaultValue(mi, settings.getForegroundColor());
                    break;
                case "CS_Background":
                    setColorSelectorDefaultValue(mi, settings.getBackgroundColor());
                    break;
                case "CS_Transparent_Background":
                    setColorSelectorDefaultValue(mi, settings.getTransparentBackgroundColor());
                    break;
                case "CS_Control_Background":
                    setColorSelectorDefaultValue(mi, settings.getControlBackgroundColor());
                    break;
                case "CS_Control_Transparent_Background":
                    setColorSelectorDefaultValue(mi, settings.getControlTransparentBackgroundColor());
                    break;
                case "CS_Control_Selected_Background":
                    setColorSelectorDefaultValue(mi, settings.getControlSelectedColor());
                    break;
                case "CS_Attack_Range_Color":
                    setColorSelectorDefaultValue(mi, settings.getAttackRangeColor());
                    break;
                case "CS_Recovering_Attack_Range_Color":
                    setColorSelectorDefaultValue(mi, settings.getRecoveringAttackRangeColor());
                    break;
                case "CS_Life_Bar_Color":
                    setColorSelectorDefaultValue(mi, settings.getLifeBarColor());
                    break;
                case "CS_Experience_Bar_Color":
                    setColorSelectorDefaultValue(mi, settings.getExperienceBarColor());
                    break;
                case "CS_Energy_Bar_Color":
                    setColorSelectorDefaultValue(mi, settings.getEnergyBarColor());
                    break;
            }
        }
    }

    /**
     * Set the default value of a ColorSelector for this state + its size.
     * @param mi The <code>MenuItem</code> associated with the ColorSelector.
     * @param color The default color.
     */
    protected void setColorSelectorDefaultValue(MenuItem mi, String color) {
        ((ColorSelector)mi.getControl()).setSelectedColor(StringHelper.getColorFromString(color));
        mi.getSize().y = (int)(gs.getNormalFont().getLineHeight() + 4 * topMargin);
    }

    /**
     * Save the settings and quit the state.
     */
    public void save() {
        Settings settings = new Settings();
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
                case "CS_Foreground":
                    settings.setForegroundColor(((ColorSelector)mi.getControl()).getSelectedColor().toString());
                    break;
                case "CS_Background":
                    settings.setBackgroundColor(((ColorSelector)mi.getControl()).getSelectedColor().toString());
                    break;
                case "CS_Transparent_Background":
                    settings.setTransparentBackgroundColor(((ColorSelector)mi.getControl()).getSelectedColor().toString());
                    break;
                case "CS_Control_Background":
                    settings.setControlBackgroundColor(((ColorSelector)mi.getControl()).getSelectedColor().toString());
                    break;
                case "CS_Control_Transparent_Background":
                    settings.setControlTransparentBackgroundColor(((ColorSelector)mi.getControl()).getSelectedColor().toString());
                    break;
                case "CS_Control_Selected_Background":
                    settings.setControlSelectedColor(((ColorSelector)mi.getControl()).getSelectedColor().toString());
                    break;
                case "CS_Attack_Range_Color":
                    settings.setAttackRangeColor(((ColorSelector)mi.getControl()).getSelectedColor().toString());
                    break;
                case "CS_Recovering_Attack_Range_Color":
                    settings.setRecoveringAttackRangeColor(((ColorSelector)mi.getControl()).getSelectedColor().toString());
                    break;
                case "CS_Life_Bar_Color":
                    settings.setLifeBarColor(((ColorSelector)mi.getControl()).getSelectedColor().toString());
                    break;
                case "CS_Experience_Bar_Color":
                    settings.setExperienceBarColor(((ColorSelector)mi.getControl()).getSelectedColor().toString());
                    break;
                case "CS_Energy_Bar_Color":
                    settings.setEnergyBarColor(((ColorSelector)mi.getControl()).getSelectedColor().toString());
                    break;
            }
        }
        MasterOfMonsGame.settings = settings;
        SuperviserNormally.getSupervisor().getSave().savingGraphic(settings);
        gsm.removeFirstState();
        gs.refreshColors();
    }
}
