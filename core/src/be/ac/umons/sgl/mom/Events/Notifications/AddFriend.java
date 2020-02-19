package be.ac.umons.sgl.mom.Events.Notifications;

import be.ac.umons.sgl.mom.Events.Events;
import be.ac.umons.sgl.mom.Objects.Characters.Mobile;

public class AddFriend implements Notification
{
    private Events evt;
    private Mobile buffer;


    /**
     * This constructor define an answer with buffer
     * @param buffer is a buffer (specific to events)
     */
    public AddFriend(Mobile buffer)
    {
        evt = Events.AddFriend;
        this.buffer = buffer;
    }


    /**
     * */
    public AddFriend()
    {
        evt= Events.AddFriend;
    }


    /***/
    @Override
    public Events getEvents()
    {
        return evt;
    }


    /***/
    @Override
    public Mobile getBuffer()
    {
        return buffer;
    }


    /***/
    @Override
    public boolean bufferEmpty()
    {
        return !(buffer == null);
    }


}
