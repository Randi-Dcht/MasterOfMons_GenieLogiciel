package be.ac.umons.mom.g02.GameStates;

import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameMapManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

/**
 * Testing class for LoadingState
 */
public class LoadingStateTest {

    protected LoadingState ls;

    @BeforeEach
    public void init() {
        ls = new LoadingState() {
            @Override
            public void init() {
                gim = Mockito.mock(GameInputManager.class);
                gsm = Mockito.mock(GameStateManager.class);
                gs = Mockito.mock(GraphicalSettings.class);
                Mockito.when(gs.getBackgroundColor()).thenReturn(new Color());
                am = Mockito.mock(AssetManager.class);
                sb = Mockito.mock(SpriteBatch.class);
                gmm = Mockito.mock(GameMapManager.class);
                sr = Mockito.mock(ShapeRenderer.class);
                font = Mockito.mock(BitmapFont.class);
                when(gs.getAssetManager()).thenReturn(am);
                when(am.update()).thenReturn(false);
                when(gs.getStringFromId("loading")).thenReturn("Loading...");
                Gdx.gl = Mockito.mock(GL20.class);
                Gdx.app = Mockito.mock(Application.class);
            }
        };
        ls.init();
    }

    /**
     * Test if the end of the loading happens at the right time
     */
    @Test
    public void testAssets() {
        Assertions.assertFalse(ls.assetsLoaded);
        Assertions.assertEquals(0, Mockito.mockingDetails(ls.am).getInvocations().size());
        ls.draw();
        Assertions.assertFalse(ls.assetsLoaded);
        Assertions.assertEquals(2, Mockito.mockingDetails(ls.am).getInvocations().size()); // 2 because update() and getProgress()
        when(ls.am.update()).thenReturn(true);
        ls.draw();
        Assertions.assertTrue(ls.assetsLoaded);
        Assertions.assertFalse(ls.mapsLoaded);
        ls.draw();
        Assertions.assertTrue(ls.assetsLoaded);
        Assertions.assertFalse(ls.mapsLoaded);
        ls.draw();
        Assertions.assertTrue(ls.assetsLoaded);
        Assertions.assertFalse(ls.mapsLoaded);
        when(ls.gmm.loadNextMap()).thenReturn(true);
        ls.draw();
        Assertions.assertTrue(ls.assetsLoaded);
        Assertions.assertTrue(ls.mapsLoaded);
    }

    /**
     * Test if the computed angle is the good one
     */
    @Test
    public void testAngle() {
        Assertions.assertEquals(0, ls.actualAngle);
        ls.update(1);
        Assertions.assertEquals(Math.PI, ls.actualAngle);
        ls.update(.5f);
        Assertions.assertEquals(1.5 * Math.PI, ls.actualAngle);
        ls.update(1);
        Assertions.assertEquals(.5 * Math.PI, ls.actualAngle);
    }
}
