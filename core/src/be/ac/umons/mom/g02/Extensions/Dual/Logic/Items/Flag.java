package be.ac.umons.mom.g02.Extensions.Dual.Logic.Items;

import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.Items.Items;

import java.awt.*;

/**
 * This class define the fag to catch in the game Dual
 */
public class Flag extends Items
{

    /**
     * This variable id the people who behove the flag
     */
    private People myCamp;
    /**
     * This is the name of the camps
     */
    private String id;
    /**
     * This is the position on the maps
     */
    private Point positionOnMap;


    /**
     * this constructor define the items flag in the extension Dual
     */
    public Flag()
    {
        super("Flag");
    }


    /**
     * This method allows to give the people who behove this
     * @param people is the people
     */
    public void setPeople(People people,String id)
    {
        myCamp  = people;
        this.id = id;
    }


    /**
     * This method return the people of the flag
     * @return people of the camp
     */
    public People getMyPeople()
    {
        return myCamp;
    }


    /**
     * This method allows to said if the items is obsolete
     * @return boolean if the item if obsolete
     */
    @Override
    public boolean getObsolete()
    {
        return false;
    }


    /**
     * This method returns the id to place the Item on the map
     * @return id of place
     */
    public String idOfPlace()
    {
        return id;
    }


    /**
     * @return the position of the flag on the maps
     */
    public Point getPosition()
    {
        return positionOnMap;
    }


    /**
     * Setter of the position of the flag
     * @param positionOnMap is the point of position
     */
    public void setPositionOnMap(Point positionOnMap)
    {
        this.positionOnMap = positionOnMap;
    }


    /**
     * The name of the flag
     * @return the name of the flag
     */
    @Override
    public String toString()
    {
        return super.toString()+id;
    }
}
