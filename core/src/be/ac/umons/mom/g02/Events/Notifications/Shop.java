package be.ac.umons.mom.g02.Events.Notifications;

import be.ac.umons.mom.g02.Objects.Characters.Character;
import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Objects.Characters.Dealer;
import be.ac.umons.mom.g02.Objects.Characters.People;

/**
 * This class define the notification of the Attack
 * @author Umons_Group_2_ComputerScience
 */
public class Shop implements Notification
{
    private Events evt;
    private Character buffer;
    private Character secondBuffer;


    /**
     * This constructor define a notification when launch attack with buffer
     * @param player is a buffer with character(specific to events)
     */
    public Shop(People player, Dealer seller)
    {
        evt = Events.Shop;
        this.buffer = player;
        secondBuffer = seller;
    }


    /**
     * This constructor define a notification when launch attack
     */
    public Shop()
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
     * @return character
     */
    public Character getBuffer()
    {
        return buffer;
    }


    /**
     * This method return the buffer of this notification if it isn't empty
     * @return character
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
