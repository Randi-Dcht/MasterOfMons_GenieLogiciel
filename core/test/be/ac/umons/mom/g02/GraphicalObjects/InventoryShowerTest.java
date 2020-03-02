package be.ac.umons.mom.g02.GraphicalObjects;

import be.ac.umons.mom.g02.Enums.KeyStatus;
import be.ac.umons.mom.g02.GraphicalObjects.Controls.InventoryShower;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import com.badlogic.gdx.Input;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

/**
 * Cette classe représente les tests unitaires de la classe InventoryShower.
 */
public class InventoryShowerTest extends InventoryShower {

    @BeforeEach
    public void init() {
        gim = Mockito.mock(GameInputManager.class);
        inventoryItemList = new ArrayList<>();
    }

    @Test
    public void handleInputTest() {
        InventoryItem ii1, ii2;
        ii1 = Mockito.mock(InventoryItem.class);
        ii2 = Mockito.mock(InventoryItem.class);
        inventoryItemList.add(ii1);
        inventoryItemList.add(ii2);
        handleInput();
        Assertions.assertNull(selectedItem);
        Mockito.when(gim.isKey(Input.Keys.NUM_1, KeyStatus.Pressed)).thenReturn(true);
        handleInput();
        Assertions.assertEquals(selectedItem, ii1);
        Mockito.verify(ii1, Mockito.times(1)).select();
        Mockito.when(gim.isKey(Input.Keys.NUM_1, KeyStatus.Pressed)).thenReturn(false);
        Mockito.when(gim.isKey(Input.Keys.NUM_2, KeyStatus.Pressed)).thenReturn(true);
        handleInput();
        Mockito.verify(ii1, Mockito.times(1)).select();
        Mockito.verify(ii1, Mockito.times(1)).unselect();
        Mockito.verify(ii2, Mockito.times(1)).select();
        Assertions.assertEquals(selectedItem, ii2);
        Mockito.when(gim.isKey(Input.Keys.NUM_2, KeyStatus.Pressed)).thenReturn(false);
        Mockito.when(gim.isKey(Input.Keys.NUM_3, KeyStatus.Pressed)).thenReturn(true);
        handleInput();
        Mockito.verify(ii1, Mockito.times(1)).select();
        Mockito.verify(ii1, Mockito.times(1)).unselect();
        Mockito.verify(ii2, Mockito.times(1)).select();
        Assertions.assertEquals(selectedItem, ii2);


    }
}
