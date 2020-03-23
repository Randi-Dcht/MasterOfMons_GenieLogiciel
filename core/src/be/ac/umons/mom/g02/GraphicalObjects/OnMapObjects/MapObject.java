package be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects;

import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Objects.Items.Items;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.awt.*;
import java.io.Serializable;

/**
 * Represent on object on the map of the game (like a dropped inventory's item)
 */
public class MapObject extends OnMapObject {
    /**
     * The characteristics of the represented item.
     */
    OnMapItem omi = new OnMapItem();

    /**
     * @param gs The game's graphical settings.
     * @param item The item to represent.
     */
    public MapObject(GraphicalSettings gs, Items item) {
        super(gs);
        this.gs = gs;
        omi.item = item;
        omi.mapPos = new Point(0,0);
    }
    public MapObject(GraphicalSettings gs, OnMapItem omi) {
        super(gs);
        this.gs = gs;
        this.omi = omi;
    }

    /**
     * Draw the object on the map with the given parameters.
     * @param batch Where to draw the character
     * @param x The horizontal position
     * @param y The vertical position
     * @param width The width of the character
     * @param height The height of the character
     */
    public void draw(Batch batch, int x, int y, int width, int height) {
        if (gs.getAssetManager().contains("Pictures/Objects/" + omi.item.toString() + ".png")) {
            Texture t = gs.getAssetManager().get("Pictures/Objects/" + omi.item.toString() + ".png");
            batch.begin();
            batch.draw(t, x, y, width, height);
            batch.end();
        }
        super.draw(batch, x, y, width, height);
    }

    @Override
    public int getPosX() {
        return omi.mapPos.x;
    }

    @Override
    public int getPosY() {
        return omi.mapPos.y;
    }

    /**
     * Set the position on the map (Pixel)
     * @param mapPos The position on the map.
     */
    public void setMapPos(Point mapPos) {
        omi.mapPos = mapPos;
    }

    public void setMap(String map) {
        omi.map = map;
    }

    public String getMap() {
        return omi.map;
    }

    /**
     * @return The item represented by this object.
     */
    public Items getItem() {
        return omi.item;
    }

    /**
     * @return The characteristics of the represented item.
     */
    public OnMapItem getCharacteristics() {
        return omi;
    }

    public static class OnMapItem implements Serializable {
        /**
         * The item represented by this object.
         */
        Items item;
        /**
         * The position if this object on the map.
         */
        Point mapPos;

        String map;
    }
}
