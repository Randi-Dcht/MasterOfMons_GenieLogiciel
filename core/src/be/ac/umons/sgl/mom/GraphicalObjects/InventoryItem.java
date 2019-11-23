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
 * @author Guillaume Cardoen
 */
public class InventoryItem {
    /**
     * L'opacité du rectangle sur laquelle l'image de l'élément d'inventaire sera déssinée.
     */
    protected static float BACKGROUND_RECTANGLE_OPACITY = .8f;

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
    private boolean isBeingAnimated;
    /**
     * L'opacité du du rectangle sur laquelle l'image de l'élément d'inventaire sera déssinée durant une animation.
     */
    private float duringAnimationForegroundOpacity;
    /**
     * Si cet élément d'inventaire a été sélectionné par l'utilisateur.
     */
    private boolean isSelected = false;
    /**
     * La couleur du rectangle en arrière-plan.
     */
    private Color backgroundColor;
    /**
     * La couleur du rectangle d'arrière-plan quand l'élément est sélectionné.
     */
    private Color selectedBackgroundColor;

    /**
     * La couleur d'arrière-plan qu'il faut utilisé à cet instant.
     */
    private Color backgroundColorToUse;

    /**
     * Crée un nouveau support pour montré l'élément d'inventaire.
     * @param gs Les paramètres graphique du jeu.
     * @param go L'élément à montrer.
     */
    public InventoryItem(GraphicalSettings gs, GameObjects go) {
        this.go = go;
        this.gs = gs;
        init();
    }

    /**
     * Initialise un nouvel élément d'inventaire.
     */
    public void init() {
        sr = new ShapeRenderer();
        backgroundColor = new Color(21f / 255, 21f / 255, 21f / 255, BACKGROUND_RECTANGLE_OPACITY);
        selectedBackgroundColor = new Color(69f / 255, 39f / 255, 160f / 255, BACKGROUND_RECTANGLE_OPACITY);
        backgroundColorToUse = backgroundColor;
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
            backgroundColorToUse.a = duringAnimationForegroundOpacity * BACKGROUND_RECTANGLE_OPACITY;
        sr.setColor(backgroundColorToUse);
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
     * Sélectionne cet élément d'inventaire.
     */
    public void select() {
        backgroundColorToUse = selectedBackgroundColor;
        isSelected = true;
    }
    /**
     * Ne sélectionne plus cet élément d'inventaire.
     */
    public void unselect() {
        backgroundColorToUse = backgroundColor;
        isSelected = false;
    }

    /**
     * Retourne si cet élément d'inventaire est sélectionné.
     * @return Si cet élément d'inventaire est sélectionné.
     */
    public boolean isSelected() {
        return isSelected;
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
        backgroundColor.a = BACKGROUND_RECTANGLE_OPACITY;
        isBeingAnimated = false;
    }

    /**
     * Libère les ressources allouées par cet élément d'inventaire.
     */
    public void dispose() {
        sr.dispose();
    }

}
