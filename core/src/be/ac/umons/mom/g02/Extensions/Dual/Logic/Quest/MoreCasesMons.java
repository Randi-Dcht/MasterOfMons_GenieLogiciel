package be.ac.umons.mom.g02.Extensions.Dual.Logic.Quest;

import be.ac.umons.mom.g02.Enums.Bloc;
import be.ac.umons.mom.g02.Enums.Difficulty;
import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Quests.Master.MasterQuest;
import be.ac.umons.mom.g02.Quests.Quest;

import java.util.ArrayList;


/**
 * This class define the more cases of the people in the city
 */
public class MoreCasesMons extends MasterQuest
{

    /**
     * This constructor define the more cases in the city for this people
     * @param people     is the people o this Quest
     * @param difficulty is the difficulty of the game
     */
    public MoreCasesMons(People people, Difficulty difficulty)
    {
        super(null,people, Bloc.Extend,difficulty);
    }


    /**
     * This method return the under Quest of this
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
        return "QuestionMoreCases";//TODO
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
        return "DualMoreCases";//TODO
    }


    /**
     * This method returns the numbers of the under quest on this
     * @return the number of recurse
     */
    @Override
    public int getTotalSubQuestsNumber()
    {
        return 0;
    }
}