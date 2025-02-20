package be.ac.umons.mom.g02.Objects.Items;

import be.ac.umons.mom.g02.Objects.Characters.People;


/**
 * This class define the drink of energizing for the player
 */
public class Energizing extends Items
{
    /**
     * If the object is used
     */
    private boolean useItem = true;


    /**
     * This constructor define the item of drink energizing
     */
    public Energizing()
    {
        super("Drink");
    }


    /**
     * This method is called when the object is used by player
     * @param pp is the player who uses the item
     */
    @Override
    public void used(People pp)
    {
        pp.addEnergy(20);
        visibly();
        useItem = false;
        super.used(pp);
    }


    /**
     * This method returns the id to place the Item on the map
     * @return id of place
     */
    public String idOfPlace()
    {
        return "OTHER";
    }


    /**
     * This method return if the object can be use in the game
     * @return a boolean if can to use
     */
    @Override
    public boolean getObsolete()
    {
        return useItem;
    }

}