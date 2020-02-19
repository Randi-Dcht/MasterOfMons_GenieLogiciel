package be.ac.umons.sgl.mom.Events.Notifications;

import be.ac.umons.sgl.mom.Events.Events;
import java.util.ArrayList;

public class Dialog implements Notification
{
    private Events evt;
    private ArrayList<String> buffer;

    public Dialog(ArrayList<String> buffer)
    {
        evt = Events.ChangeQuest;
        this.buffer = buffer;
    }

    public Dialog()
    {
        evt= Events.ChangeQuest;
    }

    public Events getEvents()
    {
        return evt;
    }

    public ArrayList<String> getBuffer()
    {
        return buffer;
    }

    public boolean bufferEmpty()
    {
        return !(buffer == null);
    }
}
