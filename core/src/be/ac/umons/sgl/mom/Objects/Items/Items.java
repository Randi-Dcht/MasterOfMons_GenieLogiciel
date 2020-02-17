package be.ac.umons.sgl.mom.Objects.Items;

import be.ac.umons.sgl.mom.Enums.Place;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import java.io.Serializable;

/**
 * This class define the Item in the game, this items help the people in the Quest
 * Every Item is associated to Place and a Quest (SupervisorNormally)
 *@author Randy Dauchot (étudiant en Sciences informatique)
 */
public abstract class Items implements Serializable
{
    /*the place of this items*/
    protected Place place;
    /*if the items isn't obsolete*/
    protected boolean visible = true;
    /*the name of this items*/
    final String name;


    /**
     * this constructor define the items
     * @param place is the place of this item
     * @param name is the name of this items
     */
    public Items(String name)
    {
        this.name = name;
    }


    /**
     * This method allows to define the visibility to false
     */
    protected void visibly()
    {
        visible = false;
    }


    /***/
    public void setPlace(Place place)
    {
        this.place = place;
    }


    /**
     * This method allows to said when people takes the items
     * @param pp is the people
     */
    public abstract void used(People pp);


    public void takeItem()
    {
        if(visible)
            visible = false;
    }


    /**
     * This method allows to decrease the life of this items
     * @param time is the time between two frames
     */
    public abstract void make(double time);


    /**
     * This method allows to said if the items is obsolete
     * @return number
     */
    public abstract double getObsolete();


    /***/
    @Override
    public String toString()
    {
        return name;
    }
}
