import java.util.*;

/**
*Cette classe abstraite permet de définir des méthodes générales pour toute les quetes et certaines varaibles communes
*@param before qui est la quête avant
*@param id qui le numéro de la quête (1 à 5)
*@param course qui est la liste des cours que le personnage doit suivre pour cette quête
*@author Randy Dauchot (étudiant en Sciences informatique)
*/
public abstract class Quest
{
  private ArrayList<Lesson> interrogation = new ArrayList<Lesson>(); //les interrogations qui doit encore passer.
  private ArrayList<Objet> availableObject = new ArrayList<Objet>(); //objet disponible sur la maps pour lui prendre
  private int percentage = 0; //avanacement de la quête
  final Quest before; //quête qui se trouve juste avant
  final int id; //permet de dire dans quelle quête cela se passe (1 à 5)
  final Lesson[] course; //cours que le personnage doit prendre pour cette quête
  private Quest after = null; //la quête qui suit

  public Quest(Quest before, int id, Lesson[] course)
  {
    this.before = before;
    this.id = id;
    this.course = course;
  }

/**
*Cette méthode permet de créer une nouvelle quête quand celle-ci est terminé
*@param people qui est le personnage qui va réalisé la quête.
*@param after qui est la quête suiavnte celle-ci (liste chainée).
*/
  public void newQuest(People people,Quete after)
  {
    this.after = after;
    personne.newQuest(after);
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
*Cette méthode permet d'ajouter à la liste d'interrogation les cours qui ont été raté dans la quête précédent
*@param list qui est une ArrayList des cours que le personnage suit.
*/
  public void retake(ArrayList<Cours> list)
  {
    interrogation.addAll(list);
  }

/*------------------------------------------------------------------------------------------------------------*/

/**
*Cette méthode permet de voir si la quête est terminée pour changer de quête ou continuer
*@param many ajoute du pourcentage de terminer quand le personnage a fait quelque chose
*/
  public abstract void successful(int many);

/**
*Cette méthode permet de gérer les rencontres entres PNJ et le personnage
*@param other qui le PNJ que le joueur va rencontrés
*/
  public abstract void meetOther(PNJ other);

/**
*Cette méthode permet de gérer les rencontres entres Persoonage1 et le personnage2
*@param other qui est l'autre personnages dans le jeu (aider - combat)
*/
  public abstract void meetOther(People other);

/**
*Cette méthode permet de gérer les évènement lorsque le joueur évolue sur la carte
*/
  public abstract void eventMaps();
}
