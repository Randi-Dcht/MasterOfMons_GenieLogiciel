package be.ac.umons.sgl.mom.GameStates;

import be.ac.umons.sgl.mom.Enums.KeyStatus;
import be.ac.umons.sgl.mom.GraphicalObjects.Controls.AgendaShower;
import be.ac.umons.sgl.mom.GraphicalObjects.Controls.Button;
import be.ac.umons.sgl.mom.GraphicalObjects.Controls.InventoryShower;
import be.ac.umons.sgl.mom.GraphicalObjects.OnMapObjects.Player;
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

import java.util.ArrayList;

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
        agendaShower = Mockito.mock(AgendaShower.class);
        pauseButton = Mockito.mock(Button.class);
        player = new Player();
        pnjs = new ArrayList<>();
        mapObjects = new ArrayList<>();

        People characteristics = Mockito.mock(People.class);
        Mockito.when(characteristics.getLife()).thenReturn(0d);
        Mockito.when(characteristics.getEnergy()).thenReturn(0d);
        Mockito.when(characteristics.getExperience()).thenReturn(0d);
        player.setCharacteristics(characteristics);


        MockitoAnnotations.initMocks(this);

        tileWidth = 5; // Re-défini pour le bien du test.
        tileHeight = 5;
        mapWidth = 10;
        mapHeight = 15;
        player.setMapHeight(mapHeight * tileHeight);
        player.setMapWidth(mapWidth * tileWidth);
        player.setTileWidth(tileWidth);
        player.setTileHeight(tileHeight);
        VELOCITY = 5;
        SHOWED_MAP_WIDTH = 17;
        SHOWED_MAP_HEIGHT = 31;
        player.setPosX(25);

        collisionObjects = new MapObjects();
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
        Assertions.assertEquals(0, player.getPosY());
    }
}
