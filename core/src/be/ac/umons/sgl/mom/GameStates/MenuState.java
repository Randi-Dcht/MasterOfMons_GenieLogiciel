package be.ac.umons.sgl.mom.GameStates;

import be.ac.umons.sgl.mom.Enums.GameKeys;
import be.ac.umons.sgl.mom.Enums.KeyStatus;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class MenuState extends GameState {

    protected SpriteBatch sb;

    protected int selectedItem = 0;

    protected MenuItem[] menuItems;

    protected double topMargin;
    protected double betweenItemMargin;

    public MenuState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }

    @Override
    public void init() {
        sb = new SpriteBatch();
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void draw() {
        int alreadyUsed = (int)(topMargin * MasterOfMonsGame.HEIGHT);

        sb.setProjectionMatrix(MasterOfMonsGame.cam.combined);

        while (! menuItems[selectedItem].selectable)
            selectedItem = (selectedItem + 1) % menuItems.length;

        sb.begin();

        for (int i = 0; i < menuItems.length; i++) {
            BitmapFont font;
            if (menuItems[i].mit.equals(MenuItemType.Normal))
                font = gs.getNormalFont();
            else
                font = gs.getTitleFont();
            if (i == selectedItem)
                font.setColor(Color.ORANGE);
            font.draw(sb, menuItems[i].header, (int)(.05 * MasterOfMonsGame.WIDTH), MasterOfMonsGame.HEIGHT - alreadyUsed);
            alreadyUsed += (int)(font.getLineHeight() + betweenItemMargin * MasterOfMonsGame.HEIGHT);
            font.setColor(Color.WHITE);
        }

        sb.end();
    }

    @Override
    public void handleInput() {
        if (gim.isKey(GameKeys.Down, KeyStatus.Pressed))
            do
                selectedItem = (selectedItem + 1) % menuItems.length;
            while ( ! menuItems[selectedItem].selectable);
        if (gim.isKey(GameKeys.Up, KeyStatus.Pressed))
            do {
                selectedItem = (selectedItem - 1) % menuItems.length; // HOW THE HELL DOES THAT RETURN -1
                if (selectedItem < 0)
                    selectedItem += menuItems.length;
            }
            while ( ! menuItems[selectedItem].selectable);
    }

    @Override
    public void dispose() {
        sb.dispose();
    }

    protected class MenuItem {
        private String header;
        private MenuItemType mit;
        protected boolean selectable;

        public MenuItem(String header) {
            this(header, MenuItemType.Normal);
        }
        public MenuItem(String header, MenuItemType mit) {
            this(header, mit, true);
        }
        public MenuItem(String header, MenuItemType mit, boolean selectable) {
            this.header = header;
            this.mit = mit;
            this.selectable = selectable;
        }
    }

    public enum MenuItemType {
        Title,
        Normal;
    }
}
