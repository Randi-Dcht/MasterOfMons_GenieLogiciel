package be.ac.umons.mom.g02.GameStates.Menus;

import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.ButtonMenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.MenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.TitleMenuItem;
import be.ac.umons.mom.g02.Regulator.SuperviserNormally;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Objects.Items.Items;
import be.ac.umons.mom.g02.Regulator.Supervisor;

import java.util.ArrayList;
import java.util.List;

public class DebugGetObject extends MenuState {


    /**
     * @param gs The graphical settings
     */
    public DebugGetObject(GraphicalSettings gs) {
        super(gs);
    }

    @Override
    public void init() {
        super.init();
        transparentBackground = true;

        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new TitleMenuItem(gs, gs.getStringFromId("debugGetObject")));
        for (Items it : Supervisor.getSupervisor().getAllItems()) {
            menuItems.add(new ButtonMenuItem(gim, gs, gs.getStringFromId(it.getIdItems()), () -> {
                Supervisor.getPeople().pushObject(it);
                gsm.removeFirstState();
            }));
        }
        setMenuItems(menuItems.toArray(new MenuItem[0]));
    }
}