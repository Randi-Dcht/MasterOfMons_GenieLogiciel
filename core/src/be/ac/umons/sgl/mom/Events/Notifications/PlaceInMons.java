package be.ac.umons.sgl.mom.Events.Notifications;

import be.ac.umons.sgl.mom.Events.Events;
import be.ac.umons.sgl.mom.Quests.Quest;

public class PlaceInMons implements Notification
{
    private Events evt;
    private String buffer;

    public PlaceInMons(String buffer)
    {
        evt = Events.ChangeQuest;
        this.buffer = buffer;
    }

    public PlaceInMons()
    {
        evt= Events.ChangeQuest;
    }

    public Events getEvents()
    {
        return evt;
    }

    public String getBuffer()
    {
        return buffer;
    }

    public boolean bufferEmpty()
    {
        return buffer == null;
    }
}
