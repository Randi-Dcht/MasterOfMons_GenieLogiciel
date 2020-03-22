package be.ac.umons.mom.g02.Extensions.Dual.Logic.Quest;

import be.ac.umons.mom.g02.Enums.Bloc;
import be.ac.umons.mom.g02.Enums.Difficulty;
import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Quests.Master.MasterQuest;
import be.ac.umons.mom.g02.Quests.Quest;
import java.util.ArrayList;


/**
 * This class define the battle between two people in the game
 */
public class BattlePeople extends MasterQuest
{

    /**
     * This is the other people in the dual
     */
    private People victim;

    /**
     * This constructor define the dual of the battle between two people
     * @param people     is the people of this Quest
     * @param difficulty is the difficulty of the game
     * @param victim     is the victim of the attack of this people
     */
    public BattlePeople(People people,People victim, Difficulty difficulty)
    {
        super(null,people, Bloc.Extend,difficulty);
        this.victim = victim;
    }


    /**
     * This method allows to give the under Quest of this
     * @return a tableau of the under quest of this
     */
    @Override
    public Quest[] getSubQuests()
    {
        return new Quest[0];
    }


    /**
     * This method allow to say what is this quest
     * @return a goal (=question) of this quest
     */
    @Override
    public String question()
    {
        return "QuestionBattle";//TODO
    }


    /**
     * This method allows to receive the notification of the game
     * @param notify is the notification in the game
     */
    @Override
    public void update(Notification notify)
    {
        super.update(notify);

        if (notify.getEvents().equals(Events.Attack))
            attackOther();
    }


    private void attackOther()
    {}


    /**
     * This method allows to create the list of the items
     */
    @Override
    protected void createListItems() throws Exception
    {
        listItems = new ArrayList<>();//TODO
    }


    /**
     * This method allows to create the list of the mobiles
     * There isn't mobile on the maps
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
        return new Maps[]{Maps.DualPark};
    }


    /**
     * This method allows to define the next MasterQuest
     */
    @Override
    public void nextQuest()
    {
    }


    /**
     * This method returns the name of this Quest
     * @return the name of the quest
     */
    @Override
    public String getName()
    {
        return "DualBattle";//TODO
    }


    /**
     * This method return the number if the recurse of under quest
     * @return the number of recurse
     */
    @Override
    public int getTotalSubQuestsNumber()
    {
        return 0;
    }
}