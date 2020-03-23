package be.ac.umons.mom.g02.Objects.Items;

import be.ac.umons.mom.g02.Objects.Characters.People;

/***/
public class Flower extends Items
{

    /**
     * this constructor define the items
     */
    public Flower()
    {
        super("FlowerLove");
    }


    /**
     * This method allows to said when people takes the items
     * @param pp is the people
     */
    @Override
    public void used(People pp)
    {
        if (pp.getSoulMate() != null)
        {
            pp.getSoulMate().giveItem(this);
        }
    }


    /**
     * This method returns the id to place the Item on the map
     * @return id of place
     */
    public String idOfPlace()
    {
        return "OTHER";//TODO
    }


    /**
     * This method allows to said if the items is obsolete
     * @return number
     */
    @Override
    public boolean getObsolete()
    {
        return true;
    }
}
