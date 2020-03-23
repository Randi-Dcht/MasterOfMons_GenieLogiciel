package be.ac.umons.mom.g02.Objects.Items;

import be.ac.umons.mom.g02.Objects.Characters.People;

public class PassLevel extends Items
{
    private boolean use = false;

    /***/
    public PassLevel()
    {
        super("PassLevel");
    }


    /***/
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


    /***/
    @Override
    public boolean getObsolete()
    {
        return !use;
    }
}
