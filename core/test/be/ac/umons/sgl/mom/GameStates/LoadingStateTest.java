package be.ac.umons.sgl.mom.GameStates;

import be.ac.umons.sgl.mom.Enums.GameStates;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Cette classe représente les tests pour la classe LoadingState. Elle sera utilisée pour tester que l'angle est correct ainsi que toutes les ressources ont été chargées correctement.
 * Malgré quelque modification, le code du test est équivalent à celui de la classe LoadingState.
 */
public class LoadingStateTest {
    /**
     * La vitesse des cercles (en rad/s).
     */
    protected static final double CIRCLE_SPEED_RAD_SEC = Math.PI;

    /**
     * Permet de dessiner les formes comme les rectangles.
     */
    @Mock
    protected ShapeRenderer sr;
    /**
     * Défini si tout les fichiers nécéssaires au jeu ont été chargés.
     */
    protected boolean assetsLoaded = false;

    /**
     * L'angle actuel des cercles.
     */
    protected double actualAngle = 0;

    /**
     * Représente les paramètres graphiques du jeu normalement donné par la classe mère de LoadingState.
     */
    @Mock
    protected GraphicalSettings gs;

    /**
     * Représente le gestionnaire d'état du jeu normalement donné par la classe mère de LoadingState.
     */
    @Mock
    protected GameStateManager gsm;
    /**
     * Représente l'AssetManager normalement donné par GraphicalSettings.
     */
    @Mock
    AssetManager am;
    /**
     * Est-on passé à PlayState ?
     */
    private boolean goneToPlay = false;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(gs.getAssetManager()).thenReturn(am);
        Mockito.when(am.update()).thenReturn(false);
    }

    @Test
    public void testAssets() {
        Assertions.assertFalse(assetsLoaded);
        Assertions.assertFalse(goneToPlay);
        Assertions.assertEquals(0, Mockito.mockingDetails(am).getInvocations().size());
        draw();
        Assertions.assertFalse(assetsLoaded);
        Assertions.assertFalse(goneToPlay);
        Assertions.assertEquals(2, Mockito.mockingDetails(am).getInvocations().size()); // 2 because update() and getProgress()
        Mockito.when(am.update()).thenReturn(true);
        draw();
        Assertions.assertTrue(assetsLoaded);
        Assertions.assertTrue(goneToPlay);
        Assertions.assertEquals(4, Mockito.mockingDetails(am).getInvocations().size());
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

    public void update(float dt) {
        actualAngle = (actualAngle + CIRCLE_SPEED_RAD_SEC * dt)  % (2 * Math.PI);
    }

    public void draw() {
        assetsLoaded = gs.getAssetManager().update();
        if (assetsLoaded) {
            gsm.setState(GameStates.Play);
            goneToPlay = true;
        }

        float progress = gs.getAssetManager().getProgress();
        sr.setColor(1 - 217f / 255 * progress, 1 - 113f / 255 * progress, 1 - 195f / 255 * progress, 1);
    }
}
