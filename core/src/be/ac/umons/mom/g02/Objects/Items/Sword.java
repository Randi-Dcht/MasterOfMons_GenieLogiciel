package be.ac.umons.mom.g02.Objects.Items;

import be.ac.umons.mom.g02.Objects.Characters.People;

public class Sword extends Items implements Guns//TODO epee
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
    public boolean getObsolete()
    {
        return true;
    }

    @Override
    public int addDamageGun()
    {
        return 1;
    }

    @Override
    public void useGun()
    {

    }

    @Override
    public void setBall(int cmb){}
}
