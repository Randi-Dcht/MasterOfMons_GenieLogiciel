package be.ac.umons.sgl.mom.Objects.Items;

import be.ac.umons.sgl.mom.Objects.Characters.People;

public class sportswear extends Items {

    public sportswear() {
        super("Sportswear");
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

    @Override
    public String question() {
        return graphic.getStringFromId("sportswear");
    }
}
