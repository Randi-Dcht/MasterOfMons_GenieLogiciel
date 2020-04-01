package be.ac.umons.mom.g02.Events.Notifications;

import be.ac.umons.mom.g02.Objects.Characters.Character;
import be.ac.umons.mom.g02.Events.Events;

/**
 * This class define the notification of the Attack
 * @author Umons_Group_2_ComputerScience
 */
public class LaunchAttack implements Notification
{
    private Events evt;
    private Character buffer;
    private Character secondBuffer;


    /**
     * This constructor define a notification when launch attack with buffer
     * @param victim is a buffer with character(specific to events)
     */
    public LaunchAttack(Character victim,Character attacker)
    {
        evt = Events.Attack;
        this.buffer = victim;
        secondBuffer = attacker;
    }


    /**
     * This constructor define a notification when launch attack
     */
    public LaunchAttack()
    {
        this(null,null);
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
     * This method return the buffer of this notification if it isn't empty
     * @return The attacker
     */
    public Character getBufferSecond()
    {
        return secondBuffer;
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
