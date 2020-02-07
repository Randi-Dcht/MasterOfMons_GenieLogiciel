package be.ac.umons.sgl.mom.Managers;

import be.ac.umons.sgl.mom.Enums.KeyStatus;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


/**
 * Cette classe gère les entrées avec l'utilisateur (clavier, souris, ...).
 * Une partie du code ci-dessous à été tiré de https://github.com/foreignguymike/legacyYTtutorials/tree/master/libgdxasteroids par ForeignGuyMike
 * @author Guillaume Cardoen
 */
public class GameInputManager implements InputProcessor {
    /**
     * Le nombre de touches disponible dans la classe <code>Input.Keys</code>.
     */
    private final int AVAILABLE_INPUT_KEYS = 256;
    /**
     * La liste des cliques depuis la dernière mise-à-jour.
     */
    private List<Point> recentClicks;
    /**
     * La dernière position de la souris.
     */
    private Point lastMousePosition;
    /**
     * La quantité de mouvement de la molette de l'utilisateur depuis la dernière mise-à-jour.
     */
    private int scrolledAmount = 0;
    /**
     * Le statut de chaque touche.
     */
    private KeyStatus[] keys;

    private List<Character> lastChars;

    /**
     * Crée un nouveau gestionnaire d'entrée.
     */
    public GameInputManager() {
        keys = new KeyStatus[AVAILABLE_INPUT_KEYS];
        lastMousePosition = new Point();
        recentClicks = new LinkedList<>();
        lastChars = new LinkedList<>();
        Arrays.fill(keys, KeyStatus.Up);
    }

    /**
     * S'éxécute lorsqu'une touche est enfoncé par l'utilisateur.
     * @param keycode L'ID de la touche.
     * @return Si l'événement à été pris en compte ou non.
     */
    @Override
    public boolean keyDown(int keycode) {
        if (keycode != -1)
            keys[keycode] = KeyStatus.Pressed;
        return true;
    }
    /**
     * S'éxécute lorsqu'une touche est relaché par l'utilisateur.
     * @param keycode L'ID de la touche.
     * @return Si l'événement à été pris en compte ou non.
     */
    @Override
    public boolean keyUp(int keycode) {
        if (keycode != -1)
            keys[keycode] = KeyStatus.UnPressed;
        return true;
    }

    /**
     * S'éxécute quand une touche à été enfoncé et puis relaché par l'utilisateur.
     * @param character La touche enfoncée.
     * @return Si l'événement à été pris en compte ou non.
     */
    @Override
    public boolean keyTyped(char character) {
        lastChars.add(character);
        return false;
    }

    /**
     * S'éxécute quand l'utilisateur commence un clique sur la fenêtre.
     * @param screenX La position horizontale du clique.
     * @param screenY La position verticale du clique.
     * @param pointer Le pointeur pour l'événement.
     * @param button Le bouton de la souris cliqué.
     * @return Si l'événement à été pris en compte ou non.
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT)
            recentClicks.add(new Point(screenX, screenY));
        return true;
    }

    /**
     * S'éxécute quand l'utilisateur termine un clique sur la fenêtre.
     * @param screenX La position horizontale du clique.
     * @param screenY La position verticale du clique.
     * @param pointer Le pointeur pour l'événement.
     * @param button Le bouton de la souris cliqué.
     * @return Si l'événement à été pris en compte ou non.
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return true;
    }

    /**
     * S'éxécute quand la souris est glissée sur l'écran en cliquant.
     * @param screenX La position horizontale du clique.
     * @param screenY La position verticale du clique.
     * @param pointer Le pointeur pour l'événement.
     * @return Si l'événement à été pris en compte ou non.
     */
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    /**
     * S'éxécute quand la souris bouge sur l'écran.
     * @param screenX La position horizontale du clique.
     * @param screenY La position verticale du clique.
     * @return Si l'événement à été pris en compte ou non.
     */
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        lastMousePosition.x = screenX;
        lastMousePosition.y = screenY;
        return false;
    }

    /**
     * S'éxécute lorsque l'utilisateur utilise la molette de sa souris.
     * @param amount La quantité de mouvement de la molette.
     * @return Si l'événement à été pris en compte ou non.
     */
    @Override
    public boolean scrolled(int amount) {
        scrolledAmount += amount;
        return true;
    }

    /**
     * Retourne si la touche <code>k</code> est dans le status <code>ks</code>.
     * @param k L'ID d'une touche.
     * @param ks L'état à vérifier.
     * @return Si la touche <code>k</code> est dans le status <code>ks</code>.
     */
    public boolean isKey(int k, KeyStatus ks) {
        return (keys[k].equals(ks));
    }

    /**
     * Met-à-jour le tableau de status des touches et remet à zéro les cliques récent ainsi que la quantité de mouvement de la molette.
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
    }

    /**
     * Retourne une liste des cliques effectués depuis la dernière mise-à-jour.
     * @return Une liste des cliques effectués depuis la dernière mise-à-jour.
     */
    public List<Point> getRecentClicks() {
        return recentClicks;
    }

    /**
     * Retourne la dernière position de la souris.
     * @return La dernière position de la souris.
     */
    public Point getLastMousePosition() {
        return lastMousePosition;
    }

    /**
     * Retourne la quantité de mouvement de la molette de l'utilisateur depuis la dernière mise-à-jour.
     * @return La quantité de mouvement de la molette de l'utilisateur depuis la dernière mise-à-jour.
     */
    public int getScrolledAmount() {
        return scrolledAmount;
    }

    public List<Character> getLastChars() {
        return lastChars;
    }
}
