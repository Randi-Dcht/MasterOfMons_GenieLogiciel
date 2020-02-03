package be.ac.umons.sgl.mom.Managers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class GameMapManagerTest extends GameMapManager {

    @BeforeEach
    public void init() {
        mapLoader = Mockito.mock(TmxMapLoader.class);
        actualItmr = Mockito.mock(IsometricTiledMapRenderer.class);
        view = Mockito.mock(OrthographicCamera.class);

    }

    @Test
    public void loadTest() {
        addMapsToLoad("testAssets/Maps/Map1", "testAssets/Maps/Map2", "NonExisting", "NonExisting2");
        Assertions.assertFalse(this::loadNextMap);
        Assertions.assertTrue(maps.containsKey("Map1"));
        Assertions.assertFalse(maps.containsKey("Map2"));
        Assertions.assertFalse(maps.containsKey("NonExisting"));
        Assertions.assertFalse(maps.containsKey("NonExisting2"));
        Assertions.assertEquals(.25, getProgress());
        Assertions.assertFalse(this::loadNextMap);
        Assertions.assertTrue(maps.containsKey("Map1"));
        Assertions.assertTrue(maps.containsKey("Map2"));
        Assertions.assertFalse(maps.containsKey("NonExisting"));
        Assertions.assertFalse(maps.containsKey("NonExisting2"));
        Assertions.assertEquals(.5, getProgress());
        Assertions.assertFalse(this::loadNextMap);
        Assertions.assertTrue(maps.containsKey("Map1"));
        Assertions.assertTrue(maps.containsKey("Map2"));
        Assertions.assertFalse(maps.containsKey("NonExisting"));
        Assertions.assertFalse(maps.containsKey("NonExisting2"));
        Assertions.assertEquals(.75, getProgress());
        Assertions.assertTrue(this::loadNextMap);
        Assertions.assertTrue(maps.containsKey("Map1"));
        Assertions.assertTrue(maps.containsKey("Map2"));
        Assertions.assertFalse(maps.containsKey("NonExisting"));
        Assertions.assertFalse(maps.containsKey("NonExisting2"));
        Assertions.assertEquals(1, getProgress());
    }
}

