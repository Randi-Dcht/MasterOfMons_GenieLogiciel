package be.ac.umons.mom.g02.Objects.Items;

import be.ac.umons.mom.g02.Objects.Characters.People;

public class Gun extends Items implements Guns
{

    private int ball = 7;

    /**
     * this constructor define the items
     */
    public Gun()
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


    /**
     * This method allows to recharge the gun
     * @param ball is the number of the recharge
     */
    @Override
    public void setBall(int ball)
    {
        this.ball += ball;
    }


    /**
     * This method allows to said if the items is obsolete
     * @return number
     */
    @Override
    public boolean getObsolete() {
        return ball <= 0;
    }


    /***/
    @Override
    public int addDamageGun()
    {
        return 2;
    }

    @Override
    public void useGun() {

    }
}
