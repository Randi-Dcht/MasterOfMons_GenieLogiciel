package be.ac.umons.mom.g02.GameStates.Menus;

import be.ac.umons.mom.g02.GraphicalObjects.Controls.ColorSelector;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.*;
import be.ac.umons.mom.g02.Helpers.StringHelper;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Objects.Settings;
import be.ac.umons.mom.g02.Regulator.SuperviserNormally;

public class ColorChoosingMenuState extends MenuState {

    /**
     * @param gs The game's graphical settings
     */
    public ColorChoosingMenuState(GraphicalSettings gs) {
        super(gs);
    }

    @Override
    public void init() {
        super.init();
        setMenuItems(new MenuItem[] {
                new TitleMenuItem(gs, gs.getStringFromId("colorChoosing")),
                new TextMenuItem(gs, gs.getStringFromId("colorHelp")),
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
        Settings settings = MasterOfMonsGame.getSettings();
        for (MenuItem mi : menuItems) {
            switch (mi.getId()) {
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
        Settings settings = MasterOfMonsGame.getSettings();
        for (MenuItem mi : menuItems) {  // No need to add SCLs because done at each click !
            switch (mi.getId()) {
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
        SuperviserNormally.getSupervisor().getSave().savingGraphic(settings);
        gsm.removeFirstState();
        gs.refreshColors();
    }
}
