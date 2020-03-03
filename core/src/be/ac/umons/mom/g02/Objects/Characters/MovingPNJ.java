package be.ac.umons.mom.g02.Objects.Characters;

import be.ac.umons.mom.g02.Enums.*;
import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Events.Observer;
import be.ac.umons.mom.g02.Events.SuperviserNormally;
import be.ac.umons.mom.g02.GameStates.PlayingState;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Character;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Player;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;


/**
 * This class define the moving PNJ with displacement in the maps
 * @author Umons_Group_2_ComputerScience
 */
public class MovingPNJ extends Mobile implements Observer
{

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
     * This is the variable of the playingState
     */
    private PlayingState ps;
    /**
     * Size of the tile
     */
    private int tileSize = 64;

    private boolean meet = false;


    /**
     * This constructor define the moving PNJ in the maps
     * @param bloc  is the level of the player
     * @param maps is the maps of this pnj
     * @param type  is the type of the Mobile
     */
    public MovingPNJ(Bloc bloc, MobileType type, Maps maps)
    {
        super("MovingPNJ",bloc,type,Actions.Dialog /*Actions.Attack*/);
        setMaps(maps);
        SuperviserNormally.getSupervisor().getEvent().add(Events.PlaceInMons,this);
    }


    /**
     * This class allows to create the instance graphic of this PNJ
     * @param gs is the graphic setting
     * @param victim is the instance of graphic Player
     */
    public Character initialisation(PlayingState ps, GraphicalSettings gs, Player victim)
    {
        myGraphic = new Character(gs,this);
        setVictim(victim);
        this.ps = ps;
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
        if (!meet)
            moving(dt);
    }


    /**
     * This method allows to move the people in the maps with refresh
     */
    private void moving(double dt)//TODO optimiser cela
    {
        int x=0,y=0;
        int toMove = (int)Math.round(ps.velocity * dt * tileSize);
        calculusDistance();

        if(tileXbetween > toMove || tileXbetween < -toMove || tileYbetween > toMove || tileYbetween < -toMove)
        {
            if (tileXbetween < 0)
            {
                x = toMove;
                myGraphic.setOrientation(Orientation.Right);
            }
            else
            {
                x = -toMove;
                myGraphic.setOrientation(Orientation.Left);
            }
            if (tileYbetween < 0)
            {
                y = toMove;
                myGraphic.setOrientation(Orientation.Top);
            }
            else
            {
                y = -toMove;
                myGraphic.setOrientation(Orientation.Bottom);
            }
        }
        else
        {
            SuperviserNormally.getSupervisor().meetCharacter(this,victim.getCharacteristics());
            meet=true;
        }
        /*if (ps.checkForCollision(myGraphic))*///TODO
            myGraphic.move(x,y);
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
