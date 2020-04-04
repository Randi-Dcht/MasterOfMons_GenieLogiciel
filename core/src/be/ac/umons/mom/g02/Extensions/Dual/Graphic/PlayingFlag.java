package be.ac.umons.mom.g02.Extensions.Dual.Graphic;

import be.ac.umons.mom.g02.Enums.KeyStatus;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Enum.TypeDual;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Items.Cases;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Items.Flag;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Regulator.SupervisorDual;
import be.ac.umons.mom.g02.Extensions.Multiplayer.Regulator.SupervisorMultiPlayer;
import be.ac.umons.mom.g02.GraphicalObjects.InventoryItem;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Character;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.MapObject;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Player;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Objects.Items.Items;
import be.ac.umons.mom.g02.Regulator.Supervisor;
import com.badlogic.gdx.graphics.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


/**
 * This class define the playingState for the catch the flag on the dual extension
 */
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


    /**
     * @param gs The game's graphical settings
     */
    public PlayingFlag(GraphicalSettings gs)
    {
        super(gs);
    }


    /**
     * This method allows to initialization of the playingState of the dual
     */
    @Override
    public void init()
    {
        super.init();
        baseOne = new Cases(gs, Color.BLACK,Color.BLUE,tileWidth*3,tileHeight,MasterOfMonsGame.WIDTH/2+player.getPosX(),MasterOfMonsGame.HEIGHT/2+player.getPosY());
        baseTwo = new Cases(gs, Color.BLACK,Color.RED,tileWidth*3,tileHeight,MasterOfMonsGame.WIDTH/2+playerTwo.getPosX(),MasterOfMonsGame.HEIGHT/2+playerTwo.getPosY());

        Point ptFirst  = new Point((int)(MasterOfMonsGame.WIDTH/2+player.getPosX()-cam.position.x)/tileWidth,(int)(MasterOfMonsGame.HEIGHT/2+player.getPosY()-cam.position.y)/tileHeight);
        Point ptSecond = new Point(playerTwo.getPosX()/tileWidth,playerTwo.getPosY()/tileHeight);

        cleanInventory((People)player.getCharacteristics());
        cleanInventory((People)playerTwo.getCharacteristics());

        PlaceFlag(new Point(ptFirst.x,ptFirst.y + 1),new Point(ptFirst.x,ptFirst.y + 2),new Point(ptFirst.x,ptFirst.y + 3),new Point(ptSecond.x,ptSecond.y + 1),new Point(ptSecond.x,ptSecond.y + 2),new Point(ptSecond.x,ptSecond.y + 3));
    }


    /**
     * This method allows to place the flag on the maps
     */
    public void PlaceFlag(Point... pt)
    {
        ArrayList<Point> lists = new ArrayList<>(Arrays.asList(pt));
        for (Items it : supervisorDual.getItems(TypeDual.CatchFlag.getStartMaps()))
            addItemToMap(it,lists.remove(new Random().nextInt(lists.size())),TypeDual.CatchFlag.getStartMaps().getMaps());
    }


    /**
     * Remove the flag in the bag
     * @param people is the people to clean inventory
     */
    private void cleanInventory(People people)
    {
        List<Items> it = people.getInventory();
        for (int i=0 ; i < it.size() ;i++)
        {
            if (it.get(i).getClass().equals(Flag.class))
                people.getInventory().remove(i);
        }
    }


    /**
     * This method allows to draw the object between map et player
     */
    @Override
    public void drawAfterMaps()
    {
        baseOne.draw((int)cam.position.x,(int)cam.position.y);
        baseTwo.draw((int)cam.position.x,(int)cam.position.y);
    }


    /**
     * See the input to check the input on the keyboard
     */
    @Override
    public void handleInput()
    {
        if (gim.isKey("pickUpAnObjectTwo", KeyStatus.Pressed) && player2Life && !(selectedOne instanceof Character) && selectedOne != null)
        {
            Items itm = ((MapObject)selectedOne).getItem();
            if (itm.getClass().equals(Flag.class) && ((Flag)itm).getMyPeople().equals(playerTwo.getCharacteristics())
                    && SupervisorMultiPlayer.getPeopleTwo().pushObject(itm))
                pickUpAnObject();
        }
        else if (gim.isKey("pickUpAnObject", KeyStatus.Pressed) && player2Life && !(selectedOne instanceof Character) && selectedOne != null)
        {
            Items itm = ((MapObject)selectedOne).getItem();
            if (itm.getClass().equals(Flag.class) && ((Flag)itm).getMyPeople().equals(player.getCharacteristics())
                    && SupervisorMultiPlayer.getPeople().pushObject(itm))
                pickUpAnObject();
        }
        else if (gim.isKey("useAnObject", KeyStatus.Pressed))
        {
            InventoryItem ii = inventoryShower.getSelectedItem();
            if (ii != null && check(player,baseOne))
               {
                   addItemToMap(ii.getItem(),player.getMapPos(),supervisorDual.getDual().getStartMaps().getMaps());
                   ii.getItem().used(Supervisor.getPeople());
               }
        }
        else if (gim.isKey("useAnObjectTwo", KeyStatus.Pressed))
        {
            InventoryItem ii = inventoryShower.getSelectedItem();
            if (ii != null && check(playerTwo,baseTwo))
            {
                addItemToMap(ii.getItem(),playerTwo.getMapPos(),supervisorDual.getDual().getStartMaps().getMaps());
                ii.getItem().used(SupervisorDual.getPeopleTwo());
            }
        }
        else if (gim.isKey("interact", KeyStatus.Pressed))
            return;
        else
            super.handleInput();
    }


    /**
     * Check if the player is on the base
     */
    private boolean check(Player player,Cases cases)
    {
        return true;
    }


    /**
     * Allows to dispose the graphical object (draw)
     */
    @Override
    public void dispose()
    {
        super.dispose();
        baseOne.dispose();
        baseTwo.dispose();
    }
}
