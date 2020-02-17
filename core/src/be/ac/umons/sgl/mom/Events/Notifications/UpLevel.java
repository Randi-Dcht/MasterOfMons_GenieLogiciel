package be.ac.umons.sgl.mom.Events.Notifications;

import be.ac.umons.sgl.mom.Events.Events;

public class UpLevel implements Notification
{
    private Events evt;
    private int buffer;

    public UpLevel(int buffer)
    {
        evt = Events.UpLevel;
        this.buffer = buffer;
    }

    public UpLevel()
    {
        evt= Events.UpLevel;
    }

    public Events getEvents()
    {
        return evt;
    }

    public Integer getBuffer()
    {
        return buffer;
    }

    public boolean bufferEmpty()
    {
        return !(buffer == 0);
    }
}
