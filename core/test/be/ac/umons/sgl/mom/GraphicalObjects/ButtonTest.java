package be.ac.umons.sgl.mom.GraphicalObjects;

import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.awt.*;
import java.util.ArrayList;

/**
 * Un bouton avec du texte.
 */
public class ButtonTest {

    /**
     * L'action a éffectué quand le bouton reçoit un clique.
     */
    private Runnable onClick;
    /**
     * Permet de dessiner les formes comme les rectangles.
     */
    @Mock
    private ShapeRenderer sr;
    /**
     * Le texte affiché sur le bouton.
     */
    private String textToShow = "";
    /**
     * Si la souris est au dessus du bouton ou non.
     */
    private boolean isMouseOver;/**
     * Représente les paramètres graphiques du jeu.
     */
    @Mock
    protected GraphicalSettings gs;
    /**
     * Les coordonées où le contrôle doit être déssiné.
     * Rajouté pour le bien du test.
     */
    protected int x, y;
    /**
     * La taille du contrôle.
     * Rajouté pour le bien du test.
     */
    protected int width, height;
    /**
     * Représente le gestionnaire d'entrée du jeu normalement donné par la classe mère de Button.
     */
    @Mock
    protected GameInputManager gim;

//    /**
//     * Crée un nouveau bouton.
//     * @param gim Le GameInputManager du jeu.
//     * @param gs Les paramètres graphiques à utiliser.
//     */
//    public ButtonTest(GameInputManager gim, GraphicalSettings gs) {
////        super(gim, gs);
//        sr = new ShapeRenderer();
//    }

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    public void draw(Batch batch, int x, int y, int width, int height) {
//        super.draw(batch, x, y, width, height);
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        if (isMouseOver)
            sr.setColor(new Color(0x21212142));
        else
            sr.setColor(new Color(0x21212121));
        sr.rect(x, y, width, height);
        sr.end();
        batch.begin();
        int leftMargin = 0;
        gs.getNormalFont().draw(batch, textToShow, x + leftMargin, y + gs.getNormalFont().getLineHeight());
        batch.end();
        Gdx.gl.glDisable(GL30.GL_BLEND);
    }

    private int windowHeight = 50; // Pour les biens du test
    public void handleInput() {
        Rectangle buttonRectangle = new Rectangle(x, windowHeight - y - height, width, height); // MasterOfMonsGame.Height remplacé par windowHeight pour les biens du test.
        for (Point click : gim.getRecentClicks())
            if (onClick != null && buttonRectangle.contains(click))
                onClick.run();
        isMouseOver = buttonRectangle.contains(gim.getLastMousePosition());
    }

    public void dispose() {
        sr.dispose();
    }

    /**
     * Retourne le texte affiché.
     * @return Le texte affiché.
     */
    public String getText() {
        return textToShow;
    }

    /**
     * Défini le texte à afficher.
     * @param text Le texte à afficher.
     */
    public void setText(String text) {
        this.textToShow = text;
    }

    /**
     * Défini l'action à éxécuter si l'on clique sur le bouton.
     * @param onClick L'action à éxécuter en cas de clique.
     */
    public void setOnClick(Runnable onClick) {
        this.onClick = onClick;
    }

    private boolean clicked = false;

    @Test
    public void clickTest() {
        setOnClick(() -> clicked = true);
        x = 1; y = 1;
        width = 10; height = 10;
        ArrayList<Point> l = new ArrayList<Point>();
        l.add(new Point(5,45)); // Dedans car le y est inversé
        Mockito.when(gim.getRecentClicks()).thenReturn(l);
        Mockito.when(gim.getLastMousePosition()).thenReturn(new Point(0,0)); // Sans importance ici vu l'implémentation
        handleInput();
        Assertions.assertTrue(clicked);
        l.clear();
        clicked = false;
        l.add(new Point(0,5)); // Dehors
        handleInput();
        Assertions.assertFalse(clicked);
        l.clear();
        clicked = false;
        l.add(new Point(5,45)); // Dedans
        l.add(new Point(0,5)); // Dehors
        handleInput();
        Assertions.assertTrue(clicked);
        l.clear();
        clicked = false;
        l.add(new Point(5,0)); // Dehors
        handleInput();
        Assertions.assertFalse(clicked);
        l.clear();
        clicked = false;
        l.add(new Point(20,45)); // Dehors
        handleInput();
        Assertions.assertFalse(clicked);
    }

    public void isMouseOverTest() {
        x = 1; y = 1;
        width = 10; height = 10;
        Mockito.when(gim.getLastMousePosition()).thenReturn(new Point(0,0));
        Mockito.when(gim.getRecentClicks()).thenReturn(new ArrayList<>()); // Sans importance ici.
        Assertions.assertFalse(isMouseOver);
        Mockito.when(gim.getLastMousePosition()).thenReturn(new Point(5,45)); // Dedans (y inversé)
        Assertions.assertTrue(isMouseOver);
        Mockito.when(gim.getLastMousePosition()).thenReturn(new Point(15,45)); // Dehors
        Assertions.assertFalse(isMouseOver);
        Mockito.when(gim.getLastMousePosition()).thenReturn(new Point(5,5)); // Dehors
        Assertions.assertFalse(isMouseOver);


    }
}
