package be.ac.umons.sgl.mom.GraphicalObjects;

import be.ac.umons.sgl.mom.Enums.KeyStatus;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;
import java.util.stream.IntStream;

/**
 * Une classe fille de <code>Control</code> permettant à l'utilisateur d'entrer du texte lorsqu'elle est selectionnée.
 */
public class TextBox extends Control {

    /**
     * Permet de dessiner les formes comme les rectangles.
     */
    private ShapeRenderer sr;
    /**
     * Le texte actuellement entré.
     */
    private String actualText = "";
    /**
     * Le suffixe à placer en fin de texte.
     */
    private String suffix = "";
    /**
     * Est-ce que le support est selectionné.
     */
    private boolean isSelected = false;

    /**
     * Crée un nouveau TextBox.
     * @param gim La GameInputManager du jeu.
     * @param gs Les paramètres graphiques du jeu.
     */
    public TextBox(GameInputManager gim, GraphicalSettings gs) {
        super(gim, gs);
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
    }

    @Override
    public void draw(Batch batch, int x, int y, int width, int height) {
        super.draw(batch, x, y, width, height);
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
        if (isSelected)
            sr.setColor(new Color(0x0288D1));
        else
            sr.setColor(Color.WHITE);
        sr.begin();
        sr.rect(x, y, width, height);
        sr.end();
        batch.begin();
        gs.getNormalFont().draw(batch, actualText + suffix, x + leftMargin, y + gs.getNormalFont().getLineHeight());
        batch.end();
        Gdx.gl.glDisable(GL30.GL_BLEND);

    }

    @Override
    public void handleInput() {
        for (Point click : gim.getRecentClicks())
            isSelected = new Rectangle(x, MasterOfMonsGame.HEIGHT - y - height, width, height).contains(click);
        if (isSelected) {
            boolean upper = false;
            if (gim.isKey(Input.Keys.SHIFT_LEFT, KeyStatus.Pressed) || gim.isKey(Input.Keys.SHIFT_RIGHT, KeyStatus.Pressed))
                upper = true;
            for (int key : IntStream.rangeClosed(Input.Keys.A, Input.Keys.Z).toArray())
                if (gim.isKey(key, KeyStatus.Pressed))
                    actualText += upper ? Input.Keys.toString(key) : Input.Keys.toString(key).toLowerCase();
            if (gim.isKey(Input.Keys.BACKSPACE, KeyStatus.Pressed))
                actualText = actualText.substring(0, actualText.length() - 1);
        }
    }

    @Override
    public void dispose() {
        sr.dispose();
    }

    /**
     * Retourne le texte entré par l'utilisateur ainsi que le suffixe si celui-ci a été défini.
     * @return Le texte entré par l'utilisateur ainsi que le suffixe si celui-ci a été défini.
     */
    public String getText() {
        return actualText + suffix;
    }

    /**
     * Défini le texte actuellement affiché à l'écran modifiable par l'utilisateur.
     * @param text Le texte actuellement affiché à l'écran modifiable par l'utilisateur.
     */
    public void setText(String text) {
        this.actualText = text;
    }

    /**
     * Retourne le suffixe à ajouter à la fin du texte ou "" si il n'a pas été défini.
     * @return Le suffixe à ajouter à la fin du texte ou "" si il n'a pas été défini.
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * Défini le suffixe à ajouter à la fin du texte
     * @param suffix Le suffixe à ajouter à la fin du texte
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
