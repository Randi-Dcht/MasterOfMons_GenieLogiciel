package be.ac.umons.sgl.mom.GameStates;

import be.ac.umons.sgl.mom.Animations.DoubleAnimation;
import be.ac.umons.sgl.mom.Enums.GameKeys;
import be.ac.umons.sgl.mom.Enums.GameStates;
import be.ac.umons.sgl.mom.Enums.KeyStatus;
import be.ac.umons.sgl.mom.Enums.Orientation;
import be.ac.umons.sgl.mom.GraphicalObjects.Character;
import be.ac.umons.sgl.mom.GraphicalObjects.InventoryShower;
import be.ac.umons.sgl.mom.GraphicalObjects.QuestShower;
import be.ac.umons.sgl.mom.Interfaces.Animation;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import be.ac.umons.sgl.mom.Objects.Quest;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static be.ac.umons.sgl.mom.GraphicalObjects.QuestShower.TEXT_AND_RECTANGLE_MARGIN;

public class PlayingState extends GameState { // TODO : Put all disposes
    public static final int SHOWED_MAP_WIDTH = 31;
    public static final int SHOWED_MAP_HEIGHT = 17;
    private final float VELOCITY = 500;

//    protected static final String FINISHED_QUEST_INDICATOR = "<> ";
//    protected static final String ACTIVATED_QUEST_INDICATOR = "<!> ";
//    protected static final String UNACTIVATED_QUEST_INDICATOR = ">!< ";


    protected int mapWidth;
    protected int mapHeight;
    protected int tileWidth;
    protected int tileHeight;

    protected Map<String, Animation> animationsList;

    // Inspired from https://www.youtube.com/watch?v=zckxJn751Gw&list=PLXY8okVWvwZ0qmqSBhOtqYRjzWtUCWylb&index=3&t=0s by dermetfan
    // Map showing inspired from https://www.youtube.com/watch?v=P8jgD-V5jG8&list=PLZm85UZQLd2SXQzsF-a0-pPF6IWDDdrXt&index=6 by Brent Aureli's - Code School (https://github.com/BrentAureli/SuperMario)

    private SpriteBatch sb;
    private TiledMap map;
    private IsometricTiledMapRenderer itmr;
    private MapObjects collisionObjects;
    public static OrthographicCamera cam;
    private QuestShower questShower;
    private InventoryShower inventoryShower;
    private Character player;

    public PlayingState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }

    @Override
    public void init() {
        sb = new SpriteBatch();

        map = new TmxMapLoader().load("Map/isoTest.tmx");
        tileWidth = (int)map.getProperties().get("tilewidth");
        tileHeight = (int)map.getProperties().get("tileheight");
        mapWidth = (int)map.getProperties().get("width");
        mapHeight = (int)map.getProperties().get("height");


        gs.setQuestFont("Fonts/Comfortaa/Comfortaa-Light.ttf", tileHeight / 2);

//        itmr = new OrthogonalTiledMapRenderer(map);
        itmr = new IsometricTiledMapRenderer(map);
        collisionObjects = map.getLayers().get("Interdit").getObjects();

//        System.out.println(itmr.getUnitScale());
        cam = new OrthographicCamera(SHOWED_MAP_WIDTH * tileWidth, SHOWED_MAP_HEIGHT * tileHeight * 2);
        cam.position.x = 212;
        cam.position.y = 318;
        cam.update();

        questShower = new QuestShower(gs, sb, tileWidth / 2 - TEXT_AND_RECTANGLE_MARGIN, MasterOfMonsGame.HEIGHT - tileHeight / 2);
        inventoryShower = new InventoryShower(sb, MasterOfMonsGame.WIDTH / 2, tileHeight, tileWidth, tileHeight);
        player = new Character(MasterOfMonsGame.WIDTH / 2, MasterOfMonsGame.HEIGHT / 2, tileWidth, tileHeight, mapWidth * tileWidth, mapHeight * tileHeight); // TODO : BUG AVEC EN BAS ET A GAUCHE

        Quest q = new Quest("Test");
        Quest q2 = new Quest("Test222222222222222222222");
        Quest q3 = new Quest("Test3");
        q.addSubQuests(q2, q3);
        q2.finish();
        q3.activate();
        questShower.setQuest(q);

        animationsList = new HashMap<>();
        animateHUD();
    }

    @Override
    public void update(float dt) {
        handleInput();

        for (Animation a : animationsList.values()) {
            a.update(dt);
        }

        for (Iterator<String> it = animationsList.keySet().iterator(); it.hasNext();) { // Done in 2 times because of ConcurrentModificationException
            String key = it.next();
            Animation a = animationsList.get(key);
            if (a.isFinished())
                it.remove();
        }

        makePlayerMove(dt);
        cam.update();
    }

    protected void makePlayerMove(float dt) {
        int toMove = Math.round(VELOCITY * dt);
        int toMoveX = 0, toMoveY = 0;

        if (gim.isKey(GameKeys.Down, KeyStatus.Down)) {
            player.setOrientation(Orientation.Bottom);
            toMoveY = -toMove;
        }
        if (gim.isKey(GameKeys.Up, KeyStatus.Down)) {
            player.setOrientation(Orientation.Top);
            toMoveY = toMove;
        }
        if (gim.isKey(GameKeys.Left, KeyStatus.Down)) {
            player.setOrientation(Orientation.Left);
            toMoveX = -toMove;
        }
        if (gim.isKey(GameKeys.Right, KeyStatus.Down)) {
            player.setOrientation(Orientation.Right);
            toMoveX = toMove;
        }

        player.move(toMoveX, toMoveY);
        if (checkForCollision(player)) {
            player.move(-toMoveX, -toMoveY);
            return;
        }
        if ((toMoveX < 0 && player.getXT() > -toMoveX) || (toMoveX > 0 && player.getXT() < -toMoveX))
            toMoveX = 0;
        else if ((toMoveX < 0 && player.getXT() > 0) || (toMoveX > 0 && player.getXT() < 0))
            toMoveX = player.getXT() + toMoveX;

        if ((toMoveY < 0 && player.getYT() > -toMoveY) || (toMoveY > 0 && player.getYT() < -toMoveY))
            toMoveY = 0;
        else if ((toMoveY < 0 && player.getYT() > 0) || (toMoveY > 0 && player.getYT() < 0))
            toMoveY = player.getYT() + toMoveY;

        cam.translate(toMoveX, toMoveY);
        if (cam.position.x < SHOWED_MAP_WIDTH * tileWidth / 2)
            cam.position.x = SHOWED_MAP_WIDTH * tileWidth / 2;
        else if (cam.position.x > (mapWidth - SHOWED_MAP_WIDTH) * tileWidth)
            cam.position.x = (mapWidth - SHOWED_MAP_WIDTH) * tileWidth;

        if (cam.position.y > 0)
            cam.position.y = 0;
        else if (cam.position.y < -(mapHeight - SHOWED_MAP_HEIGHT) * tileHeight)
            cam.position.y = -(mapHeight - SHOWED_MAP_HEIGHT) * tileHeight;

//        System.out.println(player.getPosX() + " / " + cam.position.x);
//        System.out.println(player.getPosY() + " / " + cam.position.y);
    }

    protected boolean checkForCollision(Character player) {
        for (RectangleMapObject rectangleMapObject : collisionObjects.getByType(RectangleMapObject.class)) {
            Rectangle rect = rectangleMapObject.getRectangle();
            Rectangle playerRect = player.getMapRectangle();
            return Intersector.overlaps(rect, playerRect);
        }
        return false;
    }

    @Override
    public void draw() {
        itmr.setView(cam);
        itmr.render();
        player.draw(sb);
        drawHud();
    }

    protected void drawHud() {
        questShower.draw();
        inventoryShower.draw();
    }

    @Override
    public void handleInput() {
        if (gim.isKey(GameKeys.ESC, KeyStatus.Pressed)) {
            gsm.setState(GameStates.InGameMenu);
        }
    }

    @Override
    public void dispose() {
        map.dispose();
        itmr.dispose();
    }

    protected void animateHUD() {
        animateInventoryShower(0);
        animateQuestRectangle(0);
    }

    protected void animateQuestRectangle(float from) {
        questShower.beginAnimation();
        DoubleAnimation da = new DoubleAnimation(from, 1, 750);
        da.setRunningAction(() -> {
            questShower.setDuringAnimationQuestShowerWidth((int)((double)questShower.getQuestShowerWidth() * da.getActual()));
            questShower.setDuringAnimationQuestShowerHeight((int)((double)questShower.getQuestShowerHeight() * da.getActual()));
            questShower.setDuringAnimationTextOpacity(da.getActual());
        });
        animationsList.put("QuestRectangleAnimation", da);
        da.setEndingAction(() -> {
            questShower.finishAnimation();
        });
    }

    protected void animateInventoryShower(float from) {
        inventoryShower.beginAnimation();
        DoubleAnimation da = new DoubleAnimation(from, 1, 750);
        da.setRunningAction(() -> {
            inventoryShower.setDuringAnimationWidth((int)((double)inventoryShower.getMaximumWidth() * da.getActual()));
            inventoryShower.setDuringAnimationHeight((int)((double)inventoryShower.getMaximumHeight() * da.getActual()));
            inventoryShower.setDuringAnimationBackgroundOpacity(da.getActual());
        });
        animationsList.put("InventoryShowerAnimation", da);
        DoubleAnimation da2 = new DoubleAnimation(0, 1, 750);
        da2.setEndingAction(() -> inventoryShower.finishAnimation());
        da2.setRunningAction(() -> {
            inventoryShower.setDuringAnimationForegroundOpacity(da2.getActual());
        });
        da.setEndingAction(() -> {
            animationsList.put("InventoryShowerForegroundAnimation", da2);
        });
    }
}
