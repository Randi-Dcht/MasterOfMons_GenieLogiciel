package be.ac.umons.sgl.mom.Objects;
import java.util.*;
/**
*Cette classe définit ce qu'est une quête de bachelier 1
*@author Randy Dauchot (étudiant en Sciences informatique)
*/

public class Quest1 extends Quest
{
  /*lesson que le personnage va suivre lors de ce bachelier*/

  /*Les sous quêtes que doit réaliser le personnage pour réusir celle-ci*/
//  public final GoalsQuest[] goalsQuest = {new FollowLesson(),new GoToTest(),new MeetManyPeople(),new BattleForPlace()};

  public Quest1(People people)
  {
    super(null,1,people);
    Lesson[] lesson ={Lesson.MI1,Lesson.MI2,Lesson.algo1,Lesson.algo2,Lesson.ftOrdi,Lesson.projet1};
    ObligationLesson(lesson);
  }

  /**
  *Cette méthode permet de gérer les rencontres entres Persoonage1 et le personnage2
  *@param other qui est l'autre personnages dans le jeu (aider - combat)
  */
    public void meetOther(PNJ other)
    {
      eventMaps();
    }

/**
*Cette méthode permet de retourner les sous quêtes (objectif) de cette quête
*@return goalsQuest qui sont les sous quêtes.
*/
//    public Quest[] getSubQuests()
//    {
//        return underQuest;
//    }

  /**
  *Cette méthode renvoie l'object de la quête
  */
    public String question()
    {
      return "Ta quête si tu l'accepte sera de te faire quelques amis et de participer à un mininum de X cours. Tu devras aussi partciper à tous tes examnens";
    }

/*----------------------------------------------------------------------------------------------------------------------------------------------------------*/
    public class FollowLesson
    {
      private double advancement = 0;
      public void evenActivity()
      {
      }
      public String getName()
      {
        return "Go to follow the lesson";
      }
      public double getProgress()
      {
        return (advancement/100);
      }
    };
    public class GoToTest 
    {
      private double advancement = 0;
      public void evenActivity()
      {
      }
      public String getName()
      {
        return "Go to the tests";
      }
      public double getProgress()
      {
        return (advancement/100);
      }
    };
    public class MeetManyPeople
    {
      private int safeFriend = 0;
      private double advancement = 0;
      public void evenActivity()
      {
      }
      public String getName()
      {
        return "go to meet other people";
      }
      public double getProgress()
      {
        return (advancement/100);
      }
    };
    public class BattleForPlace
    {
      private double advancement = 0;
      public void evenActivity()
      {
      }
      public String getName()
      {
        return "battle in Umons to have a place";
      }
      public double getProgress()
      {
        return (advancement/100);
      }
    };
}
