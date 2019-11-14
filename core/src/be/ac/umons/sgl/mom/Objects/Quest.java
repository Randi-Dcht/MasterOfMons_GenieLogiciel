package be.ac.umons.sgl.mom.Objects;
import java.util.*;

/**
*Cette classe abstraite permet de définir des méthodes générales pour toute les quetes et certaines varaibles communes
*@param before qui est la quête avant
*@param id qui le numéro de la quête (1 à 5)
*@param course qui est la liste des cours que le personnage doit suivre pour cette quête
*@author Randy Dauchot & Guillaume Cardoen (étudiant en Sciences informatique)
*/
public abstract class Quest
{
  protected static int numberQuest = 0;
  protected ArrayList<Lesson> interrogation = new ArrayList<Lesson>(); //les interrogations qui doit encore passer.
  protected ArrayList<Objet> availableObject = new ArrayList<Objet>(); //objet disponible sur la maps pour lui prendre
  protected double percent = 0; //avanacement de la quête
  final Quest before; //quête qui se trouve juste avant
  final int id; //permet de dire dans quelle quête cela se passe (1 à 5)
  final People people;
  final Lesson[] course; //cours que le personnage doit prendre pour cette quête
  protected Quest after = null; //la quête qui suit
  protected GoalsQuest[] goalsQuest;
  protected boolean finished = false;

  public Quest(Quest before, int id, Lesson[] course,People people)
  {
    this.before = before;
    this.id     = id;
    this.course = course;
    this.people = people;
  }

/**
*Cette méthode permet de créer une nouvelle quête quand celle-ci est terminé
*@param people qui est le personnage qui va réalisé la quête.
*@param after qui est la quête suiavnte celle-ci (liste chainée).
*/
  public void newQuest(People people,Quest after)
  {
    if(finished)
    {
      this.after = after;
      people.newQuest(after);
    }
  }

/**
* Retourne si la quête est terminée.
* @return Si la quête est terminée.
*/
  public boolean isFinished()
  {
      return finished;
  }

/**
*Cette méthode permet de retourner les cours suivis.
*@return couse qui retourne la liste des cours suivis pour cette quête.
*/
  public Lesson[] lesson()
  {
    return course;
  }

/**
* Retourne le progrés de la quête compris dans l'interval [0,1].
* @return Le progrés de la quête compris dans l'interval [0,1].
*/
  public double getProgress()
  {
      return (percent/100);
  }

/**
* Retourne une liste ses sous-quêtes de cette quête.
*/
  public abstract GoalsQuest[] getSubQuests();

/**
* Retourne le nom de la quête.
* @return Le nom de la quête.
*/
  public String getName()
  {
        return ("Quest"+id);
  }

/**
* Retourne si la quête est active ?
* @return Si la quête est active ?
*/
  public boolean isActive()
  {
    return !finished;
  }

  public int getTotalSubQuestsNumber()
  {
    return numberQuest;
  }
/**
*Cette méthode permet d'ajouter à la liste d'interrogation les cours qui ont été raté dans la quête précédent
*@param list qui est une ArrayList des cours que le personnage suit.
*/
  public void retake(ArrayList<Lesson> list)
  {
    interrogation.addAll(list);
  }

/**
*Cette méthod permet de dire où est le personnage et en fonction changer son état
*/
public void location(Place place)
{
  if(place.equals(Place.GrandAmphi) || place.equals(Place.Waroque))
   people.changedState(2);/*
  else if(place.equals(Place.kot))
   //gérer car étudie ou dort
  else
   //gérer soit courir ou rien*/
  eventMaps();
}

public void eventMaps()
{
  for(GoalsQuest gq : goalsQuest)
  {
    gq.evenActivity();
  }
}

/**
*Cette méthode permet de voir si la quête est terminée pour changer de quête ou continuer
*@param many ajoute du pourcentage de terminer quand le personnage a fait quelque chose
*/
  public void successful(int many)
  {
    percent = percent + many;
  }

/*------------------------------------------------------------------------------------------------------------*/

/**
*Cette méthode permet de gérer les rencontres entres PNJ et le personnage
*@param other qui le PNJ que le joueur va rencontrés
*/
  public abstract void meetOther(PNJ other);


/**
*Cette méthode permet d'énoner l'objectif de la quête
*@return l'objectif
*/
  public abstract String question();

}
