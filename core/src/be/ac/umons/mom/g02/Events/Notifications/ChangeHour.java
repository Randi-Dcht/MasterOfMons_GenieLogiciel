package be.ac.umons.mom.g02.Events.Notifications;

import be.ac.umons.mom.g02.Events.Events;

public class ChangeHour implements Notification
{
    private Events evt;
    private int buffer;


    /**
     * This constructor define a notification when change day with buffer
     * @param buffer is a buffer with character (specific to events)
     */
    public ChangeHour(int buffer)
    {
        evt = Events.ChangeHour;
        this.buffer = buffer;
    }


    /**
     * This constructor define a notification when change day
     */
    public ChangeHour()
    {
        evt= Events.ChangeHour;
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
     * @return character
     */
    @Override
    public Integer getBuffer()
    {
        return buffer;
    }


    /**
     * This method return if the buffer isn't empty
     * @return true is the buffer isn't empty
     */
    @Override
    public boolean bufferNotEmpty()
    {
        return true;
    }
}
