package be.ac.umons.sgl.mom.Managers;

import be.ac.umons.sgl.mom.GameStates.*;
import be.ac.umons.sgl.mom.GameStates.Menus.MainMenuState;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
        setState(MainMenuState.class);
    }

    public void setState(Class<? extends GameState> gst) {
        setState(gst, false);
    }

    /**
     * Change l'état actuelle du jeu et enlève le précédent en fonction du nouvel état.
     * @param gst Le nouvel état à ajouter.
     * @param popPreviousOne Doit-on enlever l'état précedent de la pile ?
     */
    public void setState(Class<? extends GameState> gst, boolean popPreviousOne) {
        GameState g;
        try {
            Constructor con = gst.getConstructor(GameStateManager.class, GameInputManager.class, GraphicalSettings.class);
            g = (GameState) con.newInstance(this, gim, gs);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return;
        }
        if (popPreviousOne)
            gameStateStack.pop().dispose();
        gameStateStack.push(g);
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
