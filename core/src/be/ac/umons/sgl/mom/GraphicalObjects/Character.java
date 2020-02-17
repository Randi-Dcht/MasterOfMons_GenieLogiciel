package be.ac.umons.sgl.mom.GraphicalObjects;

import be.ac.umons.sgl.mom.Enums.Orientation;
import be.ac.umons.sgl.mom.Events.SuperviserNormally;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import be.ac.umons.sgl.mom.Objects.Items.Items;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent a character of the game.
 * @author Guillaume Cardoen
 */
public class Character extends OnMapObject {
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
     * The character's inventory
     */
    protected List<Items> inventory;
    /**
     * The character's characteristics
     */
    private People characteristics;
    /**
     * The time that the character needs to wait before his next attack
     */
    private double timeBeforeAttack;

    /**
     * @param gs The game's graphical settings.
     */
    public Character(GraphicalSettings gs) {
        super(gs);
        assetManager = gs.getAssetManager();
        characteristics = SuperviserNormally.getSupervisor().getPeople();
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
        batch.begin();
        batch.draw(getTexture(),  x, y, width, height);
        batch.end();
        super.draw(batch, x, y, width, height);
    }

    /**
     * Update the character (time before attack for example)
     * @param dt The delta time
     */
    public void update(float dt) {
        if (timeBeforeAttack > 0)
            timeBeforeAttack -= dt;
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
        return new ArrayList<>(inventory);
    }

    /**
     * @return Character's characteristics
     */
    public People getCharacteristics() {
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


}
