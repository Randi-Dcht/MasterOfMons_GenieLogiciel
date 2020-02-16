package be.ac.umons.sgl.mom.GraphicalObjects;

import be.ac.umons.sgl.mom.Enums.GameObjects;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.awt.*;

public class MapObject extends OnMapObject {

    GameObjects go;

    Point mapPos;

    public MapObject(GraphicalSettings gs, GameObjects go) {
        super(gs);
        this.gs = gs;
        this.go = go;
        mapPos = new Point(0,0);
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
        if (gs.getAssetManager().contains("Pictures/Objects/" + go.toString() + ".png")) {
            Texture t = gs.getAssetManager().get("Pictures/Objects/" + go.toString() + ".png");
            batch.begin();
            batch.draw(t, x, y, width, height);
            batch.end();
        }
        super.draw(batch, x, y, width, height);
    }

    @Override
    public int getPosX() {
        return mapPos.x;
    }

    @Override
    public int getPosY() {
        return mapPos.y;
    }

    public void setMapPos(Point mapPos) {
        this.mapPos = mapPos;
    }
}
