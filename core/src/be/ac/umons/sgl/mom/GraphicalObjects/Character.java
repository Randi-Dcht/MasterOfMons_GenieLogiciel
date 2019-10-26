package be.ac.umons.sgl.mom.GraphicalObjects;

import be.ac.umons.sgl.mom.Enums.Orientation;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Character {
    private Texture playerTop, playerBottom, playerLeft, playerRight;
    private int x, y, width, height;
    private int xT, yT;
    private Orientation orientation = Orientation.Top;

    public Character(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        playerTop = new Texture(Gdx.files.internal("Pictures/arrowTop.png"));
        playerBottom = new Texture(Gdx.files.internal("Pictures/arrowBottom.png"));
        playerLeft = new Texture(Gdx.files.internal("Pictures/arrowLeft.png"));
        playerRight = new Texture(Gdx.files.internal("Pictures/arrowRight.png"));
    }

    protected Texture getTexture() {
        switch (orientation) {
            case Top:
                return playerTop;
            case Bottom:
                return playerBottom;
            case Left:
                return playerLeft;
            case Right:
                return playerRight;
        }
        return playerTop;
    }

    public void draw(Batch batch) {
        batch.begin();
        batch.draw(getTexture(), x + xT, y + yT, width, height);
        batch.end();
    }

    public void translate(int x, int y) {
        xT += x;
        yT += y;
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
        return width;
    }

    public int getHeight() {
        return height;
    }
}
