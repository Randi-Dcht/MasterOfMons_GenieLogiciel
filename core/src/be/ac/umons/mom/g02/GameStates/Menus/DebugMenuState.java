package be.ac.umons.mom.g02.GameStates.Menus;

import be.ac.umons.mom.g02.Enums.KeyStatus;
import be.ac.umons.mom.g02.GameStates.PlayingState;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.ButtonMenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.MenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.TextMenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.TitleMenuItem;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Regulator.Supervisor;
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
     * @param gs The graphical settings
     */
    public DebugMenuState(GraphicalSettings gs) {
        super(gs);
    }

    @Override
    public void init() {
        super.init();
        transparentBackground = true;
        setMenuItems(new MenuItem[] {
                new TitleMenuItem(gs, GraphicalSettings.getStringFromId("gameName")),
                new TextMenuItem(gs, GraphicalSettings.getStringFromId("debugMenu")),
                new ButtonMenuItem(gim, gs, GraphicalSettings.getStringFromId(gs.mustShowMapCoordinates() ? "debugHidePlayerCoord" : "debugPlayerCoord"), () -> gs.setShowMapCoordinates(! gs.mustShowMapCoordinates())),
                new ButtonMenuItem(gim, gs, GraphicalSettings.getStringFromId("debugLevelUp"), () -> ps.debugLevelUp()),
                new ButtonMenuItem(gim, gs, GraphicalSettings.getStringFromId("debugMakeInvincible"), () -> ps.debugMakeInvincible()),
                new ButtonMenuItem(gim, gs, GraphicalSettings.getStringFromId("debugReinitiatePlayer"), () -> Supervisor.getSupervisor().reinitialisationPlayer()),
                new ButtonMenuItem(gim, gs, GraphicalSettings.getStringFromId("debugGetObject"), () -> gsm.setState(DebugGetObject.class, true)),
                new ButtonMenuItem(gim, gs, GraphicalSettings.getStringFromId("debugNextQuest"), () -> {
                    gsm.removeFirstStateFromStack(); // Non-animated
                    Supervisor.getPeople().getQuest().passQuest();
                }),
                new ButtonMenuItem(gim, gs, GraphicalSettings.getStringFromId("debugSpeedUp"), () -> ps.debugChangePlayerSpeed()),
                new ButtonMenuItem(gim, gs, GraphicalSettings.getStringFromId("quit"), () -> gsm.removeFirstState())
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
