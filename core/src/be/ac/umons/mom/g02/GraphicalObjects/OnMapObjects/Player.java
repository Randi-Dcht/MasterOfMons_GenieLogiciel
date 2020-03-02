package be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects;

import be.ac.umons.mom.g02.Events.SuperviserNormally;
import be.ac.umons.mom.g02.GameStates.PlayingState;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

import java.awt.*;

/**
 * Represent a real player of the game.
 * @author Guillaume Cardoen
 */
public class Player extends Character {
    /**
     * The coordinates of the map's center
     */
    private int middleX, middleY;
    /**
     * A tile's size
     */
    private int tileWidth, tileHeight;
    /**
     * The translation to do originating at the middle of the screen.
     */
    private int xT, yT;
    /**
     * The map's size (in pixel)
     */
    private int mapWidth, mapHeight;

    /**
     * @param gs The game's graphical settings.
     * @param middleX The screen's center horizontal position.
     * @param middleY The screen's center vertical position.
     */
    public Player(GraphicalSettings gs, int middleX, int middleY) {
        this(gs);
        this.middleX = middleX;
        this.middleY = middleY;
        posX = middleX;
        posY = 0;
    }
    public Player() {}

    /**
     * @param gs Game's graphical settings
     */
    public Player(GraphicalSettings gs) {
        super(gs, SuperviserNormally.getSupervisor().getPeople());
    }

    /**
     * Draw the player on <code>batch</code>.
     * @param batch Where the player must be drawn.
     */
    public void draw(Batch batch) {
        arc.draw(new Point(middleX + xT + getWidth() / 2, middleY + yT + getHeight()));
        batch.begin();
        batch.draw(getTexture(), middleX + xT, middleY + yT, tileWidth, 2 * tileHeight);
        batch.end();
    }

    @Override
    public void move(int x, int y) { // TODO : Change the method
        super.move(x, y);

        Rectangle mr = getMapRectangle();

        if (mr.getX() < 0 || mr.getX() > mapWidth / tileWidth || mr.getY() < 0 || mr.getY() > mapHeight / tileHeight) {
            super.move(-x,-y);
            return;
        }

        if (Math.abs(xT + x) > PlayingState.SHOWED_MAP_WIDTH / 2 * tileWidth || Math.abs(yT + y) > PlayingState.SHOWED_MAP_HEIGHT * tileHeight) {
            super.move(-x, -y);
            return;
        }
    }

    /**
     * Set the horizontal translation from the center.
     * @param xT The horizontal translation.
     */
    public void setxT(int xT) {
        this.xT = xT;
    }
    /**
     * Set the vertical translation from the center.
     * @param yT The vertical translation.
     */
    public void setyT(int yT) {
        this.yT = yT;
    }

    /**
     * @return The width of the player.
     */
    public int getWidth() {
        return tileWidth;
    }
    /**
     * @return The height of the player
     */
    public int getHeight() {
        return tileHeight;
    }

    /**
     * A part of the code is from https://stackoverflow.com/a/34522439 by Ernst Albrigtsen
     * @return A rectangle representing the position of the player (in tiles) (originating from the tile (0,0) at the top of the map).
     */
    public Rectangle getMapRectangle() {
//        int x = (((-getPosY() + MasterOfMonsGame.HEIGHT / 2 - getHeight() / 2) * 2) - mapHeight + getPosX()) / 2 + 4276;
//        int y = getPosX() - x;
        int x = (int)((double)(-getPosY()) / tileHeight + (getPosX()) / tileWidth);
        int y = (int)(mapHeight / tileHeight - ((double)(getPosY()) / tileHeight + (getPosX()) / tileWidth));

        return new Rectangle(x , y, ((float)getWidth() / tileWidth), (float)getHeight() / tileHeight);
    }

    /**
     * Set the map's width to use.
     * @param mapWidth The map's width.
     */
    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }
    /**
     * Set the map's height to use.
     * @param mapHeight The map's height.
     */
    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }
    /**
     * Set the tile's width to use.
     * @param tileWidth The tile's width.
     */
    public void setTileWidth(int tileWidth) {
        this.tileWidth = tileWidth;
    }
    /**
     * Set the tile's height to use.
     * @param tileHeight The tile's height.
     */
    public void setTileHeight(int tileHeight) {
        this.tileHeight = tileHeight;
    }
}
