package be.ac.umons.sgl.mom.Managers;

import be.ac.umons.sgl.mom.Enums.KeyStatus;
import com.badlogic.gdx.InputProcessor;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class GameInputManager implements InputProcessor {
    private final int AVAILABLE_INPUT_KEYS = 256;
    private List<Point> recentClicks;

    protected KeyStatus[] keys;

    public GameInputManager() {
        keys = new KeyStatus[AVAILABLE_INPUT_KEYS];
        recentClicks = new LinkedList<>();
        for (int i = 0; i < keys.length; i++) {
            keys[i] = KeyStatus.Up;
        }
    }
    
    @Override
    public boolean keyDown(int keycode) {
        if (keycode != -1)
            keys[keycode] = KeyStatus.Pressed;
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode != -1)
            keys[keycode] = KeyStatus.UnPressed;
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        recentClicks.add(new Point(screenX, screenY));
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

    public boolean isKey(int gk, KeyStatus ks) {
        return (keys[gk].equals(ks));
    }

    public void update() {
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] == KeyStatus.Pressed)
                keys[i] = KeyStatus.Down;
            else if (keys[i] == KeyStatus.UnPressed)
                keys[i] = KeyStatus.Up;
        }
        recentClicks.clear();
    }

    public List<Point> getRecentClicks() {
        return recentClicks;
    }
}
