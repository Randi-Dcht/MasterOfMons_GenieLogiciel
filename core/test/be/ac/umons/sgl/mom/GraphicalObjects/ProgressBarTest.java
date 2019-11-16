package be.ac.umons.sgl.mom.GraphicalObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


/**
 * Classe de Test de ProgressBar
 */
public class ProgressBarTest {
    /**
     * La marge horizontale entre le rectangle d'avant plan et celui d'arrière-plan.
     */
    protected int BETWEEN_BACKGROUND_FOREGROUND_MARGIN_WIDTH = 7;

    /**
     * La marge verticale entre le rectangle d'avant plan et celui d'arrière-plan.
     */
    protected int BETWEEN_BACKGROUND_FOREGROUND_MARGIN_HEIGHT = 2;

    /**
     * La couleur d'arrière-plan.
     */
    protected Color backgroundColor = new Color(21f / 255, 21f/255, 21f/255, .5f);
    /**
     * La couleur d'avant-plan.
     */
    protected Color foregroundColor = new Color(42f / 255, 42f/255, 42f/255, .8f);

    /**
     * La valeur actuelle de la barre.
     */
    protected int value = 50;
    /**
     * La valeur maximale actuelle de la barre.
     */
    protected int maxValue = 100;
    /**
     * Le ratio entre la valeur et la valeur maximale de la barre.
     */
    protected double percent = .5f;
    /**
     * Permet de dessiner les formes comme les rectangles.
     */
    @Mock
    protected ShapeRenderer sr;

//    /**
//     * Initialise une nouvelle bar de progression.
//     */
//    public ProgressBarTest() {
//        sr = new ShapeRenderer();
//        sr.setAutoShapeType(true);
//    }

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Dessine la barre de progression aux coordonnées fournies avec la taille fournie..
     * @param x La position horizontale.
     * @param y La position verticale.
     * @param width La longueur.
     * @param height La largeur.
     */
    public void draw(int x, int y, int width, int height) {
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);

        sr.begin(ShapeRenderer.ShapeType.Filled);

        sr.setColor(backgroundColor);
        sr.rect(x, y, width, height);

        sr.setColor(foregroundColor);
        sr.rect(x + BETWEEN_BACKGROUND_FOREGROUND_MARGIN_WIDTH, y + BETWEEN_BACKGROUND_FOREGROUND_MARGIN_HEIGHT, (int)((width - 2 * BETWEEN_BACKGROUND_FOREGROUND_MARGIN_WIDTH) * percent), height - 2 * BETWEEN_BACKGROUND_FOREGROUND_MARGIN_HEIGHT);

        sr.end();

        Gdx.gl.glDisable(GL30.GL_BLEND);
    }

    /**
     * Défini la valeur de la barre.
     * @param value La valeur de la barre.
     */
    public void setValue(int value) {
        this.value = value;
        percent = (double)value / maxValue;
    }

    /**
     * Retourne la valeur de la barre.
     * @return La valeur de la barre.
     */
    public int getValue() {
        return value;
    }
    /**
     * Défini la valeur maximale de la barre.
     * @param maxValue La valeur maximale de la barre.
     */
    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
        percent = (double)value / maxValue;
    }
    /**
     * Retourne la valeur maximale de la barre.
     * @return La valeur maximale de la barre.
     */
    public int getMaxValue() {
        return maxValue;
    }

    /**
     * Défini la couleur d'arrière plan de la barre.
     * @param backgroundColor La couleur d'arrière plan de la barre.
     */
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * Défini la couleur d'avant-plan de la barre.
     * @param foregroundColor La couleur d'avant-plan de la barre.
     */
    public void setForegroundColor(Color foregroundColor) {
        this.foregroundColor = foregroundColor;
    }

    /**
     * Retourne le ratio entre la valeur et la valeur maximum de la barre.
     * @return Le ratio entre la valeur et la valeur maximum de la barre.
     */
    public double getPercent() {
        return percent;
    }

    @Test
    public void percentTest() {
        setMaxValue(20);
        setValue(5);
        Assertions.assertEquals(.25, getPercent());
        setMaxValue(50);
        Assertions.assertEquals(.1, getPercent());
    }
}
