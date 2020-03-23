package be.ac.umons.mom.g02.Objects.Items;

import be.ac.umons.mom.g02.Objects.Characters.People;


/**
 * This define a gun who kill the boot during the Quest
 */
public class TheKillBoot extends Items implements Guns
{

    private int ball = 30;

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
        super.used(pp);
        ball--;
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
        return 3;
    }

    @Override
    public void useGun()
    {
    }

    @Override
    public void setBall(int cmb)
    {
        ball += cmb;
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
