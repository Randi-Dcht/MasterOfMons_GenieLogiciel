package be.ac.umons.sgl.mom.Events;


import be.ac.umons.sgl.mom.Events.Notifications.Notification;

public interface Observer
{
    public void update(Notification notify);
}
