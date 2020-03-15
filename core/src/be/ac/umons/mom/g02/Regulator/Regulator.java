package be.ac.umons.mom.g02.Regulator;

import be.ac.umons.mom.g02.Events.Notifications.LowSomething;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Enums.Places;
import be.ac.umons.mom.g02.Enums.State;
import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.Dialog;
import be.ac.umons.mom.g02.Events.Notifications.EntryPlaces;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Events.Observer;
import be.ac.umons.mom.g02.Objects.Characters.SaoulMatePNJ;
import be.ac.umons.mom.g02.Other.TimeGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * This class allows to regular the people during the game as pass the night, up the energizing and etc
 * @author Umons_Group_2_ComputerScience_RandyDauchot
 */
public class Regulator implements Observer
{

    /**
     * This is the instance of the human player
     */
    protected People player;
    /**
     * This is the instance of the game time
     */
    protected TimeGame time;
    /**
     * This is the instance of the class who manage the game
     */
    protected Supervisor manager;
    /**
     * To warn the people if there are problems
     */
    protected boolean informEnergizing=true, informPlace=true, firstStart=true, firstCourse=true, firstStudy=true, chgQuest=true;
    /**
     * The all maps of the maps
     */
    protected ArrayList<Maps> maps;
    /**
     * This list allows to save the dialog when the dialog is use by other
     */
    protected ArrayList<String> waitingLine = new ArrayList<>();
    /**
     * This variable allows to know if the dialog is use
     */
    protected boolean displayQuestion = true;
    /**
     * The search of the place in the maps
     */
    protected String searchDirection = "IN";
    /**
     * This is an HasMaps who associate the string to the place in the game
     */
    protected HashMap<String,Places> associatePlace = new HashMap<>();


    /**
     * This constructor define the regulator class during the game
     * @param people is the player of the game
     * @param time   is the instance of the calculus time game
     */
    public Regulator(People people, TimeGame time)
    {
        this.player = people;
        this.time   = time;
        this.manager= Supervisor.getSupervisor();
        manager.getEvent().add(this,Events.ChangeHour,Events.PlaceInMons,Events.MeetOther,Events.EntryPlace,Events.ChangeQuest,Events.UseItems);
        maps = new ArrayList<>();
        maps.addAll(Arrays.asList(Maps.values()));
        createPlaceAssociation();
    }


    /**
     * This method allows to create a hasMap to associate the place with the string
     */
    private void createPlaceAssociation()
    {
        for (Places plc : Places.values())
            associatePlace.put(plc.toString(),plc);
    }


    /**
     * This method allows to analyse the energizing of the people if it is low
     */
    public void lowEnergizing()
    {
        if (player.getEnergy() <= 10 && informEnergizing)
        {
            push("Low10");
            informEnergizing = false ;
        }
    }


    /**
     * This method allows to give the new dialog to the player or wait if the dialog is used now
     * @param newDialog is the dialog ID
     */
    public void push(String newDialog)
    {
        if(waitingLine.size() == 0 && displayQuestion)
        {
            manager.getEvent().add(Events.Answer,this);
            manager.getEvent().notify(new Dialog(newDialog,"OK"));
            displayQuestion = false;
        }
        else
            waitingLine.add(newDialog);

    }


    public void finishQuest()
    {
        push("FinishQuest");
    }


    /**
     * This method allows to give the information about the actual Map
     */
    protected void questionPlace(Maps maps)
    {
        if (firstStart)
        {
            firstStart=false;
            push("HelloWord");
        }
        if (chgQuest)
        {
            changeQuest();
            chgQuest = false;
        }
        if (informPlace && this.maps.contains(maps))
        {
            push(maps.getInformation());
            this.maps.remove(maps);
        }

        if (this.maps.size()==0)
           informPlace = false;
    }


    /**
     * This method allows to display the question of the Quest in the screen when this starts
     */
    protected void changeQuest()
    {
        push(player.getQuest().question());
    }


    /**
     * This method allows to regular the time of the game as pass the night
     * This method also allows to add the energizing of the people
     */
    protected void kotliebed()
    {
        if (time.getDate().getHour() >= 22 && player.getMaps().equals(Maps.Kot))
        {
            time.refreshTime(0,8,0);
            player.addEnergy(90); //TODO calculer difference
        }
        if (player.getMaps().equals(Maps.Kot) && player.getPlace().equals(Places.Bed))
        {
            TimeGame.FASTER=300;
            player.reduceEnergizing(State.nap);
        }
    }


    /**
     * This method allows to inform the player of this maps and give also the information
     * @param place of the maps (enum)
     * @param direction is the direction of the player: IN or OUT
     */
    public void placeInOut(String place, String direction)
    {
        if (direction.equals(searchDirection))
        {
            manager.getEvent().notify(new EntryPlaces(associatePlace.get(place)));
            if(searchDirection.equals("IN"))
                searchDirection="OUT";
            else
                searchDirection="IN";
        }
    }


    /**
     * This method allows to regulate the interact between people and a soul Mate Pnj in the game
     * @param pnj is the mobile who is the soul mate
     */
    private void soulMateMeet(SaoulMatePNJ pnj)
    {
        System.out.println("oh il y a une chance que je lui face l'*** à " + pnj );
    }


    /**
     * This method see the place of the player and display the message or advance the time
     * @param place is the place of the player
     */
    public void timeOfDay(Places place)
    {
        if (place.equals(Places.Bed))
            kotliebed();

        if((place.equals(Places.RoomCourse) || place.equals(Places.ComputerRoom)))
        {
            if (firstCourse)
            {
                firstCourse=false;
                push("StartCourse");
            }

            if (advanceTime())
            {
                manager.getActualCourse().goCourse();
                time.refreshTime(0,2,0);
            }
        }


        if (place.equals(Places.StudyRoom) && firstStudy)
        {
            push("TimeStudy");
            firstStudy=false;
        }

        if (place.equals(Places.StudyRoom))
            TimeGame.FASTER = 350;

        if (place.equals(Places.OnTheMap))
            TimeGame.FASTER = 10;

    }


    /**
     * This method allows to advance the time when the people go to the lesson
     * @return boolean if the condition is respect
     */
    public boolean advanceTime()
    {
        if (manager.getActualCourse() == null)
            return false;
        if (!manager.getActualCourse().getLesson().location().equals(player.getMaps()))
            return false;
        /*if (player.getItems() == null || !player.getItems().getClass().equals(Pen.class))
            return false;*/
        return true;
    }


    /**
     * This method allows to analyze the answer of the player after the dialog and launch the dialog in the waiting line
     * @param answer is the answer of the player
     */
    protected void regulateDialog(String answer)
    {
        if (answer.equals("OK"))
            displayQuestion = true;

        if (waitingLine.size() != 0)
        {
            manager.getEvent().notify(new Dialog(waitingLine.get(0),"OK"));
            waitingLine.remove(0);
            displayQuestion = false;
        }
        else
        {
            manager.getEvent().notify(new Dialog("ESC"));
            manager.getEvent().remove(Events.Answer,this);
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
            kotliebed();

        if (notify.getEvents().equals(Events.PlaceInMons) && notify.bufferNotEmpty())
            questionPlace((Maps)notify.getBuffer());

        if (notify.getEvents().equals(Events.EntryPlace) && notify.bufferNotEmpty())
            timeOfDay((Places) notify.getBuffer());

        if (notify.getEvents().equals(Events.MeetOther) && notify.bufferNotEmpty() && notify.getBuffer().getClass().equals(SaoulMatePNJ.class))
            soulMateMeet((SaoulMatePNJ) notify.getBuffer());

        if (notify.getEvents().equals(Events.UseItems))
            timeOfDay(player.getPlace());

        if (notify.getEvents().equals(Events.Answer) && notify.bufferNotEmpty())
            regulateDialog((String)notify.getBuffer());

        if (notify.getEvents().equals(Events.ChangeQuest))
            changeQuest();

        if (notify.getEvents().equals(Events.LowSomething) && notify.bufferNotEmpty() && notify.getBuffer().equals(LowSomething.TypeLow.Energy))
            lowEnergizing();

    }
}
