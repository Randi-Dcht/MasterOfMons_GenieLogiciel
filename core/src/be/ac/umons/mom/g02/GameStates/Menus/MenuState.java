package be.ac.umons.mom.g02.GameStates.Menus;

import be.ac.umons.mom.g02.Enums.KeyStatus;
import be.ac.umons.mom.g02.GameStates.GameState;
import be.ac.umons.mom.g02.GraphicalObjects.Controls.Button;
import be.ac.umons.mom.g02.GraphicalObjects.Controls.Control;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.ButtonMenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.MenuItem;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static be.ac.umons.mom.g02.MasterOfMonsGame.HEIGHT;

/**
 * Abstract class representing a menu.
 * @author Guillaume Cardoen
 */
public abstract class MenuState extends GameState {

    /**
     * Allows to draw.
     */
    protected SpriteBatch sb;

    /**
     * Index of the selected item.
     */
    protected Point selectedItem = new Point(0,0);

    /**
     * The items to show.
     */
    protected MenuItem[] menuItems;
    /**
     * A list of all <code>Button</code>
     */
    protected List<List<Button>> buttons;

    /**
     * A list of all <code>Control</code>
     */
    protected List<Control> controls;

    /**
     * If the background must be transparent or not.
     */
    protected boolean transparentBackground;

    /***
     * Allow to draw shape.
     */
    protected ShapeRenderer sr;

    /**
     * If the state must be removed when escape is pressed.
     */
    protected boolean handleEscape;

    protected int mouseScrolled = 0;

    protected int maxScrolled = 0;

    /**
     * Create a new menu
     * @param gs Game's graphical settings
     */
    protected MenuState(GraphicalSettings gs) {
        super(gs);
    }

    /**
     * The default constructor. USE IT ONLY FOR TEST
     */
    protected MenuState(){}

    @Override
    public void init() {
        super.init();
        controls = new ArrayList<>();
        buttons = new ArrayList<>();
        sb = new SpriteBatch();
		sr = new ShapeRenderer();
		sr.setAutoShapeType(true);
		handleEscape = true;
    }

    @Override
    public void update(float dt) {
        handleInput();
        for (Control c : controls)
            c.update(dt);
    }

    @Override
    public void draw() {
        if (transparentBackground) {
            Gdx.gl.glEnable(GL30.GL_BLEND);
            Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.setColor(gs.getTransparentBackgroundColor());
            sr.rect(0, 0, MasterOfMonsGame.WIDTH, MasterOfMonsGame.HEIGHT);
            sr.end();
            Gdx.gl.glDisable(GL30.GL_BLEND);
        } else {
            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.setColor(gs.getBackgroundColor());
            sr.rect(0, 0, MasterOfMonsGame.WIDTH, MasterOfMonsGame.HEIGHT);
            sr.end();
        }

        int alreadyUsed = (int)(.1 * HEIGHT);
        int width = (int) leftMargin;
        for (int i = 0; i < menuItems.length; i++) {
            MenuItem menuItem = menuItems[i];

            if (menuItem.getSize().y == -2) {
                int ySize = (int) (MasterOfMonsGame.HEIGHT - alreadyUsed + 2 * topMargin);
                if (ySize < gs.getNormalFont().getLineHeight())
                    ySize = (int)Math.floor(gs.getNormalFont().getLineHeight());
                menuItem.getSize().y = ySize;
            }

            menuItem.draw(sb, new Point(width, (int)(HEIGHT - alreadyUsed + mouseScrolled - topMargin)));

            if (i == menuItems.length - 1 || (menuItems.length > i + 1 && menuItems[i+1].getDrawUnderPreviousOne())) {
                alreadyUsed += menuItem.getSize().y + topMargin;
                width = (int) leftMargin;
            }
            else
                width += menuItem.getSize().x + leftMargin;
        }
        maxScrolled = alreadyUsed - HEIGHT;
        if (maxScrolled < 0)
            maxScrolled = 0;
    }

    @Override
    public void handleInput() {
        if (handleEscape && gim.isKey(Input.Keys.ESCAPE, KeyStatus.Pressed))
            gsm.removeFirstState();
        if (! buttons.isEmpty()) {
            if (gim.isKey(Input.Keys.ENTER, KeyStatus.Pressed)) {
                Runnable run = buttons.get(selectedItem.x).get(selectedItem.y).getOnClick();
                if (run != null)
                    run.run();
            }

            Point oldSelectedItem = new Point(selectedItem.x, selectedItem.y);
            if (gim.isKey(Input.Keys.DOWN, KeyStatus.Pressed))
                selectedItem.x++;
            if (gim.isKey(Input.Keys.RIGHT, KeyStatus.Pressed))
                selectedItem.y++;
            if (gim.isKey(Input.Keys.UP, KeyStatus.Pressed))
                selectedItem.x--;
            if (gim.isKey(Input.Keys.LEFT, KeyStatus.Pressed))
                selectedItem.y--;
            checkSelectedItem();
            if (! oldSelectedItem.equals(selectedItem)) {
                buttons.get(oldSelectedItem.x).get(oldSelectedItem.y).setSelected(false);
                checkSelectedItemDrawingPosition();
                buttons.get(selectedItem.x).get(selectedItem.y).setSelected(true);
            }
        }

        for (Control c : controls)
            c.handleInput();

        mouseScrolled += gim.getScrolledAmount() * 20;
        if (mouseScrolled < 0)
            mouseScrolled = 0;
        else if (mouseScrolled > maxScrolled)
            mouseScrolled = maxScrolled;
    }

    /**
     * Check if the selected item is valid (make it valid if necessary)
     */
    protected void checkSelectedItem() {
        if (selectedItem.x >= buttons.size())
            selectedItem.x = 0;
        if (selectedItem.x < 0)
            selectedItem.x = buttons.size() - 1;
        if (selectedItem.y >= buttons.get(selectedItem.x).size())
            selectedItem.y = 0;
        if (selectedItem.y < 0)
            selectedItem.y = buttons.get(selectedItem.x).size() - 1;
    }

    protected void checkSelectedItemDrawingPosition() {
        int selectedItemY = buttons.get(selectedItem.x).get(selectedItem.y).getY();
        if (selectedItemY < 0)
            mouseScrolled -= selectedItemY - topMargin;
        if (selectedItemY > HEIGHT)
            mouseScrolled -= selectedItemY - HEIGHT + topMargin;
    }

    /**
     * Set the items to show and create the controls if necessary.
     * @param menuItems The items to show
     */
    protected void setMenuItems(MenuItem[] menuItems) {
        setMenuItems(menuItems, true);
    }

    /**
     * Set the items to show and create the controls if necessary.
     * @param menuItems The items to show
     * @param selectFirstOne If the first item needs to be selected.
     */
    protected void setMenuItems(MenuItem[] menuItems, boolean selectFirstOne) {
        if (this.menuItems != null) {
            for (MenuItem mi : this.menuItems)
                if (mi.getControl() != null)
                    mi.getControl().dispose();
            buttons.clear();
            controls.clear();
        }
        for (MenuItem mi : menuItems) {
            if (mi instanceof ButtonMenuItem) {
                ButtonMenuItem bmi = (ButtonMenuItem)mi;
                if (bmi.getDrawUnderPreviousOne()) {
                    ArrayList<Button> l = new ArrayList<>();
                    l.add(bmi.getControl());
                    buttons.add(l);
                } else {
                    if (buttons.isEmpty())
                        buttons.add(new ArrayList<>());
                    buttons.get(buttons.size() - 1).add(bmi.getControl());
                }
            }
            Control c = mi.getControl();
            if (c != null)
                controls.add(c);
        }
        if (! buttons.isEmpty() && selectFirstOne) {
            buttons.get(0).get(0).setSelected(true);
            selectedItem = new Point(0,0);
        }
        this.menuItems = menuItems;
    }

    @Override
    public void dispose() {
        sb.dispose();
    }
}
