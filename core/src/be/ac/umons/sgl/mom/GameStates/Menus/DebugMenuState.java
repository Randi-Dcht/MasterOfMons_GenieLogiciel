package be.ac.umons.sgl.mom.GameStates.Menus;

import be.ac.umons.sgl.mom.Enums.KeyStatus;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.Input;

public class DebugMenuState extends MenuState {

    public DebugMenuState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }

    @Override
    public void init() {
        super.init();
        transparentBackground = true;
        topMargin = .1;
        setMenuItems(new MenuItem[] { new MenuItem(gs.getStringFromId("gameName"), MenuItemType.Title, false),
                new MenuItem(gs.getStringFromId("debugMenu"), MenuItemType.Normal, false),
                new MenuItem(gs.getStringFromId("debugPlayerCoord"), MenuItemType.Normal),
                new MenuItem(gs.getStringFromId("debugLevelUp"), MenuItemType.Normal),
                new MenuItem(gs.getStringFromId("debugMakeInvincible"), MenuItemType.Normal),
                new MenuItem(gs.getStringFromId("debugReinitiatePlayer"), MenuItemType.Normal),
                new MenuItem(gs.getStringFromId("debugGetObject"), MenuItemType.Normal),
        });
    }

    @Override
    public void dispose() {
        super.dispose();
        sr.dispose();
    }

    @Override
    public void handleInput() {
        super.handleInput();
        if (gim.isKey(Input.Keys.ESCAPE, KeyStatus.Pressed))
            gsm.removeFirstState();
    }
}
