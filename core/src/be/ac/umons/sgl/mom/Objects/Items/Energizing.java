package be.ac.umons.sgl.mom.Objects.Items;

import be.ac.umons.sgl.mom.Objects.Characters.People;

/***/
public class Energizing extends Items
{
    private double obsolete = 31536000;
    private double reVisible = 0;


    /***/
    public Energizing()
    {
        super("Drink");
    }


    /***/
    @Override
    public void used(People pp)
    {
        pp.energy(0.23);
        visibly();
    }


    /***/
    @Override
    public void update(double time) //TODO adpater pour être revisible
    {
        obsolete = obsolete - time;
        if(obsolete <= 0)
            visibly();
    }


    /***/
    @Override
    public double getObsolete()
    {
        return obsolete;
    }
}
