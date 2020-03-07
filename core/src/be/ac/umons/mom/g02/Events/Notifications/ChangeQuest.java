package be.ac.umons.mom.g02.Events.Notifications;

import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Quests.Quest;


/**
 * This class define the notification of changed quest of the people
 * @author Umons_Group_2_ComputerScience
 */
public class ChangeQuest implements Notification
{
    private Events evt;
    private Quest buffer;


    /**
     * This constructor define a notification when change quest with buffer
     * @param buffer is a buffer with quest (specific to events)
     */
    public ChangeQuest(Quest buffer)
    {
        evt = Events.ChangeQuest;
        this.buffer = buffer;
    }


    /**
     * This constructor define a notification when change quest
     */
    public ChangeQuest()
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
     * @return quest
     */
    public Quest getBuffer()
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
