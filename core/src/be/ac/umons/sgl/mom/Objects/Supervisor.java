package be.ac.umons.sgl.mom.Objects;

import java.util.*;
import be.ac.umons.sgl.mom.Events.Events;
import be.ac.umons.sgl.mom.GraphicalObjects.*;
import be.ac.umons.sgl.mom.Enums.Type;
import be.ac.umons.sgl.mom.Objects.Characters.PNJ;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Objects.Items.Items;
import be.ac.umons.sgl.mom.Quests.Master.*;
import com.badlogic.gdx.Gdx;

/*------Commentaire à supprimer dans version final-------*/
/*-------------------Randy-------------------------------*/
/*--J'ai mis cette classe en static qui sera remplacer --*/
/*-------SuperviserNormally dans prochaine fois  --------*/
/*-------------------------------------------------------*/


public class Supervisor /*! va être supprimer !*/
{
  private static People people;
  private static ArrayList<Items> objet = new ArrayList<Items>();
  private static ArrayList<PNJ> listPNJ = new ArrayList<PNJ>();
  private static QuestShower questShower;
  private static double minute = 600;
  private static Saving save;
  private static TimeGame time;

  public static People getPeople()
  {
    return people;
  }

  public static void add(PNJ ... lst)
  {
    listPNJ.addAll(Arrays.asList(lst));
  }

  public static void add(Items ... lst)
  {
    objet.addAll(Arrays.asList(lst));
  }

  public static void newParty(String namePlayer, Type type, QuestShower graphical, GraphicalSettings gs) /*!va être supprimer!*/
  {
    questShower = graphical;
    people = new People(namePlayer,type);
    MasterQuest mQ = new Bachelor1(people,null);
    people.newQuest(mQ);
    questShower.setQuest(mQ);
    time = new TimeGame(9,1,8,2019);
    save = new Saving(people,namePlayer,gs);
  }

  public static void test() /*<= ceux-ci est implementer pour les events*/
  {
    HashMap<Events,ArrayList<String>> l = new HashMap<>();
    ArrayList<String> aa = new ArrayList<>(); aa.add("a");aa.add("b");aa.add("c");
    ArrayList<String> bb = new ArrayList<>(); bb.add("1");bb.add("2");bb.add("3");
    l.put(Events.ChangeQuest,new ArrayList<String>(aa));
    l.put(Events.HourTimer,new ArrayList<String>(bb));

    for(String str : l.get(Events.ChangeQuest))
    {
      System.out.println("quoi1 : " + str);
    }

    for(String str : l.get(Events.HourTimer))
    {
      System.out.println("quoi2 : " + str);
    }

    l.get(Events.ChangeQuest).add("rajoute");

    for(String str : l.get(Events.ChangeQuest))
    {
      System.out.println("quoi3 : " + str);
    }
  }

  public static void changedQuest()
  {
    if(questShower != null)
    {
      Gdx.app.postRunnable(() -> questShower.setQuest(people.getQuest()));
      save.Signal();
    }
  }

  public static void callMethod(double dt)
  {
    if(people != null)
        people.energy(dt);

    if(objet != null)
    {
      for (Items o : objet){o.make(dt);}
    }
    minute = minute - dt;
    if(minute <= 0)
    {
      save.Signal();
      minute = 600;
    }

    time.updateSecond(0);
  }

}
