package be.ac.umons.sgl.mom.Quests.Master;

import be.ac.umons.sgl.mom.Enums.Lesson;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Quests.Under.GoToPriorityLesson;
import be.ac.umons.sgl.mom.Quests.Under.LookSoulMate;
import be.ac.umons.sgl.mom.Quests.Under.UnderQuest;

/**
 *This class define the masterQuest of the bachelor 3
 *@author Randy Dauchot (étudiant en Sciences informatique Umons)
 */

public class Bachelor3 extends MasterQuest
{
    /*This is the lessons to follow*/
    final Lesson[] lesson = {};
    /*this is the goals of this quest*/
    final UnderQuest[] underQuest = {new LookSoulMate(this,50), new GoToPriorityLesson(this,50)};

    public Bachelor3(People people, MasterQuest before)
    {
        super(before,people);
        ObligationLesson(lesson);
        addUnderQuest(underQuest);
    }

    /**
     * This method allows to switch with the after masterQuest
     */
    public void nextQuest()
    {
        newQuest(new Master1(people,this));
    }

    /**
     *This is a question of this quest
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
