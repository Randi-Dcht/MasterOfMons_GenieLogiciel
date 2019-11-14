import java.util.*;

/**
*Cette classe permet de surveiller le jeu en temps réelle et gère en fonction des règles.
*@param nombre : combien de joeur joue actuellement.
*@param people : liste des joeurs actuelles.
*@author Randy Dauchot (étudiant en Sciences informatique)
*/

public class Rule
{
  private People[] people;
  private int numberPlayer;
  private ArrayList<PNJ> listPNJ;

  public Rule(int number, People[] people)
  {
    this.people = people;
    this.numberPlayer  = number;
  }

  public void attack(People attaquant, People attaque)
  {}
  public void attack(People attaquant, PNJ attaque)
  {}

}
