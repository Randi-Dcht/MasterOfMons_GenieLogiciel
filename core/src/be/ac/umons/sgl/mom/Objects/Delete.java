package be.ac.umons.sgl.mom.Objects;

/*Supprimer cette classe -- Supprimer cette classe*/

import java.util.*;
import be.ac.umons.sgl.mom.GraphicalObjects.*;
import be.ac.umons.sgl.mom.Enums.Lesson;
import be.ac.umons.sgl.mom.Enums.Place;
import be.ac.umons.sgl.mom.Enums.State;
import be.ac.umons.sgl.mom.Enums.Type;
import be.ac.umons.sgl.mom.Quests.Under.*;
import be.ac.umons.sgl.mom.Quests.Master.*;
import be.ac.umons.sgl.mom.Quests.Quest;

/*Supprimer cette classe -- Supprimer cette classe*/

public class Delete extends TimerTask
{
  final People p;

  public Delete(People a)
  {
    p = a;
  }
  public void run()
  {
    Supervisor.energyPeople();
    /*supprimer =>*/ //people[0].getQuest().eventMaps();
    /*supprimer =>*/for(Quest q : p.getQuest().getSubQuests())
    /*supprimer =>*/{
    /*supprimer =>*/  q.addProgress(0.05);
    /*supprimer =>*/}
    /*supprimer =>*/
    /*supprimer =>*/
    /*supprimer =>*/
    /*supprimer =>*/
  }
}

/*Supprimer cette classe -- Supprimer cette classe*/
