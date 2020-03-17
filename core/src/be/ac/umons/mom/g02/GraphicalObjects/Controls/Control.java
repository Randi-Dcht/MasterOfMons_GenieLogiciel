package be.ac.umons.mom.g02.GraphicalObjects.Controls;

import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.awt.*;

/**
 * Represent a part of the user interface which interact directly with the user.
 * @author Guillaume Cardoen
 */
public abstract class Control {
    /**
     * Where the control needs to be drawn.
     */
    protected int x, y;
    /**
     * The control size.
     */
    protected int width, height;
    /**
     * The game's input manager.
     */
    protected GameInputManager gim;
    /**
     * The game's graphical settings.
     */
    protected GraphicalSettings gs;
    /**
     * The horizontal margin
     */
    protected int leftMargin;
    /**
     * The vertical margin
     */
    protected int topMargin;

    /**
     * @param gim The game's input manager
     * @param gs The game's graphical settings
     */
    protected Control (GameInputManager gim, GraphicalSettings gs) {
        this.gim = gim;
        this.gs = gs;
        leftMargin = MasterOfMonsGame.WIDTH / 100;
        topMargin = MasterOfMonsGame.HEIGHT / 100;
    }
    /**
     * Default constructor. USE IT ONLY FOR TEST.
     */
    protected Control() {}

    /**
     * Draw the control on <code>batch</code> with the given parameters.
     * @param batch The batch where to draw the control.
     * @param pos The control's position.
     * @param size The control's size.
     */
    public void draw(Batch batch, Point pos, Point size) {
        this.x = pos.x;
        this.y = pos.y;
        this.width = size.x;
        this.height = size.y;
    }

    public void update(double dt) {}

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * Execute an action depending on which key was pressed.
     */
    public abstract void handleInput();

    /**
     * Dispose all resources reserved by this control.
     */
    public abstract void dispose();
}
