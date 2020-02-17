package be.ac.umons.sgl.mom.Events.Notifications;

import be.ac.umons.sgl.mom.Events.Events;
import be.ac.umons.sgl.mom.Objects.Characters.Character;

public class Dead implements Notification
{
    private Events evt;
    private Character buffer;

    public Dead(Character buffer)
    {
        evt = Events.Dead;
        this.buffer = buffer;
    }

    public Dead()
    {
        evt= Events.Dead;
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
