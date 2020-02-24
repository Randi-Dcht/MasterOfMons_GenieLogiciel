package be.ac.umons.sgl.mom.Objects.Items;

import be.ac.umons.sgl.mom.Objects.Characters.People;

public class Synthesis extends Items {
    /**
     * this constructor define the items
     *
     * @param name is the name of this items
     */
    public Synthesis(String name) {
        super("Synthesis of lessons");
    }

    /**
     * It help to know what do the item
     * @return the utility of the item
     */
    public String question()
    {
        return graphic.getStringFromId("synthesis");
    }

    @Override
    public void used(People pp) {

    }

    @Override
    public void make(double time) {

    }

    @Override
    public double getObsolete() {
        return 0;
    }
}
