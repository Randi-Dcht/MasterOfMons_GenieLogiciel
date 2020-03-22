package be.ac.umons.mom.g02.Managers;

import be.ac.umons.mom.g02.Animations.DoubleAnimation;
import be.ac.umons.mom.g02.GameStates.GameState;
import be.ac.umons.mom.g02.GameStates.Menus.MainMenuState;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

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
     * The time of the animation.
     */
    private static int CHANGING_TIME = 125;
    /**
     * Allow to draw the rectangle for the animation
     */
    private ShapeRenderer sr;
    /**
     * If we are currently changing from state.
     */
    private boolean isChangingState = false;
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
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
        setStateWithoutAnimation(MainMenuState.class);
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
     * @return The created GameState
     */
    public GameState setState(Class<? extends GameState> gst, boolean popPreviousOne) {
        GameState g = getState(gst);
        if (g == null) return null;
        animateForChangingState(() -> addStateToStack(g, popPreviousOne));
        return g;
    }

    /**
     * Launch the animation before changing a state
     */
    public void animateForChangingState(Runnable onBetweenAnimation) {
        isChangingState = true;
        AnimationManager.getInstance().remove("changingState");
        DoubleAnimation da = new DoubleAnimation(0, 1, CHANGING_TIME);
        da.setRunningAction(() -> sr.setColor(0,0,0, da.getActual().floatValue()));
        da.setEndingAction(() -> {
            if (onBetweenAnimation != null)
                onBetweenAnimation.run();
            Gdx.app.postRunnable(this::animateAfterChangingState);
        });
        AnimationManager.getInstance().addAnAnimation("changingState", da);

    }

    /**
     * Launch the animation after changing a state
     */
    public void animateAfterChangingState() {
        DoubleAnimation da = new DoubleAnimation(1, 0, CHANGING_TIME);
        da.setRunningAction(() -> sr.setColor(0,0,0, da.getActual().floatValue()));
        da.setEndingAction(() -> isChangingState = false);
        AnimationManager.getInstance().addAnAnimation("changingState", da);
    }

    /**
     * Add a new state to the game without removing the previous one.
     * @param gst The state to add (class).
     * @return The game's state added.
     */
    public GameState setStateWithoutAnimation(Class<? extends GameState> gst) {
        return setStateWithoutAnimation(gst, false);
    }
    /**
     * Add a new state to the game and remove the previous one if asked.
     * @param gst The state to add (class).
     * @param popPreviousOne If the previous state must be removed.
     * @return The created GameState
     */
    public GameState setStateWithoutAnimation(Class<? extends GameState> gst, boolean popPreviousOne) {
        GameState g = getState(gst);
        if (g == null) return null;
        addStateToStack(g, popPreviousOne);
        return g;
    }

    /**
     * @param gst The class of the state to return.
     * @return An instance of the given state
     */
    private GameState getState(Class<? extends GameState> gst) {
        GameState g;
        try {
            Constructor con = gst.getConstructor(GameStateManager.class, GameInputManager.class, GraphicalSettings.class);
            g = (GameState) con.newInstance(this, gim, gs);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
        return g;
    }

    /**
     * Add the given state to the stack without animating
     * @param gs The <code>GameState</code> to add
     * @param popPreviousOne If the previous one has to be removed from the stack
     */
    protected void addStateToStack(GameState gs, boolean popPreviousOne) {
        if (popPreviousOne)
            gameStateStack.pop().dispose();
        else if (! gameStateStack.empty())
            gameStateStack.peek().loseFocus();
        gs.init();
        gameStateStack.push(gs);
        gs.getFocus();
    }

    /**
     * Remove the first state from the stack with an animation.
     */
    public void removeFirstState() {
        animateForChangingState(this::removeFirstStateFromStack);
    }

    /**
     * Remove the first state from the stack.
     */
    public void removeFirstStateFromStack() {
        if (gameStateStack.size() == 1)
            return;
        gameStateStack.pop();
        gameStateStack.peek().getFocus();
    }

    /**
     * Remove all the current states and add the given state to the stack.
     * @param gst The type of state to add.
     */
    public GameState removeAllStateAndAdd(Class<? extends GameState> gst) {
        GameState g = getState(gst);
        while (! gameStateStack.empty())
            gameStateStack.pop().dispose();
        addStateToStack(g, false);
        return g;
    }

    /**
     * Remove all the state until <code>gs</code>. <code>gs</code> is removed too.
     * If <code>gs</code> doesn't exist, nothing is done !
     * @param gs A state
     */
    public void removeAllStateUntil(GameState gs) {
        if (gameStateStack.contains(gs)) {
            while (gameStateStack.pop() != gs) {}
        }
    }

    /**
     * Update the first state.
     * @param dt The time between this call and the previous one (delta-time).
     */
    public void update(float dt) {
        gameStateStack.peek().update(dt);
        AnimationManager.getInstance().update(dt);
    }

    /**
     * Draw all the state in the stack in the order in which there were added.
     */
    public void draw() {
        for (GameState gs: gameStateStack)
            gs.draw();
        if (isChangingState) {
            Gdx.gl.glEnable(GL30.GL_BLEND);
            Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.rect(0, 0, MasterOfMonsGame.WIDTH, MasterOfMonsGame.HEIGHT);
            sr.end();
            Gdx.gl.glDisable(GL30.GL_BLEND);
        }
    }

    /**
     * Dispose all the resources that this object has.
     */
    public void dispose() {
        for (GameState gs: gameStateStack)
            gs.dispose();
    }
}
