package be.ac.umons.sgl.mom.Events.Notifications;

import be.ac.umons.sgl.mom.Events.Events;

public class Notification
{
    private Events evt;
    private Object[] buffer;

    public Notification(Events events, Object ... buffer)
    {
        evt = events;
        this.buffer = buffer;
    }

    public Notification(Events events)
    {
        evt = events;
        buffer = null;
    }

    public Events getEvents()
    {
        return evt;
    }

    public Object[] getBuffer()
    {
        return buffer;
    }

    public boolean bufferEmpty()
    {
        return buffer == null;
    }
}
