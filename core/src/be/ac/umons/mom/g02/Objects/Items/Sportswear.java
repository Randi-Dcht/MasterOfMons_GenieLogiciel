package be.ac.umons.mom.g02.Objects.Items;

import be.ac.umons.mom.g02.Events.Notifications.UseItem;
import be.ac.umons.mom.g02.Regulator.SuperviserNormally;
import be.ac.umons.mom.g02.Objects.Characters.People;

public class Sportswear extends Items {


    private double reVisible = 0;

    public Sportswear() {
        super("Sportswear");
    }

    @Override
    public void used(People pp)
    {
        super.used(pp);
        pp.setSpeed(2); //on double sa vitesse quand il possede cet item
        visibly();
        pp.pushObject(this);
    }

    @Override
    public boolean getObsolete() {
        return true ;
    }
    // Il ne me faut pas cette methode car ça reste tout le temps vsible et ça ne casse pas

    @Override
    public void update(double dt) {
        //Je n'ai pas besoin de cette methode car mon objet ne s'use pas avec le temps
    }


}
