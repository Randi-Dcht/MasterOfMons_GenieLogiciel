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
        setMenuItems(new MenuItem[] { new MenuItem(gs.getStringFromId("gameName"), MenuItemType.Title),
                new MenuItem(gs.getStringFromId("debugMenu"), MenuItemType.Text),
                new MenuItem(gs.getStringFromId(gs.mustShowMapCoordinates() ? "debugHidePlayerCoord" : "debugPlayerCoord"), () -> gs.setShowMapCoordinates(! gs.mustShowMapCoordinates())),
                new MenuItem(gs.getStringFromId("debugLevelUp"), MenuItemType.Button),
                new MenuItem(gs.getStringFromId("debugMakeInvincible"), MenuItemType.Button),
                new MenuItem(gs.getStringFromId("debugReinitiatePlayer"), MenuItemType.Button),
                new MenuItem(gs.getStringFromId("debugGetObject"), MenuItemType.Button)
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
        init(); // Refresh the text
    }
}
