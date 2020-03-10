package be.ac.umons.mom.g02.GameStates.Menus;

import be.ac.umons.mom.g02.GameStates.GameState;
import be.ac.umons.mom.g02.GraphicalObjects.Controls.CheckBox;
import be.ac.umons.mom.g02.Managers.ExtensionsManager;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameMapManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.MasterOfMonsGame;
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
     * @param gsm The game's state manager
     * @param gim The game's input manager
     * @param gs The game's graphical settings.
     */
    public MainMenuState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
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
        leftMargin = .05 * MasterOfMonsGame.WIDTH;
        transparentBackground = false;
        List<MenuItem> menuItemList = new ArrayList<>();
        menuItemList.add(new MenuItem(gs.getStringFromId("gameName"), MenuItemType.Title));
        menuItemList.add(new MenuItem(gs.getStringFromId("newGame"), this::initGame));
        menuItemList.add(new MenuItem(gs.getStringFromId("load"), MenuItemType.Button, () -> gsm.setState(LoadMenuState.class)));
        menuItemList.add(new MenuItem(gs.getStringFromId("settings"), MenuItemType.Button, () -> gsm.setState(SettingsMenuState.class)));
        menuItemList.add(new MenuItem(gs.getStringFromId("quit"), MenuItemType.Button, () -> Gdx.app.exit()));
        menuItemList.add(new MenuItem(gs.getStringFromId("about"), MenuItemType.Button, () -> gsm.setState(AboutMenuState.class)));
        menuItemList.add(new MenuItem(gs.getStringFromId("extensions"), MenuItemType.Title));

        for (ExtensionsManager.Extension ext : em.getExtensions()) {
            MenuItem mi = new MenuItem(ext.extensionName, MenuItemType.CheckBox, (newState -> {
                if (newState)
                    em.onExtensionActivated(ext);
                else
                    em.onExtensionDeactivated(ext);
            }));
            menuItemList.add(mi);
            extensionCheckBoxHashMap.put(ext, mi);
            mi.size.y = (int)Math.floor(gs.getNormalFont().getLineHeight());
        }

        setMenuItems(menuItemList.toArray(new MenuItem[0]));
//        extSel = new ExtensionsSelector(gim, gs);
    }

    public void initGame() {
        em.generateLoadLists();
        for (FileHandle f : Gdx.files.internal("Tmx/").list())
            GameMapManager.getInstance().addMapsToLoad(f.path());
        GameMapManager.getInstance().addMapsToLoad(em.getMapsToLoad().toArray(new String[0]));
        gs.addFilesToLoad(em.getFilesToLoad().toArray(new LoadFile[0]));
        ExtensionsManager.Extension mainExt = em.getMainExtension();
        if (mainExt.mainClassBeforeCharacterCreation != null) {
            try {
                gsm.setState(mainExt.getMainClassBeforeCharacterCreation());
            } catch (ClassNotFoundException e) {
                Gdx.app.error("ExtensionsFile", String.format("The class %s wasn't found", mainExt.mainClassBeforeCharacterCreation), e);
                return;
            }
        } else {
            CreatePlayerMenuState cpms = (CreatePlayerMenuState) gsm.setState(CreatePlayerMenuState.class, false);
            try {
                if (mainExt != null) {
                    Class<? extends GameState> gs = mainExt.getMainClassBeforeLoading();
                    if (gs != null)
                        cpms.setAfterCreationState(gs);
                    gs = mainExt.getMainClass();
                    if (gs != null)
                        cpms.setAfterLoadingState(gs);

                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                Gdx.app.error("Critical error", e.getMessage());
            }
        }
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        for (ExtensionsManager.Extension ext : em.getExtensions()) {
            CheckBox cb = (CheckBox)extensionCheckBoxHashMap.get(ext).control;
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
