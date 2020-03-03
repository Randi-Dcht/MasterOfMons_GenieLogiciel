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
 * Cette classe représente les tests pour la classe LoadingState. Elle sera utilisée pour tester que l'angle est correct ainsi que toutes les ressources ont été chargées correctement.
 */
public class LoadingStateTest extends LoadingState {

    @BeforeEach
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
        MockitoAnnotations.initMocks(this);
        when(gs.getAssetManager()).thenReturn(am);
        when(am.update()).thenReturn(false);
        when(gs.getStringFromId("loading")).thenReturn("Loading...");
        Gdx.gl = Mockito.mock(GL20.class);
        Gdx.app = Mockito.mock(Application.class);
//        when(am.getProgress()).thenReturn(0f);
//        when(gmm.getProgress()).thenReturn(0d);
    }

    @Test
    public void testAssets() {
        Assertions.assertFalse(assetsLoaded);
        Assertions.assertEquals(0, Mockito.mockingDetails(am).getInvocations().size());
        draw();
        Assertions.assertFalse(assetsLoaded);
        Assertions.assertEquals(2, Mockito.mockingDetails(am).getInvocations().size()); // 2 because update() and getProgress()
        when(am.update()).thenReturn(true);
        draw();
        Assertions.assertTrue(assetsLoaded);
        Assertions.assertFalse(mapsLoaded);
        draw();
        Assertions.assertTrue(assetsLoaded);
        Assertions.assertFalse(mapsLoaded);
        draw();
        Assertions.assertTrue(assetsLoaded);
        Assertions.assertFalse(mapsLoaded);
        when(gmm.loadNextMap()).thenReturn(true);
        draw();
        Assertions.assertTrue(assetsLoaded);
        Assertions.assertTrue(mapsLoaded);
    }

    @Test
    public void testAngle() {
        Assertions.assertEquals(0, actualAngle);
        update(1);
        Assertions.assertEquals(Math.PI, actualAngle);
        update(.5f);
        Assertions.assertEquals(1.5 * Math.PI, actualAngle);
        update(1);
        Assertions.assertEquals(.5 * Math.PI, actualAngle);
    }
}
