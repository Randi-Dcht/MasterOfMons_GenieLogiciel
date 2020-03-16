package be.ac.umons.mom.g02.Objects.Items;

import be.ac.umons.mom.g02.Objects.Characters.People;

public class PassLevel extends Items
{
    private boolean use = false;

    /***/
    public PassLevel()
    {
        super("PassLevel");
    }


    /***/
    @Override
    public void used(People pp)
    {
        super.used(pp);
        pp.upLevel();
        use = false;
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
        return !use;
    }
}
