package be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects;

import be.ac.umons.mom.g02.Enums.Orientation;
import be.ac.umons.mom.g02.Events.SuperviserNormally;
import be.ac.umons.mom.g02.GraphicalObjects.AttackRangeCircle;
import be.ac.umons.mom.g02.GraphicalObjects.LifeBar;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Objects.Items.Items;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

import java.awt.*;
import java.util.List;

/**
 * Represent a character of the game.
 * @author Guillaume Cardoen
 */
public class Character extends OnMapObject {

    /**
     * A tile's size
     */
    protected int tileWidth, tileHeight;
    /**
     * The map's size (in pixel)
     */
    protected int mapWidth, mapHeight;
    /**
     * An asset's manager.
     */
    protected AssetManager assetManager;
    /**
     * The character's orientation
     */
    protected Orientation orientation = Orientation.Top;
    /**
     * The character's position
     */
    protected int posX, posY;
    /**
     * The character's characteristics
     */
    private be.ac.umons.mom.g02.Objects.Characters.Character characteristics;
    /**
     * The time that the character needs to wait before his next attack
     */
    private double timeBeforeAttack;

    /**
     * The life bar of this character.
     */
    protected LifeBar lifeBar;
    /**
     * The range in which the player can attack.
     */
    protected int attackRange = 200;
    /**
     * If the character is in the attack range of the player.
     */
    protected boolean isATarget = false;

    protected int width, height;


    protected AttackRangeCircle arc;

    /**
     * @param gs The game's graphical settings.
     */
    public Character(GraphicalSettings gs, be.ac.umons.mom.g02.Objects.Characters.Character characteristics) {
        super(gs);
        arc = new AttackRangeCircle(gs, this);
        assetManager = gs.getAssetManager();
        this.characteristics = characteristics;
        lifeBar = new LifeBar(gs);
        lifeBar.setForegroundColor(new Color(213f / 255, 0, 0, .8f));
    }

    protected Character() {}

    /**
     * Draw the character with the given parameters.
     * @param batch Where to draw the character
     * @param x The horizontal position
     * @param y The vertical position
     * @param width The width of the character
     * @param height The height of the character
     */
    public void draw(Batch batch, int x, int y, int width, int height) {
        this.width = width;
        this.height = height;
        if (isATarget) {
            sr.begin();
            sr.setColor(new Color(0xB71C1CAA));
            sr.ellipse(x, y, width, height / 4);
            sr.end();
        }

        batch.begin();
        batch.draw(getTexture(),  x, y, width, height);
        batch.end();

        lifeBar.setValue((int)getCharacteristics().getActualLife());
        lifeBar.setMaxValue((int)getCharacteristics().lifeMax());

//        if (lifeBar.getPercent() < 1) {
            lifeBar.draw(batch, x, y + height, width, height / 5);
//        }

        super.draw(batch, x, y, width, height);
    }

    /**
     * Update the character (time before attack for example)
     * @param dt The delta time
     */
    public void update(float dt) {
        if (timeBeforeAttack > 0)
            timeBeforeAttack -= dt;
        lifeBar.update(dt);
    }

    /**
     * @return The texture to use when drawing the character
     */
    public Texture getTexture() {
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
     * Add (<code>x</code>, <code>y</code>) to character's position
     * @param x Horizontal movement
     * @param y Vertical movement
     */
    public void move(int x, int y) {
        posX += x;
        posY += y;
    }


    public void expandAttackCircle() {
        arc.expand();
    }

    public boolean isRecovering() {
        return arc.isRecovering();
    }

    /**
     * @return Character's orientation
     */
    public Orientation getOrientation() {
        return orientation;
    }

    /**
     * Set character's orientation.
     * @param orientation Character's orientation
     */
    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    /**
     * @return Character's vertical position.
     */
    public int getPosY() {
        return posY;
    }
    /**
     * @return Character's horizontal position
     */
    public int getPosX() {
        return posX;
    }

    /**
     * @return Character's inventory
     */
    public List<Items> getInventory() {
        return SuperviserNormally.getSupervisor().getPeople().getInventory();
    }

    /**
     * @return Character's characteristics
     */
    public be.ac.umons.mom.g02.Objects.Characters.Character getCharacteristics() {
        return characteristics;
    }
    public void setCharacteristics(People characteristics) {
        this.characteristics = characteristics;
    }

    /**
     * @return If the character can attack
     */
    public boolean canAttack() {
        return timeBeforeAttack <= 0;
    }

    /**
     * Set the time before the character can attack.
     * @param timeBeforeAttack The time before the character can attack
     */
    public void setTimeBeforeAttack(double timeBeforeAttack) {
        this.timeBeforeAttack = timeBeforeAttack;
    }

    /**
     * Set the horizontal position.
     * @param posX The horizontal position.
     */
    public void setPosX(int posX) {
        this.posX = posX;
    }
    /**
     * Set the vertical position.
     * @param posY The vertical position.
     */
    public void setPosY(int posY) {
        this.posY = posY;
    }

    /**
     * Set the position.
     * @param pos The position
     */
    public void setMapPos(Point pos) {
        this.posX = pos.x;
        this.posY = pos.y;
    }

    /**
     * Set if the character is in the player's attack range.
     * @param isATarget If the character is in the player's attack range.
     */
    public void setIsATarget(boolean isATarget) {
        this.isATarget = isATarget;
    }

    /**
     * @return The character's attack range.
     */
    public int getAttackRange() {
        return attackRange;
    }


    /**
     * A part of the code is from https://stackoverflow.com/a/34522439 by Ernst Albrigtsen
     * @return A rectangle representing the position of the player (in tiles) (originating from the tile (0,0) at the top of the map).
     */
    public com.badlogic.gdx.math.Rectangle getMapRectangle() {
//        int x = (((-getPosY() + MasterOfMonsGame.HEIGHT / 2 - getHeight() / 2) * 2) - mapHeight + getPosX()) / 2 + 4276;
//        int y = getPosX() - x;
        int x = (int)((double)(-getPosY()) / tileHeight + (getPosX()) / tileWidth);
        int y = (int)(mapHeight / tileHeight - ((double)(getPosY()) / tileHeight + (getPosX()) / tileWidth));

        return new Rectangle(x , y, ((float)width / tileWidth), (float)height / tileHeight);
    }

    public void dispose() {
        lifeBar.dispose();
        arc.dispose();
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
