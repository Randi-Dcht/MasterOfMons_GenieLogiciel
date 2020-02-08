package be.ac.umons.sgl.mom.Objects;

import java.util.*;
import be.ac.umons.sgl.mom.Events.SuperviserNormally;
import be.ac.umons.sgl.mom.GraphicalObjects.*;
import be.ac.umons.sgl.mom.Enums.Type;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Objects.Items.Items;
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
  private static QuestShower questShower;
  private static double minute = 600;
  private static Saving save;
  private static TimeGame time;

  public static People getPeople()
  {
    return people;
  }

  public static void newParty(String namePlayer, Type type, QuestShower graphical) /*!va être supprimer!*/
  {
    SuperviserNormally sp = SuperviserNormally.getSupervisor();
    sp.newParty(namePlayer,type);
    questShower = graphical;
    people = sp.getPeople();
    questShower.setQuest(people.getQuest()); /*<= problème ici voir pour le final TODO superviNor à changer*/
    save = sp.save;
    time = sp.getTime();
  }


  public static void changedQuest()
  {
    if(questShower != null)
    {
      Gdx.app.postRunnable(() -> questShower.setQuest(people.getQuest()));
      save.signal();
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
      save.signal();
      minute = 600;
    }
    time.updateSecond(0.3);
  }

}
