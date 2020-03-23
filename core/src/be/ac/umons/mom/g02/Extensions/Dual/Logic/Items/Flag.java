package be.ac.umons.mom.g02.Extensions.Dual.Logic.Items;

import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.Items.Items;

/**
 * This class define the fag to catch in the game Dual
 */
public class Flag extends Items
{

    /**
     * This variable id the people who behove the flag
     */
    private People myCamp;


    /**
     * this constructor define the items flag in the extension Dual
     */
    public Flag()
    {
        super("FlagDual");
    }


    /**
     * This method allows to give the people who behove this
     * @param people is the people
     */
    public void setPeople(People people)
    {
        myCamp = people;
    }


    /**
     * This method return the people of the flag
     * @return people of the camp
     */
    public People getMyPeople()
    {
        return myCamp;
    }


    /**
     * This method allows to said if the items is obsolete
     * @return boolean if the item if obsolete
     */
    @Override
    public boolean getObsolete()
    {
        return false;
    }
}
