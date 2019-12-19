package be.ac.umons.sgl.mom.Quests.Master;

import be.ac.umons.sgl.mom.Enums.Lesson;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Quests.Under.GoToPriorityLesson;
import be.ac.umons.sgl.mom.Quests.Under.LookSoulMate;
import be.ac.umons.sgl.mom.Quests.Under.UnderQuest;

/**
*Cette classe définit ce qu'est une quête de bachelier 3
*@author Randy Dauchot (étudiant en Sciences informatique)
*/

public class Bachelor3 extends MasterQuest
{
  /*lesson que le personnage va suivre lors de ce bachelier*/
  final Lesson[] lesson = {};
/*Les sous quêtes que doit réaliser le personnage pour réusir celle-ci*/
  final UnderQuest[] underQuest = {new LookSoulMate(this,50), new GoToPriorityLesson(this,50)};

  public Bachelor3(People people, MasterQuest before)
  {
    super(before,people);
    ObligationLesson(lesson);
    addUnderQuest(underQuest);
  }

  public void nextQuest()
  {
    newQuest(new Master1(people,this));
  }

  /**
  *Cette méthode renvoie l'object de la quête
  */
    public String question()
    {
      return "Ta quête si tu l'accepte sera de te faire quelques amis et de participer à un mininum de X cours. Tu devras aussi partciper à tous tes examnens";
    }

  public String getName()
  {
    return "Bachelier3";
  }
}
