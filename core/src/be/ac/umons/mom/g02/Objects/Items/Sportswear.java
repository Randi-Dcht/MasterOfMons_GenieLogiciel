package be.ac.umons.mom.g02.Objects.Items;

import be.ac.umons.mom.g02.Objects.Characters.People;

public class Sportswear extends Items
{

    public Sportswear() {
        super("Sportswear");
    }

    @Override
    public void used(People pp)
    {
        super.used(pp);
        pp.setSpeed(2);
    }

    @Override
    public boolean getObsolete() {
        return true ;
    }


    /**
     * This method returns the id to place the Item on the map
     * @return id of place
     */
    public String idOfPlace()
    {
        return "OTHER";
    }


}
