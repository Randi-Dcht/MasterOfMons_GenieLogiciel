package be.ac.umons.sgl.mom.GameStates;

import be.ac.umons.sgl.mom.Enums.KeyStatus;
import be.ac.umons.sgl.mom.GraphicalObjects.Controls.InventoryShower;
import be.ac.umons.sgl.mom.GraphicalObjects.Player;
import be.ac.umons.sgl.mom.GraphicalObjects.ProgressBar;
import be.ac.umons.sgl.mom.GraphicalObjects.QuestShower;
import be.ac.umons.sgl.mom.Managers.AnimationManager;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameMapManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Cette classe correspond à la classe de test de la classe PLayingState.
 */
public class PlayingStateTest extends PlayingState {

    @BeforeEach
    public void init() {

        gsm = Mockito.mock(GameStateManager.class);
        gim = Mockito.mock(GameInputManager.class);
        gs = Mockito.mock(GraphicalSettings.class);
        am = Mockito.mock(AnimationManager.class);
        gmm = Mockito.mock(GameMapManager.class);
        inventoryShower = Mockito.mock(InventoryShower.class);
        questShower = Mockito.mock(QuestShower.class);
        cam = Mockito.mock(OrthographicCamera.class);
        sb = Mockito.mock(SpriteBatch.class);
        lifeBar = Mockito.mock(ProgressBar.class);
        expBar = Mockito.mock(ProgressBar.class);
        energyBar = Mockito.mock(ProgressBar.class);
        playerCharacteristics = Mockito.mock(People.class);


        MockitoAnnotations.initMocks(this);

        tileWidth = 5; // Re-défini pour le bien du test.
        tileHeight = 5;
        mapWidth = 10;
        mapHeight = 10;

        VELOCITY = 5;

        collisionObjects = new MapObjects();

        player = new Player(gs,25, 25, 5, 5, 50, 50); // TODO : BUG AVEC EN BAS ET A GAUCHE
    }

    @Override
    protected void translateCamera(int x, int y) {}

    @Test
    public void movementTest() {
        Mockito.when(gim.isKey(Input.Keys.DOWN, KeyStatus.Down)).thenReturn(true);
        update(1);
        Assertions.assertEquals(25, player.getPosX());
        Assertions.assertEquals(-5, player.getPosY());
        Mockito.when(gim.isKey(Input.Keys.LEFT, KeyStatus.Down)).thenReturn(true);
        update(1);
        Assertions.assertEquals(20, player.getPosX());
        Assertions.assertEquals(-10, player.getPosY());
        Mockito.when(gim.isKey(Input.Keys.DOWN, KeyStatus.Down)).thenReturn(false);
        Mockito.when(gim.isKey(Input.Keys.RIGHT, KeyStatus.Down)).thenReturn(true);
        update(1);
        Assertions.assertEquals(20, player.getPosX());
        Assertions.assertEquals(-10, player.getPosY());
        Mockito.when(gim.isKey(Input.Keys.LEFT, KeyStatus.Down)).thenReturn(false);
        update(1);
        Assertions.assertEquals(25, player.getPosX());
        Assertions.assertEquals(-10, player.getPosY());
        Mockito.when(gim.isKey(Input.Keys.RIGHT, KeyStatus.Down)).thenReturn(false);
        Mockito.when(gim.isKey(Input.Keys.UP, KeyStatus.Down)).thenReturn(true);
        update(1);
        Assertions.assertEquals(25, player.getPosX());
        Assertions.assertEquals(-5, player.getPosY());
        update(.5f);
        Assertions.assertEquals(25, player.getPosX());
        Assertions.assertEquals(-2, player.getPosY()); // -2.5 arrondi à -2
        Mockito.when(gim.isKey(Input.Keys.RIGHT, KeyStatus.Down)).thenReturn(true);
        update(.5f);
        Assertions.assertEquals(28, player.getPosX()); // 2.5 arrondi à 3
        Assertions.assertEquals(1, player.getPosY());
        update(.5f);
        Assertions.assertEquals(31, player.getPosX()); // 2.5 arrondi à 3
        Assertions.assertEquals(4, player.getPosY()); // 2.5 arrondi à 3 pour éviter le "entre 2 pixels"
    }

    @Test
    public void collisionTest() {
        Mockito.when(gim.isKey(Input.Keys.UP, KeyStatus.Down)).thenReturn(false);
        Mockito.when(gim.isKey(Input.Keys.DOWN, KeyStatus.Down)).thenReturn(false);
        Mockito.when(gim.isKey(Input.Keys.RIGHT, KeyStatus.Down)).thenReturn(false);
        Mockito.when(gim.isKey(Input.Keys.LEFT, KeyStatus.Down)).thenReturn(false);
        player.move(-player.getPosX(), -player.getPosY()); // Remet coordonnée à 0,0

        Assertions.assertEquals(0, player.getPosX());
        Assertions.assertEquals(0, player.getPosY());
        Mockito.when(gim.isKey(Input.Keys.RIGHT, KeyStatus.Down)).thenReturn(true);
        collisionObjects.add(new RectangleMapObject(1, 0,10,10));
        update(1);
        Assertions.assertEquals(0, player.getPosX());
        Assertions.assertEquals(0, player.getPosY());
        Mockito.when(gim.isKey(Input.Keys.RIGHT, KeyStatus.Down)).thenReturn(false);
        Mockito.when(gim.isKey(Input.Keys.DOWN, KeyStatus.Down)).thenReturn(true);
        update(1);
        Assertions.assertEquals(0, player.getPosX());
        Assertions.assertEquals(-5, player.getPosY());
    }
}
