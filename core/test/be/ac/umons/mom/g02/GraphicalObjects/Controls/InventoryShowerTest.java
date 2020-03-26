package be.ac.umons.mom.g02.GraphicalObjects.Controls;

import be.ac.umons.mom.g02.Enums.KeyStatus;
import be.ac.umons.mom.g02.GraphicalObjects.Controls.InventoryShower;
import be.ac.umons.mom.g02.GraphicalObjects.InventoryItem;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import com.badlogic.gdx.Input;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

/**
 * Testing class for <code>InventoryShower</code>
 */
public class InventoryShowerTest {

    InventoryShower is;

    @BeforeEach
    public void init() {
        is = new InventoryShower();
        is.gim = Mockito.mock(GameInputManager.class);
        is.inventoryItemList = new ArrayList<>();
    }

    /**
     * Test if the method handleInput is working as expected
     */
    @Test
    public void handleInputTest() {
        InventoryItem ii1, ii2;
        ii1 = Mockito.mock(InventoryItem.class);
        ii2 = Mockito.mock(InventoryItem.class);
        is.inventoryItemList.add(ii1);
        is.inventoryItemList.add(ii2);
        is.handleInput();
        Assertions.assertNull(is.selectedItem);
        Mockito.when(is.gim.isKey(Input.Keys.NUM_1, KeyStatus.Pressed)).thenReturn(true);
        is.handleInput();
        Assertions.assertEquals(is.selectedItem, ii1);
        Mockito.verify(ii1, Mockito.times(1)).select();
        Mockito.when(is.gim.isKey(Input.Keys.NUM_1, KeyStatus.Pressed)).thenReturn(false);
        Mockito.when(is.gim.isKey(Input.Keys.NUM_2, KeyStatus.Pressed)).thenReturn(true);
        is.handleInput();
        Mockito.verify(ii1, Mockito.times(1)).select();
        Mockito.verify(ii1, Mockito.times(1)).unselect();
        Mockito.verify(ii2, Mockito.times(1)).select();
        Assertions.assertEquals(is.selectedItem, ii2);
        Mockito.when(is.gim.isKey(Input.Keys.NUM_2, KeyStatus.Pressed)).thenReturn(false);
        Mockito.when(is.gim.isKey(Input.Keys.NUM_3, KeyStatus.Pressed)).thenReturn(true);
        is.handleInput();
        Mockito.verify(ii1, Mockito.times(1)).select();
        Mockito.verify(ii1, Mockito.times(1)).unselect();
        Mockito.verify(ii2, Mockito.times(1)).select();
        Assertions.assertEquals(is.selectedItem, ii2);
    }
}
