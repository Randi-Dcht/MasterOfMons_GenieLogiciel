package be.ac.umons.mom.g02.GraphicalObjects.MenuItems;

import be.ac.umons.mom.g02.GraphicalObjects.Controls.Control;
import be.ac.umons.mom.g02.Helpers.StringHelper;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import java.awt.*;

public abstract class MenuItem<T extends Control> {
    /**
     * The name of the item.
     */
    protected String header;
    /**
     * The number of line to show for this item.
     */
    protected int lineNumber;
    /**
     * The graphical settings to use.
     */
    protected GraphicalSettings gs;
    /**
     * The GameInputManager of the game
     */
    protected GameInputManager gim;
    /**
     * If this item must be drawn under the previous one (=true) or next to it (=false).
     */
    protected boolean drawUnderPreviousOne = true;

    /**
     * The associated control for this item.
     */
    protected T control;

    /**
     * The item's id.
     */
    protected String id;


    /**
     * The item's size. (-1 = automatic) (-2 = all available space with margin)
     */
    protected Point size = new Point(-2,-1);
    /**
     * The vertical margin
     */
    protected double topMargin;
    /**
     * The horizontal margin
     */
    protected double leftMargin;

    /**
     * Construct a new item.
     * @param gim The game's input manager
     * @param header The item's name
     */
    public MenuItem(GameInputManager gim, GraphicalSettings gs, String header) {
        this(gim, gs, header, "");
    }

    /**
     * Construct a new item.
     * @param gim The game's input manager
     * @param id The item's id.
     * @param header The item's name
     */
    public MenuItem(GameInputManager gim, GraphicalSettings gs, String header, String id) {
        this.gs = gs;
        setHeader(header);
        this.id = id;
        this.gim = gim;

        topMargin = MasterOfMonsGame.HEIGHT / 100;
        leftMargin = MasterOfMonsGame.WIDTH / 200;

        GlyphLayout layout = new GlyphLayout();
        layout.setText(gs.getNormalFont(), header);
        if (size.x == -1)
            size.x = (int) (layout.width + 2 * leftMargin);
        else if (size.x == -2)
            size.x = (int) (MasterOfMonsGame.WIDTH - 2 * leftMargin);
        if (size.y == -1)
            size.y = (int) (gs.getNormalFont().getLineHeight() * lineNumber + 2 * topMargin);
    }

    /**
     * Draw the control associated with this item with the given parameters.
     * @param batch The batch where the control must be drawn.
     * @param pos The control's position.
     */
    public void draw(Batch batch, Point pos) {
        GlyphLayout layout = new GlyphLayout();
        layout.setText(gs.getNormalFont(), header);
        if (size.x == -1)
            size.x = (int) (layout.width + 2 * leftMargin);
        else if (size.x == -2)
            size.x = (int) (MasterOfMonsGame.WIDTH - 2 * leftMargin);
        if (size.y == -1)
            size.y = (int) (gs.getNormalFont().getLineHeight() * lineNumber + 2 * topMargin);
    }

    /**
     * Draw the control next to the header.
     * @param batch Where to draw it.
     * @param pos The position of the header and the control.
     */
    protected void drawNextToHeader(Batch batch, Point pos) {
        GlyphLayout gl = new GlyphLayout();
        gl.setText(gs.getNormalFont(), header);
        batch.begin();
        gs.getNormalFont().draw(batch, header, pos.x, pos.y + (int)(gs.getNormalFont().getLineHeight()));
        batch.end();
        pos.x += gl.width + leftMargin;
        drawIfNotNull(batch, pos, new Point((int)(size.x - gl.width - leftMargin), size.y));
    }

    /**
     * Draw the control if it's non null.
     * @param batch Where to draw it
     * @param pos The position of the control.
     * @param size The size of the control.
     */
    protected void drawIfNotNull(Batch batch, Point pos, Point size) {
        if (control != null)
            control.draw(batch, pos, size);
    }

    /**
     * Update the control
     * @param dt The time between this call and the previous one
     */
    public void update(double dt) {
        if (control != null)
            control.update(dt);
    }

    /**
     * Check for any input from the user and handle it if necessary
     */
    public void handleInput() {
        if (control != null)
            control.handleInput();
    }

    /**
     * @param header The header of this item.
     */
    public void setHeader(String header) {
        if (gs.getNormalFont() != null) { // Test case
            this.header = StringHelper.adaptTextToWidth(gs.getNormalFont(), header, (int)(MasterOfMonsGame.WIDTH - 2 * leftMargin));
            lineNumber = this.header.split("\n").length;
        }
    }

    /**
     * @return The control that this item use.
     */
    public T getControl() {
        return control;
    }

    /**
     * @return The ID associated with this item.
     */
    public String getId() {
        return id;
    }

    /**
     * @return The size of this item
     */
    public Point getSize() {
        return size;
    }

    /**
     * @return If this item must be drawn under the previous one or next to it.
     */
    public boolean getDrawUnderPreviousOne() {
        return drawUnderPreviousOne;
    }
}
