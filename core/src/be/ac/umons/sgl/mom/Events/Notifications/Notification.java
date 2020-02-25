package be.ac.umons.sgl.mom.Events.Notifications;

import be.ac.umons.sgl.mom.Events.Events;


/**
 * This class define a notification
 * @author Umons_Group_2_ComputerScience
 */
public interface Notification
{


    /**
     * This method return the events
     * @return events is the events
     */
    public Events getEvents();


    /**
     * This method allows to return the object
     * @return the object
     */
    public Object getBuffer();


    /**
     * This method return if the buffer is occupy
     * @return boolean if the buffer contains element
     */
    public boolean bufferNotEmpty(); /*bufferNotEmpty*/ //TODO translate
}
