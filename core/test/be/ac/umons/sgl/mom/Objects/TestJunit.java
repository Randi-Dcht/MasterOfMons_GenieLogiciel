package be.ac.umons.sgl.mom.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.util.*;
import be.ac.umons.sgl.mom.GraphicalObjects.*;
import be.ac.umons.sgl.mom.Enums.Lesson;
import be.ac.umons.sgl.mom.Enums.Place;
import be.ac.umons.sgl.mom.Enums.State;
import be.ac.umons.sgl.mom.Enums.Type;
import be.ac.umons.sgl.mom.Quests.Under.*;
import be.ac.umons.sgl.mom.Quests.Master.*;
import be.ac.umons.sgl.mom.Quests.Quest;

/**
*Cette classe permet de créer les tets junit pour tester le code source du jeu
*@author Randy Dauchot (étudiant en Sciences informatique)
*/

public class TestJunit
{

 /**
 *Cette méthode permet de tester les méthodes de la classe Lesson
 */
  @Test
  public void TestOfLesson()
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

  /**
  *Cette méthode permet de tester la classe personnage
  */
  @Test
  public void PeopleObjectTest()
  {
    People people = new People("MasterOfMons",Type.normal);
    Battery[] b = {new Battery(0,0),new Battery(0,0),new Battery(0,0),new Battery(0,0),new Battery(0,0),new Battery(0,0)};
    Energizing e = new Energizing(0,0);
    for (int i = 0; i < 5 ; i++)
    {
      assertTrue(people.pushObject(b[i]),"add not over full");
    }
    assertFalse(people.pushObject(b[5]),"add over full");
    assertTrue(people.removeObject(b[0]),"remove true object");
    assertFalse(people.removeObject(e),"remove false object");
  }

  @Test
  public void energyPeopleTest()
  {
    People people = new People("Junit",Type.normal);
    double first = people.getEnergy();
    for (int i = 0; i < 100 ; i++ ){people.energy();}
    double second = people.getEnergy();
    assertTrue(first > second,"depency energy");

    people.changedState(State.sleep);
    for (int i = 0; i < 100 ; i++ ){people.energy();}
    first = people.getEnergy();
    assertTrue(second < first,"add energy");
  }

  @Test
  public void TestnextQuest()
  {
    People p = new People("Tesst",Type.normal);
    MasterQuest mq = new Bachelor1(p,null);
    mq.nextQuest();
    assertNull(mq.getChildren(),"quest after is null");
    assertNull(mq.getParent(),"quest before is null");
    mq.addProgress(100);
    assertNotNull(mq.getChildren(),"quest1 after isn't null");
    assertNotNull(p.getQuest().getParent(),"quest2 before isn't null");
    assertNull(p.getQuest().getChildren(),"quest2 after is null");
  }
}
