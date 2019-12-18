package be.ac.umons.sgl.mom.Quests.Master;

import be.ac.umons.sgl.mom.Enums.Lesson;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Quests.Under.FreeHeadInMons;
import be.ac.umons.sgl.mom.Quests.Under.HelpMe;
import be.ac.umons.sgl.mom.Quests.Under.SuccesfulYear;
import be.ac.umons.sgl.mom.Quests.Under.UnderQuest;

/**
*Cette classe définit ce qu'est une quête de bachelier 2
*@author Randy Dauchot (étudiant en Sciences informatique)
*/

public class Bachelor2 extends MasterQuest
{
  /*lesson que le personnage va suivre lors de ce bachelier*/
  final Lesson[] lesson = {};
/*Les sous quêtes que doit réaliser le personnage pour réusir celle-ci*/
  final UnderQuest[] underQuest = {new HelpMe(this,34),new FreeHeadInMons(this,33)/*, new GoToLesson(this,25)*/, new SuccesfulYear(this,33)};

  public Bachelor2(People people, MasterQuest before)
  {
    super(before,people);
    ObligationLesson(lesson);
    addUnderQuest(underQuest);
  }

  public void nextQuest()
  {
    newQuest(new Bachelor3(people,this));
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
    return "Bachelier2";
  }
}
