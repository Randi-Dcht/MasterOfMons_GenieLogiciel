package be.ac.umons.mom.g02.Objects.Items;

import be.ac.umons.mom.g02.Objects.Characters.People;


/**
 * This define a gun who kill the boot during the Quest
 */
public class TheKillBoot extends Items implements Guns
{


    /**
     * this constructor define the items
     */
    public TheKillBoot()
    {
        super("Gun");
    }


    /**
     * This method allows to said when people takes the items
     * @param pp is the people
     */
    @Override
    public void used(People pp)
    {

    }


    /**
     * This method allows to decrease the life of this items
     * @param time is the time between two frames
     */
    @Override
    public void update(double time)
    {

    }

    public int buy()
    {
        return 200;
    }

    /**
     * This method allows to said if the items is obsolete
     * @return number
     */
    @Override
    public boolean getObsolete()
    {
        return true;
    }


    /***/
    @Override
    public int addDamageGun()
    {
        return 0;
    }

    @Override
    public void useGun() {

    }

    @Override
    public void setBall(int cmb) {

    }
}
