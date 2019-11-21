package be.ac.umons.sgl.mom.Objects;

import java.util.*;
import be.ac.umons.sgl.mom.GraphicalObjects.*;
import be.ac.umons.sgl.mom.Enums.Lesson;
import be.ac.umons.sgl.mom.Enums.Place;
import be.ac.umons.sgl.mom.Enums.State;
import be.ac.umons.sgl.mom.Enums.Type;
import be.ac.umons.sgl.mom.Quests.Under.*;
import be.ac.umons.sgl.mom.Quests.Master.*;
import be.ac.umons.sgl.mom.Quests.Quest;
import com.badlogic.gdx.Gdx;

/*------Commentaire à supprimer dans version final-------*/
/*-------------------Randy-------------------------------*/
/*--J'ai mis cette classe en static mais cela pourrait --*/
/*-------changer en fonction de ce que l'on veut --------*/
/*-------------------------------------------------------*/

/**
*Cette classe permet de surveiller le jeu en temps réelle et gère en fonction des règles.
*@param name : comment s'appelle la partie jouée
*@author Randy Dauchot (étudiant en Sciences informatique)
*/

public class Supervisor
{
/*joueur dans cette partie*/
  private static People people;
/*liste des objets présent sur toutes les maps*/
  private static ArrayList<Items> objet = new ArrayList<Items>();
/*liste des personnages ordinateur*/
  private static ArrayList<PNJ> listPNJ = new ArrayList<PNJ>();
/*Interface graphique pour cette partie*/
  private static QuestShower questShower;
/*Mémoire pour savoir quelle MasterQuest est jouée en dernier*/
  private static int memoire;

  public static People getPeople()
  {
    return people;
  }

  public static void add(PNJ ... lst)
  {
    for(PNJ p : lst)
      listPNJ.add(p);
  }

  public static void add(Items ... lst)
  {
    for(Items o : lst)
      objet.add(o);
  }

  public static void newParty(String namePlayer, Type type, QuestShower graphical)
  {
    questShower = graphical;
    people = new People(namePlayer,type);
    MasterQuest mQ = new Bachelor1(people,null);
    people.newQuest(mQ);
    memoire = mQ.id();
    questShower.setQuest(mQ);
  }

  public static void changedQuest()
  {
    if(questShower != null)
      Gdx.app.postRunnable(() -> questShower.setQuest(people.getQuest()));
  }

  public static void attack(People attaquant, PNJ attaque)
  {}

  public static void energyPeople()
  {
    if(people != null)
        people.energy();
//        /*supprimer =>*/System.out.println("Energie:" + p.getEnergy());
  }

  public static void lifeObject()
  {
    if(objet != null)
    {
      for (Items o : objet){o.make();}
    }
  }

}
