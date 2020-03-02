package be.ac.umons.mom.g02.Extensions.LAN.GameStates;

import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;

/**
 * L'état de jeu du jeu. Il affiche la carte, un joueur ainsi qu'un HUD. Cet état suppose qu'une connexion avec un autre joueur a travers un réseau local a déjà été établi.
 * @author Guillaume Cardoen
 */
public class PlayingState extends be.ac.umons.mom.g02.Extensions.Multiplayer.GameStates.PlayingState {

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
