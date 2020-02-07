package be.ac.umons.sgl.mom.Events.Notifications;

import be.ac.umons.sgl.mom.Events.Events;

public class Answer implements Notification
{
    private Events evt;
    private String buffer;

    public Answer(String buffer)
    {
        evt = Events.Answer;
        this.buffer = buffer;
    }

    public Answer()
    {
        evt= Events.Answer;
    }

    public Events getEvents()
    {
        return evt;
    }

    public String getBuffer()
    {
        return buffer;
    }

    public boolean bufferEmpty()
    {
        return buffer == null;
    }


}
