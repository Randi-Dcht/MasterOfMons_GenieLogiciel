package be.ac.umons.sgl.mom.Objects;

import be.ac.umons.sgl.mom.Enums.Bloc;
import be.ac.umons.sgl.mom.Enums.Difficulty;
import be.ac.umons.sgl.mom.Enums.MobileType;
import be.ac.umons.sgl.mom.Events.SuperviserNormally;
import be.ac.umons.sgl.mom.GraphicalObjects.*;
import be.ac.umons.sgl.mom.Enums.Type;
import be.ac.umons.sgl.mom.Objects.Characters.Mobile;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import com.badlogic.gdx.Gdx;
import java.util.Scanner;

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
  private static Mobile mobile = new Mobile("xxx", Bloc.BA2, MobileType.Athletic);

  public static People getPeople()
  {
    return people;
  }

  public static void newParty(String namePlayer, Type type, QuestShower graphical,GraphicalSettings gs) /*!va être supprimer!*/
  {
    SuperviserNormally sp = SuperviserNormally.getSupervisor();
    sp.newParty(namePlayer,type,gs, Difficulty.Easy);
    questShower = graphical;
    people = sp.getPeople();
    questShower.setQuest(people.getQuest()); /*<= problème ici voir pour le final TODO superviNor à changer*/
    save = sp.save;
    //testingDialog();
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

  private static void testingDialog()
  {//mettre sous forme langage humain !
    String memory = "Start";
    System.out.println(memory);
    while (!memory.equals("ESC") || !memory.equals("null"))
    {
      System.out.println(people.getDialog(memory));
      memory = new Scanner(System.in).nextLine();
      memory = mobile.getDialog(memory);
      System.out.println(memory);
    }
  }


}
