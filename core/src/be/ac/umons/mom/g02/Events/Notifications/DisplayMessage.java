package be.ac.umons.mom.g02.Events.Notifications;

import be.ac.umons.mom.g02.Events.Events;

public class DisplayMessage implements Notification
{

    private Events evt;
    private String buffer;
    private String id;


    /**
     * This constructor define a notification when change quest with buffer
     * @param buffer is a buffer with quest (specific to events)
     */
    public DisplayMessage(String buffer,String id)
    {
        evt = Events.DisplayMessage;
        this.buffer = buffer;
    }


    /**
     * This constructor define a notification when change quest
     */
    public DisplayMessage()
    {
        this(null,null);
    }


    public String getId()
    {
        return id;
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
    public String  getBuffer()
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
