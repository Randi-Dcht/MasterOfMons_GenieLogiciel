package be.ac.umons.sgl.mom.Events.Notifications;

import be.ac.umons.sgl.mom.Events.Events;

public interface Notification
{

    public Events getEvents();

    public Object getBuffer();

    public boolean bufferEmpty();
}
