package be.ac.umons.sgl.mom.Objects.Items;

import be.ac.umons.sgl.mom.Enums.Maps;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;

import be.ac.umons.sgl.mom.Objects.FrameTime;
import java.io.Serializable;

/**
 * This class define the Item in the game, this items help the people in the Quest
 * Every Item is associated to Maps and a Quest (SupervisorNormally)
 *@author Randy Dauchot (étudiant en Sciences informatique)
 */
public abstract class Items implements Serializable, FrameTime
{
    /**
     * The maps of this items
     */
    protected Maps maps;
    /**
     * If the items isn't obsolete
     */
    protected boolean visible = true;
    /**
     * The name of this items
     */
    final String name;
    /*this is the instance of the graphic*/
    protected GraphicalSettings graphic;


    /**
     * this constructor define the items
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


    /**
     * This method allows to give a maps to this items
     */
    public void setMaps(Maps maps)
    {
        this.maps = maps;
    }


    /**
     * This method allows to said when people takes the items
     * @param pp is the people
     */
    public abstract void used(People pp);


    /**
     * This method allows to take the items by the people
     */
    public void takeItem()
    {
        if(visible)
            visible = false;
    }


    /**
     * This method allows to said if the items is obsolete
     * @return number
     */
    public abstract double getObsolete();


    /**
     * This method returns the maps of this items
     * @return the maps of the items
     */
    public Maps getMaps()
    {
        return maps;
    }


    /**
     * This method allows to give ID to say what is the object in the game
     * @return the id of the use
     */
    public String getIdUse()
    {
        return "Use"+name+"Id";
    }


    /**
     * This method allows to give the name of the object in the right language via the bundle
     * @return the name id of this items
     */
    public String getIdItems()
    {
        return name+"ID";
    }


    /**
     * This method return the name of the Items
     * @return name of the items
     */
    @Override
    public String toString()
    {
        return name;
    }

    /**
     *This method allow to say what is this item
     *@return the utility of this object
     */
    public abstract String question();
}
