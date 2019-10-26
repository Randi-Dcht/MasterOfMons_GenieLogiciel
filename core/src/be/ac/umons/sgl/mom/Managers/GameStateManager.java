package be.ac.umons.sgl.mom.Managers;

import be.ac.umons.sgl.mom.Enums.GameStates;
import be.ac.umons.sgl.mom.GameStates.*;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;

import java.util.Stack;

public class GameStateManager {
    private Stack<GameState> gameStateStack;
    private GameInputManager gim;
    protected GraphicalSettings gs;

    public GameStateManager(GameInputManager gim, GraphicalSettings gs) {
        gameStateStack = new Stack<>();
        this.gim = gim;
        this.gs = gs;
        setState(GameStates.MainMenu);
    }

    public void setState(GameStates gst) {
        switch (gst) {
            case MainMenu:
                gameStateStack.push(new MainMenuState(this, gim, gs));
                break;
            default:
                break;
        }
    }

    public void removeFirstState() {
        gameStateStack.pop();
    }

    public void update(float dt) {
        gameStateStack.peek().update(dt);
    }

    public void draw() {
        for (GameState gs: gameStateStack) {
            gs.draw();
        }
//        gameStateStack.peek().draw();
    }
}