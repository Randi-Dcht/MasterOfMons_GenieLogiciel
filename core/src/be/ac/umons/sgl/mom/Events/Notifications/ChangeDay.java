package be.ac.umons.sgl.mom.Events.Notifications;

import be.ac.umons.sgl.mom.Events.Events;
import be.ac.umons.sgl.mom.Objects.Characters.Character;


/**
 * This class define the changed day in the gale
 * @author Umons_Group_2_ComputerScience
 */
public class ChangeDay implements Notification
{
    private Events evt;
    private Character buffer;


    /**
     * This constructor define a notification when change day with buffer
     * @param buffer is a buffer with character (specific to events)
     */
    public ChangeDay(Character buffer)
    {
        evt = Events.ChangeDay;
        this.buffer = buffer;
    }


    /**
     * This constructor define a notification when change day
     */
    public ChangeDay()
    {
        evt= Events.ChangeDay;
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
    public Character getBuffer()
    {
        return buffer;
    }


    /**
     * This method return if the buffer isn't empty
     * @return true is the buffer isn't empty
     */
    @Override
    public boolean bufferEmpty()
    {
        return !(buffer == null);
    }
}
