package be.ac.umons.sgl.mom.Events.Notifications;

import be.ac.umons.sgl.mom.Events.Events;
import java.util.ArrayList;

public class Dialog implements Notification
{
    private Events evt;
    private ArrayList<String> buffer;


    /**
     * This constructor define a notification when start a dialog with buffer
     * @param buffer is a buffer with ArrayList of string (specific to events)
     */
    public Dialog(ArrayList<String> buffer)
    {
        evt = Events.Dialog;
        this.buffer = buffer;
    }

    /**
     *  This constructor define a notification when start a dialog
     */
    public Dialog()
    {
        evt= Events.Dialog;
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
     * @return ArrayList<String>
     */
    @Override
    public ArrayList<String> getBuffer()
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
        return !(buffer == null);
    }
}
