package be.ac.umons.sgl.mom.Events.Notifications;

import be.ac.umons.sgl.mom.Events.Events;

public class UpLevel implements Notification
{
    private Events evt;
    private int buffer;


    /**
     * This constructor define a notification when people up level with buffer
     * @param buffer is a buffer with integer (specific to events)
     */
    public UpLevel(int buffer)
    {
        evt = Events.UpLevel;
        this.buffer = buffer;
    }


    /**
     * This constructor define a notification when people up level
     */
    public UpLevel()
    {
        evt= Events.UpLevel;
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
     * @return integer
     */
    @Override
    public Integer getBuffer()
    {
        return buffer;
    }


    /**
     * This method return if the buffer isn't empty
     * @return true is the buffer isn't empty
     */
    @Override
    public boolean bufferEmpty()
    {
        return !(buffer == 0);
    }
}
