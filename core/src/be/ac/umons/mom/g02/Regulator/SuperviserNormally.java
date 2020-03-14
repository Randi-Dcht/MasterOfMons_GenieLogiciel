package be.ac.umons.mom.g02.Regulator;

import be.ac.umons.mom.g02.Enums.Difficulty;
import be.ac.umons.mom.g02.Enums.Gender;
import be.ac.umons.mom.g02.Enums.Type;
import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Events.Notifications.OtherInformation;
import be.ac.umons.mom.g02.Extensions.Multiplayer.Regulator.SupervisorMultiPlayer;
import be.ac.umons.mom.g02.GameStates.PlayingState;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.MapObject;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.Course;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Objects.Saving;
import be.ac.umons.mom.g02.Other.Date;
import be.ac.umons.mom.g02.Other.LogicSaving;
import be.ac.umons.mom.g02.Other.TimeGame;
import be.ac.umons.mom.g02.Quests.Master.MasterQuest;
import be.ac.umons.mom.g02.Quests.Master.MyFirstYear;

import java.awt.*;
import java.util.ArrayList;


/**
 * This class allows to monitor the game in the normally game without extension .
 * @author Umons_Group_2_ComputerScience_RandyDauchot
 */
public class SuperviserNormally extends Supervisor
{

    /**
     * This method to give the only instance of <code>SuperviserNormaly</code>
     */
    public static SuperviserNormally getSupervisor()
    {
        if (instance == null)
            initNormallyGame();//TODO delete go to other class
        if (instance.getClass().equals(SuperviserNormally.class))
            return (SuperviserNormally)instance;
        return null;
    }


    /**
     * This method allows to initialise the supervisor for the normally game with single
     */
    public static void initNormallyGame()
    {
        instance = new SuperviserNormally();
    }

    /*-----------------------------------------------------------------------------------------------------------------*/


    /**
     * This the actual variable of the Id on the maps
     */
    protected String actualID;


    /**
     * This constructor allows to define the class who monitor the game
     */
    protected SuperviserNormally()
    {
        super();
    }


    /***/
    @Override
    public void newParty(String namePlayer, Type type, GraphicalSettings graphic, Gender gender, Difficulty difficulty)
    {
        super.newParty(namePlayer, type, graphic, gender, difficulty);
        MasterQuest mQ = new MyFirstYear(playerOne,null,difficulty);
        playerOne.newQuest(mQ);
        save.setLogic(playerOne);
        refreshQuest();
        regulator = new Regulator(playerOne,time);
        listCourse = playerOne.getPlanning().get(time.getDate().getDay());
    }


    /***/
    @Override
    public void oldGame(String pathAndFile,PlayingState play, GraphicalSettings graphic)
    {
        LogicSaving saving =  (LogicSaving) Saving.getSaveObject(pathAndFile);
        if (saving != null && saving.getClass().equals(LogicSaving.class))
        {
            listUpdate = new ArrayList<>();
            time = new TimeGame(saving.getDate());
            this.playerOne = saving.getPlayer();
            playerOne.setMaps(saving.getMap());
            listCourse = playerOne.getPlanning().get(time.getDate().getDay());
            this.graphic = graphic;
            regulator= new Regulator(playerOne,time);
            refreshQuest();
            checkPlanning();

            play.initMap(saving.getMap().getMaps());
            play.setPlayerPosition(saving.getPlayerPosition());
            play.addItemsToMap(saving.getItemPosition());
        }
    }


    /***/
    @Override
    public void saveGame(String pathAndFile)
    {
        LogicSaving save = new LogicSaving(playerOne,time.getDate(),playGraphic.getPlayerPosition(),playGraphic.getItemsOnMap());
        Saving.setSaveObject(pathAndFile,save);
    }


    /**
     * This method allows to analyse the id in the maps
     * @param id is the id of the object name
     * @throws Exception is the exception if the the size of list is bad or the ill word
     */
    @Override
    public void analyseIdMap(String id) throws Exception
    {
        if (!id.equals(actualID))
        {
            actualID = id;
            String[] word = id.split("_");
            if (word[0].equals("Room") && word.length >= 3)
                regulator.placeInOut(word[2],word[1]);
            else if (word[0].equals("Info") && word.length >= 2)
            {
                regulator.push(word[1]);
                event.notify(new OtherInformation(word[1]));
            }
            else
                throw new Exception();
        }
    }


    /**
     * This method allows to check the actual course of the player in the game
     */
    public void checkPlanning()
    {
        Date actu = time.getDate();
        if (actualCourse == null || actualCourse.getDate().getHour() + 2 <= actu.getHour())
        {
            actualCourse = null;
            for (Course crs : listCourse)
            {
                if (crs.getDate().getHour()<= actu.getHour() && (crs.getDate().getHour()+2) > actu.getHour())
                    actualCourse = crs;
            }
        }
    }


    /**
     * This method allows to receive the notification of the other class in the game
     * @param notify is the notification
     */
    @Override
    public void update(Notification notify)
    {
        super.update(notify);

        if (notify.getEvents().equals(Events.ChangeHour))
            checkPlanning();
    }
}