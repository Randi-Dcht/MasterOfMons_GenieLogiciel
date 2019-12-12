package be.ac.umons.sgl.mom.Events;

import be.ac.umons.sgl.mom.Enums.Actions;

/**
 * This abstract class allows define the eventAction in the game for a people or a PNJ.
 */
public abstract class Events
{
    /*max number of this instance od this class*/
    private static int num = 0;
    /*for every instance, unique id */
    final int id;
    /*the name of this evens who is an enum (Action)*/
    final Actions name;
    /*To know if this instance used*/
    protected boolean see = true;

    /**
     * The constructor of a Events
     * @param name who is an Action enum
     */
    public Events(Actions name)
    {
        this.name = name;
        this.id = num;
        num++;
    }

    /**
     * This method allows the launch a other method in People or PNJ
     */
    public abstract void run();

    /**
     * This method allows return an unique ID of Events
     * @return  id who is integer
     */
    public int getID()
    {
        return id;
    }
}
