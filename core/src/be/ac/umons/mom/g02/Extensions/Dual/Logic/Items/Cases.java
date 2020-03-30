package be.ac.umons.mom.g02.Extensions.Dual.Logic.Items;

import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/***/
public class Cases
{
    /***/
    protected Color colorInt;
    /***/
    protected Color colorExt;
    /***/
    protected GraphicalSettings gs;
    /***/
    protected ShapeRenderer sr;
    /***/
    protected int height;
    /***/
    protected int width;
    /***/
    protected boolean drawInt;
    /***/
    protected int positionX;
    /***/
    protected int positionY;


    /***/
    public Cases(GraphicalSettings gs, Color colorInt, Color colorExt,int width, int height,int positionX, int positionY)
    {
        this.colorExt  = colorExt;
        this.colorInt  = colorInt;
        this.gs        = gs;
        this.width     = width;
        this.height    = height;
        this.positionX = positionX;
        this.positionY = positionY;
        drawInt        = true;
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
    }


    /***/
    public Cases(GraphicalSettings gs,Color colorExt,int width, int height,int positionX, int positionY)
    {
        this(gs,null,colorExt,width,height,positionX,positionY);
        drawInt = false;
    }


    /***/
    public void draw(int camX, int camY)
    {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(colorExt);
        sr.rect(positionX-camX,positionY-camY,width,height);
        if (drawInt)
        {
            sr.setColor(colorInt);
            sr.rect(positionX+5-camX,positionY+5-camY,width-10,height-10);
        }
        sr.end();
    }


    /***/
    public void dispose()
    {
        sr.dispose();
    }
}
