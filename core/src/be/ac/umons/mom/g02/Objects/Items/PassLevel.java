package be.ac.umons.mom.g02.Objects.Items;

import be.ac.umons.mom.g02.Objects.Characters.People;

/**
 * This class define the pass to the level for the player
 */
public class PassLevel extends Items
{
    private boolean use = false;

    /**
     * This constructor define the item of pass the level
     */
    public PassLevel()
    {
        super("PassLevel");
    }


    /**
     * This method is called when the object is used by player
     * @param pp is the player who uses the item
     */
    @Override
    public void used(People pp)
    {
        super.used(pp);
        pp.upLevel();
        use = false;
    }


    /**
     * This method returns the price of this item
     * @return the price of item
     */
    public int buy()
    {
        return 300;
    }


    /**
     * This method return if the object can be use in the game
     * @return a boolean if can to use
     */
    @Override
    public boolean getObsolete()
    {
        return !use;
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