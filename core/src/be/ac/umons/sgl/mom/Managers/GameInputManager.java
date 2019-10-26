package be.ac.umons.sgl.mom.Managers;

import be.ac.umons.sgl.mom.Enums.GameKeys;
import be.ac.umons.sgl.mom.Enums.KeyStatus;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class GameInputManager implements InputProcessor {

    protected KeyStatus[] keys;

    public GameInputManager() {
        keys = new KeyStatus[GameKeys.values().length];
        for (int i = 0; i < keys.length; i++) {
            keys[i] = KeyStatus.Up;
        }
    }
    
    @Override
    public boolean keyDown(int keycode) {
        GameKeys gk = gdxKeyToGameKeys(keycode);
        if (gk != null)
            keys[gk.ordinal()] = KeyStatus.Pressed;
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        GameKeys gk = gdxKeyToGameKeys(keycode);
        if (gk != null)
            keys[gk.ordinal()] = KeyStatus.UnPressed;
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public boolean isKey(GameKeys gk, KeyStatus ks) {
        return (keys[gk.ordinal()].equals(ks));
    }

    protected GameKeys gdxKeyToGameKeys(int keyCode) {
        switch (keyCode) {
            case Input.Keys.Z:
                return GameKeys.Z;
            case Input.Keys.Q:
                return GameKeys.Q;
            case Input.Keys.S:
                return GameKeys.S;
            case Input.Keys.D:
                return GameKeys.D;
            case Input.Keys.UP:
                return GameKeys.Up;
            case Input.Keys.DOWN:
                return GameKeys.Down;
            case Input.Keys.RIGHT:
                return GameKeys.Right;
            case Input.Keys.LEFT:
                return GameKeys.Left;
            case Input.Keys.ENTER:
                return GameKeys.Enter;
            case Input.Keys.ESCAPE:
                return GameKeys.ESC;
        }
        return null;
    }

    public void update() {
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] == KeyStatus.Pressed)
                keys[i] = KeyStatus.Down;
            else if (keys[i] == KeyStatus.UnPressed)
                keys[i] = KeyStatus.Up;
        }
    }
}
