package be.ac.umons.sgl.mom.GameStates;

import be.ac.umons.sgl.mom.Enums.KeyStatus;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.*;

import static be.ac.umons.sgl.mom.MasterOfMonsGame.gs;

public abstract class MenuState extends GameState {

    protected SpriteBatch sb;

    protected int selectedItem = 0;

    protected MenuItem[] menuItems;

    protected double topMargin;
    protected double betweenItemMargin;

    public MenuState(GameStateManager gsm, GameInputManager gim) {
        super(gsm, gim);
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
            menuItems[i].screenTextBound.setRect((int)(.05 * MasterOfMonsGame.WIDTH), alreadyUsed, menuItems[i].header.length() * font.getXHeight(), font.getLineHeight());
            font.draw(sb, layout, (int)(.05 * MasterOfMonsGame.WIDTH), MasterOfMonsGame.HEIGHT - alreadyUsed);
            alreadyUsed += (int)(font.getLineHeight() + betweenItemMargin * MasterOfMonsGame.HEIGHT);
            font.setColor(Color.WHITE);
        }

        sb.end();
    }

    @Override
    public void handleInput() {
        if (gim.isKey(Input.Keys.ENTER, KeyStatus.Pressed))
            goToSelectedItem();
        if (gim.isKey(Input.Keys.DOWN, KeyStatus.Pressed)) {
            do
                selectedItem = (selectedItem + 1) % menuItems.length;
            while ( ! menuItems[selectedItem].selectable);
        }
        if (gim.isKey(Input.Keys.UP, KeyStatus.Pressed)) {
            do {
                selectedItem = (selectedItem - 1) % menuItems.length; // HOW THE HELL DOES THAT RETURN -1
                if (selectedItem < 0)
                    selectedItem += menuItems.length;
            }
            while ( ! menuItems[selectedItem].selectable);
        }
        for (Point click: gim.getRecentClicks()) {
            for (int i = 0; i < menuItems.length; i++) {
                if (menuItems[i].screenTextBound.contains(click.x, click.y))
                    if (selectedItem == i)
                        goToSelectedItem();
                    else
                        selectedItem = i;
            }
        }
    }

    protected abstract void goToSelectedItem();

    @Override
    public void dispose() {
        sb.dispose();
    }

    protected class MenuItem {
        private String header;
        private MenuItemType mit;
        private boolean selectable;
        private Rectangle screenTextBound;

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
