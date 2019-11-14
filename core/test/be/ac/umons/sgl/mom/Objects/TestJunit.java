package be.ac.umons.sgl.mom;

import org.junit.*;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.util.concurrent.TimeUnit;
import java.util.Timer;
import java.util.TimerTask;
import java.util.*;
/**
*Cette classe permet de créer les tets junit pour tester le code source du jeu
*@author Randy Dauchot (étudiant en Sciences informatique)
*/

public class TestJunit
{
  private People[] p = {new People("MasterOfMons",5,5,5)};
  private Objet[] o ={new Energizing(0,0)};
  private Rule rule = new Rule(1,p,o);

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

    assertTrue("take the lesson of level player",l1.take(levelPlayer));
    assertTrue("take the lesson of level player",l2.take(levelPlayer));
    assertFalse("take the lesson of level player",l3.take(levelPlayer));
    assertTrue("Obligatory of lesson",l1.obligatoryCourse());
    assertSame("same place",l1.location(),Place.Nimy);
  }

  /**
  *Cette méthode permet de tester la classe personnage
  */
  @Test
  public void PeopleObjectTest()
  {
    People people = new People("MasterOfMons",5,5,5);
    Battery[] b = {new Battery(0,0),new Battery(0,0),new Battery(0,0),new Battery(0,0),new Battery(0,0),new Battery(0,0)};
    Energizing e = new Energizing(0,0);
    for (int i = 0; i < 5 ; i++)
    {
      assertTrue("add not over full",people.pushObject(b[i]));
    }
    assertFalse("add over full",people.pushObject(b[5]));
    assertTrue("remove true object",people.removeObject(b[0]));
    assertFalse("remove false object",people.removeObject(e));
  }

  @Test
  public void energyPeopleTest()
  {
    double first = p[0].getEnergy();
    Timer timer = new Timer();
    timer.schedule(rule,0,100);
    try
    {
      TimeUnit.SECONDS.sleep(1);
    }
    catch (Exception e) {
    }
    double second = p[0].getEnergy();
    assertTrue("depency energy",first > second);

    p[0].changedState(1);
    try
    {
      TimeUnit.SECONDS.sleep(1);
    }
    catch (Exception e) {
    }
    first = p[0].getEnergy();
    assertTrue("add energy",second < first);
  }
}
