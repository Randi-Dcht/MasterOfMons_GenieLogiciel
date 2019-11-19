package be.ac.umons.sgl.mom.GameStates;

import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;

/**
 * Un état du jeu.
 * Une partie du contenu a été tiré de https://github.com/foreignguymike/legacyYTtutorials/tree/master/libgdxasteroids par ForeignGuyMike
 * @author Guillaume Cardoen
 */
public abstract class GameState {

    /**
     * Le GameInputManager du jeu.
     */
    protected GameInputManager gim;
    /**
     * Le GameStateManager du jeu.
     */
    protected GameStateManager gsm;
    /**
     * Les paramètres graphiques du jeu.
     */
    protected GraphicalSettings gs;

    /**
     * La marge avec le dessus de la fenêtre
     */
    protected double topMargin;
    /**
     * La marge avec le côté gauche de la fenêtre
     */
    protected double leftMargin;

    /**
     * Crée un nouvelle état de jeu.
     * @param gsm Le GameStateManager du jeu.
     * @param gim Le GameInputManager du jeu.
     * @param gs Les paramètres graphiques à utiliser.
     */
    protected GameState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        this.gim = gim;
        this.gsm = gsm;
        this.gs = gs;
        init();
    }

    /**
     * Initialise les variables requises..
     */
    public void init() {
        topMargin = MasterOfMonsGame.HEIGHT / 100;
        leftMargin = MasterOfMonsGame.WIDTH / 100;
    }

    /**
     * Met à jour l'état en fonction de temps écoulé entre la mise à jour précédente et celle-ci.
     * @param dt Le temps écoulé entre la mise à jour précédente et celle-ci.
     */
    public abstract void update(float dt);
    /**
     * Dessine les éléments de l'état.
     */
    public abstract void draw();

    /**
     * Genère une action en fonction des entrées (clavier ou souris) de l'utilisateur.
     */
    public abstract void handleInput();

    /**
     * Libère les ressources allouées lors de l'utilisation de l'état.
     */
    public abstract void dispose();
}
