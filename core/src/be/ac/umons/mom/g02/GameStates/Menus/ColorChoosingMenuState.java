package be.ac.umons.mom.g02.GameStates.Menus;

import be.ac.umons.mom.g02.GraphicalObjects.Controls.ColorSelector;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.*;
import be.ac.umons.mom.g02.Helpers.StringHelper;
import be.ac.umons.mom.g02.Managers.GameColorManager;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Objects.Settings;
import be.ac.umons.mom.g02.Regulator.SuperviserNormally;
import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class ColorChoosingMenuState extends MenuState {

    protected GameColorManager gcm;

    /**
     * @param gs The game's graphical settings
     */
    public ColorChoosingMenuState(GraphicalSettings gs) {
        super(gs);
        gcm = GameColorManager.getInstance();
    }

    @Override
    public void init() {
        super.init();
        List<MenuItem> menuItemList = new ArrayList<>();
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
                    setColorSelectorDefaultValue(mi, gcm.getColorFor("foreground"));
                    break;
                case "CS_Background":
                    setColorSelectorDefaultValue(mi, gcm.getColorFor("background"));
                    break;
                case "CS_Transparent_Background":
                    setColorSelectorDefaultValue(mi, gcm.getColorFor("transparentBackground"));
                    break;
                case "CS_Control_Background":
                    setColorSelectorDefaultValue(mi, gcm.getColorFor("controlBackground"));
                    break;
                case "CS_Control_Transparent_Background":
                    setColorSelectorDefaultValue(mi, gcm.getColorFor("controlTransparentBackground"));
                    break;
                case "CS_Control_Selected_Background":
                    setColorSelectorDefaultValue(mi, gcm.getColorFor("controlSelected"));
                    break;
                case "CS_Attack_Range_Color":
                    setColorSelectorDefaultValue(mi, gcm.getColorFor("attackRange"));
                    break;
                case "CS_Recovering_Attack_Range_Color":
                    setColorSelectorDefaultValue(mi, gcm.getColorFor("recoveringAttackRange"));
                    break;
                case "CS_Life_Bar_Color":
                    setColorSelectorDefaultValue(mi, gcm.getColorFor("lifeBar"));
                    break;
                case "CS_Experience_Bar_Color":
                    setColorSelectorDefaultValue(mi, gcm.getColorFor("experienceBar"));
                    break;
                case "CS_Energy_Bar_Color":
                    setColorSelectorDefaultValue(mi, gcm.getColorFor("energyBar"));
                    break;
            }
        }
    }

    /**
     * Set the default value of a ColorSelector for this state + its size.
     * @param mi The <code>MenuItem</code> associated with the ColorSelector.
     * @param color The default color.
     */
    protected void setColorSelectorDefaultValue(MenuItem mi, Color color) {
        ((ColorSelector)mi.getControl()).setSelectedColor(color);
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
                    gcm.setColorFor("foreground", ((ColorSelector)mi.getControl()).getSelectedColor());
                    break;
                case "CS_Background":
                    gcm.setColorFor("background", ((ColorSelector)mi.getControl()).getSelectedColor());
                    break;
                case "CS_Transparent_Background":
                    gcm.setColorFor("transparentBackground", ((ColorSelector)mi.getControl()).getSelectedColor());
                    break;
                case "CS_Control_Background":
                    gcm.setColorFor("controlBackground", ((ColorSelector)mi.getControl()).getSelectedColor());
                    break;
                case "CS_Control_Transparent_Background":
                    gcm.setColorFor("controlTransparentBackground", ((ColorSelector)mi.getControl()).getSelectedColor());
                    break;
                case "CS_Control_Selected_Background":
                    gcm.setColorFor("controlSelected", ((ColorSelector)mi.getControl()).getSelectedColor());
                    break;
                case "CS_Attack_Range_Color":
                    gcm.setColorFor("attackRange", ((ColorSelector)mi.getControl()).getSelectedColor());
                    break;
                case "CS_Recovering_Attack_Range_Color":
                    gcm.setColorFor("recoveringAttackRange", ((ColorSelector)mi.getControl()).getSelectedColor());
                    break;
                case "CS_Life_Bar_Color":
                    gcm.setColorFor("lifeBar", ((ColorSelector)mi.getControl()).getSelectedColor());
                    break;
                case "CS_Experience_Bar_Color":
                    gcm.setColorFor("experienceBar", ((ColorSelector)mi.getControl()).getSelectedColor());
                    break;
                case "CS_Energy_Bar_Color":
                    gcm.setColorFor("energyBar", ((ColorSelector)mi.getControl()).getSelectedColor());
                    break;
            }
        }
        SuperviserNormally.getSupervisor().getSave().savingGraphic(settings);
        gsm.removeFirstState();
        gs.refreshColors();
    }
}
