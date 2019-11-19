package be.ac.umons.sgl.mom.Managers;

import be.ac.umons.sgl.mom.Enums.GameStates;
import be.ac.umons.sgl.mom.Extensions.LAN.Objects.NetworkManager;
import be.ac.umons.sgl.mom.GameStates.*;
import be.ac.umons.sgl.mom.GameStates.Menus.InGameMenuState;
import be.ac.umons.sgl.mom.GameStates.Menus.MainMenuState;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;

import java.util.Stack;


/**
 * Gère les différents états du jeu ainsi que le passage de l'un à l'autre.
 * Une partie du contenu est tiré de https://github.com/foreignguymike/legacyYTtutorials/tree/master/libgdxasteroids par ForeignGuyMike
 * @author Guillaume Cardoen
 */
public class GameStateManager {
    /**
     * La pile des différents états du jeu.
     */
    private Stack<GameState> gameStateStack;
    /**
     * Le GameInputManager du jeu.
     */
    private GameInputManager gim;
    /**
     * Les paramètres graphiques du jeu.
     */
    protected GraphicalSettings gs;

    /**
     * Le gestionnaire de cartes du jeu.
     */
    protected GameMapManager gmm;

    /**
     * Crée un nouveau gestionnaire d'état du jeu.
     * @param gim Le GameInputManager du jeu.
     * @param gs Les paramètres graphiques du jeu.
     */
    public GameStateManager(GameInputManager gim, GraphicalSettings gs) {
        gameStateStack = new Stack<>();
        this.gim = gim;
        this.gs = gs;
        gmm = new GameMapManager();
        setState(GameStates.MainMenu);
    }

    /**
     * Change l'état actuelle du jeu et enlève le précédent en fonction du nouvel état.
     * @param gst Le nouvel état à ajouter.
     */
    public void setState(GameStates gst) {
        switch (gst) {
            case MainMenu:
                gameStateStack.push(new MainMenuState(this, gim, gs));
                break;
            case Play:
                gameStateStack.pop(); // Can be done because Menu would already be there...
                gameStateStack.push(new PlayingState(this, gim, gs));
                break;
            case InGameMenu:
                gameStateStack.push(new InGameMenuState(this, gim, gs));
                break;
            case Loading:
                gameStateStack.pop();
                gameStateStack.push(new LoadingState(this, gim, gs));
                break;
            case Settings:
                gameStateStack.pop();
                gameStateStack.push(new SettingsState(this, gim, gs));
                break;
            case Save:
                gameStateStack.push(new SaveState(this, gim, gs));
                break;
            case Load:
                gameStateStack.pop();
                gameStateStack.push(new LoadState(this, gim, gs));
                break;
            case LANPlayingState:
                gameStateStack.pop(); // Can be done because Menu would already be there...
                gameStateStack.push(new be.ac.umons.sgl.mom.Extensions.LAN.GameStates.PlayingState(this, gim, gs, new NetworkManager()));
                break;
            default:
                break;
        }
    }

    /**
     * Retire l'état en cours et revient à l'état précédent.
     */
    public void removeFirstState() {
        gameStateStack.pop();
    }

    /**
     * Met à jour l'état en cours.
     * @param dt Le temps entre l'appel précédent de cette fonction et l'appel actuel.
     */
    public void update(float dt) {
        gameStateStack.peek().update(dt);
    }

    /**
     * Dessine tout les états présent dans la pile dans l'ordre de la pile.
     */
    public void draw() {
        for (GameState gs: gameStateStack) {
            gs.draw();
        }
    }

    /**
     * S'éxécute quand l'application se détruit.
     */
    public void dispose() {
        for (GameState gs: gameStateStack) {
            gs.dispose();
        }
    }

    public GameMapManager getGameMapManager() {
        return gmm;
    }
}
