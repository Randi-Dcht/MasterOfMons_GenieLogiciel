package be.ac.umons.mom.g02.Events.Notifications;

import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Objects.Characters.Character;

import java.awt.*;

public class PNJMoved implements Notification {

    private Character concernedOne;
    private Events evt;
    private Point position;

    /**
     * @param concernedOne The concerned character
     * @param buffer The new life
     */
    public PNJMoved(Character concernedOne, Point buffer) {
        this.concernedOne = concernedOne;
        evt = Events.PNJMoved;
        this.position = buffer;
    }

    /**
     * This method return the events
     *
     * @return events is the events
     */
    @Override
    public Events getEvents() {
        return evt;
    }

    /**
     * This method allows to return the object
     *
     * @return The new position of the PNJ
     */
    @Override
    public Object getBuffer() {
        return position;
    }

    /**
     * @return The PNJ that moved
     */
    public Character getConcernedOne() {
        return concernedOne;
    }

    /**
     * This method return if the buffer is occupy
     *
     * @return boolean if the buffer contains element
     */
    @Override
    public boolean bufferNotEmpty() {
        return position != null;
    }
}
