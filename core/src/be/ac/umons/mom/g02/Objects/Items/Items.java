package be.ac.umons.mom.g02.Objects.Items;

import be.ac.umons.mom.g02.Events.Notifications.UseItem;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Regulator.Supervisor;
import java.io.Serializable;


/**
 * This class define the Item in the game, this items help the people in the Quest
 * Every Item is associated to Maps and a Quest (SupervisorNormally)
 *@author Randy Dauchot (étudiant en Sciences informatique)
 */
public abstract class Items implements Serializable
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
     * This method allows to know if the item must be remove on the bag
     * @return if can remove
     */
    public boolean removeInBag()
    {
        return true;
    }


    /**
     * This method allows to said when people takes the items
     * @param pp is the people
     */
    public void used(People pp)
    {
        Supervisor.getEvent().notify(new UseItem(this,pp));
    }


    /**
     * This method allows to said if the items is obsolete
     * @return number
     */
    public abstract boolean getObsolete();


    /**
     * @return the price of this item
     */
    public int buy()
    {
        return 0;
    }


    /**
     * This method returns the id to place the Item on the map
     * @return id of place
     */
    public abstract String idOfPlace();


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
     * This methods allows to explain the action of the items on the game
     * @return the explain
     */
    public String explainAction()
    {
        return GraphicalSettings.getStringFromId("Explain"+name);
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
}
