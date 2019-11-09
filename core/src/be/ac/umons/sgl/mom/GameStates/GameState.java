package be.ac.umons.sgl.mom.GameStates;

import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;

public abstract class GameState {
    /** A LOT OF THE CONTENT BELOW HAS BEEN INSPIRED BY https://github.com/foreignguymike/legacyYTtutorials/tree/master/libgdxasteroids by ForeignGuyMike */

    protected GameInputManager gim;
    protected GameStateManager gsm;
    protected GraphicalSettings gs;

    protected GameState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        this.gim = gim;
        this.gsm = gsm;
        this.gs = gs;
        init();
    }

    public abstract void init();
    public abstract void update(float dt);
    public abstract void draw();
    public abstract void handleInput();
    public abstract void dispose();
}
