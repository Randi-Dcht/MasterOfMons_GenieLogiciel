package be.ac.umons.sgl.mom.GraphicalObjects;

import be.ac.umons.sgl.mom.Enums.GameObjects;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

import static be.ac.umons.sgl.mom.GameStates.PlayingState.SHOWED_MAP_HEIGHT;
import static be.ac.umons.sgl.mom.GameStates.PlayingState.SHOWED_MAP_WIDTH;

/**
 * Représente un joueur du jeu ainsi que ces principales caractéristiques (position, inventaire, etc...).
 * @author Guillaume Cardoen
 */
public class Player extends Character {
    /**
     * Le coordonées du milieu de la carte.
     */
    private int middleX, middleY;
    /**
     * La taille d'une tuile.
     */
    private int tileWidth, tileHeight;
    /**
     * Les coordonées à ajouter au milieu de l'écran afin de positionner le joueur correctement.
     */
    private int xT, yT;
    /**
     * La taille de la carte (en pixel).
     */
    private int mapWidth, mapHeight;



    /**
     * Crée un nouveau personnage.
     * @param gs Les paramètres graphiques.
     * @param middleX La position horizontale du milieu de l'écran.
     * @param middleY La position verticale du milieu de l'écran.
     * @param tileWidth La taille horizontale d'une tuile.
     * @param tileHeight La taille verticale d'une tuile.
     * @param mapWidth La taille horizontale de la carte (en pixel).
     * @param mapHeight La taille verticale de la carte (en pixel).
     */
    public Player(GraphicalSettings gs, int middleX, int middleY, int tileWidth, int tileHeight, int mapWidth, int mapHeight) {
        super(gs);
        this.middleX = middleX;
        this.middleY = middleY;
        posX = middleX;
        posY = 0;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        inventory = new ArrayList<>();
        inventory.add(GameObjects.Object1);
        inventory.add(GameObjects.Object2);
    }

    /**
     * Crée un nouveau personnage.
     * @param gs Les paramètres graphiques.
     */
    public Player(GraphicalSettings gs) {
        super(gs);
        inventory = new ArrayList<>();
        inventory.add(GameObjects.Object1);
        inventory.add(GameObjects.Object2);
    }

//    /**
//     * Retourne la texture à utiliser pour le personnage en fonction de l'orientation du personnage.
//     * @return La texture à utiliser pour le personnage.
//     */
//    private Texture getTexture() {
//        switch (orientation) {
//            case Top:
//                return ((posY < 0 ? -posY : posY) / 100) % 2 == 1 ? assetManager.get("Pictures/Characters/hautbh.png") : assetManager.get("Pictures/Characters/hautbh2.png");
//            case Bottom:
//                return ((posY < 0 ? -posY : posY) / 100) % 2 == 1 ? assetManager.get("Pictures/Characters/basbh.png") : assetManager.get("Pictures/Characters/basbh2.png");
//            case Left:
//                return ((posX < 0 ? -posX : posX) / 100) % 2 == 1 ? assetManager.get("Pictures/Characters/gauchebh.png") : assetManager.get("Pictures/Characters/gauchebh2.png");
//            case Right:
//                return ((posX < 0 ? -posX : posX) / 100) % 2 == 1 ? assetManager.get("Pictures/Characters/droitebh.png") : assetManager.get("Pictures/Characters/droitebh2.png");
//        }
//        return assetManager.get("Characters/hautbh.png");
//    }

    /**
     * Dessine le personnage sur <code>batch</code>.
     * @param batch Où le personnage doit être dessiner.
     */
    public void draw(Batch batch) {
        batch.begin();
        batch.draw(getTexture(), middleX + xT, middleY + yT, tileWidth, 2 * tileHeight);
        batch.end();
    }

    /**
     * Déplace le personnage de (<code>x</code>, <code>y</code>)
     * @param x Le mouvement horizontale.
     * @param y Le mouvement verticale.
     */
    @Override
    public void move(int x, int y) {
        super.move(x, y);

        if (posX < 0)
            posX = 0;
        else if (posX > mapWidth - getWidth())
            posX = mapWidth - getWidth();

        int max = mapHeight / 2 - getHeight();
        if (posY > max)
            posY = max;
        else if (posY < -max)
            posY = -max;
//
        if (posX < SHOWED_MAP_WIDTH * tileWidth / 2)
            xT = -(SHOWED_MAP_WIDTH * tileWidth / 2 - posX);
        else if (posX > mapWidth - SHOWED_MAP_WIDTH * tileWidth / 2)
            xT = posX - mapWidth + SHOWED_MAP_WIDTH * tileWidth / 2;
        else xT = 0;

        if (posY > mapHeight / 2 - SHOWED_MAP_HEIGHT * tileHeight / 2)
            yT = posY - (mapHeight / 2 - SHOWED_MAP_HEIGHT * tileHeight / 2);
        else if (posY < -mapHeight / 2 + SHOWED_MAP_HEIGHT * tileHeight / 2)
            yT = posY + mapHeight / 2 - SHOWED_MAP_HEIGHT * tileHeight / 2;
        else yT = 0;
    }

    /**
     * Retourne la valeur à ajouter horizontalement au milieu de l'écran afin de positionner le joueur correctement.
     * @return La valeur à ajouter horizontalement au milieu de l'écran afin de positionner le joueur correctement.
     */
    public int getXT() {
        return xT;
    }

    /**
     * Retourne la valeur à ajouter verticalement au milieu de l'écran afin de positionner le joueur correctement.
     * @return La valeur à ajouter horizontalement au milieu de l'écran afin de positionner le joueur correctement.
     */
    public int getYT() {
        return yT;
    }

    /**
     * Retourne la taille horizontale du personnage.
     * @return La taille horizontale du personnage.
     */
    public int getWidth() {
        return tileWidth;
    }
    /**
     * Retourne la taille verticale du personnage.
     * @return La taille verticale du personnage.
     */
    public int getHeight() {
        return tileHeight;
    }

    /**
     * Retourne un rectangle représentant la taille et la position du personnage sur la carte.
     * @return Un rectangle représentant la taille et la position du personnage sur la carte.
     */
    public Rectangle getRectangle() {
        return new Rectangle(getPosX(), getPosY(), getWidth(), getHeight());
    }

    /**
     * Retourne un rectangle représentant la taille et la position (d'un point de vue isométrique où le (0,0) est l'extrémité au-dessus la carte) du personnage sur la carte.
     * Une partie du code a été tiré de https://stackoverflow.com/a/34522439 par Ernst Albrigtsen
     * @return Un rectangle représentant la taille et la position (d'un point de vue isométrique où le (0,0) est l'extrémité au-dessus la carte) du personnage sur la carte.
     */
    public Rectangle getMapRectangle() {
//        int x = (((-getPosY() + MasterOfMonsGame.HEIGHT / 2 - getHeight() / 2) * 2) - mapHeight + getPosX()) / 2 + 4276;
//        int y = getPosX() - x;
        int x = (int)((double)(-getPosY() + mapHeight / 2) / tileHeight + (getPosX() - mapWidth / 2) / tileWidth);
        int y = (int)((double)(-getPosY() + mapHeight / 2) / tileHeight - (getPosX() - mapWidth / 2) / tileWidth);

        return new Rectangle(x , y, ((float)getWidth() / tileWidth), (float)getHeight() / tileHeight);
    }

}
