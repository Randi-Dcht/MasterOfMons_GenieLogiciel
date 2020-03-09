package be.ac.umons.mom.g02.GameStates.Menus;

import be.ac.umons.mom.g02.Events.SuperviserNormally;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Objects.Items.Items;

import java.util.ArrayList;
import java.util.List;

public class DebugGetObject extends MenuState {


    /**
     * @param gsm The game's state manager
     * @param gim The game's input manager
     * @param gs The graphical settings
     */
    public DebugGetObject(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }

    @Override
    public void init() {
        super.init();
        transparentBackground = true;

        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(gs.getStringFromId("debugGetObject"), MenuItemType.Title));
        for (Items it : SuperviserNormally.getSupervisor().getAllItems()) {
            menuItems.add(new MenuItem(gs.getStringFromId(it.getIdItems()), MenuItemType.Button, () -> {
                SuperviserNormally.getSupervisor().getPeople().pushObject(it);
                gsm.removeFirstState();
            }));
        }
        setMenuItems(menuItems.toArray(new MenuItem[0]));
    }
}