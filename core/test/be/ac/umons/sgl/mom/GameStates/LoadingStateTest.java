package be.ac.umons.sgl.mom.GameStates;

import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameMapManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.assets.AssetManager;
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

    private LoadingStateTest() {
        gim = Mockito.mock(GameInputManager.class);
        gsm = Mockito.mock(GameStateManager.class);
        gs = Mockito.mock(GraphicalSettings.class);
        am = Mockito.mock(AssetManager.class);
        sb = Mockito.mock(SpriteBatch.class);
        gmm = Mockito.mock(GameMapManager.class);
        sr = Mockito.mock(ShapeRenderer.class);
        when(gs.getTitleFont()).thenReturn(Mockito.mock(BitmapFont.class));
    }

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        when(gs.getAssetManager()).thenReturn(am);
        when(am.update()).thenReturn(false);
//        when(am.getProgress()).thenReturn(0f);
//        when(gmm.getProgress()).thenReturn(0d);
    }

    @Test
    public void testAssets() {
        Assertions.assertFalse(assetsLoaded);
        Mockito.verify(gsm, Mockito.times(0)).setState(PlayingState.class, true);
        Assertions.assertEquals(0, Mockito.mockingDetails(am).getInvocations().size());
        draw();
        Assertions.assertFalse(assetsLoaded);
        Mockito.verify(gsm, Mockito.times(0)).setState(PlayingState.class, true);
        Assertions.assertEquals(2, Mockito.mockingDetails(am).getInvocations().size()); // 2 because update() and getProgress()
        when(am.update()).thenReturn(true);
        draw();
        Assertions.assertTrue(assetsLoaded);
        Assertions.assertFalse(mapsLoaded);
        Mockito.verify(gsm, Mockito.times(0)).setState(PlayingState.class, true);
        draw();
        Assertions.assertTrue(assetsLoaded);
        Assertions.assertFalse(mapsLoaded);
        Mockito.verify(gsm, Mockito.times(0)).setState(PlayingState.class, true);
        draw();
        Assertions.assertTrue(assetsLoaded);
        Assertions.assertFalse(mapsLoaded);
        Mockito.verify(gsm, Mockito.times(0)).setState(PlayingState.class, true);
        when(gmm.loadNextMap()).thenReturn(true);
        draw();
        Assertions.assertTrue(assetsLoaded);
        Assertions.assertTrue(mapsLoaded);
        Mockito.verify(gsm, Mockito.times(1)).setState(PlayingState.class, true);
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
