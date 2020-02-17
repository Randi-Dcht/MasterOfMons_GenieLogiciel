package be.ac.umons.sgl.mom.Objects.Items;

import be.ac.umons.sgl.mom.Enums.Place;
import be.ac.umons.sgl.mom.Objects.Characters.People;


/**
 * This define a gun who kill the boot during the Quest
 */
public class TheKillBoot extends Items implements Guns
{


    /**
     * this constructor define the items
     * @param place is the place of this item
     */
    public TheKillBoot(Place place)
    {
        super(place,"GunKillBoot");
    }


    /**
     * This method allows to said when people takes the items
     * @param pp is the people
     */
    @Override
    public void used(People pp) {

    }


    /**
     * This method allows to decrease the life of this items
     * @param time is the time between two frames
     */
    @Override
    public void make(double time)
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
}
