package be.ac.umons.mom.g02.GameStates;

import be.ac.umons.mom.g02.Enums.Difficulty;
import be.ac.umons.mom.g02.Enums.Gender;
import be.ac.umons.mom.g02.Enums.KeyStatus;
import be.ac.umons.mom.g02.Enums.Type;
import be.ac.umons.mom.g02.Regulator.SuperviserNormally;
import be.ac.umons.mom.g02.GraphicalObjects.Controls.AgendaShower;
import be.ac.umons.mom.g02.GraphicalObjects.Controls.Button;
import be.ac.umons.mom.g02.GraphicalObjects.Controls.InventoryShower;
import be.ac.umons.mom.g02.GraphicalObjects.LifeBar;
import be.ac.umons.mom.g02.GraphicalObjects.NotificationRappel;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Player;
import be.ac.umons.mom.g02.GraphicalObjects.ProgressBar;
import be.ac.umons.mom.g02.GraphicalObjects.QuestShower;
import be.ac.umons.mom.g02.Managers.AnimationManager;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameMapManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Regulator.Supervisor;
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
 * Testing class of PlayingState
 */
public class PlayingStateTest {

    protected PlayingState ps;

    @BeforeEach
    public void init() {
        ps = new PlayingState() {
            /**
             * Overrided but doesn't affect the method of the following tests
             */
            @Override
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
                lifeBar = Mockito.mock(LifeBar.class);
                expBar = Mockito.mock(ProgressBar.class);
                energyBar = Mockito.mock(ProgressBar.class);
                agendaShower = Mockito.mock(AgendaShower.class);
                pauseButton = Mockito.mock(Button.class);
                notificationRappel = Mockito.mock(NotificationRappel.class);
                player = new Player();
                pnjs = new ArrayList<>();
                mapObjects = new ArrayList<>();

                People characteristics = Mockito.mock(People.class);
                Mockito.when(characteristics.getActualLife()).thenReturn(0d);
                Mockito.when(characteristics.getEnergy()).thenReturn(0d);
                Mockito.when(characteristics.getExperience()).thenReturn(0d);
                player.setCharacteristics(characteristics);


                tileWidth = 5; // Re-défini pour le bien du test.
                tileHeight = 5;
                mapWidth = 10;
                mapHeight = 15;
                player.setMapHeight(mapHeight * tileHeight);
                player.setMapWidth(mapWidth * tileWidth);
                player.setTileWidth(tileWidth);
                player.setTileHeight(tileHeight);
                SHOWED_MAP_WIDTH = 17;
                SHOWED_MAP_HEIGHT = 31;
                player.setPosX(25);
//                SuperviserNormally.getSupervisor().newParty("Test", Type.normal, Gender.Men, Difficulty.Easy);
                People pl;
                Supervisor.setPlayerOne(pl = Mockito.mock(People.class)); // Without any importance here
                supervisor = Mockito.mock(Supervisor.class);
                Mockito.when(pl.getSpeed()).thenReturn(1d);

                collisionObjects = new MapObjects();
            }
        };
        ps.init();
    }

    /**
     * Test if the movements done are the one expected
     */
    @Test
    public void movementTest() {
        Mockito.when(ps.gim.isKey("movingDownward", KeyStatus.Down)).thenReturn(true);
//        ps.makePlayerMove(1);
        Assertions.assertEquals(25, ps.player.getPosX());
        Assertions.assertEquals(0, ps.player.getPosY());
        Mockito.when(ps.gim.isKey("movingLeftward", KeyStatus.Down)).thenReturn(true);
        ps.makePlayerMove(1);
        Assertions.assertEquals(20, ps.player.getPosX());
        Assertions.assertEquals(-5, ps.player.getPosY());
        Mockito.when(ps.gim.isKey("movingDownward", KeyStatus.Down)).thenReturn(false);
        Mockito.when(ps.gim.isKey("movingRightward", KeyStatus.Down)).thenReturn(true);
        ps.makePlayerMove(1);
        Assertions.assertEquals(20, ps.player.getPosX());
        Assertions.assertEquals(-5, ps.player.getPosY());
        Mockito.when(ps.gim.isKey("movingLeftward", KeyStatus.Down)).thenReturn(false);
        ps.makePlayerMove(1);
        Assertions.assertEquals(25, ps.player.getPosX());
        Assertions.assertEquals(-5, ps.player.getPosY());
        Mockito.when(ps.gim.isKey("movingRightward", KeyStatus.Down)).thenReturn(false);
        Mockito.when(ps.gim.isKey("movingUpward", KeyStatus.Down)).thenReturn(true);
        ps.makePlayerMove(1);
        Assertions.assertEquals(25, ps.player.getPosX());
        Assertions.assertEquals(0, ps.player.getPosY());
        ps.makePlayerMove(.5f);
        Assertions.assertEquals(25, ps.player.getPosX());
        Assertions.assertEquals(3, ps.player.getPosY()); // 2.5 -> 3
        Mockito.when(ps.gim.isKey("movingRightward", KeyStatus.Down)).thenReturn(true);
        ps.makePlayerMove(.5f);
        Assertions.assertEquals(28, ps.player.getPosX()); // 2.5 -> 3
        Assertions.assertEquals(6, ps.player.getPosY());
        ps.makePlayerMove(.5f);
        Assertions.assertEquals(31, ps.player.getPosX()); // 2.5 -> 3
        Assertions.assertEquals(9, ps.player.getPosY()); // 2.5 -> 3
    }

    /**
     * Test if while moving, the player doesn't entre a collision area
     */
    @Test
    public void collisionTest() {
        Mockito.when(ps.gim.isKey("movingUpward", KeyStatus.Down)).thenReturn(false);
        Mockito.when(ps.gim.isKey("movingDownward", KeyStatus.Down)).thenReturn(false);
        Mockito.when(ps.gim.isKey("movingRightward", KeyStatus.Down)).thenReturn(false);
        Mockito.when(ps.gim.isKey("movingLeftward", KeyStatus.Down)).thenReturn(false);
        ps.player.move(-ps.player.getPosX(), -ps.player.getPosY()); // Remet coordonnée à 0,0

        Assertions.assertEquals(0, ps.player.getPosX());
        Assertions.assertEquals(0, ps.player.getPosY());
        Mockito.when(ps.gim.isKey("movingRightward", KeyStatus.Down)).thenReturn(true);
        ps.collisionObjects.add(new RectangleMapObject(1, 0,10,10));
        ps.update(1);
        Assertions.assertEquals(0, ps.player.getPosX());
        Assertions.assertEquals(0, ps.player.getPosY());
        Mockito.when(ps.gim.isKey("movingRightward", KeyStatus.Down)).thenReturn(false);
        Mockito.when(ps.gim.isKey("movingDownward", KeyStatus.Down)).thenReturn(true);
        ps.update(1);
        Assertions.assertEquals(0, ps.player.getPosX());
        Assertions.assertEquals(0, ps.player.getPosY());
    }
}
