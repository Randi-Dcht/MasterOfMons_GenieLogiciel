package be.ac.umons.sgl.mom.Events.Notifications;

import be.ac.umons.sgl.mom.Enums.Place;
import be.ac.umons.sgl.mom.Events.Events;
import be.ac.umons.sgl.mom.Quests.Quest;

public class PlaceInMons implements Notification
{
    private Events evt;
    private Place buffer;

    public PlaceInMons(Place buffer)
    {
        evt = Events.PlaceInMons;
        this.buffer = buffer;
    }

    public PlaceInMons()
    {
        evt= Events.PlaceInMons;
    }

    public Events getEvents()
    {
        return evt;
    }

    public Place getBuffer()
    {
        return buffer;
    }

    public boolean bufferEmpty()
    {
        return buffer == null;
    }
}
