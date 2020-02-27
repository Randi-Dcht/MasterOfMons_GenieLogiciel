package be.ac.umons.sgl.mom.Other;

import be.ac.umons.sgl.mom.Enums.Maps;
import be.ac.umons.sgl.mom.Enums.State;
import be.ac.umons.sgl.mom.Events.Events;
import be.ac.umons.sgl.mom.Events.Notifications.Dialog;
import be.ac.umons.sgl.mom.Events.Notifications.Notification;
import be.ac.umons.sgl.mom.Events.Observer;
import be.ac.umons.sgl.mom.Events.SuperviserNormally;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Objects.Characters.SaoulMatePNJ;
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
    private boolean informEnergizing=true, informPlace=true;
    /**
     * The all maps of the maps
     */
    private ArrayList<Maps> maps;


    /**
     * This constructor define the regulator class during the game
     * @param people is the player of the game
     * @param time   is the instance of the calculus time game
     */
    public Regulator(People people, TimeGame time)
    {
        this.player = people;
        this.time   = time;
        this.manager= SuperviserNormally.getSupervisor();
        manager.getEvent().add(Events.ChangeHour,this);
        manager.getEvent().add(Events.PlaceInMons,this);
        manager.getEvent().add(Events.MeetOther,this);
        maps = new ArrayList<>();
        maps.addAll(Arrays.asList(Maps.values()));
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
    private void questionPlace(Maps maps)
    {
        if (informPlace && this.maps.contains(maps))
        {
            manager.getEvent().notify(new Dialog(maps.getInformation(),"ESC"));
            this.maps.remove(maps);
        }
        if (this.maps.size()==0)
           informPlace = false;
    }


    /**
     * This method allows to regular the time of the game as pass the night
     * This method also allows to add the energizing of the people
     */
    private void nightHour()
    {
        if (time.getDate().getHour() >= 22 && player.getMaps().equals(Maps.Kot))
        {
            time.refreshTime(0,8,0);
            player.addEnergy(90); //TODO calculer difference
        }
    }


    /**
     * This method allows to inform the player of this maps and give also the information
     * @param id of the maps
     */
    public void placeQuestion(String id)
    {
        System.out.println("Oh je rencontre cette id : " + id);
    }


    /**
     * This method allows to regulate the interact between people and a soul Mate Pnj in the game
     * @param pnj is the mobile who is the soul mate
     */
    private void soulMateMeet(SaoulMatePNJ pnj)
    {
        System.out.println("oh il y a une chance que je lui face l'amour à " + pnj );
    }


    /***/
    public void timeOfCourse(Maps maps)//TODO
    {
       // if (maps.getState().equals(State.listen))
       //     time.refreshTime(0,1,50);
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
        {
            questionPlace((Maps)notify.getBuffer());
            timeOfCourse((Maps)notify.getBuffer());
        }
        if (notify.getEvents().equals(Events.MeetOther) && notify.bufferNotEmpty() && notify.getBuffer().getClass().equals(SaoulMatePNJ.class))
            soulMateMeet((SaoulMatePNJ) notify.getBuffer());
    }
}
