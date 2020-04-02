package be.ac.umons.mom.g02.Events.Notifications;

import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Objects.Course;

import java.util.ArrayList;
import java.util.HashMap;

public class PlanningChanged implements Notification {

    protected HashMap<Integer, ArrayList<Course>> planning;

    public PlanningChanged(HashMap<Integer, ArrayList<Course>> planning) {
        this.planning = planning;
    }

    /**
     * This method return the events
     *
     * @return events is the events
     */
    @Override
    public Events getEvents() {
        return Events.PlanningChanged;
    }

    /**
     * This method allows to return the object
     *
     * @return The planning
     */
    @Override
    public Object getBuffer() {
        return planning;
    }

    /**
     * This method return if the buffer is occupy
     *
     * @return boolean if the buffer contains element
     */
    @Override
    public boolean bufferNotEmpty() {
        return planning != null;
    }
}
