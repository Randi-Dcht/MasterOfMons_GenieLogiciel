package be.ac.umons.mom.g02.GameStates.Menus;

import be.ac.umons.mom.g02.Enums.KeyStatus;
import be.ac.umons.mom.g02.Events.SuperviserNormally;
import be.ac.umons.mom.g02.GameStates.PlayingState;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.Input;

import java.awt.*;

/**
 * The menu used for the debug options.
 */
public class DebugMenuState extends MenuState {

    /**
     * The playing state in which the debug method must be called.
     */
    private PlayingState ps;

    /**
     * @param gsm The game's state manager
     * @param gim The game's input manager
     * @param gs The graphical settings
     */
    public DebugMenuState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }

    @Override
    public void init() {
        super.init();
        transparentBackground = true;
        topMargin = .1;
        setMenuItems(new MenuItem[] {
                new MenuItem(gs.getStringFromId("gameName"), MenuItemType.Title),
                new MenuItem(gs.getStringFromId("debugMenu"), MenuItemType.Text),
                new MenuItem(gs.getStringFromId(gs.mustShowMapCoordinates() ? "debugHidePlayerCoord" : "debugPlayerCoord"), () -> gs.setShowMapCoordinates(! gs.mustShowMapCoordinates())),
                new MenuItem(gs.getStringFromId("debugLevelUp"), MenuItemType.Button, () -> ps.debugLevelUp()),
                new MenuItem(gs.getStringFromId("debugMakeInvincible"), MenuItemType.Button, () -> ps.debugMakeInvincible()),
                new MenuItem(gs.getStringFromId("debugReinitiatePlayer"), MenuItemType.Button),
                new MenuItem(gs.getStringFromId("debugGetObject"), MenuItemType.Button, () -> gsm.setState(DebugGetObject.class, true)),
                new MenuItem(gs.getStringFromId("debugNextQuest"), MenuItemType.Button, () -> SuperviserNormally.getSupervisor().getPeople().getQuest().passQuest()),
                new MenuItem(gs.getStringFromId("debugSpeedUp"), MenuItemType.Button, () -> ps.debugChangePlayerSpeed()),
                new MenuItem(gs.getStringFromId("quit"), MenuItemType.Button, () -> gsm.removeFirstState())
        });
    }

    /**
     * @param ps The playing state in which the debug method must be called.
     */
    public void setPlayingState(PlayingState ps) {
        this.ps = ps;
    }

    @Override
    public void dispose() {
        super.dispose();
        sr.dispose();
    }

    @Override
    public void handleInput() {
        super.handleInput();
        if (gim.isKey(Input.Keys.ESCAPE, KeyStatus.Pressed)) {
            Point selected = selectedItem;
            init(); // Refresh the text
            selectedItem = selected;
        }
    }
}
