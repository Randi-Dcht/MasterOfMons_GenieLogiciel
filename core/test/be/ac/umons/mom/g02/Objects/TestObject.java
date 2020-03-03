package be.ac.umons.mom.g02.Objects;

import be.ac.umons.mom.g02.Enums.*;
import be.ac.umons.mom.g02.Objects.Characters.People;
import org.junit.jupiter.api.Assertions;
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
    Assertions.assertSame(l1.location(), Maps.Nimy,"same maps"); /*<= prob*/
  }

  @Test
  void SavingClassTest() //TODO : a continuer cette méthode quand celle-ci sera finie.
  {
      People people = new People("TestObject", Type.normal, Gender.Men , Difficulty.Easy);
      Saving save = new Saving();
  }
}
