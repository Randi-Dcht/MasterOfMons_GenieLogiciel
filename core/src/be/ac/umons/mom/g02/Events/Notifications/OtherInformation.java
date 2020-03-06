package be.ac.umons.mom.g02.Events.Notifications;

import be.ac.umons.mom.g02.Events.Events;

public class OtherInformation implements Notification
{
    private Events evt;
    private String buffer;


    /**
     * This constructor define a notification when an information is refresh in the game with the buffer
     * @param buffer is a buffer with character(specific to events)
     */
    public OtherInformation(String buffer)
    {
        evt = Events.OtherInformation;
        this.buffer = buffer;
    }


    /**
     * This constructor define a notification when an information is refresh in the game
     */
    public OtherInformation()
    {
        evt= Events.OtherInformation;
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
     * @return character
     */
    @Override
    public String getBuffer()
    {
        return buffer;
    }


    /**
     * This method return if the buffer isn't empty
     * @return true is the buffer isn't empty
     */
    @Override
    public boolean bufferNotEmpty()
    {
        return !(buffer == null);
    }
}
