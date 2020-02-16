package be.ac.umons.sgl.mom.GraphicalObjects;

import be.ac.umons.sgl.mom.Enums.GameObjects;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

import static be.ac.umons.sgl.mom.GameStates.PlayingState.SHOWED_MAP_HEIGHT;
import static be.ac.umons.sgl.mom.GameStates.PlayingState.SHOWED_MAP_WIDTH;

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
        super(gs);
        this.middleX = middleX;
        this.middleY = middleY;
        posX = middleX;
        posY = 0;
        inventory = new ArrayList<>();
        inventory.add(GameObjects.Object1);
        inventory.add(GameObjects.Object2);
    }
    public Player() {}

    /**
     * @param gs Game's graphical settings
     */
    public Player(GraphicalSettings gs) {
        super(gs);
        inventory = new ArrayList<>();
        inventory.add(GameObjects.Object1);
        inventory.add(GameObjects.Object2);
    }

    /**
     * Draw the player on <code>batch</code>.
     * @param batch Where the player must be drawn.
     */
    public void draw(Batch batch) {
        batch.begin();
        batch.draw(getTexture(), middleX + xT, middleY + yT, tileWidth, 2 * tileHeight);
        batch.end();
    }

    @Override
    public void move(int x, int y) { // TODO : Change the method
        super.move(x, y);

        double maxX = mapHeight + mapWidth * tileHeight / tileWidth;
        if (posX < 0)
            posX = 0;
        else if (posX > maxX - getWidth() - SHOWED_MAP_WIDTH * tileWidth / 2)
            posX = (int)maxX - getWidth() - SHOWED_MAP_WIDTH * tileWidth / 2;

        if (posY > mapHeight / 2 - getHeight())
            posY = mapHeight / 2 - getHeight();
        else if (posY < -mapHeight - getHeight())
            posY = -mapHeight - getHeight();

        if (posX < SHOWED_MAP_WIDTH * tileWidth / 2)
            xT = -(SHOWED_MAP_WIDTH * tileWidth / 2 - posX);
        else if (posX > maxX - SHOWED_MAP_WIDTH * tileWidth)
            xT = posX - (int)maxX + SHOWED_MAP_WIDTH * tileWidth;
        else xT = 0;

        if (posY > mapHeight / 2 - SHOWED_MAP_HEIGHT * tileHeight / 2)
            yT = posY - (mapHeight / 2 - SHOWED_MAP_HEIGHT * tileHeight / 2);
        else if (posY < -mapHeight + SHOWED_MAP_HEIGHT * tileHeight / 2)
            yT = posY + mapHeight - SHOWED_MAP_HEIGHT * tileHeight / 2;
        else yT = 0;
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

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }

    public void setTileWidth(int tileWidth) {
        this.tileWidth = tileWidth;
    }

    public void setTileHeight(int tileHeight) {
        this.tileHeight = tileHeight;
    }


}
