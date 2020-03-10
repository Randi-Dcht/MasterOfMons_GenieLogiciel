package be.ac.umons.mom.g02.Objects.Items;

import be.ac.umons.mom.g02.Events.Notifications.UseItem;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Regulator.SuperviserNormally;

/***/
public class Energizing extends Items
{
    private double obsolete = 31536000;
    private double reVisible = 0;//TODO for all item


    /***/
    public Energizing()
    {
        super("Drink");
    }


    /***/
    @Override
    public void used(People pp)
    {
        pp.addEnergy(25);//TODO check
        visibly();
        SuperviserNormally.getSupervisor().getEvent().notify(new UseItem(this));
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
