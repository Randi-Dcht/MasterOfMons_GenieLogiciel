package be.ac.umons.sgl.mom.GraphicalObjects;

import be.ac.umons.sgl.mom.Enums.KeyStatus;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import com.badlogic.gdx.Input;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe représente les tests unitaires de la classe InventoryShower.
 */
public class InventoryShowerTest {
    /**
     * La liste de tout les éléments d'inventaire actuellement à montrer.
     */
    private List<InventoryItem> inventoryItemList;
    /**
     * L'indice de l'élément d'inventaire selectionné par l'utilisateur.
     */
    private InventoryItem selectedItem;

    /**
     * Le gestionnaire d'entrée du jeu normalement donné par GameState.
     */
    @Mock
    private GameInputManager gim;

    public void handleInput() {
        for (int i = Input.Keys.NUM_1; i < Input.Keys.NUM_9; i++) {
            if (gim.isKey(i, KeyStatus.Pressed)) {
                int j = i - Input.Keys.NUM_1;
                if (j >= inventoryItemList.size())
                    return;
                if (selectedItem != null)
                    selectedItem.unselect();
                selectedItem = inventoryItemList.get(j);
                selectedItem.select();
            }
        }
    }

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
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
