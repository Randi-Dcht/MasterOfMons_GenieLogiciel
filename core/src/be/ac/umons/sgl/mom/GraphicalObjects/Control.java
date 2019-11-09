package be.ac.umons.sgl.mom.GraphicalObjects;

import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.graphics.g2d.Batch;

public abstract class Control {
    protected int x, y, width, height;
    protected GameInputManager gim;
    protected GraphicalSettings gs;
    protected int leftMargin;

    protected Control (GameInputManager gim, GraphicalSettings gs) {
        this.gim = gim;
        this.gs = gs;
        leftMargin = MasterOfMonsGame.WIDTH / 100;
    }

    public void draw(Batch batch, int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}
