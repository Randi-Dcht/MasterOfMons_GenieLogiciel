package be.ac.umons.sgl.mom.Objects;

import be.ac.umons.sgl.mom.Events.SuperviserNormally;
import be.ac.umons.sgl.mom.GraphicalObjects.*;
import be.ac.umons.sgl.mom.Enums.Type;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import com.badlogic.gdx.Gdx;

/*------Commentaire à supprimer dans version final-------*/
/*-------------------Randy-------------------------------*/
/*--J'ai mis cette classe en static qui sera remplacer --*/
/*-------SuperviserNormally dans prochaine fois  --------*/
/*-------------------------------------------------------*/


public class Supervisor /*! va être supprimer !*/
{
  private static People people;
  private static QuestShower questShower;
  private static Saving save;

  public static People getPeople()
  {
    return people;
  }

  public static void newParty(String namePlayer, Type type, QuestShower graphical,GraphicalSettings gs) /*!va être supprimer!*/
  {
    SuperviserNormally sp = SuperviserNormally.getSupervisor();
    sp.newParty(namePlayer,type,gs);
    questShower = graphical;
    people = sp.getPeople();
    questShower.setQuest(people.getQuest()); /*<= problème ici voir pour le final TODO superviNor à changer*/
    save = sp.save;
  }


  public static void changedQuest()
  {
    if(questShower != null)
    {
      Gdx.app.postRunnable(() -> questShower.setQuest(people.getQuest()));
      save.signal();
      System.out.println(people.getQuest().question());
    }
  }


}
