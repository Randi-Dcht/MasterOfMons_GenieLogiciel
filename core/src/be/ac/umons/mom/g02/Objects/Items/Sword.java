package be.ac.umons.mom.g02.Objects.Items;

import be.ac.umons.mom.g02.Objects.Characters.People;

public class Sword extends Items
{

    /***/
    public Sword()
    {
        super("Sword");
    }


    /***/
    @Override
    public void used(People pp)
    {
    }

    public int buy()
    {
        return 40;
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
