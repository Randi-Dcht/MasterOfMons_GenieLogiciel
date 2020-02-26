package be.ac.umons.sgl.mom.Objects.Characters;

import be.ac.umons.sgl.mom.Enums.*;
import be.ac.umons.sgl.mom.Events.Events;
import be.ac.umons.sgl.mom.Events.Notifications.Notification;
import be.ac.umons.sgl.mom.Events.Observer;
import be.ac.umons.sgl.mom.Events.SuperviserNormally;
import be.ac.umons.sgl.mom.GraphicalObjects.OnMapObjects.Character;
import be.ac.umons.sgl.mom.GraphicalObjects.OnMapObjects.Player;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;


/**
 * This class define the moving PNJ with displacement in the maps
 * @author Umons_Group_2_ComputerScience
 */
public class MovingPNJ extends Mobile implements Observer
{

    /**
     * The constance of the time between two displacements
     */
    private static final double TIME = 0.7;
    /**
     * Distance between people and this PNJ on X
     */
    private int tileXbetween;
    /**
     * Distance between people and this PNJ on Y
     */
    private int tileYbetween;
    /**
     * The human player
     */
    private Character victim;
    /**
     * The instance of this PNJ (graphic)
     */
    private Character myGraphic;
    /**
     * Size of the tile
     */
    private int tileSize = 64;
    /**
     * The time to displacement on the maps
     */
    private double time;


    /**
     * This constructor define the moving PNJ in the maps
     * @param bloc  is the level of the player
     * @param place is the place of this pnj
     * @param type  is the type of the Mobile
     */
    public MovingPNJ(Bloc bloc, MobileType type, Place place)
    {
        super("MovingPNJ",bloc,type,Actions.Attack);
        setPlace(place);
        time = TIME;
        SuperviserNormally.getSupervisor().getEvent().add(Events.PlaceInMons,this);
    }


    /**
     * This class allows to create the instance graphic of this PNJ
     * @param gs is the graphic setting
     * @param victim is the instance of graphic Player
     */
    public Character initialisation(GraphicalSettings gs, Player victim)
    {
        myGraphic = new Character(gs,this);
        setVictim(victim);
        return myGraphic;
    }


    /**
     * This method allows to give the size of the tiles
     * @param size is the size of the tile
     */
    public void setSize(int size)
    {
        tileSize = size;
    }


    /**
     * This method allows to add a victim player to the mobile
     * @param victim  is the graphic of the player
     */
    public void setVictim(Player victim)
    {
        this.victim = victim;
    }


    /**
     * This method calculus the distance between this and victim player
     */
    private void calculusDistance()
    {
        tileXbetween = myGraphic.getPosX() - victim.getPosX();
        tileYbetween = myGraphic.getPosY() - victim.getPosY();
    }


    /**
     * This method give the time between two frames
     * @param dt is the time between two frames
     */
    @Override
    public void update(double dt)
    {
        time = time - dt;
        if (time < 0)
            moving();
    }


    /**
     * This method allows to move the people in the maps with refresh
     */
    private void moving()//TODO optimiser cela
    {
        int x=0,y=0;
        calculusDistance();

        if(tileXbetween > tileSize+5 || tileXbetween < 0)
        {
            if (tileXbetween < 0)
            {
                x = tileSize;
                myGraphic.setOrientation(Orientation.Right);
            }
            else
            {
                x = -tileSize;
                myGraphic.setOrientation(Orientation.Left);
            }
        }
        else if (tileYbetween > tileSize+5 || tileYbetween < 0)
        {
            if (tileYbetween < 0)
            {
                y = tileSize;
                myGraphic.setOrientation(Orientation.Top);
            }
            else
            {
                y = -tileSize;
                myGraphic.setOrientation(Orientation.Bottom);
            }
        }
        else
        {
            SuperviserNormally.getSupervisor().meetCharacter(this,victim.getCharacteristics());
        }
        myGraphic.move(x,y);
        time = TIME;
    }


    /**
     * This method allows to give the instance of graphic Character
     * @return instance of character
     */
    public Character getCharacter()
    {
        return myGraphic;
    }


    /**
     * The method to receive the notification
     * @param notify is the notification of the game
     */
    @Override
    public void update(Notification notify)
    {
         //TODO delete if never use
    }
}
