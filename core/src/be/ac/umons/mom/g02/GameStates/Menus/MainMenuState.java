package be.ac.umons.mom.g02.GameStates.Menus;

import be.ac.umons.mom.g02.GameStates.GameState;
import be.ac.umons.mom.g02.GameStates.LoadingState;
import be.ac.umons.mom.g02.GraphicalObjects.Controls.CheckBox;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.ButtonMenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.CheckBoxMenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.MenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.TitleMenuItem;
import be.ac.umons.mom.g02.Managers.ExtensionsManager;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameMapManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Objects.LoadFile;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The first menu presented in the game. It allow to start a party in other things.
 * @author Guillaume Cardoen
 */
public class MainMenuState extends MenuState {

    /**
     * Allows to activate/deactivate an extension.
     */
//    ExtensionsSelector extSel;

    ExtensionsManager em;
    HashMap<ExtensionsManager.Extension, MenuItem> extensionCheckBoxHashMap;

    /**
     * @param gs The game's graphical settings.
     */
    public MainMenuState(GraphicalSettings gs) {
        super(gs);
    }

    /**
     * The default constructor. USE IT ONLY FOR TESTS.
     */
    protected MainMenuState(){}

    @Override
    public void init() {
        super.init();
        extensionCheckBoxHashMap = new HashMap<>();
        em = ExtensionsManager.getInstance();
        handleEscape = false;
        transparentBackground = false;
        List<MenuItem> menuItemList = new ArrayList<>();
        menuItemList.add(new TitleMenuItem(gs, gs.getStringFromId("gameName")));
        menuItemList.add(new ButtonMenuItem(gim, gs, gs.getStringFromId("newGame"), () -> em.initGame(gsm)));
        menuItemList.add(new ButtonMenuItem(gim, gs, gs.getStringFromId("load"), this::initLoad));
        menuItemList.add(new ButtonMenuItem(gim, gs, gs.getStringFromId("settings"), () -> gsm.setState(SettingsMenuState.class)));
        menuItemList.add(new ButtonMenuItem(gim, gs, gs.getStringFromId("quit"), () -> Gdx.app.exit()));
        menuItemList.add(new ButtonMenuItem(gim, gs, gs.getStringFromId("about"), () -> gsm.setState(AboutMenuState.class)));
        menuItemList.add(new TitleMenuItem(gs, gs.getStringFromId("extensions")));

        for (ExtensionsManager.Extension ext : em.getExtensions()) {
            CheckBoxMenuItem mi = new CheckBoxMenuItem(gim, gs, ext.extensionName, (newState -> {
                if (newState)
                    em.onExtensionActivated(ext);
                else
                    em.onExtensionDeactivated(ext);
            }));
            menuItemList.add(mi);
            extensionCheckBoxHashMap.put(ext, mi);
            mi.getSize().y = (int)Math.floor(gs.getNormalFont().getLineHeight());
        }

        setMenuItems(menuItemList.toArray(new MenuItem[0]));
    }

    protected void initLoad() {
        ExtensionsManager.Extension mainExt = em.getMainExtension();
        if (mainExt != null) {
            Class<? extends GameState> gs;
            try {
                if ((gs = mainExt.getClassBeforeOldGameSelection()) != null)
                    gsm.setState(gs);
                else
                    gsm.setState(LoadMenuState.class);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else
            gsm.setState(LoadMenuState.class);
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        for (ExtensionsManager.Extension ext : em.getExtensions()) {
            CheckBox cb = (CheckBox)extensionCheckBoxHashMap.get(ext).getControl();
            if (cb != null) {
                cb.setActivated(ext.canBeActivated);
                cb.setChecked(ext.activated);
            }
        }
    }

    @Override
    public void draw() {
        super.draw();
//        extSel.draw(sb, new Point(MasterOfMonsGame.WIDTH / 3 * 2, MasterOfMonsGame.HEIGHT / 3 - 2 * (int)topMargin), new Point(MasterOfMonsGame.WIDTH / 3 - 2 * (int)leftMargin, MasterOfMonsGame.HEIGHT / 3 - 2 * (int)topMargin));
    }

    @Override
    public void handleInput() {
        super.handleInput();
//        extSel.handleInput();
    }

    @Override
    public void getFocus() {
        init();
    }
}
