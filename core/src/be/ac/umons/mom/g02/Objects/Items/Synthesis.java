package be.ac.umons.mom.g02.Objects.Items;

import be.ac.umons.mom.g02.Events.Notifications.UseItem;
import be.ac.umons.mom.g02.Events.SuperviserNormally;
import be.ac.umons.mom.g02.Objects.Characters.People;

/**
 * Cet objet permet de monter de 1 niveau
 */
public class Synthesis extends Items {

    private double obsolete = 1; //faire pour que ça soit à usage unique
    private double reVisible = 0;//TODO for all item

    public Synthesis() {
        super("Synthesis of lessons");
    }

    @Override
    public void used(People pp) {
        pp.upLevel();
        visibly();
        SuperviserNormally.getSupervisor().getEvent().notify(new UseItem(this));


    }


    @Override
    public double getObsolete() {
        return obsolete;
    }

    @Override
    public void update(double dt) {
        obsolete = obsolete - dt;
        if(obsolete <= 0)
            visibly();
    }
}
