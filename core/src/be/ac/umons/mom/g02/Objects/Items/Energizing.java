package be.ac.umons.mom.g02.Objects.Items;

import be.ac.umons.mom.g02.Events.Notifications.UseItem;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Regulator.SuperviserNormally;
import be.ac.umons.mom.g02.Regulator.Supervisor;

/***/
public class Energizing extends Items
{
    private double obsolete = 31536000;
    private double reVisible = 0;//TODO for all item
    private boolean useItem = false;


    /***/
    public Energizing()
    {
        super("Drink");
    }


    /***/
    @Override
    public void used(People pp)
    {
        super.used(pp);
        pp.addEnergy(25);//TODO check
        visibly();
        useItem = true;
    }


    /***/
    @Override
    public void update(double time) //TODO adpater pour être revisible
    {
        if (useItem)
            obsolete -= time;
        if(obsolete <= 0)
            visibly();
    }


    /***/
    @Override
    public boolean getObsolete()
    {
        return obsolete<=0;
    }

}
