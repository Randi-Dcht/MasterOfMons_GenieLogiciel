package be.ac.umons.sgl.mom.GraphicalObjects.Controls;

import be.ac.umons.sgl.mom.Animations.DoubleAnimation;
import be.ac.umons.sgl.mom.Enums.KeyStatus;
import be.ac.umons.sgl.mom.GraphicalObjects.InventoryItem;
import be.ac.umons.sgl.mom.GraphicalObjects.OnMapObjects.Player;
import be.ac.umons.sgl.mom.Managers.AnimationManager;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import be.ac.umons.sgl.mom.Objects.Items.Items;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Show the player's inventory to the user.
 * @author Guillaume Cardoen
 */
public class InventoryShower extends Control {
    /**
     * The margin between 2 inventory's item.
     */
    protected static int BETWEEN_ITEM_MARGIN = 7;
    /**
     * Background's opacity.
     */
    protected static float BACKGROUND_RECTANGLE_OPACITY = .5f;

    /**
     * The width of an inventory item.
     */
    protected int itemWidth;

    /**
     * Allow to draw shapes.
     */
    protected ShapeRenderer sr;
    /**
     * The inventory to show.
     */
    protected List<Items> inventory;
    /**
     * If the control is being animated.
     */
    protected boolean isBeingAnimated = false;
    /**
     * The size when animating.
     */
    protected float duringAnimationHeight, duringAnimationWidth;
    /**
     * The size of an item during animation.
     */
    protected float duringAnimationItemWidth;
    /**
     * The background's opacity when animating.
     */
    protected double duringAnimationBackgroundOpacity;
    /**
     * The foreground's opacity when animating.
     */
    protected double duringAnimationForegroundOpacity;
    /**
     * List of all <code>InventoryItem</code> for this state.
     */
    protected List<InventoryItem> inventoryItemList;
    /**
     * The animation's manager.
     */
    protected AnimationManager am;
    /**
     * The selected inventory's item.
     */
    protected InventoryItem selectedItem;
    /**
     * If the control is hided or not.
     */
    protected boolean hided = false;

    /**
     * @param gim The game's input manager.
     * @param gs The game's graphical settings.
     * @param inventoryOf The player.
     */
    public InventoryShower(GameInputManager gim, GraphicalSettings gs, Player inventoryOf) {
        super(gim, gs);
        inventory = inventoryOf.getInventory();
        this.am = AnimationManager.getInstance();
        init();
    }

    /**
     * Default constructor. USE IT ONLY FOR TEST.
     */
    protected InventoryShower() {}

    /**
     * Initialize this control.
     */
    public void init() {
        inventoryItemList = new ArrayList<>();
        for (Items go : inventory)
            inventoryItemList.add(new InventoryItem(gs, go));
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
        animate();
    }

    /**
     * Draw this control in the bottom's center.
     * @param batch Where the control needs to be draw
     * @param centerX The horizontal position of the center of the window.
     * @param height The control's vertical size.
     * @param itemSize The size of an inventory's item.
     */
    public void draw(Batch batch, int centerX, int height, Point itemSize) {
        if (hided)
            return;
        itemWidth = itemSize.x;
        if (isBeingAnimated)
            itemSize.x = (int)duringAnimationItemWidth;
        int beginX = centerX - (itemSize.x * inventory.size() + BETWEEN_ITEM_MARGIN * (inventory.size() + 2)) / 2;
        super.draw(batch, new Point(beginX, height), itemSize);
        this.height = height;


        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);

        sr.begin(ShapeRenderer.ShapeType.Filled);
        if (isBeingAnimated) {
            sr.setColor(21f / 255, 21f / 255, 21f / 255, (float)duringAnimationBackgroundOpacity * BACKGROUND_RECTANGLE_OPACITY);
            sr.rect(beginX, topMargin, duringAnimationWidth, duringAnimationHeight);
        }
        else {
            sr.setColor(21f / 255, 21f / 255, 21f / 255, BACKGROUND_RECTANGLE_OPACITY);
            sr.rect(beginX, topMargin, getWidth(), height);
        }
        sr.end();
        int tmpBeginX = beginX + BETWEEN_ITEM_MARGIN;
        for (InventoryItem ii : inventoryItemList) {
            ii.draw(batch, tmpBeginX, topMargin, itemSize.x, itemSize.y);
            tmpBeginX += itemSize.x + BETWEEN_ITEM_MARGIN;
        }

        Gdx.gl.glDisable(GL30.GL_BLEND);
    }

    /**
     * Set if the control is hided.
     * @param hided If the control is hided.
     */
    public void setHided(boolean hided) {
        this.hided = hided;
    }

    /**
     * Animate the control.
     */
    public void animate() {
        beginAnimation();
        DoubleAnimation da = new DoubleAnimation(0, 1, 750);
        setDuringAnimationWidth(getWidth() / 5);
        setDuringAnimationBackgroundOpacity(1);
        da.setRunningAction(() -> setDuringAnimationHeight((float)(getHeight() * da.getActual())));
        am.addAnAnimation("InventoryShowerHeightAnimation", da);
        DoubleAnimation da2 = new DoubleAnimation(0, 1, 750);
        da2.setEndingAction(this::finishAnimation);
        da2.setRunningAction(() ->  {
            setDuringAnimationWidth((float)(getWidth() * da2.getActual()));
            setDuringAnimationItemWidth((float)(itemWidth * da2.getActual()));
            setDuringAnimationForegroundOpacity(da2.getActual());
        });
        da.setEndingAction(() -> am.addAnAnimation("InventoryShowerWidthAnimation", da2));
    }

    @Override
    public void handleInput() {
        for (int i = Input.Keys.NUM_1; i <= Input.Keys.NUM_9; i++) {
            if (gim.isKey(i, KeyStatus.Pressed)) {
                int j = i - Input.Keys.NUM_1;
                if (j >= inventoryItemList.size())
                    return;
                if (selectedItem != null)
                    selectedItem.unselect();
                selectedItem = inventoryItemList.get(j);
                selectedItem.select();
            }
        }
    }

    @Override
    public void dispose() {
        sr.dispose();
    }

    /**
     * @return Control's horizontal size.
     */
    public int getWidth() {
        return itemWidth * inventory.size() + (inventory.size() + 1) * BETWEEN_ITEM_MARGIN;
    }
    /**
     * @return Control's vertical size.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Begin an animation.
     */
    public void beginAnimation() {
        isBeingAnimated = true;
        for (InventoryItem ii : inventoryItemList) {
            ii.beginAnimation();
        }
    }

    /**
     * Stop the animation.
     */
    public void finishAnimation() {
        isBeingAnimated = false;
        for (InventoryItem ii : inventoryItemList) {
            ii.finishAnimation();
        }
    }

    /**
     * Set background's opacity while animating.
     * @param duringAnimationBackgroundOpacity Background's opacity while animating.
     */
    public void setDuringAnimationBackgroundOpacity(double duringAnimationBackgroundOpacity) {
        this.duringAnimationBackgroundOpacity = duringAnimationBackgroundOpacity;
    }

    /**
     * Set foreground's opacity while animating.
     * @param duringAnimationForegroundOpacity Foreground's opacity while animating.
     */
    public void setDuringAnimationForegroundOpacity(double duringAnimationForegroundOpacity) {
        this.duringAnimationForegroundOpacity = duringAnimationForegroundOpacity;

        for (InventoryItem ii : inventoryItemList) {
            ii.setDuringAnimationForegroundOpacity((float)duringAnimationForegroundOpacity);
        }
    }

    /**
     * Set the control's width while animating.
     * @param duringAnimationWidth The control's width while animating.
     */
    public void setDuringAnimationWidth(float duringAnimationWidth) {
        this.duringAnimationWidth = duringAnimationWidth;
    }

    /**
     * Set the control's height while animating.
     * @param duringAnimationHeight The control's height while animating.
     */
    public void setDuringAnimationHeight(float duringAnimationHeight) {
        this.duringAnimationHeight = duringAnimationHeight;
    }

    /**
     * Set the width of an inventory's item.
     * @param duringAnimationItemWidth The width of an inventory's item.
     */
    public void setDuringAnimationItemWidth(float duringAnimationItemWidth) {
        this.duringAnimationItemWidth = duringAnimationItemWidth;
    }
}
