package be.ac.umons.sgl.mom.Events.Notifications;

import be.ac.umons.sgl.mom.Events.Events;

public class Answer implements Notification
{
    private Events evt;
    private String buffer;


    /**
     * This constructor define an answer with buffer
     * @param buffer is a buffer (specific to events)
     */
    public Answer(String buffer)
    {
        evt = Events.Answer;
        this.buffer = buffer;
    }


    /**
     * */
    public Answer()
    {
        evt= Events.Answer;
    }


    /***/
    @Override
    public Events getEvents()
    {
        return evt;
    }


    /***/
    @Override
    public String getBuffer()
    {
        return buffer;
    }


    /***/
    @Override
    public boolean bufferEmpty()
    {
        return !(buffer == null);
    }


}
