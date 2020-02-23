package be.ac.umons.sgl.mom.Events.Notifications;

import be.ac.umons.sgl.mom.Enums.Place;
import be.ac.umons.sgl.mom.Events.Events;


/**
 * This class define the notification when people displace the map
 * @author Umons_Group_2_ComputerScience
 */
public class PlaceInMons implements Notification
{
    private Events evt;
    private Place buffer;


    /**
     * This constructor define a notification when people change place in maps with buffer
     * @param buffer is a buffer with place (specific to events)
     */
    public PlaceInMons(Place buffer)
    {
        evt = Events.PlaceInMons;
        this.buffer = buffer;
    }


    /**
     * This constructor define a notification when people change place in maps
     */
    public PlaceInMons()
    {
        evt= Events.PlaceInMons;
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
     * @return place
     */
    @Override
    public Place getBuffer()
    {
        return buffer;
    }


    /**
     * This method return if the buffer isn't empty
     * @return true is the buffer isn't empty
     */
    public boolean bufferEmpty()
    {
        return !(buffer == null);//TODO tester ce problème
    }
}
