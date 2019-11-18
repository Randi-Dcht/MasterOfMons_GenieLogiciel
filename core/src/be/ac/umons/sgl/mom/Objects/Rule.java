package be.ac.umons.sgl.mom.Objects;
import java.util.*;
import be.ac.umons.sgl.mom.GraphicalObjects.*;

/**
*Cette classe permet de surveiller le jeu en temps réelle et gère en fonction des règles.
*@param name : comment s'appelle la partie jouée
*@author Randy Dauchot (étudiant en Sciences informatique)
*/

public class Rule extends TimerTask
{
/*joueur dans cette partie*/
  private People people;
/*liste des objets présent sur toutes les maps*/
  private ArrayList<Objet> objet = new ArrayList<Objet>();
/*liste des personnages ordinateur*/
  private ArrayList<PNJ> listPNJ = new ArrayList<PNJ>();
/*nom de la partie en cours*/
  final String name;
  final QuestShower questShower;
  private int memoire; /*changer prochaine mise à jour*/

  public Rule(String name,QuestShower qs)
  {
    this.name = name;
    questShower = qs;
  }

  public People getPeople()
  {
    return people;
  }

  public ArrayList<PNJ> getPNJ()
  {
    return listPNJ;
  }

  public ArrayList<Objet> getObjet()
  {
    return objet;
  }

  public void add(PNJ ... lst)
  {
    for(PNJ p : lst)
      listPNJ.add(p);
  }

  public void add(Objet ... lst)
  {
    for(Objet o : lst)
      objet.add(o);
  }

  public void newParty(String name, Type type)
  {
    people = new People(name,type);
    MasterQuest mQ = new Bachelor1(people,null);
    people.newQuest(mQ);
    memoire = mQ.id;
    questShower.setQuest(mQ);
  }

  public void attack(People attaquant, People attaque)
  {}

  public void attack(People attaquant, PNJ attaque)
  {}

  public void run()
  {
    /*supprimer =>*/if(memoire != people.getQuest().id)
    /*supprimer =>*/{
    /*supprimer =>*/  questShower.setQuest(people.getQuest());
    /*supprimer =>*/}
    /*supprimer =>*/ //people[0].getQuest().eventMaps();
    /*supprimer =>*/for(Quest q : people.getQuest().getSubQuests())
    /*supprimer =>*/{
    /*supprimer =>*/  q.addProgress(0.05);
    /*supprimer =>*/}
    if(people != null)
    {
        people.energy();
//        /*supprimer =>*/System.out.println("Energie:" + p.getEnergy());

    }
    if(objet != null)
    {
      for (Objet o : objet)
      {
        o.make();
      }
    }
  }

}
