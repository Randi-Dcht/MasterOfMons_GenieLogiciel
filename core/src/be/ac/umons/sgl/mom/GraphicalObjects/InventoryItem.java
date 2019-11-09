package be.ac.umons.sgl.mom.GraphicalObjects;

import be.ac.umons.sgl.mom.Enums.GameObjects;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class InventoryItem {
    protected static float FOREGROUND_RECTANGLE_OPACITY = .8f;

    protected GameObjects go;
    protected GraphicalSettings gs;
    protected ShapeRenderer sr;
    protected boolean isBeingAnimated;
    protected float duringAnimationForegroundOpacity;

    public InventoryItem(GraphicalSettings gs, GameObjects go) {
        this.go = go;
        this.gs = gs;
        sr = new ShapeRenderer();
    }

    public void draw(Batch batch, int x, int y, int width, int height) {
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        if (isBeingAnimated)
            sr.setColor(21f / 255, 21f / 255, 21f / 255, duringAnimationForegroundOpacity * FOREGROUND_RECTANGLE_OPACITY);
        else
            sr.setColor(21f / 255, 21f / 255, 21f / 255, FOREGROUND_RECTANGLE_OPACITY);
        sr.rect(x, y, width, height);
        sr.end();

        if (gs.getAssetManager().contains("Pictures/Objects/" + go.toString() + ".png")) {
            Texture t = gs.getAssetManager().get("Pictures/Objects/" + go.toString() + ".png");
            batch.begin();
            Color c = batch.getColor();
            batch.setColor(c.r, c.g, c.b, isBeingAnimated ? duringAnimationForegroundOpacity : 1);
            batch.draw(t, x, y, width, height);
            batch.end();
        }
        Gdx.gl.glDisable(GL30.GL_BLEND);
    }

    public void setDuringAnimationForegroundOpacity(float duringAnimationForegroundOpacity) {
        this.duringAnimationForegroundOpacity = duringAnimationForegroundOpacity;
    }

    public float getDuringAnimationForegroundOpacity() {
        return duringAnimationForegroundOpacity;
    }

    public void beginAnimation() {
        isBeingAnimated = true;
    }

    public void finishAnimation() {
        isBeingAnimated = false;
    }
}
