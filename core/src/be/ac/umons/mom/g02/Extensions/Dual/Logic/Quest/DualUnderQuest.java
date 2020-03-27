package be.ac.umons.mom.g02.Extensions.Dual.Logic.Quest;

import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.Items.Items;
import be.ac.umons.mom.g02.Quests.Quest;
import be.ac.umons.mom.g02.Quests.Under.UnderQuest;

import java.util.ArrayList;

public abstract class DualUnderQuest extends UnderQuest
{

    /**
     * This constructor allows define the underQuest
     * @param name   is the name of this Quest
     * @param master is the quest who call this
     * @param people is the people who play the game
     */
    public DualUnderQuest(String name,Quest master, People people)
    {
        super(name,100, master, people);
    }

    @Override
    public String getName()
    {
        return super.getName()+"("+people.getName()+")";
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
     * This method return the number if the recurse of under quest
     * @return the number of recurse
     */
    @Override
    public int getTotalSubQuestsNumber()
    {
        return 0;
    }


    /***/
    public ArrayList<Mobile> getListMobile()
    {
        return new ArrayList<>();
    }

    /***/
    public ArrayList<Items> getListItems()
    {
        return new ArrayList<>();
    }
}
