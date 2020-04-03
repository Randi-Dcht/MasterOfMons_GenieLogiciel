package be.ac.umons.mom.g02.Events.Notifications;

import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Objects.Course;

public class GoToLesson implements Notification
{
    private Events evt;
    private Course buffer;


    /**
     * This constructor define a notification when people change maps in maps with buffer
     * @param buffer is a buffer with maps (specific to events)
     */
    public GoToLesson(Course buffer)
    {
        evt = Events.GoLesson;
        this.buffer = buffer;
    }


    /**
     * This constructor define a notification when people change maps in maps
     */
    public GoToLesson()
    {
        this(null);
    }


    /**
     * This method return the event of this notification
     * @return event of notification
     */
    @Override
    public Events getEvents()
    {
        return evt;
    }


    /**
     * This method return the buffer of this notification if it isn't empty
     * @return maps
     */
    @Override
    public Course getBuffer()
    {
        return buffer;
    }


    /**
     * This method return if the buffer isn't empty
     * @return true is the buffer isn't empty
     */
    public boolean bufferNotEmpty()
    {
        return buffer != null;
    }
}
