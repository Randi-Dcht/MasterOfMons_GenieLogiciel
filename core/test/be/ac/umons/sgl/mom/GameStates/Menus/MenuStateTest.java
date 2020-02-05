package be.ac.umons.sgl.mom.GameStates.Menus;

import be.ac.umons.sgl.mom.Enums.KeyStatus;
import be.ac.umons.sgl.mom.GraphicalObjects.Controls.Button;
import be.ac.umons.sgl.mom.GraphicalObjects.Controls.ExtensionsSelector;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.Input;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

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
        buttons.add(Mockito.mock(Button.class));
        buttons.add(Mockito.mock(Button.class));
    }

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        menuItems = new MenuItem[] { new MenuItem("Master Of Mons", MenuItemType.Title, true),
                new MenuItem("Play", MenuItemType.Title, false),
                new MenuItem("Settings", MenuItemType.Title, true)};
        //setMenuItems(menuItems);
        Mockito.when(gim.getRecentClicks()).thenReturn(new ArrayList<>());
        while (! menuItems[selectedItem].selectable) // Simulate the one in draw.
            selectedItem = (selectedItem + 1) % menuItems.length;
    }

    @Test
    public void testDown() {
        Mockito.when(gim.isKey(Input.Keys.DOWN, KeyStatus.Pressed)).thenReturn(true);
        Mockito.when(gim.isKey(Input.Keys.UP, KeyStatus.Pressed)).thenReturn(false);
        Assertions.assertEquals(0, selectedItem);
        handleInput();
        Assertions.assertEquals(1, selectedItem);
        handleInput();
        Assertions.assertEquals(0, selectedItem);
    }

    @Test
    public void testUp() {
        Mockito.when(gim.isKey(Input.Keys.DOWN, KeyStatus.Pressed)).thenReturn(false);
        Mockito.when(gim.isKey(Input.Keys.UP, KeyStatus.Pressed)).thenReturn(true);
        Assertions.assertEquals(0, selectedItem);
        handleInput();
        Assertions.assertEquals(1, selectedItem);
        handleInput();
        Assertions.assertEquals(0, selectedItem);
    }
}
