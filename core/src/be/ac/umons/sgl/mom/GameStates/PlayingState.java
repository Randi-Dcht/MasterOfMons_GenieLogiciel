package be.ac.umons.sgl.mom.GameStates;

import be.ac.umons.sgl.mom.Enums.KeyStatus;
import be.ac.umons.sgl.mom.Enums.Orientation;
import be.ac.umons.sgl.mom.Enums.Type;
import be.ac.umons.sgl.mom.GameStates.Dialogs.InGameDialogState;
import be.ac.umons.sgl.mom.GameStates.Menus.DebugMenuState;
import be.ac.umons.sgl.mom.GameStates.Menus.InGameMenuState;
import be.ac.umons.sgl.mom.GraphicalObjects.Player;
import be.ac.umons.sgl.mom.GraphicalObjects.Controls.InventoryShower;
import be.ac.umons.sgl.mom.GraphicalObjects.ProgressBar;
import be.ac.umons.sgl.mom.GraphicalObjects.QuestShower;
import be.ac.umons.sgl.mom.Managers.AnimationManager;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameMapManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.Supervisor;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import be.ac.umons.sgl.mom.Objects.*;
import com.badlogic.gdx.Input;

import java.awt.*;
import java.util.Timer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

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
        am = new AnimationManager();
        gmm = gsm.getGameMapManager();

        gmm.setMap("Tmx/Umons_Nimy.tmx");

        tileWidth = (int)gmm.getActualMap().getProperties().get("tilewidth");
        tileHeight = (int)gmm.getActualMap().getProperties().get("tileheight");
        mapWidth = (int)gmm.getActualMap().getProperties().get("width");
        mapHeight = (int)gmm.getActualMap().getProperties().get("height");
        collisionObjects = gmm.getActualMap().getLayers().get("Interdit").getObjects();

        cam = new OrthographicCamera(SHOWED_MAP_WIDTH * tileWidth, SHOWED_MAP_HEIGHT * tileHeight * 2);
        cam.update();
        gmm.setView(cam);

        questShower = new QuestShower(gs, am);
        player = new Player(gs,MasterOfMonsGame.WIDTH / 2, MasterOfMonsGame.HEIGHT / 2, tileWidth, tileHeight, mapWidth * tileWidth, mapHeight * tileHeight); // TODO : BUG AVEC EN BAS ET A GAUCHE
        inventoryShower = new InventoryShower(gim, gs, am, player);


/*/!\devra être mis mais pourra changer de place (Randy pour Guillaume)/!\*/
        /*supprimer =>*///Rule rule = new Rule("TestRule",questShower);
        /*supprimer =>*/Supervisor.newParty("GuiRndMaxi",Type.normal,questShower,gs); //<= ajouter pour la save
        /*supprimer => -------------------------*/
        /*supprimer =>*/Timer timer = new Timer();
        /*supprimer =>*/Delete tt = new Delete(Supervisor.getPeople());
        /*supprimer =>*/timer.schedule(tt,0,100);

        lifeBar = new ProgressBar();
        lifeBar.setForegroundColor(new Color(213f / 255, 0, 0, .8f));
        expBar = new ProgressBar();
        expBar.setForegroundColor(new Color(46f / 255, 125f / 255, 50f / 255, .8f));
        energyBar = new ProgressBar();
        energyBar.setForegroundColor(new Color(2f / 255, 119f / 255, 189f / 255, .8f));
    }

    @Override
    public void update(float dt) {
        handleInput();
        am.update(dt);
        makePlayerMove(dt);
        cam.update();

        lifeBar.setValue((int)player.getCharacteristics().getLife());
        lifeBar.setMaxValue((int)player.getCharacteristics().lifemax());
        expBar.setValue((int)player.getCharacteristics().getExperience());
        expBar.setMaxValue((int)player.getCharacteristics().minExperience());
        energyBar.setValue((int)player.getCharacteristics().getEnergy());
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

        player.move(toMoveX, toMoveY);
        if (checkForCollision(player)) {
            player.move(-toMoveX, -toMoveY);
            return;
        }

        translateCamera(player.getPosX(), player.getPosY());
    }

    /**
     * Move the camera.
     * @param x Horizontal position of the player.
     * @param x Vertical position of the player.
     */
    protected void translateCamera(int x, int y) {
        cam.position.x = x;
        cam.position.y = y;
        double maxX = (mapHeight + mapWidth) * tileHeight;
        if (cam.position.x < SHOWED_MAP_WIDTH * tileWidth / 2)
            cam.position.x = SHOWED_MAP_WIDTH * tileWidth / 2;
        else if (cam.position.x > maxX)
            cam.position.x = (float)maxX;

        if (cam.position.y > (mapHeight - SHOWED_MAP_HEIGHT) * tileHeight / 2)
            cam.position.y = (mapHeight - SHOWED_MAP_HEIGHT) * tileHeight / 2;
        else if (cam.position.y < -(mapHeight - SHOWED_MAP_HEIGHT / 2) * tileHeight)
            cam.position.y = -(mapHeight - SHOWED_MAP_HEIGHT / 2) * tileHeight;
    }

    /**
     * Check if the player is in collision with one of the collision area on the map.
     * @param player The player.
     * @return If the player is in collision with one of the collision area on the map.
     */
    protected boolean checkForCollision(Player player) {
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

    @Override
    public void draw() {
        int topBarWidth = (int)((MasterOfMonsGame.WIDTH - 4 * leftMargin) / 3);
        int topBarHeight = 10;

        gmm.render();
        player.draw(sb);

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
    }

    @Override
    public void handleInput() {
        if (gim.isKey(Input.Keys.ESCAPE, KeyStatus.Pressed))
            gsm.setState(InGameMenuState.class);
        else if (gim.isKey(Input.Keys.B, KeyStatus.Down) && gim.isKey(Input.Keys.UP, KeyStatus.Down))
            gsm.setState(DebugMenuState.class);
        else if (gim.isKey(Input.Keys.P, KeyStatus.Pressed)) {
            inventoryShower.setHided(true);
            GameState g = gsm.setState(InGameDialogState.class); // TODO : Delete (used for test purposes)
            ((InGameDialogState)g).setText("YAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");
            ((InGameDialogState)g).addAnswer("YAY !", "yay...", "YAAAAAYYYY !!!");
        } else if (gim.isKey(Input.Keys.C, KeyStatus.Pressed)) {
            CombatState g = (CombatState) gsm.setState(CombatState.class);
            g.setPlayer1(player);
            g.setPlayer2(player);
        }
        inventoryShower.handleInput();
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
