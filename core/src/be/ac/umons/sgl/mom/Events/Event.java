package be.ac.umons.sgl.mom.Events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This abstract class allows define the eventAction in the game for a people or a PNJ.
 */
public class Event implements Observable
{
    /*This is a couple with a class associated to event*/
    private HashMap<Events, List<Observer>> list;

    public Event()
    {
        list = new HashMap<>();
    }

    @Override
    public void add(Events evt, Observer ... obs)
    {
        if(!list.containsKey(evt))
            list.put(evt,new ArrayList<>());
        for(Observer ob : obs)
            list.get(evt).add(ob);
    }

    @Override
    public void remove(Events evt, Observer... obs)
    {
        if(list.containsKey(evt))
            for(Observer ob : obs)
                list.get(evt).remove(ob);
    }

    @Override
    public void remove(Events evt)
    {
        list.remove(evt);
    }

    @Override
    public void notify(Events evt)
    {
        if(list.containsKey(evt))
          for(Observer obs : list.get(evt))
              obs.update(evt);
    }
}
