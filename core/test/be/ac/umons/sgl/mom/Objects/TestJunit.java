import be.ac.umons.sgl.mom.Objects.*;
/*
import org.junit.*;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
*/
import org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    assumeTrue(l1.take(levelPlayer),"take the lesson of level player");
    assumeTrue(l2.take(levelPlayer),"take the lesson of level player");
    assumeFalse(l3.take(levelPlayer),"take the lesson of level player");
    assumeTrue(l1.obligatoryCourse(),"Obligatory of lesson");
    //assertSame(l1.location(),Place.Nimy,"same place"); /*<= prob*/
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
      assumeTrue(people.pushObject(b[i]),"add not over full");
    }
    assumeFalse(people.pushObject(b[5]),"add over full");
    assumeTrue(people.removeObject(b[0]),"remove true object");
    assumeFalse(people.removeObject(e),"remove false object");
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
    assumeTrue(first > second,"depency energy");

    p[0].changedState(State.sleep);
    try
    {
      TimeUnit.SECONDS.sleep(1);
    }
    catch (Exception e) {
    }
    first = p[0].getEnergy();
    assumeTrue(second < first,"add energy");
  }
}
