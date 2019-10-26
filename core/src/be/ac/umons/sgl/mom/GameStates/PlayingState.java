package be.ac.umons.sgl.mom.GameStates;

import be.ac.umons.sgl.mom.Enums.GameKeys;
import be.ac.umons.sgl.mom.Enums.KeyStatus;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;

public class PlayingState extends GameState { // TODO : Put all disposes
    protected static final int SHOWED_MAP_WIDTH = 31;
    protected static final int SHOWED_MAP_HEIGHT = 17;

//    protected static final String FINISHED_QUEST_INDICATOR = "<> ";
//    protected static final String ACTIVATED_QUEST_INDICATOR = "<!> ";
//    protected static final String UNACTIVATED_QUEST_INDICATOR = ">!< ";


    protected int mapWidth;
    protected int mapHeight;
    protected int tileWidth;
    protected int tileHeight;

    // Inspired from https://www.youtube.com/watch?v=zckxJn751Gw&list=PLXY8okVWvwZ0qmqSBhOtqYRjzWtUCWylb&index=3&t=0s by dermetfan
    // Map showing inspired from https://www.youtube.com/watch?v=P8jgD-V5jG8&list=PLZm85UZQLd2SXQzsF-a0-pPF6IWDDdrXt&index=6 by Brent Aureli's - Code School (https://github.com/BrentAureli/SuperMario)

    private SpriteBatch sb;
    private TiledMap map;
    private IsometricTiledMapRenderer itmr;
    private OrthographicCamera cam;

    public PlayingState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }

    @Override
    public void init() {

        sb = new SpriteBatch();

        map = new TmxMapLoader().load("Map/isoTest.tmx");
        tileWidth = (int)map.getProperties().get("tilewidth");
        tileHeight = (int)map.getProperties().get("tileheight") * 2;
        mapWidth = (int)map.getProperties().get("width");
        mapHeight = (int)map.getProperties().get("height");


        gs.setQuestFont("Fonts/Comfortaa/Comfortaa-Light.ttf", tileHeight / 2);

//        itmr = new OrthogonalTiledMapRenderer(map);
        itmr = new IsometricTiledMapRenderer(map);
        cam = new OrthographicCamera();
        cam.setToOrtho(false, tileWidth * SHOWED_MAP_WIDTH, tileHeight * SHOWED_MAP_HEIGHT); // Rend la map 31 * 17
        cam.translate(0, -SHOWED_MAP_HEIGHT * tileHeight / 2);
        cam.update();
    }

    @Override
    public void update(float dt) {
        handleInput();
        cam.update();
    }

    @Override
    public void draw() {
        itmr.setView(cam);
        itmr.render();
    }

    @Override
    public void handleInput() {
        if (gim.isKey(GameKeys.Down, KeyStatus.Down)) {
            cam.translate(0, -10);
        }

        if (gim.isKey(GameKeys.Up, KeyStatus.Down)) {
            cam.translate(0, 10);
        }

        if (gim.isKey(GameKeys.Right, KeyStatus.Down)) {
            cam.translate(10, 0);
        }

        if (gim.isKey(GameKeys.Left, KeyStatus.Down)) {
            cam.translate(-10, 0);
        }
    }

    @Override
    public void dispose() {
        map.dispose();
        itmr.dispose();
    }
}
