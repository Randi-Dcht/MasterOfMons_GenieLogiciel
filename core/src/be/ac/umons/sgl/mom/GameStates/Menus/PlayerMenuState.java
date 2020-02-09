package be.ac.umons.sgl.mom.GameStates.Menus;

import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;

public class PlayerMenuState extends MenuState {
    /**
     * @param gsm Game's state manager
     * @param gim Game's input manager
     * @param gs Game's graphical settings
     */
    protected PlayerMenuState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }
}
