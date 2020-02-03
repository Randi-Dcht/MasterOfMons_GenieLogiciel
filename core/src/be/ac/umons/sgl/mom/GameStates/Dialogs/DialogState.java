package be.ac.umons.sgl.mom.GameStates.Dialogs;

import be.ac.umons.sgl.mom.Enums.KeyStatus;
import be.ac.umons.sgl.mom.GameStates.GameState;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.HashMap;

public abstract class DialogState extends GameState {
    protected String text;
    protected String selected = null;
    protected HashMap<String, Runnable> whenSelectedActions;
    protected ShapeRenderer sr;
    protected SpriteBatch sb;

    public DialogState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }

    @Override
    public void init() {
        super.init();
        whenSelectedActions = new HashMap<>();
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
        sb = new SpriteBatch();
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void handleInput() {
        if (gim.isKey(Input.Keys.ESCAPE, KeyStatus.Pressed))
            gsm.removeFirstState();
    }

    @Override
    public void dispose() {
        sr.dispose();
        sb.dispose();
    }

    public void addAnswer(String... answer) {
        for (String s : answer)
            addAnswer(s, null);
    }
    public void addAnswer(String answer, Runnable run) {
        whenSelectedActions.put(answer, run);
    }

    public String getResult() {
        return selected;
    }

    public void setWhenSelected(String answer, Runnable run) {
        whenSelectedActions.put(answer, run);
    }

    public void setText(String text) {
        this.text = text;
    }
}
