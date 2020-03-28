package be.ac.umons.mom.g02.Regulator;

import be.ac.umons.mom.g02.Enums.Difficulty;
import be.ac.umons.mom.g02.Enums.Gender;
import be.ac.umons.mom.g02.Enums.Type;
import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Events.Notifications.OtherInformation;
import be.ac.umons.mom.g02.GameStates.PlayingState;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Objects.Items.PositionOnMaps;
import be.ac.umons.mom.g02.Objects.Saving;
import be.ac.umons.mom.g02.Other.LogicSaving;
import be.ac.umons.mom.g02.Other.TimeGame;
import be.ac.umons.mom.g02.Quests.Master.MasterQuest;
import be.ac.umons.mom.g02.Quests.Master.MyFirstYear;

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
     * This constructor allows to define the class who monitor the game
     */
    protected SuperviserNormally()
    {
        super();
        setMoneyGame(true);
    }


    /***/
    @Override
    public void newParty(String namePlayer, Type type, Gender gender, Difficulty difficulty)
    {
        super.newParty(namePlayer, type, gender, difficulty);
        MasterQuest mQ = new MyFirstYear(playerOne,null,difficulty);
        playerOne.newQuest(mQ);
        save.setLogic(playerOne);
        refreshQuest();
        regulator = new Regulator(playerOne,time,this);
        listCourse = playerOne.getPlanning().get(time.getDate().getDay());
        checkPlanning();
    }


    /***/
    @Override
    public void oldGame(String pathAndFile,PlayingState play, GraphicalSettings graphic)//TODO see same line
    {
        LogicSaving saving =  (LogicSaving) Saving.getSaveObject(pathAndFile);
        if (saving != null && saving.getClass().equals(LogicSaving.class))
        {
            placePosition = new PositionOnMaps();
            listUpdate = new ArrayList<>();
            time = new TimeGame(saving.getDate());
            playerOne = saving.getPlayer();
            playerOne.setMaps(saving.getMap());
            listCourse = playerOne.getPlanning().get(time.getDate().getDay());
            if (listCourse == null)
                listCourse = new ArrayList<>();
            Supervisor.graphic = graphic;
            regulator= new Regulator(playerOne,time,this);
            refreshQuest();
            checkPlanning();
            playGraphic  = play;

            play.initMap(saving.getMap().getMaps());
            play.setPlayerPosition(saving.getPlayerPosition());
            play.addItemsToMap(saving.getItemPosition());
            play.getPlayer().setCharacteristics(playerOne);
        }
    }


    /***/
    @Override
    public MasterQuest actualQuest()
    {
        return playerOne.getQuest();
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
        analyseNormalGameIdMap(id);
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