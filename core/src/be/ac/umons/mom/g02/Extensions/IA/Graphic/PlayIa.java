package be.ac.umons.mom.g02.Extensions.IA.Graphic;

import be.ac.umons.mom.g02.Extensions.Multiplayer.GameStates.PlayingState;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;

public class PlayIa extends PlayingState {
    /**
     * Create a new game state.
     * @param gsm The GameStateManager of the game.
     * @param gim The GameInputManager of the game.
     * @param gs  The graphic parameters to use.
     */
    public PlayIa(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs)
        {
            super(gsm, gim, gs);
        }
}
