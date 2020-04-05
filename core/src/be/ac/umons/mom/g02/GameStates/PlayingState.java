package be.ac.umons.mom.g02.GameStates;

import be.ac.umons.mom.g02.Enums.KeyStatus;
import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Enums.Orientation;
import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.*;
import be.ac.umons.mom.g02.Events.Observer;
import be.ac.umons.mom.g02.GameStates.Dialogs.InGameDialogState;
import be.ac.umons.mom.g02.GameStates.Dialogs.OutGameDialogState;
import be.ac.umons.mom.g02.GameStates.Menus.*;
import be.ac.umons.mom.g02.GraphicalObjects.Controls.AgendaShower;
import be.ac.umons.mom.g02.GraphicalObjects.Controls.Button;
import be.ac.umons.mom.g02.GraphicalObjects.Controls.InventoryShower;
import be.ac.umons.mom.g02.GraphicalObjects.*;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Character;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.MapObject;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.OnMapObject;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Player;
import be.ac.umons.mom.g02.Managers.*;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Objects.Characters.MovingPNJ;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Objects.Items.Items;
import be.ac.umons.mom.g02.Objects.Saving;
import be.ac.umons.mom.g02.Quests.Quest;
import be.ac.umons.mom.g02.Regulator.SuperviserNormally;
import be.ac.umons.mom.g02.Regulator.Supervisor;
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
import com.badlogic.gdx.utils.Array;

import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

/**
 * The playing state.
 * A part of the code has been found in https://github.com/foreignguymike/legacyYTtutorials/tree/master/libgdxasteroids by ForeignGuyMike.
 * and https://www.youtube.com/watch?v=P8jgD-V5jG8 by Brent Aureli's - Code School (https://github.com/BrentAureli/SuperMario)
 * @author Guillaume Cardoen
 */
public class PlayingState extends GameState implements Observer {
    /**
     * The horizontal map's length to show.
     */
    public static int SHOWED_MAP_WIDTH = 31;
    /**
     * The vertical map's length to show.
     */
    public static int SHOWED_MAP_HEIGHT = 34;
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
    /**
     * The positions of the map changing objects.
     */
    protected MapObjects changingMapObjects;
    /**
     * The positions where PNJs can be spawned.
     */
    protected MapObjects randomPNJPositions;
    /**
     * The position where the player can have additional information.
     */
    protected MapObjects aboutObjects;
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
    protected LifeBar lifeBar;
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
     * The objects that are on this map (items).
     */
    protected List<MapObject> mapObjects;
    /**
     * The button pause.
     */
    protected be.ac.umons.mom.g02.GraphicalObjects.Controls.Button pauseButton;
    /**
     * The agenda's shower
     */
    protected AgendaShower agendaShower;
    /**
     * The clock used in this state.
     */
    protected TimeShower timeShower;
    /**
     * The state of dialog if one is active.
     */
    protected InGameDialogState dialogState;
    /**
     * The graphical object showing older (important) notification if needed.
     */
    protected NotificationRappel notificationRappel;

    /**
     * The GameKeyManager of the game
     */
    protected GameKeyManager gkm;
    /**
     * The instance of <code>Supervisor</code> to use.
     */
    protected Supervisor supervisor;

    /**
     * @param gs The game's graphical settings
     */
    public PlayingState(GraphicalSettings gs) {
        super(gs);
        setSupervisor();
    }

    /**
     * Default constructor. USES IT ONLY FOR TESTS PURPOSES
     */
    protected PlayingState() {}

    /**
     * Set the supervisor to use in this state.
     */
    protected void setSupervisor() {
        supervisor = Supervisor.getSupervisor();
    }

    @Override
    public void init() {
        super.init();
        sb = new SpriteBatch();
        am = AnimationManager.getInstance();
        gmm = GameMapManager.getInstance();
        gkm = GameKeyManager.getInstance();
        questShower = new QuestShower(gsm, gs);
        agendaShower = new AgendaShower(gs);
        timeShower = new TimeShower(gs);
        notificationRappel = new NotificationRappel(gs);
        player = new Player(gs,MasterOfMonsGame.WIDTH / 2, MasterOfMonsGame.HEIGHT / 2);
        mapObjects = new ArrayList<>();

        if (MasterOfMonsGame.getGameToLoad() != null)
            loadOldGame();

        Supervisor.getEvent().add(this, Events.Dead, Events.ChangeQuest, Events.Dialog, Events.UpLevel,Events.DisplayMessage,Events.Shop,Events.Teleport);

        supervisor.setGraphic(questShower,this);

        if (MasterOfMonsGame.getGameToLoad() == null && MasterOfMonsGame.getSaveToLoad() == null)
            initMap("Tmx/Umons_Nimy.tmx");

        inventoryShower = new InventoryShower(gs, player);

        cam = new OrthographicCamera(SHOWED_MAP_WIDTH * tileWidth, SHOWED_MAP_HEIGHT * tileHeight);
        cam.position.x = player.getPosX();
        cam.position.y = player.getPosY();
        gmm.setView(cam);
        cam.update();

        lifeBar = new LifeBar(gs);
        lifeBar.setForegroundColor(gcm.getColorFor("lifeBar"));
        expBar = new ProgressBar(gs);
        expBar.setForegroundColor(gcm.getColorFor("experienceBar"));
        energyBar = new ProgressBar(gs);
        energyBar.setForegroundColor(gcm.getColorFor("energyBar"));

        pauseButton = new Button(gs);
        pauseButton.setText("||");
        pauseButton.setOnClick(() -> gsm.setState(InGameMenuState.class));
        pauseButton.setFont(gs.getSmallFont());
    }

    /**
     * Load an old game (MasterOfMonsGame.getGameToLoad()).
     */
    protected void loadOldGame() {
        supervisor.oldGame(MasterOfMonsGame.getGameToLoad(), this, gs);
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

    /**
     * Initialise all the variables for the given map.
     * @param mapPath The map's path
     * @param spawnX On which CASE the character will be horizontally.
     * @param spawnY On which CASE the character will be vertically
     */
    public void initMap(String mapPath, int spawnX, int spawnY) {
        gmm.setMap(mapPath);
        Maps map = supervisor.getMaps(mapPath);
        pnjs = getPNJsOnMap(mapPath);

        tileWidth = (int)gmm.getActualMap().getProperties().get("tilewidth");
        tileHeight = (int)gmm.getActualMap().getProperties().get("tileheight");
        mapWidth = (int)gmm.getActualMap().getProperties().get("width");
        mapHeight = (int)gmm.getActualMap().getProperties().get("height");
        if (cam != null) {
            cam.viewportWidth = SHOWED_MAP_WIDTH * tileWidth;
            cam.viewportHeight = SHOWED_MAP_HEIGHT * tileHeight;
        }
        MapLayer collLayer = gmm.getActualMap().getLayers().get("Interdit");
        if (collLayer != null)
            collisionObjects = collLayer.getObjects();
        MapLayer changeLayer = gmm.getActualMap().getLayers().get("Changer");
        if (changeLayer != null)
            changingMapObjects = changeLayer.getObjects();
        MapLayer pnjLayer = gmm.getActualMap().getLayers().get("RandomPNJ");
        if (pnjLayer != null)
            randomPNJPositions = pnjLayer.getObjects();
        MapLayer aboutLayer = gmm.getActualMap().getLayers().get("About");
        if (aboutLayer != null)
            aboutObjects = aboutLayer.getObjects();

        initPNJsPositions(pnjs);
        initPlayerPosition(spawnX, spawnY);
        Supervisor.getEvent().notify(new PlaceInMons(map));
    }

    /**
     * @param mapName The map where the PNJs must be.
     * @return A list of the PNJs that must be drawn on the map.
     */
    protected List<Character> getPNJsOnMap(String mapName) {
        Maps map = supervisor.getMaps(mapName);
        List<Character> pnjs = new LinkedList<>();
        supervisor.init(player.getCharacteristics(), player);
        for (Mobile mob : supervisor.getMobile(map)) {
            Character c = new Character(gs, mob);
            pnjs.add(c);
            supervisor.init(mob, c);
        }

        for (MovingPNJ mv : supervisor.getMovingPnj(map)) {
            Character c = new Character(gs, mv);
            pnjs.add(c);
            mv.initialisation(c, this, player);
        }

        return pnjs;
    }

    /**
     * Spawn all the PNJs randomly.
     */
    protected void initPNJsPositions(List<Character> pnjs) {
        Array<RectangleMapObject> rmos = randomPNJPositions.getByType(RectangleMapObject.class);
        for (Character c : pnjs) {
            initPNJPosition(c, rmos);
        }
    }

    /**
     * Set the position of the PNJ by selecting one in the given array.
     * @param c The PNJ.
     * @param rmos The array representing the positions where the PNJ can be.
     */
    protected void initPNJPosition(Character c, Array<RectangleMapObject> rmos) {
        if (rmos.size <= 0)
            return;
        Random random = new Random();
        int posIndex = random.nextInt(rmos.size);
        Rectangle mo = rmos.removeIndex(posIndex).getRectangle();
        Rectangle mapRect = new Rectangle( mo.x * 2 / tileWidth, (mapHeight * tileHeight - mo.y - mo.height) / tileHeight, mo.width * 2 / tileWidth, mo.height / tileHeight);
        c.setMapPos(new Point((int)(mapRect.x - mapRect.y) * tileWidth / 2 + mapHeight * tileWidth / 2,
                mapHeight * tileHeight / 2 - (int)(mapRect.x + mapRect.y) * tileHeight / 2));
        c.setMapWidth(mapWidth * tileWidth);
        c.setMapHeight(mapHeight * tileHeight);
        c.setTileWidth(tileWidth);
        c.setTileHeight(tileHeight);
    }

    /**
     * Add the given item at the given position.
     * @param item The item
     * @param pos The position (in cases)
     * @param map The map on which this item is
     */
    public void addItemToMap(Items item, Point pos, String map) {
        Point mapPos = new Point((pos.x - pos.y) * tileWidth / 2 + mapHeight * tileWidth / 2,
                mapHeight * tileHeight / 2 - (pos.x + pos.y) * tileHeight / 2);
        MapObject.OnMapItem omi = new MapObject.OnMapItem(item, mapPos, map);
        addItemToMap(omi);
    }
    /**
     * Add the given item to the list of items to draw.
     * @param omi The item to add.
     */
    public void addItemToMap(MapObject.OnMapItem omi) {
        mapObjects.add(new MapObject(gs, omi));
    }

    /**
     * Add the given items to the list of items to draw.
     * @param items The items to add.
     */
    public void addItemsToMap(MapObject.OnMapItem[] items) {
        for (MapObject.OnMapItem omi : items)
            addItemToMap(omi);
    }

    /**
     * @return An array representing all the items that are drawn on all maps
     */
    public MapObject.OnMapItem[] getItemsOnMap() {
        List<MapObject.OnMapItem> res = new ArrayList<>();
        for (MapObject omo : mapObjects)
            res.add(omo.getCharacteristics());
        return res.toArray(new MapObject.OnMapItem[0]);
    }

    /**
     * Spawn the player and set all the needed information with the map.
     * @param spawnX On which CASE the player must spawn horizontally.
     * @param spawnY On which CASE the player must spawn vertically.
     */
    public void initPlayerPosition(int spawnX, int spawnY) {
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

    @Override
    public void update(float dt) {
        handleInput();
        makePlayerMove(dt);
        cam.update();

        if (((People)player.getCharacteristics()).isInvincible())
            player.getCharacteristics().setActualLife(player.getCharacteristics().lifeMax());

        supervisor.callMethod(dt);
        notificationRappel.update(dt);

        lifeBar.setValue((int)player.getCharacteristics().getActualLife());
        lifeBar.setMaxValue((int)player.getCharacteristics().lifeMax());
        lifeBar.update(dt);
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
        double velocity = Supervisor.getPeople().getSpeed();
        int toMove = (int)Math.round(velocity * dt * tileWidth);
        int toMoveX = 0, toMoveY = 0;

        if (gim.isKey("movingDownward", KeyStatus.Down)) {
            player.setOrientation(Orientation.Bottom);
            toMoveY += -toMove;
        }
        if (gim.isKey("movingUpward", KeyStatus.Down)) {
            player.setOrientation(Orientation.Top);
            toMoveY += toMove;
        }
        if (gim.isKey("movingLeftward", KeyStatus.Down)) {
            player.setOrientation(Orientation.Left);
            toMoveX += -toMove;
        }
        if (gim.isKey("movingRightward", KeyStatus.Down)) {
            player.setOrientation(Orientation.Right);
            toMoveX += toMove;
        }

        translateCamera(player.getPosX() + toMoveX, player.getPosY() + toMoveY);
        player.move(toMoveX, toMoveY);
        if (checkForCollision(player)) {
            player.move(-toMoveX, -toMoveY);
        }
        checkForMapChanging(player);
        checkForNearSelectable(player);
        checkForAboutCollision(player);

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
        translateCameraFollowingPlayer(x, y);
    }

    /**
     * Move the camera in a way which follow the player.
     * @param x Horizontal position of the player.
     * @param y Vertical position of the player.
     */
    protected void translateCameraFollowingPlayer(int x, int y) { // Not direct in translateCamera for using later in extension
        double minX = (double)SHOWED_MAP_WIDTH / 2;
        double minY = (double)SHOWED_MAP_HEIGHT / 4;

        Rectangle pmr = player.getMapRectangle();

        if (pmr.x > minX && pmr.x < mapWidth - minX)
            cam.position.x = x;
        if (pmr.y > minY && pmr.y < mapHeight - minY)
            cam.position.y = y;

    }

    /**
     * Check if the player is in collision with one of the given collision area.
     * @param objectsToCheck Which objects must be checked.
     * @param player The player.
     * @return If the player is in collision with one of the given collision area.
     */
    public RectangleMapObject checkForCollision(MapObjects objectsToCheck, Character player) {
        if (objectsToCheck == null)
            return null;
        for (RectangleMapObject rectangleMapObject : objectsToCheck.getByType(RectangleMapObject.class)) {
            Rectangle rect = rectangleMapObject.getRectangle();
            Rectangle playerRect = player.getMapRectangle();
            Rectangle mapRect = new Rectangle( rect.x * 2 / tileWidth, (mapHeight * tileHeight - rect.y - rect.height) / tileHeight, rect.width * 2 / tileWidth, rect.height / tileHeight);
            if (Intersector.overlaps(mapRect, playerRect)) {
                return rectangleMapObject;
            }
        }
        return null;
    }

    /**
     * Check if the player is in collision with one of the collision area on the map.
     * @param player The player.
     * @return If the player is in collision with one of the collision area on the map.
     */
    public boolean checkForCollision(Character player) {
        return checkForCollision(collisionObjects, player) != null;
    }

    /**
     * Check if the player is on a case to change the map.
     * @param player The player.
     */
    protected void checkForMapChanging(Player player) {
        RectangleMapObject rectangleMapObject = checkForCollision(changingMapObjects, player);
        if (rectangleMapObject == null)
            return;
        if (rectangleMapObject.getProperties().containsKey("spawnX") && rectangleMapObject.getProperties().containsKey("spawnY")) {
            int spawnX, spawnY;
            spawnX = (int)rectangleMapObject.getProperties().get("spawnX");
            spawnY = (int)rectangleMapObject.getProperties().get("spawnY");
            initMap(rectangleMapObject.getName(), spawnX, spawnY);
        } else
            initMap(rectangleMapObject.getName());
    }

    /**
     * Check if the player is in collision with one of the about collision area
     * @param player The player.
     */
    protected void checkForAboutCollision(Player player) {
        RectangleMapObject rectangleMapObject = checkForCollision(aboutObjects, player);
        if (rectangleMapObject == null)
            return;
        try {
            supervisor.analyseIdMap(rectangleMapObject.getName());
        } catch (Exception e) {
            Gdx.app.error("PlayingState", "The following tag triggered an exception : " + rectangleMapObject.getName(), e);
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
     * @param inFrontOf If the PNJ must be in front of the player.
     * @return Return all the PNJs near enough from the player.
     */
    protected List<Character> getCharacterInRange(Player player, double dist, boolean inFrontOf) {
        LinkedList<Character> res = new LinkedList<>();
        for (Character character : pnjs) {
            double d = Math.pow(player.getPosX() - character.getPosX(), 2) + Math.pow(player.getPosY() - character.getPosY(), 2);
            if (d < dist) {
                if (! inFrontOf || isInFrontOf(player, character))
                    res.add(character);
            }
        }
        return res;
    }

    /**
     * Check if the second character is in front of the first one.
     * @param c1 The first character.
     * @param c2 The second character.
     * @return If the second character is in front of the first one.
     */
    protected boolean isInFrontOf(Character c1, Character c2) {
        switch (c1.getOrientation()){
            case Top:
                return c2.getPosY() >= c1.getPosY();
            case Left:
                return c2.getPosX() <= c1.getPosX();
            case Bottom:
                return c2.getPosY() <= c1.getPosY();
            case Right:
                return c2.getPosX() >= c1.getPosX();
        }
        return false;
    }

    /**
     * Executed when the player launch an attack.
     * @param player The player.
     */
    protected void attack(Player player) {
        if (player.isRecovering())
            return;
        player.expandAttackCircle();
        for (Character c : getCharacterInRange(player,player.getAttackRange() * player.getAttackRange(), true)) {
            supervisor.attackMethod(player.getCharacteristics(), c.getCharacteristics());
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
            if (mo.getMap().equals(gmm.getActualMapName()))
                mo.draw(sb, mo.getPosX() - (int)cam.position.x + MasterOfMonsGame.WIDTH / 2, mo.getPosY() - (int)cam.position.y + MasterOfMonsGame.HEIGHT / 2, tileWidth, tileHeight);

        sb.begin();
        if (gs.mustShowMapCoordinates()) {
            gs.getSmallFont().draw(sb, String.format("(%f, %f)", player.getMapRectangle().x, player.getMapRectangle().y), (int)leftMargin, (int)(10 * topMargin - topBarHeight));
            gs.getSmallFont().draw(sb, String.format("(%d, %d)", player.getPosX(), player.getPosY()), (int)leftMargin, (int)(10 * topMargin - topBarHeight - gs.getSmallFont().getLineHeight()));
        }
        sb.end();

        int inventoryShowerHeight = tileHeight * 2;
        // Draw the HUD
        agendaShower.draw(sb);
        timeShower.draw(sb, new Point((int)(MasterOfMonsGame.WIDTH - timeShower.getWidth()), (int)topMargin * 2 + inventoryShowerHeight),
                new Point((int)(timeShower.getWidth()), (int)(gs.getSmallFont().getLineHeight() + 2 * topMargin)));
        notificationRappel.draw(sb, new Point((int)(MasterOfMonsGame.WIDTH - notificationRappel.getWidth()), (int)topMargin * 2),
                new Point((int)(notificationRappel.getWidth()), (int)(gs.getSmallFont().getLineHeight() + 2 * topMargin)));
        questShower.draw(sb, tileWidth / 2 - QuestShower.TEXT_AND_RECTANGLE_MARGIN, (int)(MasterOfMonsGame.HEIGHT - 2 * topMargin - topBarHeight));
        inventoryShower.draw(sb, MasterOfMonsGame.WIDTH / 2, inventoryShowerHeight, new Point(tileWidth, tileWidth));
        lifeBar.draw(sb, (int)leftMargin, MasterOfMonsGame.HEIGHT - (int)topMargin - topBarHeight, topBarWidth, topBarHeight);
        expBar.draw((int)leftMargin * 2 + topBarWidth, MasterOfMonsGame.HEIGHT - (int)topMargin - topBarHeight, topBarWidth, topBarHeight);
        energyBar.draw((int)leftMargin * 3 + topBarWidth * 2, MasterOfMonsGame.HEIGHT - (int)topMargin - topBarHeight, topBarWidth, topBarHeight);
        Point pauseButtonSize = new Point((int)(2 * gs.getSmallFont().getXHeight() + 2 * leftMargin), (int)(2 * topMargin + gs.getSmallFont().getLineHeight()));
        pauseButton.draw(sb, new Point(MasterOfMonsGame.WIDTH - pauseButtonSize.x, (int)(MasterOfMonsGame.HEIGHT - pauseButtonSize.y - topBarHeight - 2 * topMargin)),
                pauseButtonSize);
    }

    @Override
    public void handleInput() {
        if (gim.isKey(Input.Keys.ESCAPE, KeyStatus.Pressed)) {
            InGameMenuState igms = (InGameMenuState) gsm.setState(InGameMenuState.class);
            igms.setPlayingState(this);
        }
        if (gim.isKey(Input.Keys.B, KeyStatus.Down) && gim.isKey(Input.Keys.UP, KeyStatus.Pressed)) {
            DebugMenuState dms = (DebugMenuState) gsm.setState(DebugMenuState.class);
            dms.setPlayingState(this);
        }
        if (gim.isKey("debugLevelUp", KeyStatus.Pressed))
            debugLevelUp();
        if (gim.isKey("debugMakeInvincible", KeyStatus.Pressed))
            debugMakeInvincible();
        if (gim.isKey("debugGetObject", KeyStatus.Pressed))
            gsm.setState(DebugGetObject.class);
        if (gim.isKey("attack", KeyStatus.Pressed))
            attack(player);
        if (gim.isKey("dropAnObject", KeyStatus.Pressed))
            dropSelectedObject();
        if (gim.isKey("useAnObject", KeyStatus.Pressed)) {
            InventoryItem ii = inventoryShower.getSelectedItem();
            if (ii != null)
                Supervisor.getPeople().useObject(ii.getItem());
            if (timeShower != null && ii != null)
                timeShower.extendOnFullWidth(ii.getItem().explainAction());
        }
        if (gim.isKey("quickSave", KeyStatus.Pressed)) {
            timeShower.extendOnFullWidth(GraphicalSettings.getStringFromId("quickSaving"));
            quickSave();
        }
        if (gim.isKey("quickLoad", KeyStatus.Pressed))
            quickLoad(gsm, gs);
        if (gim.isKey("pointsAttribution", KeyStatus.Pressed))
            goToLevelUpState();
        if (gim.isKey("pickUpAnObject", KeyStatus.Pressed)) {
            if (selectedOne != null && selectedOne instanceof MapObject) {
                if (Supervisor.getPeople().pushObject(((MapObject)selectedOne).getItem()))
                    pickUpAnObject();
            }
        }
        if (gim.isKey("interact", KeyStatus.Pressed)) {
            if (selectedOne != null && selectedOne instanceof Character)
                supervisor.meetCharacter(player.getCharacteristics(), ((Character)selectedOne).getCharacteristics());
        }
        inventoryShower.handleInput();
        pauseButton.handleInput();
        agendaShower.handleInput();
    }

    /**
     * Go to the leveling up state.
     */
    public void goToLevelUpState() {
        LevelUpMenuState lums = (LevelUpMenuState) gsm.setState(LevelUpMenuState.class);
        lums.setPlayer(Supervisor.getPeople());
        lums.setOnPointsAttributed(this::onPointsAttributed);
    }

    /**
     * What to do when the player has attributed his points
     */
    protected void onPointsAttributed(int strength, int defence, int agility) {
        int pointLevel = ((People)player.getCharacteristics()).getPointLevel();
        if (pointLevel != 0)
            notificationRappel.addANotification("pointsToAttribute", String.format(GraphicalSettings.getStringFromId("pointsToAttribute"),
                    pointLevel, Input.Keys.toString(gkm.getKeyCodeFor("pointsAttribution"))));
        else
            notificationRappel.removeANotification("pointsToAttribute");
    }

    /**
     * Pick up an object. (Doesn't check if it can be picked up)
     */
    protected void pickUpAnObject() {
        inventoryShower.addAnItem(((MapObject)selectedOne).getItem());
        mapObjects.remove(selectedOne);
        selectedOne = null;
    }

    /**
     * Drop the selected item from the inventory on the map.
     * @return The dropped item
     */
    public MapObject dropSelectedObject() {
        InventoryItem dropped = inventoryShower.dropSelectedItem();
        if (dropped != null) {
            MapObject mo = new MapObject(gs, dropped.getItem());
            mo.setMapPos(new Point(player.getPosX(), player.getPosY()));
            mo.setMap(gmm.getActualMapName());
            mapObjects.add(mo);
            Supervisor.getPeople().removeObject(dropped.getItem());
            dropped.getItem().setMaps(supervisor.getMaps(gmm.getActualMapName()));
            return mo;
        }
        return null;
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
            notificationRappel.addANotification("invincibleNotification", GraphicalSettings.getStringFromId("playerIsInvincible"));
        } else {
            ((People)player.getCharacteristics()).invincible(true);
            lifeBar.setForegroundColor(new Color(0x212121AA));
        }
    }

    /**
     * Change the player speed if the user ask it as a debug option.
     */
    public void debugChangePlayerSpeed() {
        Supervisor.getPeople().setSpeed(5);
        notificationRappel.addANotification("speedChangedNotification", GraphicalSettings.getStringFromId("playerIsFaster"));
    }

    @Override
    public void update(Notification notify) {
        if (notify.getEvents().equals(Events.Dead) && notify.getBuffer().equals(player.getCharacteristics()))
            gsm.setState(DeadMenuState.class);
        else if (notify.getEvents().equals(Events.Shop))
            gsm.setState(SellerMenuState.class);
        else if (notify.getEvents().equals(Events.DisplayMessage) && notify.bufferNotEmpty())
            notificationRappel.addANotification(((DisplayMessage)notify).getId(),((DisplayMessage)notify).getBuffer());
        else if (notify.getEvents().equals(Events.Dead)) {
            for (int i = 0; i < pnjs.size(); i++) {
                if (pnjs.get(i).getCharacteristics().equals(notify.getBuffer())) {
                    if (selectedOne != null && selectedOne.equals(pnjs.get(i)))
                        selectedOne = null;
                    pnjs.remove(i);
                    break;
                }
            }
        } else if (notify.getEvents().equals(Events.ChangeQuest)) {
//            Gdx.app.postRunnable(() -> {
                Quest q = ((People)player.getCharacteristics()).getQuest();
                questShower.setQuest(q);
                NewChapterState ncms = (NewChapterState) gsm.setState(NewChapterState.class);
                ncms.setNewChapterName(q.getName());
//            });
        } else if (notify.getEvents().equals(Events.Dialog) && notify.bufferNotEmpty()) {
            ArrayList<String> diag = (ArrayList<String>)notify.getBuffer();
            Gdx.app.postRunnable(() -> updateDialog(diag));
        } else if (notify.getEvents().equals(Events.UpLevel) && notify.getBuffer() == player.getCharacteristics()) {
            timeShower.extendOnFullWidth(GraphicalSettings.getStringFromId("gainALevel"));
            notificationRappel.addANotification("pointsToAttribute", String.format(GraphicalSettings.getStringFromId("pointsToAttribute"),
                    ((People)player.getCharacteristics()).getPointLevel(), Input.Keys.toString(gkm.getKeyCodeFor("pointsAttribution"))));
        } else if (notify.getEvents().equals(Events.Teleport) && notify.bufferNotEmpty()) {
            initMap((String)notify.getBuffer());
        }
    }

    /**
     * Update the dialog state with the given information.
     * @param diag The text of the dialog (index 0) and the answers (index 1+).
     */
    public void updateDialog(ArrayList<String> diag) {
        if (diag.get(0).equals("ESC")) {
            dialogState = null;
            inventoryShower.setHided(false);
            gsm.removeFirstStateFromStack();
            return;
        }
        if (dialogState == null) {
            dialogState = (InGameDialogState) gsm.setStateWithoutAnimation(InGameDialogState.class);
            dialogState.setMustQuitWhenAnswered(false);
            inventoryShower.setHided(true);
        }
        dialogState.setText(diag.get(0));
        for (int i = 1; i < diag.size(); i++) {
            String s = diag.get(i);
            dialogState.addAnswer(s, () -> Supervisor.getEvent().notify(new Answer(s)));
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

    /**
     * Executed when the user want to quick save a game.
     */
    public static void quickSave() {
        String name = String.format("QS-MOM-%s", new SimpleDateFormat("dd_MM_yy_HH:mm:ss").format(new Date()));
        String newName = getNonExistingFilePath(name);
        Saving save = SuperviserNormally.getSupervisor().getSave();
        save.setNameSave(newName);
        save.signal();
        MasterOfMonsGame.getSettings().setLastSavePath(newName);
    }

    /**
     * @param name The base name.
     * @return A alteration of the base name (or itself) which doesn't exists in the current directory.
     */
    private static String getNonExistingFilePath(String name) {
        String newName = name;
        int i = 1;
        while (new File(new File(".").getAbsoluteFile().getParent(), newName + ".mom").exists()) {
            newName = String.format("%s(%d)", name, i);
            i++;
        }
        return newName + ".mom";
    }

    /**
     * Executed when the user want to quick load a game.
     * @param gsm The game's state manager (needed for the dialog)
     */
    public static void quickLoad(GameStateManager gsm, GraphicalSettings gs) {
        OutGameDialogState ogds = (OutGameDialogState) gsm.setStateWithoutAnimation(OutGameDialogState.class);
        ogds.setText(GraphicalSettings.getStringFromId("sureLoad"));
        ogds.addAnswer("yes", () ->
                Supervisor.getSupervisor().oldGame(MasterOfMonsGame.getSettings().getLastSavePath(),gs));
        ogds.addAnswer("no");
    }

    /**
     * @return The player position.
     */
    public Point getPlayerPosition() {
        return new Point(player.getPosX(), player.getPosY());
    }

    /**
     * @param pos The player position
     */
    public void setPlayerPosition(Point pos) {
        player.setPosX(pos.x);
        player.setPosY(pos.y);
    }

    /**
     * @return The player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return A list of the graphical object representing all the objects on the map.
     */
    public List<MapObject> getMapObjects() {
        return mapObjects;
    }

    /**
     * @return The instance of <code>TimeShower</code> used in this state.
     */
    public TimeShower getTimeShower() {
        return timeShower;
    }

    /**
     * @return A list of ths PNJs on this map.
     */
    public List<Character> getPNJs() {
        return pnjs;
    }
}
