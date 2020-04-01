package be.ac.umons.mom.g02.Extensions.Dual.Logic.Mobile;

import be.ac.umons.mom.g02.Enums.Actions;
import be.ac.umons.mom.g02.Enums.Bloc;
import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Enums.MobileType;
import be.ac.umons.mom.g02.Enums.NameDialog;
import be.ac.umons.mom.g02.Enums.Orientation;
import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Events.Observer;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Character;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Player;
import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Regulator.Supervisor;

import java.util.Random;


/***/
public class ZombiePNJ extends Mobile implements Observer
{

    private static double SPEED = 0.18;
    private static int number = 0;

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
     * If the moving PNJ already meet the player on the maps
     */
    private boolean meet = false;
    /***/
    private Bloc[] listBloc = Bloc.values();


    /**
     * This constructor define the moving PNJ in the maps
     * @param maps is the maps of this pnj
     * @param type  is the type of the Mobile
     */
    public ZombiePNJ(MobileType type, Maps maps)
    {
        super("MovingPNJ",Bloc.BA1,type,Actions.Attack, NameDialog.Move);
        setMaps(maps);
    }


    /**
     * This class allows to create the instance graphic of this PNJ
     * @param character is the graphic setting
     * @param victim is the instance of graphic Player
     */
    public void initialisation(Character character,Player victim)
    {
        myGraphic = character;
        setVictim(victim);
        Supervisor.getSupervisor().addRefresh(this);
        Supervisor.getEvent().add(Events.Dead,this);
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
     * This method allows to give the size of the tiles
     * @param size is the size of the tile
     */
    public void setSize(int size)
    {
        tileSize = size;
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
        if (!meet && myGraphic != null && victim != null)
        {
            calculusDistance();
            moving(dt);
        }
    }


    /**
     * This method allows to move the people in the maps with refresh
     */
    private void moving(double dt)
    {
        int x=0,y=0;
        int toMove = (int)Math.round(SPEED * dt * tileSize);


        if(tileXbetween > toMove || tileXbetween < -toMove || tileYbetween > toMove || tileYbetween < -toMove)
        {
            if (tileXbetween < 0 )
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
            Supervisor.getSupervisor().meetCharacter(this,victim.getCharacteristics());
            meet=true;
            emplaceAdd();
        }
        myGraphic.move(x,y);
    }


    /***/
    public void emplaceAdd()
    {
        number++;
        if (number >= 10)
            speed = 0.12;
    }


    /***/
    public void emplaceRemove()
    {
        number--;
        if (number < 10)
            speed = 0.17;
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
     * @param notify
     */
    @Override
    public void update(Notification notify)
    {
        if (notify.getEvents().equals(Events.Dead) && notify.bufferNotEmpty() && notify.getBuffer().equals(this))
            emplaceRemove();
    }
}
