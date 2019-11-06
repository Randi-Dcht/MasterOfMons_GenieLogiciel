package be.ac.umons.sgl.mom.GraphicalObjects;

import be.ac.umons.sgl.mom.Enums.GameObjects;
import be.ac.umons.sgl.mom.Enums.Orientation;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

import static be.ac.umons.sgl.mom.GameStates.PlayingState.SHOWED_MAP_HEIGHT;
import static be.ac.umons.sgl.mom.GameStates.PlayingState.SHOWED_MAP_WIDTH;

public class Character {
    private int middleX, middleY, tileWidth, tileHeight;
    private int xT, yT;
    private Orientation orientation = Orientation.Top;
    private int posX, posY;
    private int mapWidth, mapHeight;
    private List<GameObjects> inventory;
    protected AssetManager assetManager;

    public Character(int middleX, int middleY, int tileWidth, int tileHeight, int mapWidth, int mapHeight) {
        assetManager = MasterOfMonsGame.gs.getAssetManager();
        this.middleX = middleX;
        this.middleY = middleY;
        posX = middleX;
        posY = 0;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        inventory = new ArrayList<>();
        inventory.add(GameObjects.Object1);
        inventory.add(GameObjects.Object2);
    }

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

    public void draw(Batch batch) {
        batch.begin();
        batch.draw(getTexture(), middleX + xT, middleY + yT, tileWidth, tileHeight);
        batch.end();
    }

    public void translate(int x, int y) {
        xT += x;
        yT += y;
    }

    public void move(int x, int y) {
        posX += x;
        posY += y;

        if (posX < 0)
            posX = 0;
        else if (posX > mapWidth * Math.cos(42.5f / 360 * 2 * Math.PI) - getWidth())
            posX = (int)(mapWidth * Math.cos(42.5f / 360 * 2 * Math.PI) - getWidth());

        if (posY > SHOWED_MAP_HEIGHT * tileHeight - getHeight())
            posY = SHOWED_MAP_HEIGHT * tileHeight - getHeight();
        else if (posY < -mapHeight + getHeight())
            posY = -mapHeight + getHeight();

        if (posX < SHOWED_MAP_WIDTH * tileWidth / 2)
            xT = -(SHOWED_MAP_WIDTH * tileWidth / 2 - posX);
        else if (posX > mapWidth - SHOWED_MAP_WIDTH * tileWidth - getWidth())
            xT = posX - mapWidth + SHOWED_MAP_WIDTH * tileWidth + getWidth();
        else xT = 0;

        if (posY > 0)
            yT = posY;
        else if (posY < -mapHeight + SHOWED_MAP_HEIGHT * tileHeight)
            yT = posY + mapHeight - SHOWED_MAP_HEIGHT * tileHeight;
        else yT = 0;

    }

    public int getXT() {
        return xT;
    }

    public int getYT() {
        return yT;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public int getWidth() {
        return tileWidth;
    }

    public int getHeight() {
        return tileHeight;
    }

    public int getPosY() {
        return posY;
    }

    public int getPosX() {
        return posX;
    }

    public Rectangle getRectangle() {
        return new Rectangle(getPosX(), getPosY(), getWidth(), getHeight());
    }

    public Rectangle getMapRectangle() {
        int x = (((-getPosY() + MasterOfMonsGame.HEIGHT / 2 - getHeight() / 2) * 2) - mapHeight + getPosX()) / 2; // https://stackoverflow.com/a/13838164
        int y = getPosX() - x;
        return new Rectangle(x , y, getWidth(), getHeight());

    }

    public List<GameObjects> getInventory() {
        return new ArrayList<>(inventory);
    }
}
