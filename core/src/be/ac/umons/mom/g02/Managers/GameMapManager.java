package be.ac.umons.mom.g02.Managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.LocalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;

import java.util.*;

/**
 * Represent a game's map manager. It loads and render maps.
 */
public class GameMapManager {

    /**
     * The instance of the GameMapManager if already created.
     */
    private static GameMapManager instance;

    /**
     * @return The instance of the GameMapManager if already created.
     */
    public static GameMapManager getInstance() {
        if (instance == null)
            instance = new GameMapManager();
        return instance;
    }


    /**
     * Every map's renderer already loaded.
     */
    protected Map<String, IsometricTiledMapRenderer> tiledMapsRenderer;
    /**
     * The map's loader.
     */
    protected TmxMapLoader mapLoader;
    /**
     * The renderer to use at this moment.
     */
    protected IsometricTiledMapRenderer actualItmr;
    /**
     * The camera which shows the map.
     */
    protected OrthographicCamera view;
    /**
     * The asset's manager to allows the loading of the map asynchronously.
     */
    protected AssetManager am;

    /**
     * The actual map's name.
     */
    protected String actualMapName;

    protected GameMapManager() {
        tiledMapsRenderer = new HashMap<>();
        mapLoader = new TmxMapLoader(new LocalFileHandleResolver());
        am = new AssetManager();
        am.setLoader(TiledMap.class, mapLoader);
    }

    /**
     * Set the map to render.
     * @param mapName The map's name
     */
    public void setMap(String mapName) {
        actualMapName = mapName;
        if (tiledMapsRenderer.containsKey(mapName))
            actualItmr = tiledMapsRenderer.get(mapName);
    }
    /**
     * @return The <code>TiledMap</code> representing <code>mapName</code>. <code>null</code> if it isn't loaded.
     */
    public TiledMap getMap(String mapName) {
        if (tiledMapsRenderer.containsKey(mapName))
            return tiledMapsRenderer.get(mapName).getMap();
        return null;
    }

    /**
     * Render the actual map.
     */
    public void render() {
        if (actualItmr != null) {
            actualItmr.setView(view);
            actualItmr.render();
        }
    }

    /**
     * Set the camera on which the map should be showed.
     * @param cam The camera on which the map should be showed.
     */
    public void setView(OrthographicCamera cam) {
        view = cam;
    }

    /**
     * Load the next map, except if all the map are already loaded.
     * @return If every map was loaded.
     */
    public boolean loadNextMap() {
        boolean finish = am.update();
        if (finish) {
            for (String name : am.getAssetNames()) {
                if (am.getAssetType(name).equals(TiledMap.class))
                    tiledMapsRenderer.put(name, new IsometricTiledMapRenderer(am.get(name)));
            }
        }
        return finish;
    }

    /**
     * Add the maps <code>mapsPath</code> on the loading list.
     * @param mapsPath The path of the maps to load.
     */
    public void addMapsToLoad(String... mapsPath) {
        for (String map : mapsPath)
            am.load(map, TiledMap.class);
    }

    /**
     * @return The progress of the loading.
     */
    public double getProgress() {
        return am.getProgress();
    }

    /**
     * @return The map currently rendered.
     */
    public TiledMap getActualMap() {
        return actualItmr.getMap();
    }

    /**
     * @return The actual map's name.
     */
    public String getActualMapName() {
        return actualMapName;
    }

    /**
     * Dispose all resources that this manager has.
     */
    public void dispose() {
        for (IsometricTiledMapRenderer itmr : tiledMapsRenderer.values())
            itmr.dispose();
        am.dispose();
    }
}
