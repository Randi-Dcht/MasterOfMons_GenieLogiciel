package be.ac.umons.mom.g02.GameStates.Menus;

import be.ac.umons.mom.g02.Enums.KeyStatus;
import be.ac.umons.mom.g02.GraphicalObjects.Controls.Button;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.ButtonMenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.MenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.TextMenuItem;
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
 * Classe réprésente la classe MainMenuState. Cette classe de test a principalement pour but de tester la méthode handleInput, en évitant d'allouer trop de ressources.
 */
public class MenuStateTest extends MenuState {

    private MenuStateTest() {
        super();
        gim = Mockito.mock(GameInputManager.class);
        gsm = Mockito.mock(GameStateManager.class);
        gs = Mockito.mock(GraphicalSettings.class);
        buttons = new ArrayList<>();
        buttons.add(new ArrayList<>());
        buttons.add(new ArrayList<>());
        buttons.get(0).add(Mockito.mock(Button.class));
        buttons.get(1).add(Mockito.mock(Button.class));
        buttons.get(0).add(Mockito.mock(Button.class));
        controls = new ArrayList<>();
    }

    @BeforeEach
    public void init() {
        menuItems = new MenuItem[] { Mockito.mock(TextMenuItem.class),
                Mockito.mock(ButtonMenuItem.class),
                Mockito.mock(ButtonMenuItem.class)};
        //setMenuItems(menuItems);
        Mockito.when(gim.getRecentClicks()).thenReturn(new ArrayList<>());
    }

    @Test
    public void testDown() {
        Mockito.when(gim.isKey(Input.Keys.DOWN, KeyStatus.Pressed)).thenReturn(true);
        Mockito.when(gim.isKey(Input.Keys.UP, KeyStatus.Pressed)).thenReturn(false);
        Assertions.assertEquals(new Point(0,0), selectedItem);
        handleInput();
        Assertions.assertEquals(new Point(1,0), selectedItem);
        handleInput();
        Assertions.assertEquals(new Point(0,0), selectedItem);
        Mockito.when(gim.isKey(Input.Keys.DOWN, KeyStatus.Pressed)).thenReturn(false);
        Mockito.when(gim.isKey(Input.Keys.RIGHT, KeyStatus.Pressed)).thenReturn(true);
        handleInput();
        Assertions.assertEquals(new Point(0,1), selectedItem);
        handleInput();
        Assertions.assertEquals(new Point(0,0), selectedItem);
        Mockito.when(gim.isKey(Input.Keys.RIGHT, KeyStatus.Pressed)).thenReturn(false);
        Mockito.when(gim.isKey(Input.Keys.DOWN, KeyStatus.Pressed)).thenReturn(true);
        handleInput();
        Assertions.assertEquals(new Point(1,0), selectedItem);
        Mockito.when(gim.isKey(Input.Keys.RIGHT, KeyStatus.Pressed)).thenReturn(true);
        Mockito.when(gim.isKey(Input.Keys.DOWN, KeyStatus.Pressed)).thenReturn(false);
        handleInput();
        Assertions.assertEquals(new Point(1,0), selectedItem);
    }

    @Test
    public void testUp() {
        Mockito.when(gim.isKey(Input.Keys.DOWN, KeyStatus.Pressed)).thenReturn(false);
        Mockito.when(gim.isKey(Input.Keys.UP, KeyStatus.Pressed)).thenReturn(true);
        Assertions.assertEquals(new Point(0,0), selectedItem);
        handleInput();
        Assertions.assertEquals(new Point(1,0), selectedItem);
        handleInput();
        Assertions.assertEquals(new Point(0,0), selectedItem);
        Mockito.when(gim.isKey(Input.Keys.UP, KeyStatus.Pressed)).thenReturn(false);
        Mockito.when(gim.isKey(Input.Keys.LEFT, KeyStatus.Pressed)).thenReturn(true);
        handleInput();
        Assertions.assertEquals(new Point(0,1), selectedItem);
        Mockito.when(gim.isKey(Input.Keys.DOWN, KeyStatus.Pressed)).thenReturn(true);
        Mockito.when(gim.isKey(Input.Keys.LEFT, KeyStatus.Pressed)).thenReturn(false);
        handleInput();
        Assertions.assertEquals(new Point(1,0), selectedItem);
        Mockito.when(gim.isKey(Input.Keys.DOWN, KeyStatus.Pressed)).thenReturn(false);
        Mockito.when(gim.isKey(Input.Keys.LEFT, KeyStatus.Pressed)).thenReturn(true);
        handleInput();
        Assertions.assertEquals(new Point(1,0), selectedItem);
    }
}
