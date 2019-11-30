package be.ac.umons.sgl.mom.GraphicalObjects;

import be.ac.umons.sgl.mom.Animations.DoubleAnimation;
import be.ac.umons.sgl.mom.Enums.GameObjects;
import be.ac.umons.sgl.mom.Enums.KeyStatus;
import be.ac.umons.sgl.mom.Managers.AnimationManager;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.ArrayList;
import java.util.List;

/**
 * Support pour montrer l'inventaire d'un personnage.
 * @author Guillaume Cardoen
 */
public class InventoryShower extends Control {
    /**
     * La marge entre les différents éléments d'inventaire.
     */
    private static int BETWEEN_ITEM_MARGIN = 7;
    /**
     * La marge par rapport au bas de la fenêtre.
     */
    private static int BOTTOM_MARGIN = 7;
    /**
     * L'opacité du rectangle d'arrière-plan.
     */
    private static float BACKGROUND_RECTANGLE_OPACITY = .5f;

    /**
     * La hauteur du support.
     */
    private int height;
    /**
     * La taille d'un seul élément d'inventaire.
     */
    private int itemWidth;

    /**
     * Permet de dessiner les formes comme les rectangles.
     */
    private ShapeRenderer sr;
    /**
     * L'inventaire à montrer.
     */
    private List<GameObjects> inventory;
    /**
     * Doit-on utiliser les variables d'animations ?
     */
    private boolean isBeingAnimated = false;
    /**
     * La taille du support durant l'animation de celui-ci.
     */
    private float duringAnimationHeight, duringAnimationWidth;
    /**
     * L'opacité du rectangle d'arrière-plan durant l'animation du support.
     */
    private double duringAnimationBackgroundOpacity;
    /**
     * L'opacité du rectangle d'avant-plan durant l'animation du support.
     */
    private double duringAnimationForegroundOpacity;
    /**
     * La liste de tout les éléments d'inventaire actuellement à montrer.
     */
    private List<InventoryItem> inventoryItemList;
    /**
     * L'objet responsable des animations.
     */
    private AnimationManager am;
    /**
     * L'indice de l'élément d'inventaire selectionné par l'utilisateur.
     */
    private InventoryItem selectedItem;

    /**
     * Crée un nouveau support pour montrer l'inventaire d'un joueur.
     * @param gim Le gestionnaire d'entrée du jeu.
     * @param gs Les paramètres graphiques du jeu.
     * @param am Un gestionnaire d'animation afin de mettre à jour les animations.
     * @param inventoryOf Le joueur dont l'inventaire doit être montré.
     */
    public InventoryShower(GameInputManager gim, GraphicalSettings gs, AnimationManager am, Player inventoryOf) {
        super(gim, gs);
        inventory = inventoryOf.getInventory();
        this.am = am;
        init();
    }

    /**
     * Initialise les variables du support.
     */
    public void init() {
        inventoryItemList = new ArrayList<>();
        for (GameObjects go : inventory)
            inventoryItemList.add(new InventoryItem(gs, go));
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
        animateInventoryShower();
    }

    /**
     * Dessine le support en le centrant dans le bas de l'écran.
     * @param batch Où le support doit être dessiné.
     * @param centerX La position horizontale du centre de la fenêtre.
     * @param height La taille verticale du support.
     * @param itemWidth La taille horizontale d'un seul élément d'inventaire.
     * @param itemHeight La taille verticale d'un seul élément d'inventaire.
     */
    public void draw(Batch batch, int centerX, int height, int itemWidth, int itemHeight) {
        int beginX = centerX - (itemWidth * inventory.size() + BETWEEN_ITEM_MARGIN * (inventory.size() + 2)) / 2;
        super.draw(batch, beginX, height, itemWidth, itemHeight);
        this.height = height;
        this.itemWidth = itemWidth;
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);

        sr.begin(ShapeRenderer.ShapeType.Filled);
        if (isBeingAnimated) {
            sr.setColor(21f / 255, 21f / 255, 21f / 255, (float)duringAnimationBackgroundOpacity * BACKGROUND_RECTANGLE_OPACITY);
            sr.rect(beginX, BOTTOM_MARGIN, duringAnimationWidth, duringAnimationHeight);
        }
        else {
            sr.setColor(21f / 255, 21f / 255, 21f / 255, BACKGROUND_RECTANGLE_OPACITY);
            sr.rect(beginX, BOTTOM_MARGIN, getWidth(), height);
        }
        sr.end();
        int tmpBeginX = beginX + BETWEEN_ITEM_MARGIN;
        for (InventoryItem ii : inventoryItemList) {
            ii.draw(batch, tmpBeginX, BOTTOM_MARGIN, itemWidth, itemHeight);
            tmpBeginX += itemWidth + BETWEEN_ITEM_MARGIN;
        }

        Gdx.gl.glDisable(GL30.GL_BLEND);
    }


    /**
     * Lance les animations de la partie "Inventaire" du HUD.
     */
    public void animateInventoryShower() {
        beginAnimation();
        DoubleAnimation da = new DoubleAnimation(0, 1, 750);
        da.setRunningAction(() -> {
            setDuringAnimationWidth((int)((double)getWidth() * da.getActual()));
            setDuringAnimationHeight((int)((double)getHeight() * da.getActual()));
            setDuringAnimationBackgroundOpacity(da.getActual());
        });
        am.addAnAnimation("InventoryShowerAnimation", da);
        DoubleAnimation da2 = new DoubleAnimation(0, 1, 750);
        da2.setEndingAction(this::finishAnimation);
        da2.setRunningAction(() -> {
            setDuringAnimationForegroundOpacity(da2.getActual());
        });
        da.setEndingAction(() -> {
            am.addAnAnimation("InventoryShowerForegroundAnimation", da2);
        });
    }

    @Override
    public void handleInput() {
        for (int i = Input.Keys.NUM_1; i <= Input.Keys.NUM_9; i++) {
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

    @Override
    public void dispose() {
        sr.dispose();
    }

    /**
     * Retourne la taille horizontale du support.
     * @return La taille horizontale du support.
     */
    public int getWidth() {
        return itemWidth * inventory.size() + (inventory.size() + 1) * BETWEEN_ITEM_MARGIN;
    }
    /**
     * Retourne la taille verticale du support.
     * @return La taille verticale du support.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Commencer à utiliser les variables d'animations.
     */
    public void beginAnimation() {
        isBeingAnimated = true;
        for (InventoryItem ii : inventoryItemList) {
            ii.beginAnimation();
        }
    }

    /**
     * Arrêter d'utiliser les variables d'animations.
     */
    public void finishAnimation() {
        isBeingAnimated = false;
        for (InventoryItem ii : inventoryItemList) {
            ii.finishAnimation();
        }
    }

    /**
     * Retourne l'opacité du rectangle d'arrière-plan durant l'animation du support.
     * @return L'opacité du rectangle d'arrière-plan durant l'animation du support.
     */
    public double getDuringAnimationBackgroundOpacity() {
        return duringAnimationBackgroundOpacity;
    }

    /**
     * Défini l'opacité du rectangle d'arrière-plan durant l'animation du support.
     * @param duringAnimationBackgroundOpacity L'opacité du rectangle d'arrière-plan durant l'animation du support.
     */
    public void setDuringAnimationBackgroundOpacity(double duringAnimationBackgroundOpacity) {
        this.duringAnimationBackgroundOpacity = duringAnimationBackgroundOpacity;
    }

    /**
     * Retourne l'opacité du rectangle d'avant-plan durant l'animation du support.
     * @return L'opacité du rectangle d'avant-plan durant l'animation du support.
     */
    public double getDuringAnimationForegroundOpacity() {
        return duringAnimationForegroundOpacity;
    }

    /**
     * Défini l'opacité du rectangle d'avant-plan durant l'animation du support.
     * @param duringAnimationForegroundOpacity L'opacité du rectangle d'avant-plan durant l'animation du support.
     */
    public void setDuringAnimationForegroundOpacity(double duringAnimationForegroundOpacity) {
        this.duringAnimationForegroundOpacity = duringAnimationForegroundOpacity;

        for (InventoryItem ii : inventoryItemList) {
            ii.setDuringAnimationForegroundOpacity((float)duringAnimationForegroundOpacity);
        }
    }

    /**
     * Retourne la taille horizontale du support durant l'animation.
     * @return La taille horizontale du support durant l'animation.
     */
    public float getDuringAnimationWidth() {
        return duringAnimationWidth;
    }

    /**
     * Défini la taille horizontale du support durant l'animation.
     * @param duringAnimationWidth La taille horizontale du support durant l'animation.
     */
    public void setDuringAnimationWidth(float duringAnimationWidth) {
        this.duringAnimationWidth = duringAnimationWidth;
    }

    /**
     * Retourne la taille verticale du support durant l'animation.
     * @return La taille verticale du support durant l'animation.
     */
    public float getDuringAnimationHeight() {
        return duringAnimationHeight;
    }

    /**
     * Défini la taille verticale du support durant l'animation.
     * @param duringAnimationHeight La taille verticale du support durant l'animation.
     */
    public void setDuringAnimationHeight(float duringAnimationHeight) {
        this.duringAnimationHeight = duringAnimationHeight;
    }
}
