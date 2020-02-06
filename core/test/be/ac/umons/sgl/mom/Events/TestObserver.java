package be.ac.umons.sgl.mom.Events;

import be.ac.umons.sgl.mom.Events.Notifications.Notification;

/**
 * This class allows to test the event hasMap in the class Event*/
public class TestObserver implements Observer
{
    public boolean value = false;
    final String name;

    public TestObserver(String name)
    {
        this.name = name;
    }

    @Override
    public void update(Notification notify)
    {
        value = true;
    }
}
