package be.ac.umons.mom.g02.GameStates.Menus;

import be.ac.umons.mom.g02.Enums.KeyStatus;
import be.ac.umons.mom.g02.GraphicalObjects.Controls.Button;
import be.ac.umons.mom.g02.GraphicalObjects.Controls.ExtensionsSelector;
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
public class MenuStateTest extends MainMenuState {

    private MenuStateTest() {
        super();
        gim = Mockito.mock(GameInputManager.class);
        gsm = Mockito.mock(GameStateManager.class);
        gs = Mockito.mock(GraphicalSettings.class);
        extSel = Mockito.mock(ExtensionsSelector.class);
        buttons = new ArrayList<>();
        buttons.add(new ArrayList<>());
        buttons.add(new ArrayList<>());
        buttons.get(0).add(Mockito.mock(Button.class));
        buttons.get(1).add(Mockito.mock(Button.class));
        buttons.get(0).add(Mockito.mock(Button.class));
        textBoxes = new ArrayList<>();
        scrollListChoosers = new ArrayList<>();
        colorSelectors = new ArrayList<>();
    }

    @BeforeEach
    public void init() {
        menuItems = new java.awt.MenuItem[] { new java.awt.MenuItem("Master Of Mons", MenuItemType.Text),
                new java.awt.MenuItem("Play", MenuItemType.Button),
                new java.awt.MenuItem("Settings", MenuItemType.Button)};
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
