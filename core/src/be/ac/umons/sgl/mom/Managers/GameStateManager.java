package be.ac.umons.sgl.mom.Managers;

import be.ac.umons.sgl.mom.GameStates.*;
import be.ac.umons.sgl.mom.GameStates.Menus.MainMenuState;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Stack;


/**
 * Manage every state and the transition between one and another.
 * A part of the code is from https://github.com/foreignguymike/legacyYTtutorials/tree/master/libgdxasteroids by ForeignGuyMike
 * @author Guillaume Cardoen
 */
public class GameStateManager {
    /**
     * The stack containing every <code>GameState</code> to be drawn.
     */
    private Stack<GameState> gameStateStack;
    /**
     * The game's input manager.
     */
    private GameInputManager gim;
    /**
     * The game's graphical settings.
     */
    protected GraphicalSettings gs;

    /**
     * @param gim The game's input manager.
     * @param gs The game's graphical settings.
     */
    public GameStateManager(GameInputManager gim, GraphicalSettings gs) {
        gameStateStack = new Stack<>();
        this.gim = gim;
        this.gs = gs;
        setState(MainMenuState.class);
    }

    /**
     * Add a new state to the game without removing the previous one.
     * @param gst The state to add (class).
     * @return The game's state added.
     */
    public GameState setState(Class<? extends GameState> gst) {
        return setState(gst, false);
    }

    /**
     * Add a new state to the game and remove the previous one if asked.
     * @param gst The state to add (class).
     * @param popPreviousOne If the previous state must be removed.
     */
    public GameState setState(Class<? extends GameState> gst, boolean popPreviousOne) {
        GameState g;
        try {
            Constructor con = gst.getConstructor(GameStateManager.class, GameInputManager.class, GraphicalSettings.class);
            g = (GameState) con.newInstance(this, gim, gs);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
        if (popPreviousOne)
            gameStateStack.pop().dispose();
        else if (! gameStateStack.empty())
            gameStateStack.peek().loseFocus();
        gameStateStack.push(g);
        g.getFocus();
        return g;
    }

    /**
     * Remove the first state in the stack.
     */
    public void removeFirstState() {
        gameStateStack.pop();
        gameStateStack.peek().getFocus();
    }

    /**
     * Remove all the current states and add the given state to the stack.
     * @param gst The type of state to add.
     */
    public void removeAllStateAndAdd(Class<? extends GameState> gst) {
        gameStateStack.clear();
        setState(gst);
    }

    /**
     * Update the first state.
     * @param dt The time between this call and the previous one (delta-time).
     */
    public void update(float dt) {
        gameStateStack.peek().update(dt);
    }

    /**
     * Draw all the state in the stack in the order in which there were added.
     */
    public void draw() {
        for (GameState gs: gameStateStack)
            gs.draw();
    }

    /**
     * Dispose all the resources that this object has.
     */
    public void dispose() {
        for (GameState gs: gameStateStack)
            gs.dispose();
    }
}
