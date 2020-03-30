package be.ac.umons.mom.g02.GraphicalObjects.Controls;

import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.*;

public class SlidingBarTest {

    SlidingBar sb;
    GameInputManager gim;

    @BeforeEach
    public void init() {
        sb = new SlidingBar();
        sb.sr = Mockito.mock(ShapeRenderer.class);
        sb.actualColor = new Color();
        gim = Mockito.mock(GameInputManager.class);
        sb.gim = gim;
        MasterOfMonsGame.WIDTH = 50; // Redefined for test purposes
        MasterOfMonsGame.HEIGHT = 50;
        sb.x = 5;
        sb.y = 5;
        sb.width = 10;
        sb.height = 2;
        sb.setMaxValue(10);
        sb.setActualValue(5);
    }

    @Test
    public void test() {
        Point p = new Point();
        Mockito.when(gim.getLastMousePosition()).thenReturn(p);
        Mockito.when(gim.isMouseJustClicked()).thenReturn(false);
        Mockito.when(gim.isMouseJustUnclicked()).thenReturn(false);
        sb.handleInput();
        Assertions.assertFalse(sb.isMoving);
        p.x = 6; p.y = 44; // In because y inverted but not on circle
        sb.handleInput();
        Assertions.assertFalse(sb.isMoving);
        Assertions.assertEquals(10, sb.posX);
        Assertions.assertEquals(5, sb.getActualValue());
        Mockito.when(gim.isMouseJustClicked()).thenReturn(true);
        sb.handleInput();
        Assertions.assertFalse(sb.isMoving);
        Assertions.assertEquals(10, sb.posX);
        Assertions.assertEquals(5, sb.getActualValue());
        Mockito.when(gim.isMouseJustClicked()).thenReturn(true);
        p.x = 10; // In because y inverted and on circle
        sb.handleInput();
        Assertions.assertTrue(sb.isMoving);
        Assertions.assertEquals(10, sb.posX);
        p.x = 11;
        Mockito.when(gim.isMouseJustClicked()).thenReturn(false);
        sb.handleInput();
        Assertions.assertTrue(sb.isMoving);
        Assertions.assertEquals(11, sb.posX);
        p.x = 15;
        sb.handleInput();
        Assertions.assertTrue(sb.isMoving);
        Assertions.assertEquals(14, sb.posX); // 14 because half-circle margin
        Assertions.assertEquals(10, sb.getActualValue());
        p.x = 0;
        sb.handleInput();
        Assertions.assertTrue(sb.isMoving);
        Assertions.assertEquals(6, sb.posX);
        Assertions.assertEquals(0, sb.getActualValue());
        Mockito.when(gim.isMouseJustUnclicked()).thenReturn(true);
        p.x = 30;
        sb.handleInput();
        Assertions.assertFalse(sb.isMoving);
        Assertions.assertEquals(6, sb.posX);
        Assertions.assertEquals(0, sb.getActualValue());
    }

}
