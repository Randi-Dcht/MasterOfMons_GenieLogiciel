package be.ac.umons.sgl.mom.Events.Notifications;

import be.ac.umons.sgl.mom.Events.Events;
import be.ac.umons.sgl.mom.Objects.Characters.Character;

public class ChangeDay implements Notification
{
    private Events evt;
    private Character buffer;

    public ChangeDay(Character buffer)
    {
        evt = Events.ChangeDay;
        this.buffer = buffer;
    }

    public ChangeDay()
    {
        evt= Events.ChangeDay;
    }

    public Events getEvents()
    {
        return evt;
    }

    public Character getBuffer()
    {
        return buffer;
    }

    public boolean bufferEmpty()
    {
        return !(buffer == null);
    }
}
