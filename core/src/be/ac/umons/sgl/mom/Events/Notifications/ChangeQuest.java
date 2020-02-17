package be.ac.umons.sgl.mom.Events.Notifications;

import be.ac.umons.sgl.mom.Events.Events;
import be.ac.umons.sgl.mom.Quests.Quest;

public class ChangeQuest implements Notification
{
    private Events evt;
    private Quest buffer;

    public ChangeQuest(Quest buffer)
    {
        evt = Events.ChangeQuest;
        this.buffer = buffer;
    }

    public ChangeQuest()
    {
        evt= Events.ChangeQuest;
    }

    public Events getEvents()
    {
        return evt;
    }

    public Quest getBuffer()
    {
        return buffer;
    }

    public boolean bufferEmpty()
    {
        return !(buffer == null);
    }
}
