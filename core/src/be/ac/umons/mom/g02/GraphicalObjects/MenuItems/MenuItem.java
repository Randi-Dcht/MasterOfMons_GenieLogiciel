package be.ac.umons.mom.g02.GraphicalObjects.MenuItems;

import be.ac.umons.mom.g02.GraphicalObjects.Controls.Control;
import be.ac.umons.mom.g02.Helpers.StringHelper;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class MenuItem<T extends Control> {
    /**
     * The name of the item.
     */
    protected String header;
    protected int lineNumber;

    protected GraphicalSettings gs;
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

    protected double topMargin;
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

    protected void drawNextToHeader(Batch batch, Point pos) {
        GlyphLayout gl = new GlyphLayout();
        gl.setText(gs.getNormalFont(), header);
        batch.begin();
        gs.getNormalFont().draw(batch, header, pos.x, pos.y + (int)(gs.getNormalFont().getLineHeight()));
        batch.end();
        pos.x += gl.width + leftMargin;
        drawIfNonNull(batch, pos, new Point((int)(size.x - gl.width - leftMargin), size.y));
    }

    protected void drawIfNonNull(Batch batch, Point pos, Point size) {
        if (control != null)
            control.draw(batch, pos, size);
    }

    public void setHeader(String header) {
        if (gs.getNormalFont() != null) { // Test case
            this.header = StringHelper.adaptTextToWidth(gs.getNormalFont(), header, (int)(MasterOfMonsGame.WIDTH - 2 * leftMargin));
            lineNumber = this.header.split("\n").length;
        }
    }

    public T getControl() {
        return control;
    }

    public String getId() {
        return id;
    }

    public Point getSize() {
        return size;
    }

    public boolean getDrawUnderPreviousOne() {
        return drawUnderPreviousOne;
    }
}
