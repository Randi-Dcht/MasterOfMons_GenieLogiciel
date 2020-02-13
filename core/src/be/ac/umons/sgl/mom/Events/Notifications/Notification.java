package be.ac.umons.sgl.mom.Events.Notifications;

import be.ac.umons.sgl.mom.Events.Events;

public interface Notification
{


    /**
     * This method return the events
     * @return events is the events
     */
    public Events getEvents();


    /**
     * This method allows to return the object
     * @return the object
     */
    public Object getBuffer();


    /**
     * This method return if the buffer is occupy
     * @return boolean if the buffer contains element
     */
    public boolean bufferEmpty();
}
