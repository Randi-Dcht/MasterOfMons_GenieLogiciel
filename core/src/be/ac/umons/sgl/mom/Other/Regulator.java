package be.ac.umons.sgl.mom.Other;

import be.ac.umons.sgl.mom.Enums.Place;
import be.ac.umons.sgl.mom.Events.Events;
import be.ac.umons.sgl.mom.Events.Notifications.Dialog;
import be.ac.umons.sgl.mom.Events.Notifications.Notification;
import be.ac.umons.sgl.mom.Events.Observer;
import be.ac.umons.sgl.mom.Events.SuperviserNormally;
import be.ac.umons.sgl.mom.Objects.Characters.People;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class allows to regular the people during the game as pass the night, up the energizing and etc
 * @author Umons_Group_2_ComputerScience_RandyDauchot
 */
public class Regulator implements Observer
{

    /**
     * This is the instance of the human player
     */
    private People player;
    /**
     * This is the instance of the game time
     */
    private TimeGame time;
    /**
     * This is the instance of the class who manage the game
     */
    private SuperviserNormally manager;
    /**
     * To warn the people if there are problems
     */
    private boolean informEnergizing = true;
    /**
     * The all place of the maps
     */
    private ArrayList<Place> places;


    /**
     * This constructor define the regulator class during the game
     */
    public Regulator(People people, TimeGame time)
    {
        this.player = people;
        this.time   = time;
        this.manager= SuperviserNormally.getSupervisor();
        manager.getEvent().add(Events.ChangeHour,this);
        manager.getEvent().add(Events.PlaceInMons,this);
        places = new ArrayList<Place>();
        places.addAll(Arrays.asList(Place.values()));

    }


    /**
     * This method allows to analyse the energizing of the people if it is low
     */
    public void lowEnergizing()
    {
        if (player.getEnergy() <= 10 && informEnergizing)
        {
            manager.getEvent().notify(new Dialog("Low10","ESC"));
            informEnergizing = false ;
        }
    }


    /**
     * This method allows to give the information about the actual Map
     */
    public void questionPlace(Place place)
    {
        if (places.contains(place))
        {
            manager.getEvent().notify(new Dialog(place.getInformation(),"ESC"));
            places.remove(place);
        }
        if (places.size()==0)
            manager.getEvent().remove(Events.PlaceInMons,this);
    }


    /**
     * This method allows to regular the time of the game as pass the night
     * This method also allows to add the energizing of the people
     */
    public void nightHour()
    {
        if (time.getDate().getHour() >= 22 && player.getPlace().equals(Place.Kot))
        {
            time.refreshTime(0,8,0);
            player.addEnergy(90); //TODO calculer difference
        }
    }


    /**
     * This method allows to receive the notification of the other class
     * @param notify is the notification
     */
    @Override
    public void update(Notification notify)
    {
        if (notify.getEvents().equals(Events.ChangeHour))
            nightHour();
        if (notify.getEvents().equals(Events.PlaceInMons) && notify.bufferNotEmpty())
            questionPlace((Place)notify.getBuffer());
    }
}
