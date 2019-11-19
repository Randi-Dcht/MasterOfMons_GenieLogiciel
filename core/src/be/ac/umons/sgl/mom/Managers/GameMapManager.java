package be.ac.umons.sgl.mom.Managers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;

import java.io.File;
import java.util.*;

public class GameMapManager {
    private Map<String, TiledMap> maps;
    private Map<String, IsometricTiledMapRenderer> tiledMapsRenderer;
    private List<String> mapsToLoad;
    private Iterator<String> loadIterator;
    private TmxMapLoader mapLoader;
    private IsometricTiledMapRenderer actualItmr;
    private OrthographicCamera view;
    private int mapsLoaded = 0;

    public GameMapManager() {
        maps = new HashMap<>();
        tiledMapsRenderer = new HashMap<>();
        mapsToLoad = new LinkedList<>();
        mapLoader = new TmxMapLoader();
    }

    public void setMap(String mapName) {
        actualItmr = tiledMapsRenderer.get(mapName);
    }

    public void render() {
        if (actualItmr != null) {
            actualItmr.setView(view);
            actualItmr.render();
        }
    }

    public void setView(OrthographicCamera cam) {
        view = cam;
    }

    public boolean loadNextMap() {
        if (loadIterator == null)
            loadIterator = mapsToLoad.iterator();
        if ( ! loadIterator.hasNext())
            return true;
        String path = loadIterator.next();
        String name = new File(path).getName();
        TiledMap map = mapLoader.load(path);
        maps.put(name, map);
        tiledMapsRenderer.put(name, new IsometricTiledMapRenderer(map));
        mapsLoaded++;
        return ! loadIterator.hasNext();
    }

    public void addMapsToLoad(String... mapsPath) {
        Collections.addAll(mapsToLoad, mapsPath);
    }

    public double getProgress() {
        return (double)mapsLoaded / mapsToLoad.size();
    }

    public TiledMap getActualMap() {
        return actualItmr.getMap();
    }

    public void dispose() {
        for (IsometricTiledMapRenderer itmr : tiledMapsRenderer.values())
            itmr.dispose();
        for (TiledMap map : maps.values())
            map.dispose();
    }
}
