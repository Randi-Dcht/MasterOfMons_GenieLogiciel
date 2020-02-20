package be.ac.umons.sgl.mom.GameStates;

import be.ac.umons.sgl.mom.Enums.*;
import be.ac.umons.sgl.mom.Events.Events;
import be.ac.umons.sgl.mom.Events.Notifications.Answer;
import be.ac.umons.sgl.mom.Events.Notifications.Notification;
import be.ac.umons.sgl.mom.Events.Notifications.PlaceInMons;
import be.ac.umons.sgl.mom.Events.Observer;
import be.ac.umons.sgl.mom.Events.SuperviserNormally;
import be.ac.umons.sgl.mom.GameStates.Dialogs.InGameDialogState;
import be.ac.umons.sgl.mom.GameStates.Menus.DeadMenuState;
import be.ac.umons.sgl.mom.GameStates.Menus.DebugMenuState;
import be.ac.umons.sgl.mom.GameStates.Menus.InGameMenuState;
import be.ac.umons.sgl.mom.GameStates.Menus.LevelUpMenuState;
import be.ac.umons.sgl.mom.GraphicalObjects.OnMapObjects.Character;
import be.ac.umons.sgl.mom.GraphicalObjects.Controls.AgendaShower;
import be.ac.umons.sgl.mom.GraphicalObjects.Controls.Button;
import be.ac.umons.sgl.mom.GraphicalObjects.Controls.InventoryShower;
import be.ac.umons.sgl.mom.GraphicalObjects.*;
import be.ac.umons.sgl.mom.GraphicalObjects.OnMapObjects.MapObject;
import be.ac.umons.sgl.mom.GraphicalObjects.OnMapObjects.OnMapObject;
import be.ac.umons.sgl.mom.GraphicalObjects.OnMapObjects.Player;
import be.ac.umons.sgl.mom.Managers.AnimationManager;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameMapManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.Characters.Mobile;
import be.ac.umons.sgl.mom.Objects.Characters.Mobile;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import be.ac.umons.sgl.mom.Objects.Items.Items;

import com.badlogic.gdx.Gdx;
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
import java.util.LinkedList;
import java.util.List;

import static be.ac.umons.sgl.mom.GraphicalObjects.QuestShower.TEXT_AND_RECTANGLE_MARGIN;

/**
 * The playing state.
 * A part of the code has been found in https://github.com/foreignguymike/legacyYTtutorials/tree/master/libgdxasteroids by ForeignGuyMike.
 * and https://www.youtube.com/watch?v=P8jgD-V5jG8 by Brent Aureli's - Code School (https://github.com/BrentAureli/SuperMario)
 * @author Guillaume Cardoen
 */
public class PlayingState extends GameState implements Observer {
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

    /**
     * The list of PNJ that will be on this map.
     */
    protected List<Character> pnjs;
    /**
     * The selected object/character on the map if one is selected.
     */
    protected OnMapObject selectedOne;
    /**
     * The objects that are on this map.
     */
    protected List<MapObject> mapObjects;
    /**
     * The button pause.
     */
    protected Button pauseButton;
    /**
     * The agenda's shower
     */
    protected AgendaShower agendaShower;

    protected TimeShower timeShower;

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
        agendaShower = new AgendaShower(gim, gs);
        timeShower = new TimeShower(gs);

        SuperviserNormally.getSupervisor().setQuest(questShower);
        player = new Player(gs,MasterOfMonsGame.WIDTH / 2, MasterOfMonsGame.HEIGHT / 2);
        initMap("Tmx/Umons_Nimy.tmx");

        Character testPNJ = new Character(gs, new Mobile("xx",Bloc.BA1, MobileType.Lambda));
        pnjs.add(testPNJ);
        testPNJ.setMapPos(new Point(player.getPosX() + tileWidth, player.getPosY() + tileHeight));

        cam = new OrthographicCamera(SHOWED_MAP_WIDTH * tileWidth, SHOWED_MAP_HEIGHT * tileHeight * 2);
        cam.update();
        cam.position.x = player.getPosX();
        cam.position.y = player.getPosY();
        gmm.setView(cam);

        inventoryShower = new InventoryShower(gim, gs, player);

        lifeBar = new ProgressBar(gs);
        lifeBar.setForegroundColor(new Color(213f / 255, 0, 0, .8f));
        expBar = new ProgressBar(gs);
        expBar.setForegroundColor(new Color(46f / 255, 125f / 255, 50f / 255, .8f));
        energyBar = new ProgressBar(gs);
        energyBar.setForegroundColor(new Color(2f / 255, 119f / 255, 189f / 255, .8f));

        pauseButton = new Button(gim, gs);
        pauseButton.setText("||");
        pauseButton.setOnClick(() -> gsm.setState(InGameMenuState.class));
        pauseButton.setFont(gs.getSmallFont());

        SuperviserNormally.getSupervisor().getEvent().add(Events.Dead, this);
        SuperviserNormally.getSupervisor().getEvent().add(Events.ChangeQuest, this);
        SuperviserNormally.getSupervisor().getEvent().add(Events.Dialog,this);
    }

    /**
     * Initialise all the variables for the given map.
     * @param mapPath The map's path
     * @param spawnX On which CASE the character will be horizontally.
     * @param spawnY On which CASE the character will be vertically
     */
    public void initMap(String mapPath, int spawnX, int spawnY) {
        gmm.setMap(mapPath);
        SuperviserNormally.getSupervisor().getEvent().notify(new PlaceInMons(SuperviserNormally.getSupervisor().getMaps(mapPath)));
        pnjs = new ArrayList<>();
        mapObjects = new ArrayList<>();

        for (Items it : SuperviserNormally.getSupervisor().getItems(SuperviserNormally.getSupervisor().getMaps(mapPath)))
            mapObjects.add(new MapObject(gs, it));

        for (Mobile mob : SuperviserNormally.getSupervisor().getMobile(SuperviserNormally.getSupervisor().getMaps(mapPath)))
            pnjs.add(new Character(gs, mob));
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
        int x = (mapHeight - spawnY) * tileWidth / 2 + spawnX * tileHeight;
        int y = (mapHeight - spawnX - spawnY) * tileHeight / 2;
        player.setPosX(x);
        player.setPosY(y);
        if (cam != null) {
            cam.position.x = x;
            cam.position.y = y;
        }
    }

    /**
     * Initialise all the variables for the given map.
     * @param mapPath The map's path
     */
    public void initMap(String mapPath) {
        gmm.setMap(mapPath);
        int spawnX = 0, spawnY = 0;
        if (gmm.getActualMap().getProperties().containsKey("spawnX"))
            spawnX = (int)gmm.getActualMap().getProperties().get("spawnX");
        if (gmm.getActualMap().getProperties().containsKey("spawnY"))
            spawnY = (int)gmm.getActualMap().getProperties().get("spawnY");
        initMap(mapPath, spawnX, spawnY);
    }

    @Override
    public void update(float dt) {
        handleInput();
        am.update(dt);
        makePlayerMove(dt);
        cam.update();


        SuperviserNormally.getSupervisor().callMethod(dt);

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

        if (cam.position != null) { // For testing purposes
            player.setxT((int)(player.getPosX() - cam.position.x));
            player.setyT((int)(player.getPosY() - cam.position.y));
        }
    }

    /**
     * Move the camera.
     * @param x Horizontal position of the player.
     * @param y Vertical position of the player.
     */
    protected void translateCamera(int x, int y) {
        double minX = (double)SHOWED_MAP_WIDTH / 2;
        double minY = (double)SHOWED_MAP_HEIGHT / 2;

        Rectangle pmr = player.getMapRectangle();

        if (pmr.x < minX || pmr.getY() < minY ||
                pmr.x > mapWidth - minX || pmr.getY() > mapHeight - minY) {
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

    /**
     * Check if the player is on a case to change the map.
     * @param player The player.
     */
    protected void checkForMapChanging(Player player) {
        if (changingMapObjects == null)
            return;
        for (RectangleMapObject rectangleMapObject : changingMapObjects.getByType(RectangleMapObject.class)) {
            Rectangle rect = rectangleMapObject.getRectangle();
            Rectangle playerRect = player.getMapRectangle();
            Rectangle mapRect = new Rectangle( rect.x * 2 / tileWidth, (mapHeight * tileHeight - rect.y - rect.height) / tileHeight, rect.width * 2 / tileWidth, rect.height / tileHeight);
            if (Intersector.overlaps(mapRect, playerRect)) {
                if (rectangleMapObject.getProperties().containsKey("spawnX") && rectangleMapObject.getProperties().containsKey("spawnY")) {
                    int spawnX, spawnY;
                    spawnX = (int)rectangleMapObject.getProperties().get("spawnX");
                    spawnY = (int)rectangleMapObject.getProperties().get("spawnY");
                    initMap(rectangleMapObject.getName(), spawnX, spawnY);
                } else
                    initMap(rectangleMapObject.getName());
            }
        }
    }

    /**
     * Check if the player is near a selectable object and select it.
     * @param player The player
     */
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
            if (i < pnjs.size())
                pnjs.get(i).setIsATarget(dist < Math.pow(player.getAttackRange(), 2));
        }
        if (nearest != null) {
            nearest.setSelected(true);
            selectedOne = nearest;
        }
    }

    /**
     * @param player The player
     * @param dist A distance (^2)
     * @return Return all the PNJs near enough from the player.
     */
    protected List<Character> getPlayerInRange(Player player, double dist) {
        LinkedList<Character> res = new LinkedList<>();
        for (Character character : pnjs) {
            double d = Math.pow(player.getPosX() - character.getPosX(), 2) + Math.pow(player.getPosY() - character.getPosY(), 2);
            if (d < dist)
                res.add(character);
        }
        return res;
    }

    /**
     * Executed when the player launch an attack.
     * @param player The player.
     */
    protected void attack(Player player) {
        if (player.isRecovering())
            return;
        player.expandAttackCircle();
        for (Character c : getPlayerInRange(player,player.getAttackRange() * player.getAttackRange())) {
            SuperviserNormally.getSupervisor().attackMethod(player.getCharacteristics(), c.getCharacteristics());
            player.setTimeBeforeAttack(player.getCharacteristics().recovery());
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
        agendaShower.draw(sb);
        timeShower.draw(sb, new Point((int)(MasterOfMonsGame.WIDTH - timeShower.getWidth()), (int)topMargin),
                new Point((int)(timeShower.getWidth()), (int)(gs.getSmallFont().getLineHeight() + 2 * topMargin)));
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
//            CombatState g = (CombatState) gsm.setState(CombatState.class);
//            g.setPlayer1(player);
//            g.setPlayer2(player);
            attack(player);
        } else if (gim.isKey(Input.Keys.N, KeyStatus.Pressed)) {
            LevelUpMenuState lums = (LevelUpMenuState) gsm.setState(LevelUpMenuState.class);
            lums.setPlayer(player);
        } else if (gim.isKey(Input.Keys.E, KeyStatus.Pressed)) {
            if (selectedOne instanceof Character)
                SuperviserNormally.getSupervisor().meetCharacter(player.getCharacteristics(), ((Character)selectedOne).getCharacteristics());
            // TODO interaction objects
        }
        inventoryShower.handleInput();
        pauseButton.handleInput();
        agendaShower.handleInput();
    }

    /**
     * Make the player level up.
     */
    public void debugLevelUp() {
        ((People)player.getCharacteristics()).upLevel();
    }

    /**
     * Make the player invincible (or vincible)
     */
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
    public void update(Notification notify) {
        if (notify.getEvents().equals(Events.Dead) && notify.getBuffer().equals(player.getCharacteristics()))
            gsm.setState(DeadMenuState.class);
        else if (notify.getEvents().equals(Events.Dead)) {
            for (int i = 0; i < pnjs.size(); i++) {
                if (pnjs.get(i).getCharacteristics().equals(notify.getBuffer())) {
                    pnjs.remove(i);
                    break;
                }
            }
        } else if (notify.getEvents().equals(Events.ChangeQuest))
            Gdx.app.postRunnable(() -> questShower.setQuest(((People)player.getCharacteristics()).getQuest()));
        else if (notify.getEvents().equals(Events.Dialog) && notify.bufferEmpty())
        {
            ArrayList<String> diag = (ArrayList<String>)notify.getBuffer();
            InGameDialogState igds = (InGameDialogState) gsm.setState(InGameDialogState.class);
            igds.setText(diag.get(0));
            for (int i = 1; i < diag.size(); i++) {
                String s = diag.get(i);
                igds.addAnswer(s, () -> SuperviserNormally.getSupervisor().getEvent().notify(new Answer(s)));

            }
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
        agendaShower.dispose();
        player.dispose();
        for (Character pnj : pnjs)
            pnj.dispose();
    }

    @Override
    public void getFocus() {
        inventoryShower.setHided(false);
    }
}
