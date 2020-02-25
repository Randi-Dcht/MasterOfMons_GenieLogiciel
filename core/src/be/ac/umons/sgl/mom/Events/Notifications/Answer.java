package be.ac.umons.sgl.mom.Events.Notifications;

import be.ac.umons.sgl.mom.Events.Events;


/**
 * This method allows to give an answer during the dialog.
 * @author Umons_Group_2_ComputerScience
 */
public class Answer implements Notification
{
    private Events evt;
    private String buffer;


    /**
     * This constructor define a notification when give a answer with buffer
     * @param buffer is a buffer with string(specific to events)
     */
    public Answer(String buffer)
    {
        evt = Events.Answer;
        this.buffer = buffer;
    }


    /**
     *This constructor define a notification when give a answer
     */
    public Answer()
    {
        evt= Events.Answer;
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
     * @return string
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
