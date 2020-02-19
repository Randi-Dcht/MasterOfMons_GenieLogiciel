package be.ac.umons.sgl.mom.Events.Notifications;

import be.ac.umons.sgl.mom.Events.Events;
import be.ac.umons.sgl.mom.Objects.Characters.Mobile;

public class AddFriend implements Notification
{
    private Events evt;
    private Mobile buffer;


    /**
     * This constructor define a notification when add friend with buffer
     * @param buffer is a buffer with mobile (specific to events)
     */
    public AddFriend(Mobile buffer)
    {
        evt = Events.AddFriend;
        this.buffer = buffer;
    }


    /**
     * This constructor define a notification when add friend
     */
    public AddFriend()
    {
        evt= Events.AddFriend;
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
     * @return mobile
     */
    @Override
    public Mobile getBuffer()
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
