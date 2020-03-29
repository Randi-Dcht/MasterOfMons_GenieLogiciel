package be.ac.umons.mom.g02.Extensions.Dual.Graphic;

import be.ac.umons.mom.g02.Extensions.Dual.Logic.Enum.TypeDual;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Items.RectDual;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Objects.Items.Items;
import com.badlogic.gdx.graphics.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


/***/
public class PlayingFlag extends PlayingStateDual
{
    /***/
    protected RectDual baseOne;
    /***/
    protected RectDual baseTwo;

    /***/
    public PlayingFlag(GraphicalSettings gs)
    {
        super(gs);
    }


    /***/
    @Override
    public void init()
    {
        super.init();
        baseOne = new RectDual(gs, Color.BLACK,Color.BLUE,tileWidth*3,tileHeight,(int)(MasterOfMonsGame.WIDTH/2+player.getPosX()),(int)(MasterOfMonsGame.HEIGHT/2+player.getPosY()));
        baseTwo = new RectDual(gs, Color.BLACK,Color.RED,tileWidth*3,tileHeight,(int)(MasterOfMonsGame.WIDTH/2+playerTwo.getPosX()),(int)(MasterOfMonsGame.HEIGHT/2+playerTwo.getPosY()));

        PlaceFlag(new Point(10,33),new Point(10,32),new Point(10,31),new Point(11,33),new Point(12,33),new Point(13,33),new Point(14,33),new Point(15,33));
    }


    /***/
    public void PlaceFlag(Point... pt)
    {
        ArrayList<Point> lists = new ArrayList<>(Arrays.asList(pt));
        for (Items it : supervisorDual.getItems(TypeDual.CatchFlag.getStartMaps()))
            addItemToMap(it,lists.remove(new Random().nextInt(lists.size())),TypeDual.CatchFlag.getStartMaps().getMaps());

    }


    /***/
    @Override
    public void drawAfterMaps()
    {
        baseOne.draw((int)cam.position.x,(int)cam.position.y);
        baseTwo.draw((int)cam.position.x,(int)cam.position.y);
    }


    /***/
    @Override
    public void dispose()
    {
        super.dispose();
        baseOne.dispose();
        baseTwo.dispose();
    }
}
