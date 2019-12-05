package be.ac.umons.sgl.mom.Objects;

import java.util.*;
import be.ac.umons.sgl.mom.GraphicalObjects.*;
import be.ac.umons.sgl.mom.Enums.Type;
import be.ac.umons.sgl.mom.Objects.Characters.Attack;
import be.ac.umons.sgl.mom.Objects.Characters.PNJ;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Objects.Items.Items;
import be.ac.umons.sgl.mom.Quests.Master.*;
import com.badlogic.gdx.Gdx;

/*------Commentaire à supprimer dans version final-------*/
/*-------------------Randy-------------------------------*/
/*--J'ai mis cette classe en static mais cela pourrait --*/
/*-------changer en fonction de ce que l'on veut --------*/
/*-------------------------------------------------------*/

/**
*Cette classe permet de surveiller le jeu en temps réelle et gère en fonction des règles.
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
  /*chaque 10 minutes*/
  private static double minute = 600;
  /*sauveguarde du jeux*/
  private static Saving save;
  /*temps du jeu*/
  private static  Schedule time;

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

  /**
   * Permet de créer une nouvelle partie du jeu.
   * Elle permet d'instancier les classes nécessaire
   * @param namePlayer qui est le pseudo du joueur
   * @param type qui est le type de personnage (fort,maigre,...)
   * @param graphical qui est l'affiche graphique du jeu
   * */
  public static void newParty(String namePlayer, Type type, QuestShower graphical,GraphicalSettings gs)
  {
    questShower = graphical;
    people = new People(namePlayer,type);
    MasterQuest mQ = new Bachelor1(people,null);
    people.newQuest(mQ);
    questShower.setQuest(mQ);
    time = new Schedule(9,1,8,2019);
    save = new Saving(people,namePlayer,gs);
  }

  /**
   * Permet de dire que le joueur change de quêtes et appelle l'interface graphique
   * */
  public static void changedQuest()
  {
    if(questShower != null) //permet lors des tests de ne pas intancier de classes graphique.
    {
      Gdx.app.postRunnable(() -> questShower.setQuest(people.getQuest()));
      save.Signal();
    }
  }

  /**
   * Cette méthode permet de gérer l'attque entre un joueur et un PNj dans le jeu de base
   * */
  public static void meetTogether(PNJ pnj)
  {}

  public static void attack(Attack attacker , Attack victim)
  {}

  /**
   * Cette méthode permet d'appeler régulièrement la méthode énergie du joueur
   * */ //TODO: guillaume appeler cela
  public static void callMethod(double dt)
  {
    if(people != null) /*cette méthode permet diminuer l'énergie du joueur*/
        people.energy(dt); //pour le joueur

    if(objet != null) /*cette méthode permet de diminuer la vie de l'objet*/
    {
      for (Items o : objet){o.make(dt);}//pour chaques objets
    }
    minute = minute - dt;
    if(minute <= 0) /*permet de faire une sauvguarde de temps en temps automatiquement*/
    {
      save.Signal();
      minute = 600;
    }

    time.updateSecond(0);
    //System.out.println("Temps jeu: " + time); /*<= pour tester le temps du jeu*/
  }

}
