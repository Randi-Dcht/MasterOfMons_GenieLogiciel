package be.ac.umons.sgl.mom.Objects.Items;

import be.ac.umons.sgl.mom.Objects.Characters.People;

public class Sportswear extends Items {

    public Sportswear() {
        super("Sportswear");
    }

    @Override
    public void used(People pp){
        //pp.setSpeed();
    }

    public void make(double time) {

    }

    @Override
    public double getObsolete() {
        return 0;
    }

    /**
     * It help to know what do the item
     * @return the utility of the item
     */
    @Override
    public String question() {
        return graphic.getStringFromId("sportswear");
    }

    @Override
    public void update(double dt) {

    }
}
