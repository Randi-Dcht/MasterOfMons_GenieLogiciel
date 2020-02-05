package be.ac.umons.sgl.mom.Objects;

import be.ac.umons.sgl.mom.Enums.Lesson;
import be.ac.umons.sgl.mom.Enums.Place;
import be.ac.umons.sgl.mom.Enums.Type;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
*Cette classe permet de créer les tets junit pour tester le code source du jeu
*@author Randy Dauchot (étudiant en Sciences informatique)
*/

class TestObject
{

 /**
 *Cette méthode permet de tester les méthodes de la classe Lesson
 */
  @Test
  void TestOfLesson()
  {
    Lesson l1 = Lesson.MI1;
    Lesson l2 = Lesson.ecopol;
    Lesson l3 = Lesson.statistique;
    int levelPlayer = 2;

    assertTrue(l1.take(levelPlayer),"take the lesson of level player");
    assertTrue(l2.take(levelPlayer),"take the lesson of level player");
    assertFalse(l3.take(levelPlayer),"take the lesson of level player");
    assertTrue(l1.obligatoryCourse(),"Obligatory of lesson");
    assertSame(l1.location(),Place.Nimy,"same place"); /*<= prob*/
  }

  @Test
  void SavingClassTest() //TODO : a continuer cette méthode quand celle-ci sera finie.
  {
      People people = new People("TestObject",Type.normal);
      Saving save = new Saving(people,"TestSaving",null);
  }
/*
    @Test
    void ScheduleTest()
    {
        Schedule time = new Schedule(9,1,8,2019);
        assertTrue(1==1);
        try
        {
            Method mtd = time.getClass().getDeclaredMethod("?",null);
            mtd.setAccessible(true);
            Object rtr = mtd.invoke(time);
        }
        catch (Exception e)
        {}


        MaClasse maClasse = new MaClasse();
    try {
      Method method = maClasse.getClass().getDeclaredMethod("maMethodePrivee", null);
      method.setAccessible(true);
      Object retour = method.invoke(maClasse);
      Logger.getLogger(TestPrivateMethodInvoke.class.getName())
        .log(Level.INFO, "Valeur de retour = " + retour);
    } catch (Exception ex) {
      Logger.getLogger(TestPrivateMethodInvoke.class.getName())
        .log(Level.SEVERE, null, ex);
    }

    }*/
}
