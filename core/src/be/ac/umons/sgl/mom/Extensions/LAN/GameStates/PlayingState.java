package be.ac.umons.sgl.mom.Extensions.LAN.GameStates;

import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;

public class PlayingState extends be.ac.umons.sgl.mom.Extensions.Multiplayer.GameStates.PlayingState {
    /**
     * Crée un nouvel état de jeu.
     *
     * @param gsm Le GameStateManager du jeu.
     * @param gim Le GameInputManager du jeu.
     * @param gs  Les paramètres graphiques à utiliser.
     */
    public PlayingState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }

    @Override
    public void draw() {
        super.draw();
    }
}
