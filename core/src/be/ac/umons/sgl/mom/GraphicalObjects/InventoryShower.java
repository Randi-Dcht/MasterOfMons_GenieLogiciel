package be.ac.umons.sgl.mom.GraphicalObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class InventoryShower {
    public static int BETWEEN_ITEM_MARGIN = 7;
    public static int BOTTOM_MARGIN = 7;
    protected static float BACKGROUND_RECTANGLE_OPACITY = .5f;
    protected static float FOREGROUND_RECTANGLE_OPACITY = .8f;

    protected int beginX, height, tileWidth, tileHeight;
    protected int itemWidth, itemHeight;

    private ShapeRenderer sr;
    private Batch batch;
    protected int itemsToShow = 3;
    protected boolean isBeingAnimated = false;
    protected float duringAnimationHeight, duringAnimationWidth;
    protected double duringAnimationBackgroundOpacity, duringAnimationForegroundOpacity;

    public InventoryShower(Batch batch, int centerX, int height, int tileWidth, int tileHeight) {
        itemWidth = tileWidth;
        itemHeight = tileWidth;
        this.beginX = centerX - (itemWidth * itemsToShow + BETWEEN_ITEM_MARGIN * (itemsToShow + 2)) / 2;
        this.height = height;
        this.tileHeight = tileHeight;
        this.tileWidth = tileWidth;
        this.batch = batch;
        init();
    }

    public void init() {
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
            sr.rect(beginX, BOTTOM_MARGIN, getMaximumWidth(), height);
        }
        int tmpBeginX = beginX + BETWEEN_ITEM_MARGIN;
        for (int i = 0; i < itemsToShow; i++) {
            if (isBeingAnimated) {
                sr.setColor(21f / 255, 21f / 255, 21f / 255, (float)(duringAnimationForegroundOpacity * FOREGROUND_RECTANGLE_OPACITY));
                sr.rect(tmpBeginX, BOTTOM_MARGIN, itemWidth, height);

            }
            else {
                sr.setColor(21f / 255, 21f / 255, 21f / 255, FOREGROUND_RECTANGLE_OPACITY);
                sr.rect(tmpBeginX, BOTTOM_MARGIN, itemWidth, height);
            }
            tmpBeginX += itemWidth + BETWEEN_ITEM_MARGIN;
        }
        sr.end();

        Gdx.gl.glDisable(GL30.GL_BLEND);
    }

    public int getMaximumWidth() {
        return itemWidth * itemsToShow + (itemsToShow + 1) * BETWEEN_ITEM_MARGIN;
    }
    public int getMaximumHeight() {
        return height;
    }

    public void beginAnimation() {
        isBeingAnimated = true;
    }

    public void finishAnimation() {
        isBeingAnimated = false;
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
