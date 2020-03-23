package be.ac.umons.mom.g02.Objects.Items;

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
     * This method allows to said if the items is obsolete
     * @return number
     */
    @Override
    public boolean getObsolete()
    {
        return true;
    }


    /**
     * This method returns the id to place the Item on the map
     * @return id of place
     */
    public String idOfPlace()
    {
        return "OTHER";//TODO
    }
}
