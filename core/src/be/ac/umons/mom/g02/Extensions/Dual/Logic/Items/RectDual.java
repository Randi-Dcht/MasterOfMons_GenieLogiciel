package be.ac.umons.mom.g02.Extensions.Dual.Logic.Items;

import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/***/
public class RectDual
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
    public RectDual(GraphicalSettings gs, Color colorInt, Color colorExt,int width, int height)
    {
        this.colorExt = colorExt;
        this.colorInt = colorInt;
        this.gs       = gs;
        this.width    = width;
        this.height   = height;
        drawInt       = colorInt != null;
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
    }


    /***/
    public void draw(int positionX, int positionY)
    {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(colorExt);
        sr.rect(positionX,positionY,width,height);
        if (drawInt)
        {
            sr.setColor(colorInt);
            sr.rect(positionX,positionY,width-10,height-10);
        }
        sr.end();
    }


    /***/
    public void dispose()
    {
        sr.dispose();
    }
}
