package be.ac.umons.mom.g02.GameStates.Menus;

import be.ac.umons.mom.g02.GraphicalObjects.Controls.KeySelector;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameKeyManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KeyChoosingMenuState extends MenuState {

    HashMap<String, Integer> keysMap;

    /**
     * @param gsm The game's state manager
     * @param gim The game's input manager
     * @param gs The game's graphical settings
     */
    public KeyChoosingMenuState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }

    @Override
    public void init() {
        super.init();
        keysMap = GameKeyManager.getInstance().getKeysMap();

        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(gs.getStringFromId("keyChoosing"), MenuItemType.Title));
        for (String id : keysMap.keySet())
            menuItems.add(new MenuItem(gs.getStringFromId(id), MenuItemType.KeySelector, id));
        menuItems.add(new MenuItem(gs.getStringFromId("save"), MenuItemType.Button, this::save));
        setMenuItems(menuItems.toArray(new MenuItem[0]));
        setDefaultValue();
    }

    protected void setDefaultValue() {
        for (MenuItem mi : menuItems)
            if (!mi.id.equals(""))
                ((KeySelector)mi.control).setActualKeyCode(keysMap.get(mi.id));
    }

    protected void save() {
        for (MenuItem mi : menuItems)
            if (!mi.id.equals(""))
                keysMap.put(mi.id, ((KeySelector)mi.control).getActualKeyCode());
        GameKeyManager.getInstance().saveKeysMap();
        gsm.removeFirstState();
    }
}
