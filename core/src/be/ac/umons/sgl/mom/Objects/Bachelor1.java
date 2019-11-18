package be.ac.umons.sgl.mom.Objects;
import be.ac.umons.sgl.mom.Objects.Under.*;
import java.util.*;

/**
*Cette classe définit ce qu'est une quête de bachelier 1
*@author Randy Dauchot (étudiant en Sciences informatique)
*/

public class Bachelor1 extends MasterQuest
{
  /*lesson que le personnage va suivre lors de ce bachelier*/
  final Lesson[] lesson ={Lesson.MI1,Lesson.MI2,Lesson.algo1,Lesson.algo2,Lesson.ftOrdi,Lesson.projet1};
/*Les sous quêtes que doit réaliser le personnage pour réusir celle-ci*/
  final UnderQuest[] underQuest = {new FollowLesson(this),new GoToTest(this),new MeetManyPeople(this),new BattleForPlace(this)};

  public Bachelor1(People people,MasterQuest before)
  {
    super(before,people);
    ObligationLesson(lesson);
    addUnderQuest(underQuest);
  }

  public void nextQuest()
  {
    newQuest(new Bachelor2(people,this));
  }

  /**
  *Cette méthode permet de gérer les rencontres entres Persoonage1 et le personnage2
  *@param other qui est l'autre personnages dans le jeu (aider - combat)
  */
    public void meetOther(PNJ other)
    {
    }



  /**
  *Cette méthode renvoie l'object de la quête
  */
    public String question()
    {
      return "Ta quête si tu l'accepte sera de te faire quelques amis et de participer à un mininum de X cours. Tu devras aussi partciper à tous tes examnens";
    }
}
