package be.ac.umons.mom.g02.Extensions.Dual.Logic.Quest;

import be.ac.umons.mom.g02.Enums.Bloc;
import be.ac.umons.mom.g02.Enums.Difficulty;
import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Quests.Master.MasterQuest;
import be.ac.umons.mom.g02.Quests.Quest;
import java.util.ArrayList;


/**
 * This class define the first people who take the flag of the adversary
 */
public class TakeFlag extends MasterQuest
{

    /**
     * This constructor define the quest when the people takes the flag
     * @param people     is this people plays the quest
     * @param difficulty is the difficulty of the game
     */
    public TakeFlag(People people, Difficulty difficulty)
    {
        super(null,people, Bloc.Extend,difficulty);
    }


    /**
     * This method return the all goals of this Quest
     * @return a tableau with the under quest of this
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
        return "QuestionFlag";
    }


    /**
     * This method allows to create the list of the items
     */
    @Override
    protected void createListItems() throws Exception
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
        return "DualFlag";
    }


    /**
     * This method returns the numbers of the recurse of this quest
     * @return the number of the recurse
     */
    @Override
    public int getTotalSubQuestsNumber()
    {
        return 0;
    }
}