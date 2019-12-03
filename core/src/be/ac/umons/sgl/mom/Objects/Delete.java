package be.ac.umons.sgl.mom.Objects;

/*Supprimer cette classe -- Supprimer cette classe*/

import java.util.*;

import be.ac.umons.sgl.mom.Objects.Characters.People;
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
    Supervisor.callMethod(1);
    /*supprimer =>*/ //people[0].getQuest().eventMaps();
    /*supprimer =>*/for(Quest q : p.getQuest().getSubQuests())
    /*supprimer =>*/{
    /*supprimer =>*/  q.addProgress(0.9);
    /*supprimer =>*/}
    /*supprimer =>*/
    /*supprimer =>*/
    /*supprimer =>*/
    /*supprimer =>*/
  }
}

/*Supprimer cette classe -- Supprimer cette classe*/
