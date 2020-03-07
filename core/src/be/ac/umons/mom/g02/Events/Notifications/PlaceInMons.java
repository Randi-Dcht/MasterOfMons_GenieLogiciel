package be.ac.umons.mom.g02.Events.Notifications;

import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Events.Events;


/**
 * This class define the notification when people displace the map
 * @author Umons_Group_2_ComputerScience
 */
public class PlaceInMons implements Notification
{
    private Events evt;
    private Maps buffer;


    /**
     * This constructor define a notification when people change maps in maps with buffer
     * @param buffer is a buffer with maps (specific to events)
     */
    public PlaceInMons(Maps buffer)
    {
        evt = Events.PlaceInMons;
        this.buffer = buffer;
    }


    /**
     * This constructor define a notification when people change maps in maps
     */
    public PlaceInMons()
    {
        this(null);
    }


    /**
     * This method return the event of this notification
     * @return event of notification
     */
    @Override
    public Events getEvents()
    {
        return evt;
    }


    /**
     * This method return the buffer of this notification if it isn't empty
     * @return maps
     */
    @Override
    public Maps getBuffer()
    {
        return buffer;
    }


    /**
     * This method return if the buffer isn't empty
     * @return true is the buffer isn't empty
     */
    public boolean bufferNotEmpty()
    {
        return !(buffer == null);//TODO tester ce problème
    }
}
