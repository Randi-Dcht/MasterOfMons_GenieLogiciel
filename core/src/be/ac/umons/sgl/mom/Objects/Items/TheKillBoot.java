package be.ac.umons.sgl.mom.Objects.Items;

import be.ac.umons.sgl.mom.Objects.Characters.People;


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


    /**
     * This method allows to said if the items is obsolete
     * @return number
     */
    @Override
    public double getObsolete()
    {
        return 0;
    }


    /***/
    @Override
    public int addDamageGun()
    {
        return 0;
    }
}
