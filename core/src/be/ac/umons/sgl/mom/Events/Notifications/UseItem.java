package be.ac.umons.sgl.mom.Events.Notifications;

import be.ac.umons.sgl.mom.Events.Events;
import be.ac.umons.sgl.mom.Objects.Items.Items;

/**
 * This class define the notification of use the item who is in the bag's people
 * @author Umons_Group_2_ComputerScience
 */
public class UseItem implements Notification
{
    private Events evt;
    private Items buffer;


    /**
     * This constructor define a notification when people uses item
     * @param buffer is a buffer with quest (specific to events)
     */
    public UseItem(Items buffer)
    {
        evt = Events.UseItems;
        this.buffer = buffer;
    }


    /**
     * This constructor define a notification when people uses item
     */
    public UseItem()
    {
        evt= Events.UseItems;
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
     * @return quest
     */
    public Items getBuffer()
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
