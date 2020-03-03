package be.ac.umons.mom.g02.GameStates.Menus;

import be.ac.umons.mom.g02.Enums.KeyStatus;
import be.ac.umons.mom.g02.GameStates.GameState;
import be.ac.umons.mom.g02.GraphicalObjects.Controls.Button;
import be.ac.umons.mom.g02.GraphicalObjects.Controls.*;
import be.ac.umons.mom.g02.Helpers.StringHelper;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
     * A list of all <code>Button</code> used in this state.
     */
    protected List<List<be.ac.umons.mom.g02.GraphicalObjects.Controls.Button>> buttons;

    /**
     * A list of all <code>TextBox</code> used in this state.
     */
    protected List<List<TextBox>> textBoxes;

    /**
     * A list of all <code>ScrollListChooser</code>
     */
    protected List<List<ScrollListChooser>> scrollListChoosers;

    protected List<List<ColorSelector>> colorSelectors;

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

    protected double topMargin = .1;

    protected int mouseScrolled = 0;

    protected int maxScrolled = 0;

    /**
     * Create a new menu
     * @param gsm Game's state manager
     * @param gim Game's input manager
     * @param gs Game's graphical settings
     */
    protected MenuState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }

    /**
     * The default constructor. USE IT ONLY FOR TEST
     */
    protected MenuState(){}

    @Override
    public void init() {
        super.init();
        buttons = new ArrayList<>();
        textBoxes = new ArrayList<>();
        scrollListChoosers = new ArrayList<>();
        colorSelectors = new ArrayList<>();
        sb = new SpriteBatch();
		sr = new ShapeRenderer();
		sr.setAutoShapeType(true);
		handleEscape = true;
    }

    @Override
    public void update(float dt) {
        handleInput();
        for (List<TextBox> tbs : textBoxes)
            for (TextBox tb : tbs)
                tb.update(dt);
    }

    @Override
    public void draw() { // TODO : Clean this method a bit
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

        int alreadyUsed = (int)(topMargin * HEIGHT);

        BitmapFont font;
        int width = (int) leftMargin;
        for (int i = 0; i < menuItems.length; i++) {
            MenuItem menuItem = menuItems[i];
            GlyphLayout layout = new GlyphLayout();
            if (menuItem.mit.equals(MenuItemType.Title))
                font = gs.getTitleFont();
            else
                font = gs.getNormalFont();
            layout.setText(font, menuItem.header);
            Point size = menuItem.size;
            if (size.x == -1)
                size.x = (int) (layout.width + 2 * leftMargin);
            else if (size.x == -2)
                size.x = (int) (MasterOfMonsGame.WIDTH - 2 * leftMargin);
            if (size.y == -1)
                size.y = (int) (font.getLineHeight() * menuItem.lineNumber + 2 * topMargin);
            else if (size.y == -2)
                size.y = (int) (MasterOfMonsGame.HEIGHT - alreadyUsed + 2 * topMargin);

            menuItem.draw(sb, new Point(width, (int)(HEIGHT - alreadyUsed - (menuItem.control != null ? font.getLineHeight() + 4 * topMargin : topMargin) + mouseScrolled)), size);
            if (i == menuItems.length - 1 || (menuItems.length > i + 1 && menuItems[i+1].drawUnderPreviousOne)) {
                alreadyUsed += size.y + topMargin;
                width = (int) leftMargin;
            }
            else
                width += size.x + leftMargin;
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
            if (gim.isKey(Input.Keys.ENTER, KeyStatus.Pressed))
                buttons.get(selectedItem.x).get(selectedItem.y).getOnClick().run();

            buttons.get(selectedItem.x).get(selectedItem.y).setSelected(false);
            if (gim.isKey(Input.Keys.DOWN, KeyStatus.Pressed))
                selectedItem.x++;
            if (gim.isKey(Input.Keys.RIGHT, KeyStatus.Pressed))
                selectedItem.y++;
            if (gim.isKey(Input.Keys.UP, KeyStatus.Pressed))
                selectedItem.x--;
            if (gim.isKey(Input.Keys.LEFT, KeyStatus.Pressed))
                selectedItem.y--;
            checkSelectedItem();
            buttons.get(selectedItem.x).get(selectedItem.y).setSelected(true);
        }

        for (List<be.ac.umons.mom.g02.GraphicalObjects.Controls.Button> lb : buttons)
            for (be.ac.umons.mom.g02.GraphicalObjects.Controls.Button b : lb)
                b.handleInput();
        for (List<TextBox> ltb : textBoxes)
            for (TextBox tb : ltb)
                tb.handleInput();
        for (List<ScrollListChooser> lslc : scrollListChoosers)
            for (ScrollListChooser slc : lslc)
                slc.handleInput();
        for (List<ColorSelector> csl : colorSelectors)
            for (ColorSelector cs : csl)
                cs.handleInput();

        mouseScrolled += gim.getScrolledAmount() * 20;
        if (mouseScrolled < 0)
            mouseScrolled = 0;
        else if (mouseScrolled > maxScrolled)
            mouseScrolled = maxScrolled;
    }

    /**
     * Check if the selected item is valid (make it valid if necessary)
     */
    public void checkSelectedItem() {
        if (selectedItem.x >= buttons.size())
            selectedItem.x = 0;
        if (selectedItem.x < 0)
            selectedItem.x = buttons.size() - 1;
        if (selectedItem.y >= buttons.get(selectedItem.x).size())
            selectedItem.y = 0;
        if (selectedItem.y < 0)
            selectedItem.y = buttons.get(selectedItem.x).size() - 1;

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
        for (MenuItem mi : menuItems) {
            Control c = null;
            switch (mi.mit) {
                case Button:
                    be.ac.umons.mom.g02.GraphicalObjects.Controls.Button b = createMenuItemControl(Button.class, mi, buttons);
                    b.setText(mi.header);
                    b.setOnClick(mi.toDoIfExecuted);
                    c = b;
                    break;
                case TextBox:
                case NumberTextBox:
                    TextBox tb = createMenuItemControl(TextBox.class, mi, textBoxes);
                    if (mi.mit.equals(MenuItemType.NumberTextBox))
                        tb.setAcceptOnlyNumbers(true);
                    c = tb;
                    break;
                case ScrollListChooser:
                    c = createMenuItemControl(ScrollListChooser.class, mi, scrollListChoosers);
                    break;
                case ColorChooser:
                    c = createMenuItemControl(ColorSelector.class, mi, colorSelectors);
                default:
                    break;
            }
            mi.control = c;
        }
        if (! buttons.isEmpty() && selectFirstOne) {
            buttons.get(0).get(0).setSelected(true);
            selectedItem = new Point(0,0);
        }
        this.menuItems = menuItems;
    }

    /**
     * Create a new control for the given menu's item.
     * @param itemClass The class that the control must be.
     * @param mi The menu's item.
     * @param list The list in which the control must be put when created.
     * @param <T> The type of class the control must be.
     * @return The created control.
     */
    protected<T extends Control> T createMenuItemControl(Class<T> itemClass, MenuItem mi, List<List<T>> list) {
        T t;
        try {
            Constructor<T> con = itemClass.getConstructor(GameInputManager.class, GraphicalSettings.class);
            t = (T) con.newInstance(gim, gs);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
        if (mi.drawUnderPreviousOne) {
            ArrayList<T> l = new ArrayList<>();
            l.add(t);
            list.add(l);
        } else {
            if (list.isEmpty())
                list.add(new ArrayList<>());
            list.get(list.size() - 1).add(t);
        }
        return t;
    }

    @Override
    public void dispose() {
        sb.dispose();
    }

    /**
     * An item.
     */
    public class MenuItem {
        /**
         * The name of the item.
         */
        private String header;
        public int lineNumber;
        /**
         * The type of the item.
         */
        public MenuItemType mit;

        /**
         * The associated control for this item.
         */
        public Control control;

        /**
         * The action to do if the item is selected.
         */
        public Runnable toDoIfExecuted;

        /**
         * The item's id.
         */
        public String id;


        /**
         * The item's size. (-1 = automatic) (-2 = all available space with margin)
         */
        public Point size = new Point(-2,-1);
        /**
         * If this item must be drawn under the previous one (=true) or next to it (=false).
         */
        public boolean drawUnderPreviousOne = true;

        /**
         * Construct a new item.
         * @param header The item's name
         */
        public MenuItem(String header) {
            this(header, MenuItemType.Text, "", null);
        }
        /**
         * Construct a new item.
         * @param header The item's name
         * @param toDoIfExecuted The action to do if the item is selected.
         */
        public MenuItem(String header, Runnable toDoIfExecuted) {
            this(header, MenuItemType.Button, "", toDoIfExecuted);
        }
        /**
         * Construct a new item.
         * @param header The item's name
         * @param mit The item's type.
         * @param toDoIfExecuted The action to do if the item is selected.
         */
        public MenuItem(String header, MenuItemType mit, Runnable toDoIfExecuted) {
            this(header, mit, "", toDoIfExecuted);
        }
        /**
         * Construct a new item.
         * @param header The item's name
         * @param mit The item's type.
         * @param toDoIfExecuted The action to do if the item is selected.
         * @param drawUnderPreviousOne If this item must be drawn under the previous one (=true) or next to it (=false).
         */
        public MenuItem(String header, MenuItemType mit, Runnable toDoIfExecuted, boolean drawUnderPreviousOne) {
            this(header, mit, "", toDoIfExecuted);
            this.drawUnderPreviousOne = drawUnderPreviousOne;
        }

        /**
         * Construct a new item.
         * @param header The item's name
         * @param mit The item's type.
         * @param id The item's id.
         */
        public MenuItem(String header, MenuItemType mit, String id) {
            this(header, mit, id, null);
        }
        /**
         * Construct a new item.
         * @param header The item's name
         * @param mit The item's type.
         */
        public MenuItem(String header, MenuItemType mit) {
            this(header, mit, "", null);
        }
        /**
         * Construct a new item.
         * @param header The item's name
         * @param mit The item's type.
         * @param id The item's id
         * @param toDoIfExecuted The action to do if the item is selected.
         */
        public MenuItem(String header, MenuItemType mit, String id, Runnable toDoIfExecuted) {
            this.mit = mit;
            setHeader(header);
            this.toDoIfExecuted = toDoIfExecuted;
            this.id = id;
        }

        /**
         * Draw the control associated with this item with the given parameters.
         * @param batch The batch where the control must be drawn.
         * @param pos The control's position.
         * @param size The control's size.
         */
        public void draw(Batch batch, Point pos, Point size) {
            switch (mit) {
                case Title:
                    batch.begin();
                    gs.getTitleFont().draw(batch, header, pos.x, pos.y);
                    batch.end();
                    break;
                case Text:
                    batch.begin();
                    gs.getNormalFont().draw(batch, header, pos.x, pos.y);
                    batch.end();
                    break;
                case NumberTextBox:
                case TextBox:
                case ScrollListChooser:
                case ColorChooser:
                    GlyphLayout gl = new GlyphLayout();
                    gl.setText(gs.getNormalFont(), header);
                    batch.begin();
                    gs.getNormalFont().draw(batch, header, pos.x, pos.y + (int)(gs.getNormalFont().getLineHeight() + 2 * topMargin));
                    batch.end();
                    pos.x += gl.width + leftMargin;
                    size = new Point((int)(size.x - gl.width - leftMargin), size.y);
                case Button:
                    control.draw(batch, pos, size);
                    break;
            }
        }

        public void setHeader(String header) {
            if (gs.getNormalFont() != null) { // Test case
                this.header = StringHelper.adaptTextToWidth(mit.equals(MenuItemType.Title) ? gs.getTitleFont() : gs.getNormalFont(), header, (int)(MasterOfMonsGame.WIDTH - 2 * leftMargin));
                lineNumber = this.header.split("\n").length;
            }
        }
    }

    /**
     * Enumerations of all possible type for an item.
     */
    public enum MenuItemType {
        Title,
        Text,
        Button,
        TextBox,
        NumberTextBox,
        ScrollListChooser,
        ColorChooser
    }
}
