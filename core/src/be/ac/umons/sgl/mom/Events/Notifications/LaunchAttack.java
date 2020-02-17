package be.ac.umons.sgl.mom.Events.Notifications;

import be.ac.umons.sgl.mom.Events.Events;
import be.ac.umons.sgl.mom.Objects.Characters.Character;

public class LaunchAttack implements Notification
{
    private Events evt;
    private Character buffer;

    public LaunchAttack(Character buffer)
    {
        evt = Events.Attack;
        this.buffer = buffer;
    }

    public LaunchAttack()
    {
        evt= Events.Attack;
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
