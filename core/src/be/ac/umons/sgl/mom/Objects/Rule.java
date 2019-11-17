package be.ac.umons.sgl.mom.Objects;
import java.util.*;

/**
*Cette classe permet de surveiller le jeu en temps réelle et gère en fonction des règles.
*@param nombre : combien de joeur joue actuellement.
*@param people : liste des joeurs actuelles.
*@author Randy Dauchot (étudiant en Sciences informatique)
*/

public class Rule extends TimerTask
{
  private People[] people;
  private Objet[] objet;
  private int numberPlayer;
  private ArrayList<PNJ> listPNJ;
  /* prochaine mise à jour :
    ------------------------
  private ArrayList<People> people;
  private ArrayList<Objet> objet;
  private int numberPlayer;
  private ArrayList<PNJ> listPNJ;
  private Place actual;
  private State state;
  */

  public Rule(int number, People[] people,Objet[] objet)
  {
    this.people = people;
    this.numberPlayer  = number;
    this.objet = objet;
  }

  public void attack(People attaquant, People attaque)
  {}
  public void attack(People attaquant, PNJ attaque)
  {}

  public void run()
  {
    /*supprimer =>*/ //people[0].getQuest().eventMaps();
    /*supprimer =>*/for(Quest q : people[0].getQuest().getSubQuests())
    /*supprimer =>*/{
    /*supprimer =>*/  q.addProgress(0.05);
    /*supprimer =>*/}
    if(people != null)
    {
      for (People p : people)
      {
        p.energy();
//        /*supprimer =>*/System.out.println("Energie:" + p.getEnergy());
      }
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
