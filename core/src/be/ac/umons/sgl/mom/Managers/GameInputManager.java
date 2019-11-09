package be.ac.umons.sgl.mom.Managers;

import be.ac.umons.sgl.mom.Enums.KeyStatus;
import com.badlogic.gdx.InputProcessor;

import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


/** Part of content below was inspired by https://github.com/foreignguymike/legacyYTtutorials/tree/master/libgdxasteroids by ForeignGuyMike */

public class GameInputManager implements InputProcessor {
    private final int AVAILABLE_INPUT_KEYS = 256;
    private List<Point> recentClicks;
    private Point lastMousePosition;
    private int scrolledAmount = 0;

    private KeyStatus[] keys;

    public GameInputManager() {
        keys = new KeyStatus[AVAILABLE_INPUT_KEYS];
        lastMousePosition = new Point();
        recentClicks = new LinkedList<>();
        Arrays.fill(keys, KeyStatus.Up);
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
        lastMousePosition.x = screenX;
        lastMousePosition.y = screenY;
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        scrolledAmount += amount;
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
        scrolledAmount = 0;
    }

    public List<Point> getRecentClicks() {
        return recentClicks;
    }

    public Point getLastMousePosition() {
        return lastMousePosition;
    }

    public int getScrolledAmount() {
        return scrolledAmount;
    }
}
