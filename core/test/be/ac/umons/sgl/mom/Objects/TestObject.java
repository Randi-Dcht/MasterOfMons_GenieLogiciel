package be.ac.umons.sgl.mom.Objects;

import be.ac.umons.sgl.mom.Enums.*;
import be.ac.umons.sgl.mom.Events.Notifications.ChangeQuest;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Other.Date;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
*Cette classe permet de créer les tets junit pour tester le code source du jeu
*@author Randy Dauchot (étudiant en Sciences informatique)
*/

class TestObject
{

 /**
 *This method allows to test to method of the enum class Lesson
 */
  @Test
  void TestOfLesson()
  {
    Lesson l1 = Lesson.MI1;
    Lesson l2 = Lesson.ecopol;
    Lesson l3 = Lesson.statistique;
    int levelPlayer = 2;

    //assertTrue(l1.take(levelPlayer),"take the lesson of level player");
    //assertTrue(l2.take(levelPlayer),"take the lesson of level player");
    //assertFalse(l3.take(levelPlayer),"take the lesson of level player");
    assertTrue(l1.obligatoryCourse(),"Obligatory of lesson");
    assertSame(l1.location(),Place.Nimy,"same place"); /*<= prob*/
  }

  @Test
  void SavingClassTest() //TODO : a continuer cette méthode quand celle-ci sera finie.
  {
      People people = new People("TestObject",Type.normal, Gender.Men ,Difficulty.Easy);
      Saving save = new Saving(people,"TestSaving");
  }

    @Test
    void TimeGameTest()
    {
        TimeGame tg = new TimeGame(new Date(15,9,2019,8,0));
        tg.update(new ChangeQuest());
        assertEquals(1, tg.getValueTest()[0]);
        for(int i = 0; i <= 60; i++)
            tg.update(new ChangeQuest());
        System.out.println(tg.getValueTest()[1]);
        //assertEquals(9, tg.getValueTest()[1]);
    }
}
