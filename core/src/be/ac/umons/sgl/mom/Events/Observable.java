package be.ac.umons.sgl.mom.Events;

import be.ac.umons.sgl.mom.Events.Notifications.Notification;

/**
 * This class define the design pattern observable
 * @author Umons_Group_2_ComputerScience
 */
public interface Observable
{
    /**The new notification in the game*/
    void notify(Notification notif);


    /**Add a new observer associate an events*/
    void add(Events evt,Observer ... obs);


    /**Remove a observer associate an events*/
    void remove(Events evt, Observer obs);


    /**Remove all observer with the events*/
    void remove(Events evt);
}
