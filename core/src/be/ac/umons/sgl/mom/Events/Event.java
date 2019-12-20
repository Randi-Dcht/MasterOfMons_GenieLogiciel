package be.ac.umons.sgl.mom.Events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This abstract class allows define the eventAction in the game for a people or a PNJ.
 */
public class Event
{
    /*This is a couple with a class associated to event*/
    private HashMap<Events, List<Observer>> list;

    public Event()
    {
        list = new HashMap<>();
    }

    public void add(Events evt, Observer ... obs)
    {
        if(!list.containsKey(evt))
            list.put(evt,new ArrayList<>());
        for(Observer ob : obs)
            list.get(evt).add(ob);
    }

    public void update(Events evt)
    {
        if(list.containsKey(evt))
          for(Observer obs : list.get(evt))
              obs.notify(evt);
    }
}
