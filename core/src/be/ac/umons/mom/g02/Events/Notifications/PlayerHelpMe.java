package be.ac.umons.mom.g02.Events.Notifications;

import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Objects.Characters.Character;

public class PlayerHelpMe implements Notification
{
    private Events evt;
    private Character buffer;


    /**
     * This constructor define a notification when launch attack with buffer
     * @param buffer is a buffer with character(specific to events)
     */
    public PlayerHelpMe(Character buffer)
    {
        evt = Events.Help;
        this.buffer = buffer;
    }


    /**
     * This constructor define a notification when launch attack
     */
    public PlayerHelpMe()
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
     * @return The victim
     */
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
