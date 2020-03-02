package be.ac.umons.mom.g02.Objects.Items;

import be.ac.umons.mom.g02.Objects.Characters.People;

/**
 * This class define a pen whose use by a people during the Quest
 */
public class Pen extends Items
{


    /**
     * this constructor define the items
     */
    public Pen() {
        super("Pen");
    }


    /**
     * This method allows to said when people takes the items
     * @param pp is the people
     */
    @Override
    public void used(People pp)
    {

    }


    /**
     * This method allows to decrease the life of this items
     * @param time is the time between two frames
     */
    @Override
    public void update(double time)
    {

    }


    /**
     * This method allows to said if the items is obsolete
     * @return number
     */
    @Override
    public double getObsolete()
    {
        return 0;
    }
}
