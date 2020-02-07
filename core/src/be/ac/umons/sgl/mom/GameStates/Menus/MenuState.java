package be.ac.umons.sgl.mom.GameStates.Menus;

import be.ac.umons.sgl.mom.Enums.KeyStatus;
import be.ac.umons.sgl.mom.GameStates.GameState;
import be.ac.umons.sgl.mom.GraphicalObjects.Controls.Button;
import be.ac.umons.sgl.mom.GraphicalObjects.Controls.Control;
import be.ac.umons.sgl.mom.GraphicalObjects.Controls.TextBox;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static be.ac.umons.sgl.mom.MasterOfMonsGame.*;

/**
 * CLasse abstraite représentant un menu du jeu.
 * @author Guillaume Cardoen
 */
public abstract class MenuState extends GameState {

    /**
     * Utilisé afin de dessiner en autre le texte.
     */
    protected SpriteBatch sb;

    /**
     * L'indice de l'élement selectionné.
     */
    protected int selectedItem = 0;

    /**
     * Les différents éléments à montrer à l'écran.
     */
    protected MenuItem[] menuItems;
    /**
     * The selectable elements represented as Button.
     */
    protected List<Button> buttons;

    protected List<TextBox> textBoxes;

    /**
     * La caméra permettant d'afficher le texte et de zoomer au besoin.
     */
    protected OrthographicCamera cam;

    protected boolean transparentBackground;

    /***
     * Allow to draw shape.
     */
    protected ShapeRenderer sr;

    /**
     * Crée un nouveau menu.
     * @param gsm Le GameStateManager du jeu.
     * @param gim Le GameInputManager du jeu.
     * @param gs Les paramètres graphiques à utiliser.
     */
    protected MenuState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }
    protected MenuState(){}

    @Override
    public void init() {
        super.init();
        buttons = new ArrayList<>();
        textBoxes = new ArrayList<>();
        sb = new SpriteBatch();
		cam = new OrthographicCamera(WIDTH, HEIGHT); // Make the camera the same size as the game
		cam.translate(WIDTH / 2, HEIGHT / 2);
		cam.update();
		sr = new ShapeRenderer();
		sr.setAutoShapeType(true);
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void draw() {
        if (transparentBackground) {
            Gdx.gl.glEnable(GL30.GL_BLEND);
            Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.setColor(21f / 255, 21f / 255, 21f / 255, .5f);
            sr.rect(0, 0, MasterOfMonsGame.WIDTH, MasterOfMonsGame.HEIGHT);
            sr.end();
            Gdx.gl.glDisable(GL30.GL_BLEND);
        } else {
            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.setColor(21f / 255, 21f / 255, 21f / 255, 1);
            sr.rect(0, 0, MasterOfMonsGame.WIDTH, MasterOfMonsGame.HEIGHT);
            sr.end();
        }

        int alreadyUsed = (int)(topMargin * HEIGHT);

        sb.setProjectionMatrix(cam.combined);

        GlyphLayout layout;
        BitmapFont font;
        for (MenuItem menuItem : menuItems) {
            layout = new GlyphLayout();
            if (menuItem.mit.equals(MenuItemType.Title))
                font = gs.getTitleFont();
            else
                font = gs.getNormalFont();
            layout.setText(font, menuItem.header);
            menuItem.draw(sb, new Point((int) (.05 * WIDTH), (int)(HEIGHT - alreadyUsed - (menuItem.control != null ? font.getLineHeight() + 2 * topMargin : 0))),
                    new Point((int) (layout.width + 2 * leftMargin),
                            (int) (font.getLineHeight() + 2 * topMargin)));
            alreadyUsed += (int)(font.getLineHeight() + 2 * topMargin) + topMargin;
//            sb.begin();
//            font.setColor(Color.WHITE);
//            layout.setText(font, menuItem.header);
//            if (menuItem.control == null)
//                font.draw(sb, layout, (int) (.05 * WIDTH), HEIGHT - alreadyUsed);
//            sb.end();
//            if (menuItem.control != null) {
//                menuItem.draw(sb, new Point((int) (.05 * WIDTH), (int)(HEIGHT - alreadyUsed - (font.getLineHeight() + 2 * topMargin))),
//                        new Point((int) (layout.width + 2 * leftMargin),
//                                (int) (font.getLineHeight() + 2 * topMargin)));
//            }
//            alreadyUsed += (int)(font.getLineHeight() + 2 * topMargin) + topMargin;
        }

    }

    @Override
    public void handleInput() {
        if (gim.isKey(Input.Keys.ENTER, KeyStatus.Pressed))
            executeSelectedItem();
        if (gim.isKey(Input.Keys.DOWN, KeyStatus.Pressed)) {
            buttons.get(selectedItem).setSelected(false);
            selectedItem++;
            if (selectedItem >= buttons.size())
                selectedItem = 0;
            buttons.get(selectedItem).setSelected(true);
        }
        if (gim.isKey(Input.Keys.UP, KeyStatus.Pressed)) {
            buttons.get(selectedItem).setSelected(false);
            selectedItem--;
            if (selectedItem < 0)
                selectedItem = buttons.size() - 1;
            buttons.get(selectedItem).setSelected(true);
        }
        for (Button b : buttons)
            b.handleInput();
        for (TextBox tb : textBoxes)
            tb.handleInput();
    }

    /***
     * Exécute l'action relié à un des éléments du menu en fonction de celui selectionné par l'utilisateur.
     */
    private void executeSelectedItem() {
        buttons.get(selectedItem).getOnClick().run();
    }

    protected void setMenuItems(MenuItem[] menuItems) {
        for (MenuItem mi : menuItems) {
            Control c = null;
            switch (mi.mit) {
                case Button:
                    Button b = new Button(gim, gs);
                    b.setText(mi.header);
                    b.setOnClick(mi.toDoIfExecuted);
                    buttons.add(b);
                    c = b;
                    break;
                case TextBox:
                    TextBox tb = new TextBox(gim, gs);
                    textBoxes.add(tb);
                    c = tb;
                    break;
                default:
                    break;
            }
            mi.control = c;
        }
        if (! buttons.isEmpty())
            buttons.get(0).setSelected(true);
        this.menuItems = menuItems;
    }

    public int getSelectedItem() {
        return selectedItem;
    }

    @Override
    public void dispose() {
        sb.dispose();
    }

    /***
     * Représente un élément du menu.
     */
    public class MenuItem {
        /**
         * Le nom de l'élément.
         */
        private String header;
        /**
         * Le type de l'élément.
         */
        protected MenuItemType mit;

        /**
         * The associated control for this item.
         */
        private Control control;

        /**
         * L'action a faire si jamais l'on clique sur cet élément.
         */
        private Runnable toDoIfExecuted;

        private String id;

        /**
         * Initialise un élément du menu.
         * @param header Le nom de l'élément.
         */
        public MenuItem(String header) {
            this(header, MenuItemType.Text, null);
        }
        /**
         * Initialise un élément du menu.
         * @param header Le nom de l'élément.
         * @param toDoIfExecuted L'action a faire si jamais l'on clique sur cet élément.
         */
        public MenuItem(String header, Runnable toDoIfExecuted) {
            this(header, MenuItemType.Button, "", toDoIfExecuted);
        }

        /**
         * Initialise un élément du menu.
         * @param header Le nom de l'élément.
         * @param mit Le type de l'élement.
         * @param id The id of the item.
         */
        public MenuItem(String header, MenuItemType mit, String id) {
            this(header, mit, id, null);
        }
        /**
         * Initialise un élément du menu.
         * @param header Le nom de l'élément.
         * @param mit Le type de l'élement.
         */
        public MenuItem(String header, MenuItemType mit) {
            this(header, mit, "", null);
        }
        /**
         * Initialise un élément du menu.
         * @param header Le nom de l'élément.
         * @param mit Le type de l'élement.
         * @param toDoIfExecuted L'action a faire si jamais l'on clique sur cet élément.
         */
        public MenuItem(String header, MenuItemType mit, String id, Runnable toDoIfExecuted) {
            this.header = header;
            this.mit = mit;
            this.toDoIfExecuted = toDoIfExecuted;
            this.id = id;
        }

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
                case TextBox:
                    GlyphLayout gl = new GlyphLayout();
                    gl.setText(gs.getNormalFont(), header);
                    batch.begin();
                    gs.getNormalFont().draw(batch, header, pos.x, pos.y + (int)(gs.getNormalFont().getLineHeight() + 2 * topMargin));
                    batch.end();
                    pos.x += gl.width + leftMargin;
                case Button:
                    control.draw(batch, pos, size);
                    break;
            }
        }
    }

    /***
     * Les différents types possibles des éléments d'un menu.
     */
    public enum MenuItemType {
        Title,
        Text,
        Button,
        TextBox
    }
}
