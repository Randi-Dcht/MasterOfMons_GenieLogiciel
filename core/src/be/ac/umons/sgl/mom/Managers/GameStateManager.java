package be.ac.umons.sgl.mom.Managers;

import be.ac.umons.sgl.mom.Enums.GameStates;
import be.ac.umons.sgl.mom.GameStates.*;
import be.ac.umons.sgl.mom.GameStates.Menus.InGameMenuState;
import be.ac.umons.sgl.mom.GameStates.Menus.MainMenuState;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;

import java.util.Stack;


/** Part of content below was inspired by https://github.com/foreignguymike/legacyYTtutorials/tree/master/libgdxasteroids by ForeignGuyMike */

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
    }
}