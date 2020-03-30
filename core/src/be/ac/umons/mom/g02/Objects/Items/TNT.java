package be.ac.umons.mom.g02.Objects.Items;

import be.ac.umons.mom.g02.Objects.Characters.People;

public class TNT extends Items implements Guns
{

    private boolean useTnt = true;

    /***/
    public TNT()
    {
        super("TNT");
    }


    /***/
    @Override
    public void used(People pp)
    {
        super.used(pp);
        useTnt = false;
    }

    public int buy()
    {
        return 40;
    }


    /***/
    @Override
    public boolean getObsolete()
    {
        return useTnt;
    }

    @Override
    public int addDamageGun()
    {
        return 100;
    }

    @Override
    public void useGun() {

    }

    @Override
    public void setBall(int cmb) {
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