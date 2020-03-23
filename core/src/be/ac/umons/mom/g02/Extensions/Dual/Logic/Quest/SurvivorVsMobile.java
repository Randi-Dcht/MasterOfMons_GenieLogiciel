package be.ac.umons.mom.g02.Extensions.Dual.Logic.Quest;

import be.ac.umons.mom.g02.Enums.Bloc;
import be.ac.umons.mom.g02.Enums.Difficulty;
import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Quests.Master.MasterQuest;
import java.util.ArrayList;


/**
 * This class define the survivor between two people with many Mobile to kill
 */
public class SurvivorVsMobile extends MasterQuest
{

    /**
     * This constructor define the survivor people face to mobile
     * @param playerOne  is the player of this quest
     * @param difficulty is the difficulty of the game
     */
    public SurvivorVsMobile(People playerOne, Difficulty difficulty)
    {
        super(null,playerOne, Bloc.Extend,difficulty);
    }


    /**
     * This is the end of this Quest
     * This method allows to switch after this dual
     */
    @Override
    public void nextQuest()
    {

    }


    /**
     * This method return the name of this quest
     * @return the name of this quest
     */
    @Override
    public String getName()
    {
        return "DualSurvivor";//TODO
    }



    /**
     * This method return the question of this Quest
     * @return a question of this Quest
     */
    @Override
    public String question()
    {
        return "QuestionSurvivor";//TODO
    }


    /**
     * This method allows to create the list of the items
     */
    @Override
    protected void createListItems()
    {
        listItems = new ArrayList<>();
    }


    /**
     * This method allows to create the list of the mobiles
     */
    @Override
    protected void createListMobiles()
    {
        listMobs = new ArrayList<>();
    }


    /**
     * This method return the maps for this Quest
     * @return list of the maps
     */
    @Override
    public Maps[] getListMaps()
    {
        return new Maps[0];
    }


    /**
     * This method allows to receive the notification of the all class in the game
     * @param notify is the notification
     */
    @Override
    public void update(Notification notify)
    {
    }
}