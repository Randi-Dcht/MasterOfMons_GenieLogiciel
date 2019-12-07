package be.ac.umons.sgl.mom.GameStates;

import be.ac.umons.sgl.mom.Enums.KeyStatus;
import be.ac.umons.sgl.mom.Enums.Orientation;
import be.ac.umons.sgl.mom.GameStates.Menus.InGameMenuState;
import be.ac.umons.sgl.mom.GraphicalObjects.Player;
import be.ac.umons.sgl.mom.GraphicalObjects.Controls.InventoryShower;
import be.ac.umons.sgl.mom.GraphicalObjects.QuestShower;
import be.ac.umons.sgl.mom.Managers.AnimationManager;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Cette classe correspond à la classe de test de la classe PLayingState. Tout le code testé est équivalent, cependant, certaines parties jugées inutiles ont été retirées.
 * Une partie du contenu a été tiré de https://github.com/foreignguymike/legacyYTtutorials/tree/master/libgdxasteroids par ForeignGuyMike.
 * L'affichage de la carte a été tiré de https://www.youtube.com/watch?v=P8jgD-V5jG8 par Brent Aureli's - Code School (https://github.com/BrentAureli/SuperMario)
 */
public class PlayingStateTest { // TODO : Put all disposes
    /**
     * La taille horizontale de la carte montré à l'écran.
     */
    public static final int SHOWED_MAP_WIDTH = 31;
    /**
     * La taille verticale de la carte montré à l'écran.
     */
    public static final int SHOWED_MAP_HEIGHT = 17;
    /**
     * La vitesse du joueur.
     */
    private final float VELOCITY = 5; // Modifié pour le bien du test

    /**
     * La taille horizontale (en nombre de tuile) de la carte entière.
     */
    protected int mapWidth;
    /**
     * La taille verticale (en nombre de tuile) de la carte entière.
     */
    protected int mapHeight;
    /**
     * La taille horizontale d'une tuile.
     */
    protected int tileWidth;
    /**
     * La taille verticale d'une tuile.
     */
    protected int tileHeight;
    /**
     * Utilisé afin de dessiner en autre le texte.
     */
    @Mock
    private SpriteBatch sb;
    /**
     * La carte du jeu.
     */
    @Mock
    private TiledMap map;
    /**
     * Le "renderer" de la carte du jeu.
     */
    @Mock
    private IsometricTiledMapRenderer itmr;
    /**
     * Les objets empêchant le joueur d'aller plus loin.
     */
    private MapObjects collisionObjects;
    /**
     * La caméra permettant de déplacer la partie de la carte montrée et ainsi le joueur. Elle permet aussi de montrer seulement une partie de carte en effectuant un zoom sur celle-ci.
     */
    @Mock
    private OrthographicCamera cam;
    /**
     * Une partie du HUD montrant la quête active.
     */
    @Mock
    private QuestShower questShower;
    /**
     * Une partie du HUD montrant l'inventaire du joueur.
     */
    @Mock
    private InventoryShower inventoryShower;
    /**
     * Le joueur de la partie.
     */
    private Player player;
    /**
     * L'objet responsable des animations.
     */
    @Mock
    private AnimationManager am;

    /**
     * Représente les paramètres graphiques du jeu normalement donné par la classe mère de PlayingState.
     */
    @Mock
    protected GraphicalSettings gs;

    /**
     * Représente le gestionnaire d'entrée du jeu normalement donné par la classe mère de PlayingState.
     */
    @Mock
    protected GameInputManager gim;

    /**
     * Représente le gestionnaire d'état du jeu normalement donné par la classe mère de PlayingState.
     */
    @Mock
    protected GameStateManager gsm;

    @BeforeEach
    public void init() {

        MockitoAnnotations.initMocks(this);

        tileWidth = 5; // Re-défini pour le bien du test.
        tileHeight = 5;
        mapWidth = 10;
        mapHeight = 10;

        collisionObjects = new MapObjects();

        player = new Player(gs,25, 25, 5, 5, 50, 50); // TODO : BUG AVEC EN BAS ET A GAUCHE

//        Quest q = new Quest("Test");
//        Quest q2 = new Quest("Test222222222222222222222");
//        Quest q3 = new Quest("Test3");
//        q.addSubQuests(q2, q3);
//        q2.finish();
//        q3.activate();
//        questShower.setQuest(q);

//        animateHUD();
    }

    public void update(float dt) {
        handleInput();

        am.update(dt);

        makePlayerMove(dt);
        cam.update();
    }

    /**
     * Met à jour les coordonées du joueur en fonction du temps avec la précédente mise à jour. Elle évite aussi que le joueur sorte du cadre de la carte.
     * @param dt Le temps avec la précédente mise à jour
     */
    protected void makePlayerMove(float dt) {
        int toMove = Math.round(VELOCITY * dt);
        int toMoveX = 0, toMoveY = 0;

        if (gim.isKey(Input.Keys.DOWN, KeyStatus.Down)) {
            player.setOrientation(Orientation.Bottom);
            toMoveY += -toMove;
        }
        if (gim.isKey(Input.Keys.UP, KeyStatus.Down)) {
            player.setOrientation(Orientation.Top);
            toMoveY += toMove;
        }
        if (gim.isKey(Input.Keys.LEFT, KeyStatus.Down)) {
            player.setOrientation(Orientation.Left);
            toMoveX += -toMove;
        }
        if (gim.isKey(Input.Keys.RIGHT, KeyStatus.Down)) {
            player.setOrientation(Orientation.Right);
            toMoveX += toMove;
        }

        player.move(toMoveX, toMoveY);
        if (checkForCollision(player)) {
            player.move(-toMoveX, -toMoveY);
            return;
        }
        if ((toMoveX < 0 && player.getXT() > -toMoveX) || (toMoveX > 0 && player.getXT() < -toMoveX))
            toMoveX = 0;
        else if ((toMoveX < 0 && player.getXT() > 0) || (toMoveX > 0 && player.getXT() < 0))
            toMoveX = player.getXT() + toMoveX;

        if ((toMoveY < 0 && player.getYT() > -toMoveY) || (toMoveY > 0 && player.getYT() < -toMoveY))
            toMoveY = 0;
        else if ((toMoveY < 0 && player.getYT() > 0) || (toMoveY > 0 && player.getYT() < 0))
            toMoveY = player.getYT() + toMoveY;

//        cam.translate(toMoveX, toMoveY);
//        if (cam.position.x < SHOWED_MAP_WIDTH * tileWidth / 2)
//            cam.position.x = SHOWED_MAP_WIDTH * tileWidth / 2;
//        else if (cam.position.x > (mapWidth - SHOWED_MAP_WIDTH) * tileWidth)
//            cam.position.x = (mapWidth - SHOWED_MAP_WIDTH) * tileWidth;
//
//        if (cam.position.y > 0)
//            cam.position.y = 0;
//        else if (cam.position.y < -(mapHeight - SHOWED_MAP_HEIGHT) * tileHeight)
//            cam.position.y = -(mapHeight - SHOWED_MAP_HEIGHT) * tileHeight;
    }

    /**
     * Vérifie si le joueur est en collision avec un des objets de la carte.
     * @param player Le joueur.
     * @return Si le joueur est en collision avec un des objets de la carte.
     */
    protected boolean checkForCollision(Player player) {
        for (RectangleMapObject rectangleMapObject : collisionObjects.getByType(RectangleMapObject.class)) {
            Rectangle rect = rectangleMapObject.getRectangle();
            Rectangle playerRect = player.getMapRectangle();
            if (Intersector.overlaps(rect, playerRect))
                return true;
        }
        return false;
    }

//    public void draw() {
//        itmr.setView(cam);
//        itmr.render();
//        player.draw(sb);
//        drawHud();
//    }

//    /**
//     * Dessine le HUD.
//     */
//    protected void drawHud() {
//        questShower.draw(sb, tileWidth / 2 - TEXT_AND_RECTANGLE_MARGIN, (int)(MasterOfMonsGame.HEIGHT - 2 * topMargin - topBarHeight));
//        inventoryShower.draw();
//    }

    public void handleInput() {
        if (gim.isKey(Input.Keys.ESCAPE, KeyStatus.Pressed)) {
            gsm.setState(InGameMenuState.class);
        }
    }

    public void dispose() {
        map.dispose();
        itmr.dispose();
        sb.dispose();
    }

//    /**
//     * Lance les animations du HUD.
//     */
//    protected void animateHUD() {
//        animateInventoryShower();
//        animateQuestRectangle();
//    }
//
//    /**
//     * Lance les animations de la partie "Quête" du HUD.
//     */
//    protected void animateQuestRectangle() {
//        questShower.beginAnimation();
//        DoubleAnimation da = new DoubleAnimation(0, 1, 750);
//        da.setRunningAction(() -> {
//            questShower.setDuringAnimationQuestShowerWidth((int)((double)questShower.getWidth() * da.getActual()));
//            questShower.setDuringAnimationQuestShowerHeight((int)((double)questShower.getHeight() * da.getActual()));
////            questShower.setDuringAnimationTextOpacity(da.getActual());
//        });
//        am.addAnAnimation("QuestRectangleAnimation", da);
//        da.setEndingAction(() -> {
//            questShower.finishAnimation();
//        });
//    }
//
//    /**
//     * Lance les animations de la partie "Inventaire" du HUD.
//     */
//    protected void animateInventoryShower() {
//        inventoryShower.beginAnimation();
//        DoubleAnimation da = new DoubleAnimation(0, 1, 750);
//        da.setRunningAction(() -> {
//            inventoryShower.setDuringAnimationWidth((int)((double)inventoryShower.getWidth() * da.getActual()));
//            inventoryShower.setDuringAnimationHeight((int)((double)inventoryShower.getHeight() * da.getActual()));
//            inventoryShower.setDuringAnimationBackgroundOpacity(da.getActual());
//        });
//        am.addAnAnimation("InventoryShowerAnimation", da);
//        DoubleAnimation da2 = new DoubleAnimation(0, 1, 750);
//        da2.setEndingAction(() -> inventoryShower.finishAnimation());
//        da2.setRunningAction(() -> {
//            inventoryShower.setDuringAnimationForegroundOpacity(da2.getActual());
//        });
//        da.setEndingAction(() -> {
//            am.addAnAnimation("InventoryShowerForegroundAnimation", da2);
//        });
//    }

    @Test
    public void movementTest() {
        Mockito.when(gim.isKey(Input.Keys.DOWN, KeyStatus.Down)).thenReturn(true);
        update(1);
        Assertions.assertEquals(25, player.getPosX());
        Assertions.assertEquals(-5, player.getPosY());
        Mockito.when(gim.isKey(Input.Keys.LEFT, KeyStatus.Down)).thenReturn(true);
        update(1);
        Assertions.assertEquals(20, player.getPosX());
        Assertions.assertEquals(-10, player.getPosY());
        Mockito.when(gim.isKey(Input.Keys.DOWN, KeyStatus.Down)).thenReturn(false);
        Mockito.when(gim.isKey(Input.Keys.RIGHT, KeyStatus.Down)).thenReturn(true);
        update(1);
        Assertions.assertEquals(20, player.getPosX());
        Assertions.assertEquals(-10, player.getPosY());
        Mockito.when(gim.isKey(Input.Keys.LEFT, KeyStatus.Down)).thenReturn(false);
        update(1);
        Assertions.assertEquals(25, player.getPosX());
        Assertions.assertEquals(-10, player.getPosY());
        Mockito.when(gim.isKey(Input.Keys.RIGHT, KeyStatus.Down)).thenReturn(false);
        Mockito.when(gim.isKey(Input.Keys.UP, KeyStatus.Down)).thenReturn(true);
        update(1);
        Assertions.assertEquals(25, player.getPosX());
        Assertions.assertEquals(-5, player.getPosY());
        update(.5f);
        Assertions.assertEquals(25, player.getPosX());
        Assertions.assertEquals(-2, player.getPosY()); // -2.5 arrondi à -2
        Mockito.when(gim.isKey(Input.Keys.RIGHT, KeyStatus.Down)).thenReturn(true);
        update(.5f);
        Assertions.assertEquals(28, player.getPosX()); // 2.5 arrondi à 3
        Assertions.assertEquals(1, player.getPosY());
        update(.5f);
        Assertions.assertEquals(31, player.getPosX()); // 2.5 arrondi à 3
        Assertions.assertEquals(4, player.getPosY()); // 2.5 arrondi à 3 pour éviter le "entre 2 pixels"
    }

    @Test
    public void collisionTest() {
        Mockito.when(gim.isKey(Input.Keys.UP, KeyStatus.Down)).thenReturn(false);
        Mockito.when(gim.isKey(Input.Keys.DOWN, KeyStatus.Down)).thenReturn(false);
        Mockito.when(gim.isKey(Input.Keys.RIGHT, KeyStatus.Down)).thenReturn(false);
        Mockito.when(gim.isKey(Input.Keys.LEFT, KeyStatus.Down)).thenReturn(false);
        player.move(-player.getPosX(), -player.getPosY()); // Remet coordonnée à 0,0

        Assertions.assertEquals(0, player.getPosX());
        Assertions.assertEquals(0, player.getPosY());
        Mockito.when(gim.isKey(Input.Keys.RIGHT, KeyStatus.Down)).thenReturn(true);
        collisionObjects.add(new RectangleMapObject(player.getMapRectangle().x + 1, player.getMapRectangle().y - 1,10,10));
        update(1);
        Assertions.assertEquals(0, player.getPosX());
        Assertions.assertEquals(0, player.getPosY());
        Mockito.when(gim.isKey(Input.Keys.RIGHT, KeyStatus.Down)).thenReturn(false);
        Mockito.when(gim.isKey(Input.Keys.DOWN, KeyStatus.Down)).thenReturn(true);
        update(1);
        Assertions.assertEquals(0, player.getPosX());
        Assertions.assertEquals(0, player.getPosY());
    }
}
