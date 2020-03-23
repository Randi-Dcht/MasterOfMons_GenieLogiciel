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
