package be.ac.umons.sgl.mom.Objects;
import java.util.*;
/**
*Cette classe définit ce qu'est une quête de bachelier 1
*@author Randy Dauchot (étudiant en Sciences informatique)
*/

public class Quest1 extends Quest
{
  public final Lesson[] lesson ={Lesson.MI1,Lesson.MI2,Lesson.algo1,Lesson.algo2,Lesson.ftOrdi,Lesson.projet1};
  public final GoalsQuest[] goalsQuest = {new FollowLesson(),new GoToTest(),new MeetManyPeople(),new BattleForPlace()};
  public Quest1(People people)
  {
    super(null,1,null,people);
  }

  /**
  *Cette méthode permet de gérer les rencontres entres Persoonage1 et le personnage2
  *@param other qui est l'autre personnages dans le jeu (aider - combat)
  */
    public void meetOther(PNJ other)
    {
      eventMaps();
    }

    public GoalsQuest[] getSubQuests()
    {
        return goalsQuest;
    }

  /**
  *Cette méthode renvoie l'object de la quête
  */
    public String question()
    {
      return "Ta quête si tu l'accepte sera de te faire quelques amis et de participer à un mininum de X cours. Tu devras aussi partciper à tous tes examnens";
    }

/*----------------------------------------------------------------------------------------------------------------------------------------------------------*/
    public class FollowLesson implements GoalsQuest
    {
      public void evenActivity()
      {
      }
      public String getName()
      {
        return "x";
      }
    };
    public class GoToTest implements GoalsQuest
    {
      public void evenActivity()
      {
      }
      public String getName()
      {
        return "x";
      }
    };
    public class MeetManyPeople implements GoalsQuest
    {
      public int safeFriend = 0;
      public void evenActivity()
      {
      }
      public String getName()
      {
        return "x";
      }
    };
    public class BattleForPlace implements GoalsQuest
    {
      public void evenActivity()
      {
      }
      public String getName()
      {
        return "x";
      }
    };
}
