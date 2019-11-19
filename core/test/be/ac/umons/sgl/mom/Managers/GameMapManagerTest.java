package be.ac.umons.sgl.mom.Managers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.util.*;

public class GameMapManagerTest {
    private Map<String, TiledMap> maps;
    private Map<String, IsometricTiledMapRenderer> tiledMapsRenderer;
    private List<String> mapsToLoad;
    private Iterator<String> loadIterator;
    @Mock
    private TmxMapLoader mapLoader;
    @Mock
    private IsometricTiledMapRenderer actualItmr;
    @Mock
    private OrthographicCamera view;
    private int mapsLoaded = 0;

    public GameMapManagerTest() {
        maps = new HashMap<>();
        tiledMapsRenderer = new HashMap<>();
        mapsToLoad = new LinkedList<>();
//        mapLoader = new TmxMapLoader();
    }

    public boolean loadNextMap() {
        if (loadIterator == null)
            loadIterator = mapsToLoad.iterator();
        if (! loadIterator.hasNext())
            return true;
        String path = loadIterator.next();
        boolean lastOne = ! loadIterator.hasNext();
        String name = new File(path).getName();
        TiledMap map = mapLoader.load(path);
        maps.put(name, map);
        tiledMapsRenderer.put(name, Mockito.mock(IsometricTiledMapRenderer.class)); // Modifié pour le bien du test.
        mapsLoaded++;
        return lastOne;
    }

    public void addMapsToLoad(String... mapsPath) {
        Collections.addAll(mapsToLoad, mapsPath);
    }

    public double getProgress() {
        return (double)mapsLoaded / mapsToLoad.size();
    }

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void loadTest() {
        addMapsToLoad("Test1", "Test2");
        Assertions.assertFalse(this::loadNextMap);
        Assertions.assertEquals(1, mapsLoaded);
        Assertions.assertTrue(tiledMapsRenderer.containsKey("Test1"));
        Assertions.assertFalse(tiledMapsRenderer.containsKey("Test2"));
        Assertions.assertTrue(maps.containsKey("Test1"));
        Assertions.assertFalse(maps.containsKey("Test2"));
        Assertions.assertEquals(.5, getProgress());
        Assertions.assertTrue(this::loadNextMap);
        Assertions.assertEquals(2, mapsLoaded);
        Assertions.assertTrue(tiledMapsRenderer.containsKey("Test1"));
        Assertions.assertTrue(tiledMapsRenderer.containsKey("Test2"));
        Assertions.assertTrue(maps.containsKey("Test1"));
        Assertions.assertTrue(maps.containsKey("Test2"));
        Assertions.assertEquals(1, getProgress());
        Assertions.assertTrue(this::loadNextMap);
        Assertions.assertEquals(2, mapsLoaded);
        Assertions.assertTrue(tiledMapsRenderer.containsKey("Test1"));
        Assertions.assertTrue(tiledMapsRenderer.containsKey("Test2"));
        Assertions.assertTrue(maps.containsKey("Test1"));
        Assertions.assertTrue(maps.containsKey("Test2"));
        Assertions.assertEquals(1, getProgress());
    }
}

