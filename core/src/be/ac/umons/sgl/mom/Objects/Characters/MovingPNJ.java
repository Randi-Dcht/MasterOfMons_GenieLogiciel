package be.ac.umons.sgl.mom.Objects.Characters;

import be.ac.umons.sgl.mom.Enums.Actions;
import be.ac.umons.sgl.mom.Enums.Bloc;
import be.ac.umons.sgl.mom.Enums.MobileType;
import be.ac.umons.sgl.mom.Enums.Place;
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

    private int tileXbetween;
    private int tileYbetween;
    private Character victim;
    private Character myGraphic;
    private boolean run = false;

public double time = 0.5;
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
        SuperviserNormally.getSupervisor().getEvent().add(Events.PlaceInMons,this);
    }


    public Character initialisation(GraphicalSettings gs, Player victim)
    {
        myGraphic = new Character(gs,this);
        setVictim(victim);
        return myGraphic;
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
    public void calculusDistance()
    {
        tileXbetween = myGraphic.getPosX() - victim.getPosX();
        tileYbetween = myGraphic.getPosY() - victim.getPosY();
    }


    /***/
    @Override
    public void update(double dt)
    {}


    /**
     * This method allows to move the people in the maps with refresh
     */
    public void moving(double dt)
    {

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
        if (notify.getEvents().equals(Events.PlaceInMons) && notify.bufferNotEmpty() && notify.getBuffer().equals(place))
            run = true;
        if (notify.getEvents().equals(Events.PlaceInMons) && notify.bufferNotEmpty() && !notify.getBuffer().equals(place))
            run = false;
    }
}
