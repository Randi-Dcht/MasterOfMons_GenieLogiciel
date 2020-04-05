package be.ac.umons.mom.g02.GraphicalObjects.MenuItems;

import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;

public class NumberTextBoxMenuItem extends TextBoxMenuItem {
    /**
     * Construct a new item.
     *
     * @param gs The graphical settings to use
     * @param header The item's name
     */
    public NumberTextBoxMenuItem(GameInputManager gim, GraphicalSettings gs, String header) {
        this(gim, gs, header, "");
    }

    /**
     * Construct a new item.
     *
     * @param gs The graphical settings to use
     * @param header The item's name
     * @param id     The item's id.
     */
    public NumberTextBoxMenuItem(GameInputManager gim, GraphicalSettings gs, String header, String id) {
        super(gim, gs, header, id);
        control.setAcceptOnlyNumbers(true);
    }
}
