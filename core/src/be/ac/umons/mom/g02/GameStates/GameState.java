package be.ac.umons.mom.g02.GameStates;

import be.ac.umons.mom.g02.Managers.GameColorManager;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;

/**
 * A game's state.
 * A part of this code was found in https://github.com/foreignguymike/legacyYTtutorials/tree/master/libgdxasteroids by ForeignGuyMike
 * @author Guillaume Cardoen
 */
public abstract class GameState {

    /**
     * The game's input manager
     */
    protected GameInputManager gim;
    /**
     * The game's state manager
     */
    protected GameStateManager gsm;
    /**
     * The game's graphical settings
     */
    protected GraphicalSettings gs;

    /**
     * The GameColorManager of the game
     */
    protected GameColorManager gcm;

    /**
     * The vertical margin
     */
    protected double topMargin;
    /**
     * The horizontal margin
     */
    protected double leftMargin;

    /**
     * Create a new game's state
     * @param gs Game's graphical settings
     */
    protected GameState(GraphicalSettings gs) {
        this.gim = GameInputManager.getInstance();
        this.gsm = GameStateManager.getInstance();
        this.gcm = GameColorManager.getInstance();
        this.gs = gs;
    }

    /**
     * Create a new GameState
     * Used for test purposes.
     */
    protected GameState(){}

    /**
     * Initialize the state.
     */
    public void init() {
        topMargin = MasterOfMonsGame.HEIGHT / 100;
        leftMargin = MasterOfMonsGame.WIDTH / 200;
    }

    /**
     * Update the state.
     * @param dt The delta time (in seconds).
     */
    public abstract void update(float dt);
    /**
     * Draw the state.
     */
    public abstract void draw();

    /**
     * Execute an action depending on which key was pressed.
     */
    public abstract void handleInput();

    /**
     * Dispose all resources reserved by this state.
     */
    public abstract void dispose();

    /**
     * Action to do when the state get the focus (is in foreground)
     */
    public void getFocus() {}
    /**
     * Action to do when the state loses the focus (is in background)
     */
    public void loseFocus() {}
}
