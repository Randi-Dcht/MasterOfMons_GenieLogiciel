package be.ac.umons.mom.g02.GameStates.Menus;

import be.ac.umons.mom.g02.Enums.KeyStatus;
import be.ac.umons.mom.g02.GraphicalObjects.Controls.Button;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.ButtonMenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.MenuItem;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.Input;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.*;
import java.util.ArrayList;

/**
 * Testing class for MenuState
 */
public class MenuStateTest {

    GameInputManager gimMock;
    MenuState ms;

    @BeforeEach
    public void init() {
        gimMock = Mockito.mock(GameInputManager.class);
        ButtonMenuItem bmi = Mockito.mock(ButtonMenuItem.class);
        Mockito.when(bmi.getControl()).thenReturn(Mockito.mock(Button.class));
        Mockito.when(bmi.getDrawUnderPreviousOne()).thenReturn(true);
        ButtonMenuItem bmi2 = Mockito.mock(ButtonMenuItem.class);
        Mockito.when(bmi2.getControl()).thenReturn(Mockito.mock(Button.class));
        Mockito.when(bmi2.getDrawUnderPreviousOne()).thenReturn(false);
        ButtonMenuItem bmi3 = Mockito.mock(ButtonMenuItem.class);
        Mockito.when(bmi3.getControl()).thenReturn(Mockito.mock(Button.class));
        Mockito.when(bmi3.getDrawUnderPreviousOne()).thenReturn(true);

        ms = new MenuState() {
            @Override
            public void init() {
                buttons = new ArrayList<>();
                gim = gimMock;
                gsm = Mockito.mock(GameStateManager.class);
                gs = Mockito.mock(GraphicalSettings.class);
                setMenuItems(new MenuItem[]{
                        bmi,
                        bmi2,
                        bmi3
                });
            }
        };
        ms.init();
        Mockito.when(gimMock.getRecentClicks()).thenReturn(new ArrayList<>());
    }

    /**
     * Test if the down key is well interpreted and if the selected item is the one expected
     */
    @Test
    public void testDown() {
        Mockito.when(gimMock.isKey(Input.Keys.DOWN, KeyStatus.Pressed)).thenReturn(true);
        Mockito.when(gimMock.isKey(Input.Keys.UP, KeyStatus.Pressed)).thenReturn(false);
        Assertions.assertEquals(new Point(0,0), ms.selectedItem);
        ms.handleInput();
        Assertions.assertEquals(new Point(1,0), ms.selectedItem);
        ms.handleInput();
        Assertions.assertEquals(new Point(0,0), ms.selectedItem);
        Mockito.when(gimMock.isKey(Input.Keys.DOWN, KeyStatus.Pressed)).thenReturn(false);
        Mockito.when(gimMock.isKey(Input.Keys.RIGHT, KeyStatus.Pressed)).thenReturn(true);
        ms.handleInput();
        Assertions.assertEquals(new Point(0,1), ms.selectedItem);
        ms.handleInput();
        Assertions.assertEquals(new Point(0,0), ms.selectedItem);
        Mockito.when(gimMock.isKey(Input.Keys.RIGHT, KeyStatus.Pressed)).thenReturn(false);
        Mockito.when(gimMock.isKey(Input.Keys.DOWN, KeyStatus.Pressed)).thenReturn(true);
        ms.handleInput();
        Assertions.assertEquals(new Point(1,0), ms.selectedItem);
        Mockito.when(gimMock.isKey(Input.Keys.RIGHT, KeyStatus.Pressed)).thenReturn(true);
        Mockito.when(gimMock.isKey(Input.Keys.DOWN, KeyStatus.Pressed)).thenReturn(false);
        ms.handleInput();
        Assertions.assertEquals(new Point(1,0), ms.selectedItem);
    }

    /**
     * Test if the up key is well interpreted and if the selected item is the one expected
     */
    @Test
    public void testUp() {
        Mockito.when(gimMock.isKey(Input.Keys.DOWN, KeyStatus.Pressed)).thenReturn(false);
        Mockito.when(gimMock.isKey(Input.Keys.UP, KeyStatus.Pressed)).thenReturn(true);
        Assertions.assertEquals(new Point(0,0), ms.selectedItem);
        ms.handleInput();
        Assertions.assertEquals(new Point(1,0), ms.selectedItem);
        ms.handleInput();
        Assertions.assertEquals(new Point(0,0), ms.selectedItem);
        Mockito.when(gimMock.isKey(Input.Keys.UP, KeyStatus.Pressed)).thenReturn(false);
        Mockito.when(gimMock.isKey(Input.Keys.LEFT, KeyStatus.Pressed)).thenReturn(true);
        ms.handleInput();
        Assertions.assertEquals(new Point(0,1), ms.selectedItem);
        Mockito.when(gimMock.isKey(Input.Keys.DOWN, KeyStatus.Pressed)).thenReturn(true);
        Mockito.when(gimMock.isKey(Input.Keys.LEFT, KeyStatus.Pressed)).thenReturn(false);
        ms.handleInput();
        Assertions.assertEquals(new Point(1,0), ms.selectedItem);
        Mockito.when(gimMock.isKey(Input.Keys.DOWN, KeyStatus.Pressed)).thenReturn(false);
        Mockito.when(gimMock.isKey(Input.Keys.LEFT, KeyStatus.Pressed)).thenReturn(true);
        ms.handleInput();
        Assertions.assertEquals(new Point(1,0), ms.selectedItem);
    }
}
