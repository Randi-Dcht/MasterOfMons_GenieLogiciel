package be.ac.umons.mom.g02.Events.Notifications;

import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Objects.Characters.Character;


/**
 * This class define a notification of dead character
 * @author Umons_Group_2_ComputerScience
 */
public class Dead implements Notification
{
    private Events evt;
    private Character buffer;


    /**
     * This constructor define a notification when a character is dead with buffer
     * @param buffer is a buffer with character (specific to events)
     */
    public Dead(Character buffer)
    {
        evt = Events.Dead;
        this.buffer = buffer;
    }


    /**
     * This constructor define a notification when a character is dead
     */
    public Dead()
    {
        evt= Events.Dead;
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
    public boolean bufferNotEmpty()
    {
        return !(buffer == null);
    }
}
