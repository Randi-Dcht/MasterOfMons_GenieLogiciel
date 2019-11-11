package be.ac.umons.sgl.mom.GraphicalObjects;

import be.ac.umons.sgl.mom.Enums.GameObjects;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Montre un élément de l'inventaire d'un personnage.
 */
public class InventoryItem {
    /**
     * L'opacité du rectangle sur laquelle l'image de l'élément d'inventaire sera déssinée.
     */
    protected static float FOREGROUND_RECTANGLE_OPACITY = .8f;

    /**
     * L'élément à montrer.
     */
    protected GameObjects go;
    /**
     * Les paramètres graphiques du jeu.
     */
    protected GraphicalSettings gs;
    /**
     * Permet de dessiner les formes comme les rectangles.
     */
    protected ShapeRenderer sr;
    /**
     * Doit-on utiliser les variables d'animations ?
     */
    protected boolean isBeingAnimated;
    /**
     * L'opacité du du rectangle sur laquelle l'image de l'élément d'inventaire sera déssinée durant une animation.
     */
    protected float duringAnimationForegroundOpacity;

    /**
     * Crée un nouveau support pour montré l'élément d'inventaire.
     * @param gs Les paramètres graphique du jeu.
     * @param go L'élément à montrer.
     */
    public InventoryItem(GraphicalSettings gs, GameObjects go) {
        this.go = go;
        this.gs = gs;
        sr = new ShapeRenderer();
    }

    /**
     * Dessine l'élément d'inventaire sur <code>batch</code> aux coordonées (<code>x</code>, <code>y</code>) avec la longueur <code>width</code> et la largeur <code>y</code>.
     * @param batch Où le contrôle sera déssiné.
     * @param x La position horizontale.
     * @param y La position verticale.
     * @param width La longueur.
     * @param height La largeur.
     */
    public void draw(Batch batch, int x, int y, int width, int height) {
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        if (isBeingAnimated)
            sr.setColor(21f / 255, 21f / 255, 21f / 255, duringAnimationForegroundOpacity * FOREGROUND_RECTANGLE_OPACITY);
        else
            sr.setColor(21f / 255, 21f / 255, 21f / 255, FOREGROUND_RECTANGLE_OPACITY);
        sr.rect(x, y, width, height);
        sr.end();

        if (gs.getAssetManager().contains("Pictures/Objects/" + go.toString() + ".png")) {
            Texture t = gs.getAssetManager().get("Pictures/Objects/" + go.toString() + ".png");
            batch.begin();
            Color c = batch.getColor();
            batch.setColor(c.r, c.g, c.b, isBeingAnimated ? duringAnimationForegroundOpacity : 1);
            batch.draw(t, x, y, width, height);
            batch.end();
        }
        Gdx.gl.glDisable(GL30.GL_BLEND);
    }

    /**
     * Défini l'opacité du rectangle d'arrière-plan de l'élément d'inventaire durant une animation.
     * @param duringAnimationForegroundOpacity L'opacité du rectangle d'arrière-plan de l'élément d'inventaire durant une animation.
     */
    public void setDuringAnimationForegroundOpacity(float duringAnimationForegroundOpacity) {
        this.duringAnimationForegroundOpacity = duringAnimationForegroundOpacity;
    }

    /**
     * Retourne l'opacité du rectangle d'arrière-plan de l'élément d'inventaire durant une animation.
     * @return L'opacité du rectangle d'arrière-plan de l'élément d'inventaire durant une animation.
     */
    public float getDuringAnimationForegroundOpacity() {
        return duringAnimationForegroundOpacity;
    }

    /**
     * Commencer à utiliser les variables d'animations.
     */
    public void beginAnimation() {
        isBeingAnimated = true;
    }

    /**
     * Arreter d'utiliser les variables d'animations.
     */
    public void finishAnimation() {
        isBeingAnimated = false;
    }
}
