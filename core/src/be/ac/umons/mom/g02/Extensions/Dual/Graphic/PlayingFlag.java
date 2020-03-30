package be.ac.umons.mom.g02.Extensions.Dual.Graphic;

import be.ac.umons.mom.g02.Extensions.Dual.Logic.Enum.TypeDual;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Items.Cases;
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
    /**
     * This is a base for the first people
     */
    protected Cases baseOne;
    /**
     * This is a base for the second people
     */
    protected Cases baseTwo;


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
        baseOne = new Cases(gs, Color.BLACK,Color.BLUE,tileWidth*3,tileHeight,MasterOfMonsGame.WIDTH/2+player.getPosX(),MasterOfMonsGame.HEIGHT/2+player.getPosY());
        baseTwo = new Cases(gs, Color.BLACK,Color.RED,tileWidth*3,tileHeight,MasterOfMonsGame.WIDTH/2+playerTwo.getPosX(),MasterOfMonsGame.HEIGHT/2+playerTwo.getPosY());

        Point ptFirst  = new Point(player.getPosX()/tileWidth,player.getPosY()/tileHeight);
        Point ptSecond = new Point(playerTwo.getPosX()/tileWidth,playerTwo.getPosY()/tileHeight);

        PlaceFlag(new Point(ptFirst.x,ptFirst.y + 1),new Point(ptFirst.x,ptFirst.y + 2),new Point(ptFirst.x,ptFirst.y + 3),new Point(ptSecond.x,ptSecond.y + 1),new Point(ptSecond.x,ptSecond.y + 2),new Point(ptSecond.x,ptSecond.y + 3));
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
