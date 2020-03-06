package be.ac.umons.mom.g02.Managers;

import be.ac.umons.mom.g02.Enums.KeyStatus;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


/**
 * This class manage all interactions with the user.
 * This code is partly from https://github.com/foreignguymike/legacyYTtutorials/tree/master/libgdxasteroids by ForeignGuyMike
 * @author Guillaume Cardoen
 */
public class GameInputManager implements InputProcessor {
    /**
     * The number of keys available.
     */
    private final int AVAILABLE_INPUT_KEYS = 256;
    /**
     * The list of the positions of the last clicks.
     */
    private List<Point> recentClicks;
    /**
     * The last mouse position.
     */
    private Point lastMousePosition;
    /**
     * At which point the user want to go down.
     */
    private int scrolledAmount = 0;
    /**
     * The status of each key.
     */
    private KeyStatus[] keys;

    /**
     * The last chars that has been typed.
     */
    private List<Character> lastChars;

    private int lastKeyCode;
    private GameKeyManager gkm;

    public GameInputManager() {
        keys = new KeyStatus[AVAILABLE_INPUT_KEYS];
        lastMousePosition = new Point();
        recentClicks = new LinkedList<>();
        lastChars = new LinkedList<>();
        Arrays.fill(keys, KeyStatus.Up);
        gkm = GameKeyManager.getInstance();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode != -1) {
            keys[keycode] = KeyStatus.Pressed;
            lastKeyCode = keycode;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode != -1)
            keys[keycode] = KeyStatus.UnPressed;
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        lastChars.add(character);
        return false;
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT)
            recentClicks.add(new Point(screenX, screenY));
        return true;
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
        return true;
    }

    /**
     * Check if the key <code>k</code> is in the status <code>ks</code>
     * @param k The key
     * @param ks The status in which the key must be
     * @return If the key is in this status.
     */
    public boolean isKey(int k, KeyStatus ks) {
        return (keys[k].equals(ks));
    }
    /**
     * Check if the key <code>k</code> is in the status <code>ks</code>
     * @param id The id of the key in the GameKeyManager
     * @param ks The status in which the key must be
     * @return If the key is in this status.
     */
    public boolean isKey(String id, KeyStatus ks) {
        return (keys[gkm.getKeyCodeFor(id)].equals(ks));
    }

    /**
     * Updated every key status and clear recent clicks.
     */
    public void update() {
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] == KeyStatus.Pressed)
                keys[i] = KeyStatus.Down;
            else if (keys[i] == KeyStatus.UnPressed)
                keys[i] = KeyStatus.Up;
        }
        recentClicks.clear();
        lastChars.clear();
        scrolledAmount = 0;
        lastKeyCode = -1;
    }

    /**
     * @return A list of the click that happened after the previous updates.
     */
    public List<Point> getRecentClicks() {
        return recentClicks;
    }

    /**
     * @return The last mouse position.
     */
    public Point getLastMousePosition() {
        return lastMousePosition;
    }

    /**
     * @return How much the user scrolled from the last update.
     */
    public int getScrolledAmount() {
        return scrolledAmount;
    }

    /**
     * @return A list of the chars that has been typed since the last update.
     */
    public List<Character> getLastChars() {
        return lastChars;
    }

    public int getLastKeyPressedCode() {
        return lastKeyCode;
    }
}
