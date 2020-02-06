package be.ac.umons.sgl.mom.GameStates.Menus;

import be.ac.umons.sgl.mom.Enums.KeyStatus;
import be.ac.umons.sgl.mom.GameStates.GameState;
import be.ac.umons.sgl.mom.GraphicalObjects.Controls.Button;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
        }

        int alreadyUsed = (int)(topMargin * HEIGHT);

        sb.setProjectionMatrix(cam.combined);

        GlyphLayout layout;
        BitmapFont font;
        for (MenuItem menuItem : menuItems) {
            layout = new GlyphLayout();
            sb.begin();
            if (menuItem.mit.equals(MenuItemType.Normal))
                font = gs.getNormalFont();
            else
                font = gs.getTitleFont();
            font.setColor(Color.WHITE);
            layout.setText(font, menuItem.header);
            if (menuItem.button == null)
                font.draw(sb, layout, (int) (.05 * WIDTH), HEIGHT - alreadyUsed);
            sb.end();
            if (menuItem.button != null) {
                menuItem.button.setFont(font);
                menuItem.button.draw(sb, new Point((int) (.05 * WIDTH), (int)(HEIGHT - alreadyUsed - (font.getLineHeight() + 2 * topMargin))),
                        new Point((int) (layout.width + 2 * leftMargin),
                                (int) (font.getLineHeight() + 2 * topMargin)));
            }
            alreadyUsed += (int)(font.getLineHeight() + 2 * topMargin) + topMargin;
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
    }

    /***
     * Exécute l'action relié à un des éléments du menu en fonction de celui selectionné par l'utilisateur.
     */
    private void executeSelectedItem() {
        buttons.get(selectedItem).getOnClick().run();
    }

    protected void setMenuItems(MenuItem[] menuItems) {
        for (MenuItem mi : menuItems) {
            if (mi.selectable) {
                Button b = new Button(gim, gs);
                b.setText(mi.header);
                b.setOnClick(mi.toDoIfExecuted);
                buttons.add(b);
                mi.button = b;
            }
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
        private MenuItemType mit;
        /**
         * L'élément est-il selectionnable ?
         */
        public boolean selectable;

        /**
         * The associated button for this item.
         */
        private Button button;

        /**
         * L'action a faire si jamais l'on clique sur cet élément.
         */
        private Runnable toDoIfExecuted;

        /**
         * Initialise un élément du menu.
         * @param header Le nom de l'élément.
         */
        public MenuItem(String header) {
            this(header, MenuItemType.Normal, true);
        }
        /**
         * Initialise un élément du menu.
         * @param header Le nom de l'élément.
         * @param toDoIfExecuted L'action a faire si jamais l'on clique sur cet élément.
         */
        public MenuItem(String header, Runnable toDoIfExecuted) {
            this(header, MenuItemType.Normal, true, toDoIfExecuted);
        }

        /**
         * Initialise un élément du menu.
         * @param header Le nom de l'élément.
         * @param mit Le type de l'élement.
         */
        public MenuItem(String header, MenuItemType mit) {
            this(header, mit, true);
        }

        /**
         * Initialise un élément du menu.
         * @param header Le nom de l'élément.
         * @param mit Le type de l'élement.
         * @param selectable L'élément est-il selectionnable ?
         */
        public MenuItem(String header, MenuItemType mit, boolean selectable) {
            this.header = header;
            this.mit = mit;
            this.selectable = selectable;
        }
        /**
         * Initialise un élément du menu.
         * @param header Le nom de l'élément.
         * @param mit Le type de l'élement.
         * @param selectable L'élément est-il selectionnable ?
         * @param toDoIfExecuted L'action a faire si jamais l'on clique sur cet élément.
         */
        public MenuItem(String header, MenuItemType mit, boolean selectable, Runnable toDoIfExecuted) {
            this (header, mit, selectable);
            this.toDoIfExecuted = toDoIfExecuted;
        }
    }

    /***
     * Les différents types possibles des éléments d'un menu.
     */
    public enum MenuItemType {
        Title,
        Normal
    }
}
