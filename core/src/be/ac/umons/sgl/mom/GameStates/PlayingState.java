package be.ac.umons.sgl.mom.GameStates;

import be.ac.umons.sgl.mom.Animations.DoubleAnimation;
import be.ac.umons.sgl.mom.Enums.GameStates;
import be.ac.umons.sgl.mom.Enums.KeyStatus;
import be.ac.umons.sgl.mom.Enums.Orientation;
import be.ac.umons.sgl.mom.Enums.Type;
import be.ac.umons.sgl.mom.GraphicalObjects.Player;
import be.ac.umons.sgl.mom.GraphicalObjects.InventoryShower;
import be.ac.umons.sgl.mom.GraphicalObjects.ProgressBar;
import be.ac.umons.sgl.mom.GraphicalObjects.QuestShower;
import be.ac.umons.sgl.mom.Managers.AnimationManager;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameMapManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import be.ac.umons.sgl.mom.Objects.*;
import com.badlogic.gdx.Input;
import java.util.Timer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import static be.ac.umons.sgl.mom.GraphicalObjects.QuestShower.TEXT_AND_RECTANGLE_MARGIN;

/**
 * L'état de jeu du jeu. Il affiche la carte, un joueur ainsi qu'un HUD.
 * Une partie du contenu a été tiré de https://github.com/foreignguymike/legacyYTtutorials/tree/master/libgdxasteroids par ForeignGuyMike.
 * L'affichage de la carte a été tiré de https://www.youtube.com/watch?v=P8jgD-V5jG8 par Brent Aureli's - Code School (https://github.com/BrentAureli/SuperMario)
 * @author Guillaume Cardoen
 */
public class PlayingState extends GameState { // TODO : Put all disposes
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
    protected final float VELOCITY = 500;

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
    protected SpriteBatch sb;
    /**
     * Les objets empêchant le joueur d'aller plus loin.
     */
    protected MapObjects collisionObjects;
    /**
     * La caméra permettant de déplacer la partie de la carte montrée et ainsi le joueur. Elle permet aussi de montrer seulement une partie de carte en effectuant un zoom sur celle-ci.
     */
    protected OrthographicCamera cam;
    /**
     * Une partie du HUD montrant la quête active.
     */
    protected QuestShower questShower;
    /**
     * Une partie du HUD montrant l'inventaire du joueur.
     */
    protected InventoryShower inventoryShower;
    /**
     * Le joueur de la partie.
     */
    protected Player player;
    /**
     * L'objet responsable des animations.
     */
    protected AnimationManager am;

    /**
     * La barre de vie du joueur.
     */
    protected ProgressBar lifeBar;
    /**
     * La barre d'expérience du joueur.
     */
    protected ProgressBar expBar;
    /**
     * La barre d'énergie du joueur.
     */
    protected ProgressBar energyBar;

    protected GameMapManager gmm;

    /**
     * Crée un nouvel état de jeu.
     * @param gsm Le GameStateManager du jeu.
     * @param gim Le GameInputManager du jeu.
     * @param gs Les paramètres graphiques à utiliser.
     */
    public PlayingState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }

    @Override
    public void init() {
        super.init();
        sb = new SpriteBatch();
        am = new AnimationManager();
        gmm = gsm.getGameMapManager();

        gmm.setMap("isoTest.tmx");


        tileWidth = (int)gmm.getActualMap().getProperties().get("tilewidth");
        tileHeight = (int)gmm.getActualMap().getProperties().get("tileheight");
        mapWidth = (int)gmm.getActualMap().getProperties().get("width");
        mapHeight = (int)gmm.getActualMap().getProperties().get("height");
        collisionObjects = gmm.getActualMap().getLayers().get("Interdit").getObjects();

        cam = new OrthographicCamera(SHOWED_MAP_WIDTH * tileWidth, SHOWED_MAP_HEIGHT * tileHeight * 2);
        cam.position.x = SHOWED_MAP_WIDTH * tileWidth / 2;
        cam.position.y = SHOWED_MAP_HEIGHT * tileHeight;
        cam.update();
        gmm.setView(cam);

        questShower = new QuestShower(gs, am);
        player = new Player(gs,MasterOfMonsGame.WIDTH / 2, MasterOfMonsGame.HEIGHT / 2, tileWidth, tileHeight, mapWidth * tileWidth, mapHeight * tileHeight); // TODO : BUG AVEC EN BAS ET A GAUCHE
        inventoryShower = new InventoryShower(gs, sb, MasterOfMonsGame.WIDTH / 2, tileHeight * 2, tileWidth, tileWidth, player);


/*/!\devra être mis mais pourra changer de place (Randy pour Guillaume)/!\*/
        /*supprimer =>*///Rule rule = new Rule("TestRule",questShower);
        /*supprimer =>*/Supervisor.newParty("GuiRndMaxi",Type.normal,questShower);
        /*supprimer =>*/Objet o =new Energizing(0,0);
        /*supprimer =>*/Supervisor.add(o);
        /*supprimer => -------------------------*/
        /*supprimer =>*/Timer timer = new Timer();
        /*supprimer =>*/Delete tt = new Delete(Supervisor.getPeople());
        /*supprimer =>*/timer.schedule(tt,0,100);

        /*déplacer Supervisor =>*/ //questShower.setQuest(rule.getPeople().getQuest());
        //q.addProgress(50);

        lifeBar = new ProgressBar();
        lifeBar.setForegroundColor(new Color(213f / 255, 0, 0, .8f));
        expBar = new ProgressBar();
        expBar.setForegroundColor(new Color(46f / 255, 125f / 255, 50f / 255, .8f));
        energyBar = new ProgressBar();
        energyBar.setForegroundColor(new Color(2f / 255, 119f / 255, 189f / 255, .8f));

        animateHUD();
    }

    @Override
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

        translateCamera(toMoveX, toMoveY);
    }

    /**
     * Bouge la caméra en fonction de la position du joueur.
     * @param movedX Le mouvement horizontal du joueur.
     * @param movedY La mouvement vertical du joueur.
     */
    protected void translateCamera(int movedX, int movedY) {
        cam.translate(movedX, movedY);
        if (cam.position.x < SHOWED_MAP_WIDTH * tileWidth / 2)
            cam.position.x = SHOWED_MAP_WIDTH * tileWidth / 2;
        else if (cam.position.x > (mapWidth - SHOWED_MAP_WIDTH) * tileWidth)
            cam.position.x = (mapWidth - SHOWED_MAP_WIDTH) * tileWidth;

        if (cam.position.y > 0)
            cam.position.y = 0;
        else if (cam.position.y < -(mapHeight - SHOWED_MAP_HEIGHT) * tileHeight)
            cam.position.y = -(mapHeight - SHOWED_MAP_HEIGHT) * tileHeight;
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

    @Override
    public void draw() {
        int topBarWidth = (int)((MasterOfMonsGame.WIDTH - 4 * leftMargin) / 3);
        int topBarHeight = 10;

        gmm.render();
        player.draw(sb);

        // Dessine le HUD.
        questShower.draw(sb, tileWidth / 2 - TEXT_AND_RECTANGLE_MARGIN, (int)(MasterOfMonsGame.HEIGHT - 2 * topMargin - topBarHeight));
        inventoryShower.draw();
        lifeBar.draw((int)leftMargin, MasterOfMonsGame.HEIGHT - (int)topMargin - topBarHeight, topBarWidth, topBarHeight);
        expBar.draw((int)leftMargin * 2 + topBarWidth, MasterOfMonsGame.HEIGHT - (int)topMargin - topBarHeight, topBarWidth, topBarHeight);
        energyBar.draw((int)leftMargin * 3 + topBarWidth * 2, MasterOfMonsGame.HEIGHT - (int)topMargin - topBarHeight, topBarWidth, topBarHeight);
    }

    @Override
    public void handleInput() {
        if (gim.isKey(Input.Keys.ESCAPE, KeyStatus.Pressed)) {
            gsm.setState(GameStates.InGameMenu);
        }
    }

    @Override
    public void dispose() {
        gmm.dispose();
        sb.dispose();
    }

    /**
     * Lance les animations du HUD.
     */
    protected void animateHUD() {
        animateInventoryShower();
        animateQuestRectangle();
    }

    /**
     * Lance les animations de la partie "Quête" du HUD.
     */
    protected void animateQuestRectangle() {
        questShower.beginAnimation();
        DoubleAnimation da = new DoubleAnimation(0, 1, 750);
        da.setRunningAction(() -> {
            questShower.setDuringAnimationQuestShowerWidth((int)((double)questShower.getWidth() * da.getActual()));
            questShower.setDuringAnimationQuestShowerHeight((int)((double)questShower.getHeight() * da.getActual()));
            questShower.setDuringAnimationForegroundOpacity(da.getActual());
        });
        am.addAnAnimation("QuestRectangleAnimation", da);
        da.setEndingAction(() -> {
            questShower.finishAnimation();
        });
    }

    /**
     * Lance les animations de la partie "Inventaire" du HUD.
     */
    protected void animateInventoryShower() {
        inventoryShower.beginAnimation();
        DoubleAnimation da = new DoubleAnimation(0, 1, 750);
        da.setRunningAction(() -> {
            inventoryShower.setDuringAnimationWidth((int)((double)inventoryShower.getWidth() * da.getActual()));
            inventoryShower.setDuringAnimationHeight((int)((double)inventoryShower.getHeight() * da.getActual()));
            inventoryShower.setDuringAnimationBackgroundOpacity(da.getActual());
        });
        am.addAnAnimation("InventoryShowerAnimation", da);
        DoubleAnimation da2 = new DoubleAnimation(0, 1, 750);
        da2.setEndingAction(() -> inventoryShower.finishAnimation());
        da2.setRunningAction(() -> {
            inventoryShower.setDuringAnimationForegroundOpacity(da2.getActual());
        });
        da.setEndingAction(() -> {
            am.addAnAnimation("InventoryShowerForegroundAnimation", da2);
        });
    }
}
