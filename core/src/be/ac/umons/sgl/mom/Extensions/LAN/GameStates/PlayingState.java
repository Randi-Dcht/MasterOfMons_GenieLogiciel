package be.ac.umons.sgl.mom.Extensions.LAN.GameStates;

import be.ac.umons.sgl.mom.Extensions.LAN.Objects.NetworkManager;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;

/**
 * L'état de jeu du jeu. Il affiche la carte, un joueur ainsi qu'un HUD. Cet état suppose qu'une connexion avec un autre joueur a travers un réseau local a déjà été établi.
 * @author Guillaume Cardoen
 */
public class PlayingState extends be.ac.umons.sgl.mom.Extensions.Multiplayer.GameStates.PlayingState {

    private NetworkManager nm;

    /**
     * Crée un nouvel état de jeu.
     *
     * @param gsm Le GameStateManager du jeu.
     * @param gim Le GameInputManager du jeu.
     * @param gs  Les paramètres graphiques à utiliser.
     * @param nm Le gestionnaire réseau du jeu.
     */
    public PlayingState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs, NetworkManager nm) {
        super(gsm, gim, gs);
        this.nm = nm;
    }
}
