package be.ac.umons.sgl.mom.GameStates.Menus;

import be.ac.umons.sgl.mom.Enums.KeyStatus;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import com.badlogic.gdx.Input;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.awt.*;
import java.util.ArrayList;

/**
 * Classe réprésente la classe MenuState. Cette classe de test a principalement pour but de tester la méthode handleInput, cette méthode est restée inchangée mais le reste de la classe a été modifiée de manière à ce que l'éxécution de handleInput soit équivalente
 * mais sans avoir besoin de dessiner sur un SpriteBatch.
 */
public class MenuStateTest {

    /**
     * L'indice de l'élement selectionné.
     */
    protected int selectedItem = 0;

    /**
     * Les différents éléments à montrer à l'écran.
     */
    protected MenuItem[] menuItems;

    @Mock
    GameInputManager gim;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        menuItems = new MenuItem[] { new MenuItem("Master Of Mons", MenuItemType.Title, false, new Rectangle(5, 10, 20, 10)),
                new MenuItem("Play", MenuItemType.Title, true, new Rectangle(10, 20, 20, 10)),
                new MenuItem("Settings", MenuItemType.Title, true, new Rectangle(15, 30, 20, 10))};
        Mockito.when(gim.getRecentClicks()).thenReturn(new ArrayList<>());
        while (! menuItems[selectedItem].selectable) // Simulate the one in draw.
            selectedItem = (selectedItem + 1) % menuItems.length;
    }

    @Test
    public void testDown() {
        Mockito.when(gim.isKey(Input.Keys.DOWN, KeyStatus.Pressed)).thenReturn(true);
        Mockito.when(gim.isKey(Input.Keys.UP, KeyStatus.Pressed)).thenReturn(false);
        Assertions.assertEquals(1, selectedItem);
        handleInput();
        Assertions.assertEquals(2, selectedItem);
        handleInput();
        Assertions.assertEquals(1, selectedItem);
    }

    @Test
    public void testUp() {
        Mockito.when(gim.isKey(Input.Keys.DOWN, KeyStatus.Pressed)).thenReturn(false);
        Mockito.when(gim.isKey(Input.Keys.UP, KeyStatus.Pressed)).thenReturn(true);
        Assertions.assertEquals(1, selectedItem);
        handleInput();
        Assertions.assertEquals(2, selectedItem);
        handleInput();
        Assertions.assertEquals(1, selectedItem);
    }

    @Test
    public void testClick() {

        ArrayList<Point> l = new ArrayList<>();
        l.add(new Point(50, 50));
        Mockito.when(gim.getRecentClicks()).thenReturn(l);
        Assertions.assertEquals(1, selectedItem);
        handleInput();
        Assertions.assertEquals(1, selectedItem);
        l.clear();
        l.add(new Point(11, 11));
        handleInput();
        Assertions.assertEquals(1, selectedItem);
        l.clear();
        l.add(new Point(11, 21));
        handleInput();
        Assertions.assertEquals(1, selectedItem);
        l.clear();
        l.add(new Point(16, 31));
        handleInput();
        Assertions.assertEquals(2, selectedItem);
        l.add(new Point(49, 21));
        l.add(new Point(11, 11));
        l.add(new Point(50, 22));
        handleInput();
        Assertions.assertEquals(2, selectedItem);

    }

    public void handleInput() {
        if (gim.isKey(Input.Keys.DOWN, KeyStatus.Pressed)) {
            do
                selectedItem = (selectedItem + 1) % menuItems.length;
            while ( ! menuItems[selectedItem].selectable);
        }
        else if (gim.isKey(Input.Keys.UP, KeyStatus.Pressed)) {
            do {
                selectedItem = (selectedItem - 1) % menuItems.length;
                if (selectedItem < 0)
                    selectedItem += menuItems.length;
            }
            while ( ! menuItems[selectedItem].selectable);
        }
        for (Point click: gim.getRecentClicks()) {
            for (int i = 0; i < menuItems.length; i++) {
                if (menuItems[i].selectable && (menuItems[i].screenTextBound.contains(click.x, click.y)))
                    selectedItem = i;
            }
        }
    }

    /***
     * Représente un élément du menu.
     */
    protected class MenuItem {
        /***
         * Le nom de l'élément.
         */
        private String header;
        /***
         * Le type de l'élément.
         */
        private MenuItemType mit;
        /***
         * L'élément est-il selectionnable ?
         */
        private boolean selectable;
        /***
         * Représente la position et la taille de l'élément (ATTENTION : En fonction des coordonnées de l'écran)
         */
        private Rectangle screenTextBound;

        /***
         * Initialise un élément du menu.
         * @param header Le nom de l'élément.
         */
        public MenuItem(String header) {
            this(header, MenuItemType.Normal);
        }

        /***
         * Initialise un élément du menu.
         * @param header Le nom de l'élément.
         * @param mit Le type de l'élement.
         */
        public MenuItem(String header, MenuItemType mit) {
            this(header, mit, true);
        }

        /***
         * Initialise un élément du menu.
         * @param header Le nom de l'élément.
         * @param mit Le type de l'élement.
         * @param selectable L'élément est-il selectionnable ?
         */
        public MenuItem(String header, MenuItemType mit, boolean selectable) {
            this.header = header;
            this.mit = mit;
            this.selectable = selectable;
        }
        /***
         * Initialise un élément du menu.
         * @param header Le nom de l'élément.
         * @param mit Le type de l'élement.
         * @param selectable L'élément est-il selectionnable ?
         * @param screenTextBound Rectangle représentant la position et la taille de l'élément.
         */
        public MenuItem(String header, MenuItemType mit, boolean selectable, Rectangle screenTextBound) {
            this.header = header;
            this.mit = mit;
            this.selectable = selectable;
            this.screenTextBound = screenTextBound;
        }
    }

    /***
     * Les différents types possibles des éléments d'un menu.
     */
    public enum MenuItemType {
        Title,
        Normal;
    }
}
