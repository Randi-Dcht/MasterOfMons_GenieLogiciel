package be.ac.umons.mom.g02.GameStates.Menus;

import be.ac.umons.mom.g02.Enums.KeyStatus;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.ButtonMenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.MenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.TextMenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.TitleMenuItem;
import be.ac.umons.mom.g02.Regulator.SuperviserNormally;
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
        setMenuItems(new MenuItem[] {
                new TitleMenuItem(gs, gs.getStringFromId("gameName")),
                new TextMenuItem(gs, gs.getStringFromId("debugMenu")),
                new ButtonMenuItem(gim, gs, gs.getStringFromId(gs.mustShowMapCoordinates() ? "debugHidePlayerCoord" : "debugPlayerCoord"), () -> gs.setShowMapCoordinates(! gs.mustShowMapCoordinates())),
                new ButtonMenuItem(gim, gs, gs.getStringFromId("debugLevelUp"), () -> ps.debugLevelUp()),
                new ButtonMenuItem(gim, gs, gs.getStringFromId("debugMakeInvincible"), () -> ps.debugMakeInvincible()),
                new ButtonMenuItem(gim, gs, gs.getStringFromId("debugReinitiatePlayer"), () -> SuperviserNormally.getSupervisor().reinitialisationPlayer()),
                new ButtonMenuItem(gim, gs, gs.getStringFromId("debugGetObject"), () -> gsm.setState(DebugGetObject.class, true)),
                new ButtonMenuItem(gim, gs, gs.getStringFromId("debugNextQuest"), () -> SuperviserNormally.getSupervisor().getPeople().getQuest().passQuest()),
                new ButtonMenuItem(gim, gs, gs.getStringFromId("debugSpeedUp"), () -> ps.debugChangePlayerSpeed()),
                new ButtonMenuItem(gim, gs, gs.getStringFromId("quit"), () -> gsm.removeFirstState())
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
