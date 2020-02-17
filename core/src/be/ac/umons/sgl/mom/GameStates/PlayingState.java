package be.ac.umons.sgl.mom.GameStates;

import be.ac.umons.sgl.mom.Enums.KeyStatus;
import be.ac.umons.sgl.mom.Enums.Orientation;
import be.ac.umons.sgl.mom.Enums.Type;
import be.ac.umons.sgl.mom.GameStates.Menus.DebugMenuState;
import be.ac.umons.sgl.mom.GameStates.Menus.InGameMenuState;
import be.ac.umons.sgl.mom.GameStates.Menus.LevelUpMenuState;
import be.ac.umons.sgl.mom.GraphicalObjects.Character;
import be.ac.umons.sgl.mom.GraphicalObjects.Controls.Button;
import be.ac.umons.sgl.mom.GraphicalObjects.Controls.InventoryShower;
import be.ac.umons.sgl.mom.GraphicalObjects.*;
import be.ac.umons.sgl.mom.Managers.AnimationManager;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameMapManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import be.ac.umons.sgl.mom.Objects.Items.Battery;
import be.ac.umons.sgl.mom.Objects.Supervisor;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static be.ac.umons.sgl.mom.GraphicalObjects.QuestShower.TEXT_AND_RECTANGLE_MARGIN;

/**
 * The playing state.
 * A part of the code has been found in https://github.com/foreignguymike/legacyYTtutorials/tree/master/libgdxasteroids by ForeignGuyMike.
 * and https://www.youtube.com/watch?v=P8jgD-V5jG8 by Brent Aureli's - Code School (https://github.com/BrentAureli/SuperMario)
 * @author Guillaume Cardoen
 */
public class PlayingState extends GameState {
    /**
     * The horizontal map's width to show.
     */
    public static int SHOWED_MAP_WIDTH = 31;
    /**
     * The vertical map's width to show.
     */
    public static int SHOWED_MAP_HEIGHT = 17;
    /**
     * The player's speed
     */
    protected float VELOCITY = 5000;
    /**
     * The number of tile in the map (horizontally)
     */
    protected int mapWidth;
    /**
     * The number of tile in the map (vertically)
     */
    protected int mapHeight;
    /**
     * A tile's width.
     */
    protected int tileWidth;
    /**
     * A tile's height.
     */
    protected int tileHeight;
    /**
     * Allow to draw
     */
    protected SpriteBatch sb;
    /**
     * The positions where the user can't go.
     */
    protected MapObjects collisionObjects;

    protected MapObjects changingMapObjects;
    /**
     * The camera.
     */
    protected OrthographicCamera cam;
    /**
     * Part of the HUD showing active quest.
     */
    protected QuestShower questShower;
    /**
     * Part of the HUD showing player's inventory.
     */
    protected InventoryShower inventoryShower;
    /**
     * The player.
     */
    protected Player player;
    /**
     * The animation's manager.
     */
    protected AnimationManager am;

    /**
     * The player's life bar.
     */
    protected ProgressBar lifeBar;
    /**
     * The player's experience bar.
     */
    protected ProgressBar expBar;
    /**
     * The player's energy bar.
     */
    protected ProgressBar energyBar;

    /**
     * The game's map manager.
     */
    protected GameMapManager gmm;

    protected List<Character> pnjs;

    protected OnMapObject selectedOne;

    protected List<MapObject> mapObjects;

    protected Button pauseButton;

    /**
     * @param gsm The game's state manager
     * @param gim The game's input manager
     * @param gs The game's graphical settings
     */
    public PlayingState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }
    protected PlayingState() {}

    @Override
    public void init() {
        super.init();
        sb = new SpriteBatch();
        am = AnimationManager.getInstance();
        gmm = GameMapManager.getInstance();
        questShower = new QuestShower(gs);

        /*/!\devra être mis mais pourra changer de place (Randy pour Guillaume)/!\*/
        /*supprimer =>*/Supervisor.newParty("GuiRndMaxi",Type.normal,questShower,gs); //<= ajouter pour la save
        player = new Player(gs,MasterOfMonsGame.WIDTH / 2, MasterOfMonsGame.HEIGHT / 2);
        initMap("Tmx/Umons_Nimy.tmx");

        MapObject mo = new MapObject(gs, new Battery());
        mapObjects.add(mo);
        mo.setMapPos(new Point(player.getPosX() + tileWidth, player.getPosY() + tileHeight));

        cam = new OrthographicCamera(SHOWED_MAP_WIDTH * tileWidth, SHOWED_MAP_HEIGHT * tileHeight * 2);
        cam.update();
        cam.position.x = player.getPosX();
        cam.position.y = player.getPosY();
        gmm.setView(cam);

        inventoryShower = new InventoryShower(gim, gs, player);

        lifeBar = new ProgressBar();
        lifeBar.setForegroundColor(new Color(213f / 255, 0, 0, .8f));
        expBar = new ProgressBar();
        expBar.setForegroundColor(new Color(46f / 255, 125f / 255, 50f / 255, .8f));
        energyBar = new ProgressBar();
        energyBar.setForegroundColor(new Color(2f / 255, 119f / 255, 189f / 255, .8f));

        pauseButton = new Button(gim, gs);
        pauseButton.setText("||");
        pauseButton.setOnClick(() -> gsm.setState(InGameMenuState.class));
        pauseButton.setFont(gs.getSmallFont());
    }

    public void initMap(String mapPath) {
        gmm.setMap(mapPath);
//        SuperviserNormally.getSupervisor().getEvent().notify(new PlaceInMons(SuperviserNormally.getSupervisor().getMaps("Tmx/Umons_Nimy.tmx")));
        pnjs = new ArrayList<>();
        mapObjects = new ArrayList<>();

//        for (Items it : SuperviserNormally.getSupervisor().getItems(SuperviserNormally.getSupervisor().getMaps(mapPath)))
//            mapObjects.add(new MapObject(gs, it));
//
//        for (Mobile mob : SuperviserNormally.getSupervisor().getMobile(SuperviserNormally.getSupervisor().getMaps(mapPath)))
//            pnjs.add(new Character(gs, mob));
        tileWidth = (int)gmm.getActualMap().getProperties().get("tilewidth");
        tileHeight = (int)gmm.getActualMap().getProperties().get("tileheight");
        mapWidth = (int)gmm.getActualMap().getProperties().get("width");
        mapHeight = (int)gmm.getActualMap().getProperties().get("height");
        MapLayer collLayer = gmm.getActualMap().getLayers().get("Interdit");
        if (collLayer != null)
            collisionObjects = collLayer.getObjects();
        MapLayer changeLayer = gmm.getActualMap().getLayers().get("Changer");
        if (changeLayer != null)
            changingMapObjects = changeLayer.getObjects();
        player.setMapWidth(mapWidth * tileWidth);
        player.setMapHeight(mapHeight * tileHeight);
        player.setTileWidth(tileWidth);
        player.setTileHeight(tileHeight);
        player.move(-player.getPosX(), -player.getPosY());
        int spawnX = 0;
        int spawnY = 0;
        if (gmm.getActualMap().getProperties().containsKey("spawnX"))
            spawnX = (int)gmm.getActualMap().getProperties().get("spawnX");
        if (gmm.getActualMap().getProperties().containsKey("spawnY"))
            spawnY = (int)gmm.getActualMap().getProperties().get("spawnY");
        int x = (mapHeight - spawnY) * tileWidth / 2 + spawnX * tileHeight;
        int y = (mapHeight - spawnX - spawnY) * tileHeight / 2;
        player.move(x,y);
    }

    @Override
    public void update(float dt) {
        handleInput();
        am.update(dt);
        makePlayerMove(dt);
        cam.update();

        lifeBar.setValue((int)player.getCharacteristics().getLife());
        lifeBar.setMaxValue((int)player.getCharacteristics().lifemax());
        expBar.setValue((int)((People)player.getCharacteristics()).getExperience());
        expBar.setMaxValue((int)((People)player.getCharacteristics()).minExperience());
        energyBar.setValue((int)((People)player.getCharacteristics()).getEnergy());
        energyBar.setMaxValue(100);
    }

    /**
     * Make the player move and check that the position of the player is not out of the map.
     * @param dt The delta time
     */
    protected void makePlayerMove(float dt) {
        int toMove = Math.round(VELOCITY * dt);
        int toMoveX = 0, toMoveY = 0;

        if (gim.isKey(Input.Keys.DOWN, KeyStatus.Down)) {
            player.setOrientation(Orientation.Bottom);
            toMoveY += -toMove;
        }
        if (gim.isKey(Input.Keys.UP, KeyStatus.Down)) {
            player.setOrientation(Orientation.Top);
            toMoveY += toMove;
        }
        if (gim.isKey(Input.Keys.LEFT, KeyStatus.Down)) {
            player.setOrientation(Orientation.Left);
            toMoveX += -toMove;
        }
        if (gim.isKey(Input.Keys.RIGHT, KeyStatus.Down)) {
            player.setOrientation(Orientation.Right);
            toMoveX += toMove;
        }


        translateCamera(player.getPosX() + toMoveX, player.getPosY() + toMoveY);
        player.move(toMoveX, toMoveY);
        if (checkForCollision(player)) {
            player.move(-toMoveX, -toMoveY);
            return;
        }
        checkForMapChanging(player);
        checkForNearSelectable(player);

        player.setxT((int)(player.getPosX() - cam.position.x));
        player.setyT((int)(player.getPosY() - cam.position.y));
    }

    /**
     * Move the camera.
     * @param x Horizontal position of the player.
     * @param y Vertical position of the player.
     */
    protected void translateCamera(int x, int y) {
        double minX = (double)SHOWED_MAP_WIDTH / 2;
        double minY = (double)SHOWED_MAP_HEIGHT / 2;

        if (player.getMapRectangle().x < minX || player.getMapRectangle().getY() < minY) { // TODO : Bug when going on the border
            return;
        }

        cam.position.x = x;
        cam.position.y = y;
//        double maxX = (mapHeight + mapWidth) * tileHeight;
//        if (cam.position.x < SHOWED_MAP_WIDTH * tileWidth / 2)
//            cam.position.x = SHOWED_MAP_WIDTH * tileWidth / 2;
//        else if (cam.position.x > maxX)
//            cam.position.x = (float)maxX;
//
//        if (cam.position.y > (mapHeight - SHOWED_MAP_HEIGHT) * tileHeight / 2)
//            cam.position.y = (mapHeight - SHOWED_MAP_HEIGHT) * tileHeight / 2;
//        else if (cam.position.y < -(mapHeight - SHOWED_MAP_HEIGHT / 2) * tileHeight)
//            cam.position.y = -(mapHeight - SHOWED_MAP_HEIGHT / 2) * tileHeight;
    }

    /**
     * Check if the player is in collision with one of the collision area on the map.
     * @param player The player.
     * @return If the player is in collision with one of the collision area on the map.
     */
    protected boolean checkForCollision(Player player) {
        if (collisionObjects == null)
            return false;
        for (RectangleMapObject rectangleMapObject : collisionObjects.getByType(RectangleMapObject.class)) {
            Rectangle rect = rectangleMapObject.getRectangle();
            Rectangle playerRect = player.getMapRectangle();
            Rectangle mapRect = new Rectangle( rect.x * 2 / tileWidth, (mapHeight * tileHeight - rect.y - rect.height) / tileHeight, rect.width * 2 / tileWidth, rect.height / tileHeight);
            if (Intersector.overlaps(mapRect, playerRect)) {
                return true;
            }
        }
        return false;
    }

    protected void checkForMapChanging(Player player) {
        if (changingMapObjects == null)
            return;
        for (RectangleMapObject rectangleMapObject : changingMapObjects.getByType(RectangleMapObject.class)) {
            Rectangle rect = rectangleMapObject.getRectangle();
            Rectangle playerRect = player.getMapRectangle();
            Rectangle mapRect = new Rectangle( rect.x * 2 / tileWidth, (mapHeight * tileHeight - rect.y - rect.height) / tileHeight, rect.width * 2 / tileWidth, rect.height / tileHeight);
            if (Intersector.overlaps(mapRect, playerRect)) {
                initMap(rectangleMapObject.getName());
            }
        }
    }

    protected void checkForNearSelectable(Player player) {
        OnMapObject nearest = null;
        double nearestDist = tileWidth * mapWidth;
        if (selectedOne != null)
            selectedOne.setSelected(false);
        for (int i = 0; i < pnjs.size() + mapObjects.size(); i++) {
            OnMapObject omo = (i < pnjs.size() ? pnjs.get(i) : mapObjects.get(i - pnjs.size()));
            double dist = Math.pow(player.getPosX() - omo.getPosX(), 2) + Math.pow(player.getPosY() - omo.getPosY(), 2);
            if (dist < nearestDist && dist < 30000) {
                nearest = omo;
                nearestDist = dist;
            }
        }
        if (nearest != null) {
            nearest.setSelected(true);
            selectedOne = nearest;
        }
    }

    @Override
    public void draw() {
        int topBarWidth = (int)((MasterOfMonsGame.WIDTH - 4 * leftMargin) / 3);
        int topBarHeight = 10;

        gmm.render();
        player.draw(sb);
        for (Character pnj : pnjs)
            pnj.draw(sb, pnj.getPosX() - (int)cam.position.x + MasterOfMonsGame.WIDTH / 2, pnj.getPosY() - (int)cam.position.y + MasterOfMonsGame.HEIGHT / 2, tileWidth, 2 * tileHeight);
        for (MapObject mo : mapObjects)
            mo.draw(sb, mo.getPosX() - (int)cam.position.x + + MasterOfMonsGame.WIDTH / 2, mo.getPosY() - (int)cam.position.y + MasterOfMonsGame.HEIGHT / 2, tileWidth, tileHeight);

        sb.begin();
        if (gs.mustShowMapCoordinates())
            gs.getSmallFont().draw(sb, String.format("(%f, %f)", player.getMapRectangle().x, player.getMapRectangle().y), (int)leftMargin, (int)(10 * topMargin - topBarHeight));
        sb.end();

        // Dessine le HUD.
        questShower.draw(sb, tileWidth / 2 - TEXT_AND_RECTANGLE_MARGIN, (int)(MasterOfMonsGame.HEIGHT - 2 * topMargin - topBarHeight));
        inventoryShower.draw(sb, MasterOfMonsGame.WIDTH / 2, tileHeight * 2, new Point(tileWidth, tileWidth));
        lifeBar.draw((int)leftMargin, MasterOfMonsGame.HEIGHT - (int)topMargin - topBarHeight, topBarWidth, topBarHeight);
        expBar.draw((int)leftMargin * 2 + topBarWidth, MasterOfMonsGame.HEIGHT - (int)topMargin - topBarHeight, topBarWidth, topBarHeight);
        energyBar.draw((int)leftMargin * 3 + topBarWidth * 2, MasterOfMonsGame.HEIGHT - (int)topMargin - topBarHeight, topBarWidth, topBarHeight);
        Point pauseButtonSize = new Point((int)(2 * gs.getSmallFont().getXHeight() + 2 * leftMargin), (int)(2 * topMargin + gs.getSmallFont().getLineHeight()));
        pauseButton.draw(sb, new Point((int)(MasterOfMonsGame.WIDTH - pauseButtonSize.x), (int)(MasterOfMonsGame.HEIGHT - pauseButtonSize.y - topBarHeight - 2 * topMargin)),
                pauseButtonSize);
    }

    @Override
    public void handleInput() {
        if (gim.isKey(Input.Keys.ESCAPE, KeyStatus.Pressed))
            gsm.setState(InGameMenuState.class);
        else if (gim.isKey(Input.Keys.B, KeyStatus.Down) && gim.isKey(Input.Keys.UP, KeyStatus.Down)) {
            DebugMenuState dms = (DebugMenuState) gsm.setState(DebugMenuState.class);
            dms.setPlayingState(this);
        } else if (gim.isKey(Input.Keys.P, KeyStatus.Pressed))
            debugLevelUp();
        else if (gim.isKey(Input.Keys.I, KeyStatus.Pressed))
            debugMakeInvincible();
        else if (gim.isKey(Input.Keys.C, KeyStatus.Pressed)) {
            CombatState g = (CombatState) gsm.setState(CombatState.class);
            g.setPlayer1(player);
            g.setPlayer2(player);
        } else if (gim.isKey(Input.Keys.N, KeyStatus.Pressed)) {
            LevelUpMenuState lums = (LevelUpMenuState) gsm.setState(LevelUpMenuState.class);
            lums.setPlayer(player);
        } else if (gim.isKey(Input.Keys.E, KeyStatus.Pressed)) {
            // TODO interaction
        }
        inventoryShower.handleInput();
        pauseButton.handleInput();
    }

    public void debugLevelUp() {
        ((People)player.getCharacteristics()).upLevel();
    }
    public void debugMakeInvincible() {
        if (((People)player.getCharacteristics()).isInvincible()) {
            ((People)player.getCharacteristics()).invincible(false);
            lifeBar.setForegroundColor(new Color(213f / 255, 0, 0, .8f));
        } else {
            ((People)player.getCharacteristics()).invincible(true);
            lifeBar.setForegroundColor(new Color(0x212121AA));
        }
    }

    @Override
    public void dispose() {
        sb.dispose();
        questShower.dispose();
        inventoryShower.dispose();
        lifeBar.dispose();
        energyBar.dispose();
        expBar.dispose();
    }

    @Override
    public void getFocus() {
        inventoryShower.setHided(false);
    }
}
