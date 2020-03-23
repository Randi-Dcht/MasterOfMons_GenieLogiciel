package be.ac.umons.mom.g02.Objects.Items;

import be.ac.umons.mom.g02.Events.Notifications.UseItem;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.FrameTime;
import be.ac.umons.mom.g02.Regulator.SuperviserNormally;
import be.ac.umons.mom.g02.Regulator.Supervisor;

/***/
public class Energizing extends Items
{
    private double obsolete = 100;
    private double reVisible = 0;//TODO for all item
    private boolean useItem = true;


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
        useItem = false;
    }


    /***/
    @Override
    public boolean getObsolete()
    {
        return useItem;
    }

}
