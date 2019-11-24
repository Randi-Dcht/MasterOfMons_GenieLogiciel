package be.ac.umons.sgl.mom.GameStates.Menus;

import be.ac.umons.sgl.mom.Enums.KeyStatus;
import be.ac.umons.sgl.mom.GameStates.GameState;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.*;

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
     * La marge entre les différents éléments.
     */
    protected double betweenItemMargin;
    /**
     * La caméra permettant d'afficher le texte et de zoomer au besoin.
     */
    protected OrthographicCamera cam;

    /**
     * Crée un nouveau menu.
     * @param gsm Le GameStateManager du jeu.
     * @param gim Le GameInputManager du jeu.
     * @param gs Les paramètres graphiques à utiliser.
     */
    protected MenuState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }

    @Override
    public void init() {
        super.init();
        sb = new SpriteBatch();
		cam = new OrthographicCamera(WIDTH, HEIGHT); // Make the camera the same size as the game
		cam.translate(WIDTH / 2, HEIGHT / 2);
		cam.update();
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void draw() {
        int alreadyUsed = (int)(topMargin * HEIGHT);

        sb.setProjectionMatrix(cam.combined);

        while (! menuItems[selectedItem].selectable)
            selectedItem = (selectedItem + 1) % menuItems.length;

        sb.begin();

        GlyphLayout layout;
        BitmapFont font;
        for (int i = 0; i < menuItems.length; i++) {
            layout = new GlyphLayout();
            menuItems[i].screenTextBound = new Rectangle();
            if (menuItems[i].mit.equals(MenuItemType.Normal))
                font = gs.getNormalFont();
            else
                font = gs.getTitleFont();
            if (i == selectedItem)
                font.setColor(Color.ORANGE);
            layout.setText(font, menuItems[i].header);
            menuItems[i].screenTextBound.setRect((int)(.05 * WIDTH), alreadyUsed, menuItems[i].header.length() * font.getXHeight(), font.getLineHeight());
            font.draw(sb, layout, (int)(.05 * WIDTH), HEIGHT - alreadyUsed);
            alreadyUsed += (int)(font.getLineHeight() + betweenItemMargin * HEIGHT);
            font.setColor(Color.WHITE);
        }

        sb.end();
    }

    @Override
    public void handleInput() {
        if (gim.isKey(Input.Keys.ENTER, KeyStatus.Pressed))
            executeSelectedItem();
        if (gim.isKey(Input.Keys.DOWN, KeyStatus.Pressed)) {
            do
                selectedItem = (selectedItem + 1) % menuItems.length;
            while ( ! menuItems[selectedItem].selectable);
        }
        if (gim.isKey(Input.Keys.UP, KeyStatus.Pressed)) {
            do {
                selectedItem = (selectedItem - 1) % menuItems.length;
                if (selectedItem < 0)
                    selectedItem += menuItems.length;
            }
            while ( ! menuItems[selectedItem].selectable);
        }
        for (Point click: gim.getRecentClicks()) {
            for (int i = 0; i < menuItems.length; i++) {
                if (menuItems[i].selectable && menuItems[i].screenTextBound.contains(click.x, click.y))
                    if (selectedItem == i)
                        executeSelectedItem();
                    else
                        selectedItem = i;
            }
        }
    }

    /***
     * Exécute l'action relié à un des éléments du menu en fonction de celui selectionné par l'utilisateur.
     */
    private void executeSelectedItem() {
        if (menuItems[selectedItem].toDoIfExecuted != null)
            menuItems[selectedItem].toDoIfExecuted.run();
    }

    @Override
    public void dispose() {
        sb.dispose();
    }

    /***
     * Représente un élément du menu.
     */
    protected class MenuItem {
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
        private boolean selectable;
        /**
         * Représente la position et la taille de l'élément (ATTENTION : En fonction des coordonnées de l'écran)
         */
        private Rectangle screenTextBound;

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
        Normal;
    }
}
