package be.ac.umons.mom.g02.Events.Notifications;

import be.ac.umons.mom.g02.Enums.Places;
import be.ac.umons.mom.g02.Events.Events;

public class EntryPlaces implements Notification
{
    private Events evt;
    private Places buffer;


    /**
     * This constructor define a notification when people change maps in maps with buffer
     * @param buffer is a buffer with maps (specific to events)
     */
    public EntryPlaces(Places buffer)
    {
        evt = Events.EntryPlace;
        this.buffer = buffer;
    }


    /**
     * This constructor define a notification when people change maps in maps
     */
    public EntryPlaces()
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
    public Places getBuffer()
    {
        return buffer;
    }


    /**
     * This method return if the buffer isn't empty
     * @return true is the buffer isn't empty
     */
    public boolean bufferNotEmpty()
    {
        return buffer != null;
    }
}
