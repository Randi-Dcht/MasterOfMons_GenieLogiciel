package be.ac.umons.mom.g02.Events;

import be.ac.umons.mom.g02.Events.Notifications.Notification;


/**
 * This class define the observer
 * This class follows the design pattern observable
 * @author Umons_Group_2_ComputerScience
 **/
public interface Observer
{
    /**The method to receive the notification*/
    public void update(Notification notify);
}
