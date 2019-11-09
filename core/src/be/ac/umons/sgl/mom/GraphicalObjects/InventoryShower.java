package be.ac.umons.sgl.mom.GraphicalObjects;

import be.ac.umons.sgl.mom.Enums.GameObjects;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.List;

public class InventoryShower {
    public static int BETWEEN_ITEM_MARGIN = 7;
    public static int BOTTOM_MARGIN = 7;
    protected static float BACKGROUND_RECTANGLE_OPACITY = .5f;

    protected int beginX, height, tileWidth, tileHeight;
    protected int itemWidth, itemHeight;

    private ShapeRenderer sr;
    private Batch batch;
    protected GraphicalSettings gs;
    protected List<GameObjects> inventory;
    protected boolean isBeingAnimated = false;
    protected float duringAnimationHeight, duringAnimationWidth;
    protected double duringAnimationBackgroundOpacity, duringAnimationForegroundOpacity;
    protected Character player;
    protected List<InventoryItem> inventoryItemList;

    public InventoryShower(GraphicalSettings gs, Batch batch, int centerX, int height, int tileWidth, int tileHeight, Character inventoryOf) {
        itemWidth = tileWidth;
        itemHeight = tileWidth;
        player = inventoryOf;
        inventory = inventoryOf.getInventory();
        this.beginX = centerX - (itemWidth * inventory.size() + BETWEEN_ITEM_MARGIN * (inventory.size() + 2)) / 2;
        this.height = height;
        this.tileHeight = tileHeight;
        this.tileWidth = tileWidth;
        this.batch = batch;
        this.gs = gs;
        init();
    }

    public void init() {
        inventoryItemList = new ArrayList<>();
        for (GameObjects go : inventory)
            inventoryItemList.add(new InventoryItem(gs, go));
        sr = new ShapeRenderer();
        sr.setProjectionMatrix(batch.getProjectionMatrix());
        sr.setAutoShapeType(true);
    }

    public void draw() {
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);

        sr.begin(ShapeRenderer.ShapeType.Filled);
        if (isBeingAnimated) {
            sr.setColor(21f / 255, 21f / 255, 21f / 255, (float)duringAnimationBackgroundOpacity * BACKGROUND_RECTANGLE_OPACITY);
            sr.rect(beginX, BOTTOM_MARGIN, duringAnimationWidth, duringAnimationHeight);
        }
        else {
            sr.setColor(21f / 255, 21f / 255, 21f / 255, BACKGROUND_RECTANGLE_OPACITY);
            sr.rect(beginX, BOTTOM_MARGIN, getWidth(), height);
        }
        sr.end();
        int tmpBeginX = beginX + BETWEEN_ITEM_MARGIN;
        for (InventoryItem ii : inventoryItemList) {
            ii.draw(batch, tmpBeginX, BOTTOM_MARGIN, itemWidth, itemHeight);
            tmpBeginX += itemWidth + BETWEEN_ITEM_MARGIN;
        }

        Gdx.gl.glDisable(GL30.GL_BLEND);
    }

    public int getWidth() {
        return itemWidth * inventory.size() + (inventory.size() + 1) * BETWEEN_ITEM_MARGIN;
    }
    public int getHeight() {
        return height;
    }

    public void beginAnimation() {
        isBeingAnimated = true;
        for (InventoryItem ii : inventoryItemList) {
            ii.beginAnimation();
        }
    }

    public void finishAnimation() {
        isBeingAnimated = false;
        for (InventoryItem ii : inventoryItemList) {
            ii.finishAnimation();
        }
    }

    public double getDuringAnimationBackgroundOpacity() {
        return duringAnimationBackgroundOpacity;
    }

    public void setDuringAnimationBackgroundOpacity(double duringAnimationBackgroundOpacity) {
        this.duringAnimationBackgroundOpacity = duringAnimationBackgroundOpacity;
    }

    public double getDuringAnimationForegroundOpacity() {
        return duringAnimationForegroundOpacity;
    }
    public void setDuringAnimationForegroundOpacity(double duringAnimationForegroundOpacity) {
        this.duringAnimationForegroundOpacity = duringAnimationForegroundOpacity;

        for (InventoryItem ii : inventoryItemList) {
            ii.setDuringAnimationForegroundOpacity((float)duringAnimationForegroundOpacity);
        }
    }

    public float getDuringAnimationWidth() {
        return duringAnimationWidth;
    }

    public void setDuringAnimationWidth(float duringAnimationWidth) {
        this.duringAnimationWidth = duringAnimationWidth;
    }

    public float getDuringAnimationHeight() {
        return duringAnimationHeight;
    }

    public void setDuringAnimationHeight(float duringAnimationHeight) {
        this.duringAnimationHeight = duringAnimationHeight;
    }
}
