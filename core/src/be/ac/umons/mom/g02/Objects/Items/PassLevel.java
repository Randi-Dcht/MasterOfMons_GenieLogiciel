package be.ac.umons.mom.g02.Objects.Items;

import be.ac.umons.mom.g02.Objects.Characters.People;

public class PassLevel extends Items
{

    /***/
    public PassLevel()
    {
        super("PassLevel");
    }


    /***/
    @Override
    public void used(People pp)
    {
    }

    public int buy()
    {
        return 300;
    }


    /***/
    @Override
    public void update(double time)
    {
    }


    /***/
    @Override
    public double getObsolete()
    {
        return 0;
    }
}
