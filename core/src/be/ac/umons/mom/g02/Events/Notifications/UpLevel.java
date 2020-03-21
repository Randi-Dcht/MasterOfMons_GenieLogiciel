package be.ac.umons.mom.g02.Events.Notifications;

import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Objects.Characters.People;


/**
 * This class define the notification when people up level
 * @author Umons_Group_2_ComputerScience
 */
public class UpLevel implements Notification
{
    private Events evt;
    private People buffer;
    private boolean empty;


    /**
     * This constructor define a notification when people up level with buffer
     * @param buffer is a buffer with integer (specific to events)
     */
    public UpLevel(People buffer)
    {
        evt = Events.UpLevel;
        this.buffer = buffer;
        empty = false;
    }



    /**
     * This constructor define a notification when people up level
     */
    public UpLevel()
    {
        evt= Events.UpLevel;
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
     * @return integer
     */
    @Override
    public People getBuffer()
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
