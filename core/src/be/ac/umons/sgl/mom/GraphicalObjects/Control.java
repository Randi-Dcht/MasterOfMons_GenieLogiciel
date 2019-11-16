package be.ac.umons.sgl.mom.GraphicalObjects;

import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Représente une partie d'interface utilisateur comme un bouton, une "boîte de texte", ... qui devra intéragir avec l'utilisateur.
 * @author Guillaume Cardoen
 */
public abstract class Control {
    /**
     * Les coordonées où le contrôle doit être déssiné.
     */
    protected int x, y;
    /**
     * La taille du contrôle.
     */
    protected int width, height;
    /**
     * Le GameInputManager du jeu.
     */
    protected GameInputManager gim;
    /**
     * Les paramètres graphiques du jeu.
     */
    protected GraphicalSettings gs;
    /**
     * La marge par rapport au bord gauche de la fenêtre.
     */
    protected int leftMargin;

    /**
     * La marge par rapport au bord haut de la fenêtre.
     */
    protected int topMargin;

    /**
     * Crée un nouveau contrôle.
     * @param gim Le GameInputManager du jeu.
     * @param gs Les paramètres graphiques du jeu.
     */
    protected Control (GameInputManager gim, GraphicalSettings gs) {
        this.gim = gim;
        this.gs = gs;
        leftMargin = MasterOfMonsGame.WIDTH / 100;
        topMargin = MasterOfMonsGame.HEIGHT / 100;
    }

    /**
     * Dessine le contrôle sur <code>batch</code> aux coordonées (<code>x</code>, <code>y</code>) avec la longueur <code>width</code> et la largeur <code>y</code>.
     * @param batch Où le contrôle sera déssiné.
     * @param x La position horizontale.
     * @param y La position verticale.
     * @param width La longueur.
     * @param height La largeur.
     */
    public void draw(Batch batch, int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Genère une action en fonction des entrées (clavier ou souris) de l'utilisateur.
     */
    public abstract void handleInput();

    public abstract void dispose();
}
