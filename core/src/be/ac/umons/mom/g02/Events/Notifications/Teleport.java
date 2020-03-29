package be.ac.umons.mom.g02.Events.Notifications;

import be.ac.umons.mom.g02.Events.Events;

public class Teleport implements Notification {

    protected String buffer;

    /**
     * @param map The destination map
     */
    public Teleport(String map) {
        this.buffer = map;
    }

    /**
     * This method return the events
     *
     * @return events is the events
     */
    @Override
    public Events getEvents() {
        return Events.Teleport;
    }

    /**
     * This method allows to return the object
     *
     * @return the object
     */
    @Override
    public String getBuffer() {
        return buffer;
    }

    /**
     * This method return if the buffer is occupy
     *
     * @return boolean if the buffer contains element
     */
    @Override
    public boolean bufferNotEmpty() {
        return buffer != null;
    }
}
