package be.ac.umons.mom.g02.Extensions.Dual.Logic.Items;

import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Cases
{

    /**
     * This variable allows draw rectangle on the maps.
     */
    protected ShapeRenderer sr;
    /**
     * The variable of graphical settings.
     */
    protected GraphicalSettings gs;
    /**
     * This is the position X on the math of the cases
     */
    protected int positionX;
    /**
     * This is the position YX on the math of the cases
     */
    protected int positionY;
    /**
     * This is the color of the player
     */
    protected Color colorPlayer;


    /**
     * This constructor define a case color
     * @param x  is the position on X
     * @param y  is the position on Y
     * @param gs The graphical settings.
     */
    public Cases(GraphicalSettings gs,int x, int y,Color color)
    {
        this.gs = gs;
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
        this.positionX = x;
        this.positionY = y;
        colorPlayer = color;
    }


    /**
     * Draw the cases on the maps with the color of the player
     */
    public void draw()
    {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(colorPlayer);
        sr.rect((int)(positionX/10.41), (int)(positionY/4.97), 64, 32);
        sr.end();

        // System.out.println(positionX + ";" + positionY + "  /  " + MasterOfMonsGame.WIDTH/2 + ";" + MasterOfMonsGame.HEIGHT/2);
    }


    /**
     * This method allows to dispose the cases
     */
    public void dispose()
    {
        sr.dispose();
    }
}

