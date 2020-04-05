package be.ac.umons.mom.g02.GameStates.Menus;

import be.ac.umons.mom.g02.GraphicalObjects.Controls.ColorSelector;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.*;
import be.ac.umons.mom.g02.Managers.GameColorManager;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ColorChoosingMenuState extends MenuState {

    /**
     * The GameColorManager of the game
     */
    protected GameColorManager gcm;
    /**
     * The map making the link between an id and the color associated with this ID.
     */
    protected HashMap<String, Color> colorsMap;

    /**
     * @param gs The game's graphical settings
     */
    public ColorChoosingMenuState(GraphicalSettings gs) {
        super(gs);
        gcm = GameColorManager.getInstance();
        colorsMap = gcm.getColorsMap();
    }

    @Override
    public void init() {
        super.init();
        List<MenuItem> menuItemList = new ArrayList<>();
        menuItemList.add(new TitleMenuItem(gs, gs.getStringFromId("colorChoosing")));
        menuItemList.add(new TextMenuItem(gs, gs.getStringFromId("colorHelp")));
        for (String key : colorsMap.keySet())
            menuItemList.add(new ColorSelectorMenuItem(gim, gs, gs.getStringFromId(key), key));

        menuItemList.add(new ButtonMenuItem(gim, gs, gs.getStringFromId("save"), this::save));

        setMenuItems(menuItemList.toArray(new MenuItem[0]));

        initDefaultValue();
    }

    /**
     * Init the default value of each <code>MenuItem</code> higher.
     */
    public void initDefaultValue() {
        for (MenuItem mi : menuItems) {
            if (!mi.getId().equals("")) {
                ((ColorSelector)mi.getControl()).setSelectedColor(gcm.getColorFor(mi.getId()));
                mi.getSize().y = (int)(gs.getNormalFont().getLineHeight() + 4 * topMargin);
            }
        }
    }

    /**
     * Save the settings and quit the state.
     */
    public void save() {
        for (MenuItem mi : menuItems)
            if (! mi.getId().equals(""))
                gcm.setColorFor(mi.getId(), ((ColorSelector)mi.getControl()).getSelectedColor());

        gcm.saveColorsMap();
        gsm.removeFirstState();
        gs.refreshColors();
    }
}
