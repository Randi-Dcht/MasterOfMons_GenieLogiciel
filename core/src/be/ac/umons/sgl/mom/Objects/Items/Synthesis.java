package be.ac.umons.sgl.mom.Objects.Items;

import be.ac.umons.sgl.mom.Objects.Characters.People;

public class Synthesis extends Items {

    public Synthesis() {
        super("Synthesis of lessons");
    }

    /**
     * It help to know what do the item
     * @return the utility of the item
     */
    public String question()
    {
        return "synthesis";
    }

    @Override
    public void used(People pp) {

    }


    public void make(double time) {

    }

    @Override
    public double getObsolete() {
        return 0;
    }

    @Override
    public void update(double dt) {

    }
}
