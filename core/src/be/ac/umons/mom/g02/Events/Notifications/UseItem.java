package be.ac.umons.mom.g02.Events.Notifications;

import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.Items.Items;

/**
 * This class define the notification of use the item who is in the bag's people
 * @author Umons_Group_2_ComputerScience
 */
public class UseItem implements Notification
{
    private Events evt;
    private Items buffer;
    private People who;


    /**
     * This constructor define a notification when people uses item
     * @param buffer is a buffer with quest (specific to events)
     */
    public UseItem(Items buffer, People people)
    {
        evt = Events.UseItems;
        this.buffer = buffer;
        who = people;
    }


    /**
     * This constructor define a notification when people uses item
     */
    public UseItem()
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
     * @return quest
     */
    public Items getBuffer()
    {
        return buffer;
    }


    public People getPeople()
    {
        return who;
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
