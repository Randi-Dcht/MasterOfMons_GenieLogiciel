package be.ac.umons.mom.g02.Events;

import be.ac.umons.mom.g02.Events.Notifications.Notification;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class allows to add events to the list of Event
 * This class follows the design pattern Observable.
 * @author Umons_Group_2_ComputerScience
 */
public class Event implements Observable
{
    /**
     * This is a couple with a class associated to events
     */
    private HashMap<Events, List<Observer>> list;
    /***/
    private Observer removeObserver;
    /***/
    private Events removeEvents;


    /**
     * This constructor define the observable with events
     */
    public Event()
    {
        list = new HashMap<>();
    }


    /**
     * This method allows to add the observer(instance) with a event
     * @param evt is a events
     * @param obs is an instance of the observer
     */
    @Override
    public void add(Events evt, Observer ... obs)
    {
        if(!list.containsKey(evt))
            list.put(evt,new ArrayList<>());
        for(Observer ob : obs)
            list.get(evt).add(ob);
    }


    /***/
    public void add(Observer obs,Events ... evts)
    {
        for (Events evt : evts)
            add(evt,obs);
    }


    /**
     * This method allows to remove the observer associate with the event
     * @param evt is an events
     * @param obs is the observer who remove
     */
    @Override
    public void remove(Events evt, Observer obs)
    {
       removeEvents   = evt;
       removeObserver = obs;
    }


    /**
     * This method allows to remove an events
     * @param evt is teh event to remove in the list
     */
    @Override
    public void remove(Events evt)
    {
        list.remove(evt);
    }


    /**
     * This method allows to notify the observer
     * @param notify is the notification with Events and a buffer
     */
    @Override
    public void notify(Notification notify)
    {
        if (removeObserver != null && removeEvents != null)
        {
            list.get(removeEvents).remove(removeObserver);
            removeEvents=null; removeObserver=null;
        }

        if(list.containsKey(notify.getEvents()))
        {
            for (Observer obs : list.get(notify.getEvents()))
                obs.update(notify);
        }
    }


    /*This method is only for the JunitTest*/
    HashMap<Events, List<Observer>> getList()
    {
        return list;
    }
}
