package be.ac.umons.mom.g02.Objects.Items;

import be.ac.umons.mom.g02.Objects.Characters.People;

public class SuperChargerGun extends Items
{

    /***/
    public SuperChargerGun()
    {
        super("SuperChargerGun");
    }


    /***/
    @Override
    public void used(People pp)
    {
        if (pp.howGun())
        {
            pp.getGun().setBall(7);
        }
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
    public boolean getObsolete()
    {
        return true;
    }
}
