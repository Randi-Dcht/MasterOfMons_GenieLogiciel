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
    //TODO replace this
  }

  @Test
  void SavingClassTest()
  {
      People people = new People("TestObject", Type.normal, Gender.Men , Difficulty.Easy);
      Saving save = new Saving();
  }
}
