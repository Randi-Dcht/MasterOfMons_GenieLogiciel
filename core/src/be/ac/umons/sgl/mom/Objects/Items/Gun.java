package be.ac.umons.sgl.mom.Objects.Items;

import be.ac.umons.sgl.mom.Enums.Place;
import be.ac.umons.sgl.mom.Objects.Characters.People;

public class Gun extends Items implements Guns
{

    /**
     * this constructor define the items
     *
     * @param place is the place of this item
     * @param name  is the name of this items
     */
    public Gun(Place place, String name) {
        super(place, name);
    }

    /**
     * This method allows to said when people takes the items
     *
     * @param pp is the people
     */
    @Override
    public void used(People pp) {

    }

    /**
     * This method allows to decrease the life of this items
     *
     * @param time is the time between two frames
     */
    @Override
    public void make(double time) {

    }

    /**
     * This method allows to said if the items is obsolete
     *
     * @return number
     */
    @Override
    public double getObsolete() {
        return 0;
    }

    /**
     * This method return the damage of this gun
     *
     * @return the damage (double)
     */
    @Override
    public double damage()
    {
        return 0;
    }
}
