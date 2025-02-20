package be.ac.umons.mom.g02.Events.Notifications;

import be.ac.umons.mom.g02.Events.Events;


/**
 * This class define the changed day in the gale
 * @author Umons_Group_2_ComputerScience_RandyDauchot
 */
public class ChangeDay implements Notification
{
    private Events evt;
    private int buffer;
    private boolean empty;


    /**
     * This constructor define a notification when change day with buffer
     * @param buffer is a buffer with character (specific to events)
     */
    public ChangeDay(int buffer)
    {
        evt = Events.ChangeDay;
        this.buffer = buffer;
        empty = false;
    }


    /**
     * This constructor define a notification when change day
     */
    public ChangeDay()
    {
        evt = Events.ChangeDay;
        empty = true;
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
        return !empty;
    }
}
