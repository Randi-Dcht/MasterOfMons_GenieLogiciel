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
    for (People p : people)
    {
      p.energy();
      System.out.println("E:" + p.getEnergy());
    }
    for (Objet o : objet)
    {
      o.make();
      System.out.println("O:" + o.getObsolete());
    }
  }

}
