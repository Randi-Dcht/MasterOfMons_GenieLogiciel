package be.ac.umons.mom.g02.Objects.Items;

import be.ac.umons.mom.g02.Objects.Characters.People;

public class TNT extends Items
{

    /***/
    public TNT()
    {
        super("TNT");
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
