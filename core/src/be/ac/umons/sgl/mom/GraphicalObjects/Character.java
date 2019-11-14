package be.ac.umons.sgl.mom.GraphicalObjects;

import be.ac.umons.sgl.mom.Enums.GameObjects;
import be.ac.umons.sgl.mom.Enums.Orientation;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.ArrayList;
import java.util.List;

public class Character {

    protected GraphicalSettings gs;
    /**
     * L'objet permettant de charger les textures nécessaires.
     */
    protected AssetManager assetManager;
    /**
     * L'orientation du personnage.
     */
    protected Orientation orientation = Orientation.Top;
    /**
     * Les coordonnées du personnage.
     */
    protected int posX, posY;
    /**
     * L'inventaire du personnage.
     */
    protected List<GameObjects> inventory;

    public Character(GraphicalSettings gs) {
        this.gs = gs;
        assetManager = gs.getAssetManager();
    }

    public void draw(Batch batch, int x, int y, int width, int height) {
        batch.begin();
        batch.draw(getTexture(),  x, y, width, height);
        batch.end();

    }

    /**
     * Retourne la texture à utiliser pour le personnage en fonction de l'orientation du personnage.
     * @return La texture à utiliser pour le personnage.
     */
    protected Texture getTexture() {
        switch (orientation) {
            case Top:
                return ((posY < 0 ? -posY : posY) / 100) % 2 == 1 ? assetManager.get("Pictures/Characters/hautbh.png") : assetManager.get("Pictures/Characters/hautbh2.png");
            case Bottom:
                return ((posY < 0 ? -posY : posY) / 100) % 2 == 1 ? assetManager.get("Pictures/Characters/basbh.png") : assetManager.get("Pictures/Characters/basbh2.png");
            case Left:
                return ((posX < 0 ? -posX : posX) / 100) % 2 == 1 ? assetManager.get("Pictures/Characters/gauchebh.png") : assetManager.get("Pictures/Characters/gauchebh2.png");
            case Right:
                return ((posX < 0 ? -posX : posX) / 100) % 2 == 1 ? assetManager.get("Pictures/Characters/droitebh.png") : assetManager.get("Pictures/Characters/droitebh2.png");
        }
        return assetManager.get("Characters/hautbh.png");
    }

    /**
     * Déplace le personnage de (<code>x</code>, <code>y</code>)
     * @param x Le mouvement horizontale.
     * @param y Le mouvement verticale.
     */
    public void move(int x, int y) {
        posX += x;
        posY += y;
    }

    /**
     * Retourne l'orientation du personnage.
     * @return L'orientation du personnage.
     */
    public Orientation getOrientation() {
        return orientation;
    }

    /**
     * Défini l'orientation du personnage.
     * @param orientation L'orientation du personnage.
     */
    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    /**
     * Retourne la position verticale du personnage sur la carte.
     * @return La position verticale du personnage sur la carte.
     */
    public int getPosY() {
        return posY;
    }
    /**
     * Retourne la position horizontale du personnage sur la carte.
     * @return La position horizontale du personnage sur la carte.
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Retourne l'inventaire du personnage.
     * @return L'inventaire du personnage.
     */
    public List<GameObjects> getInventory() {
        return new ArrayList<>(inventory);
    }

}
