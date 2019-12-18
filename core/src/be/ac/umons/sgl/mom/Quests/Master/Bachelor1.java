package be.ac.umons.sgl.mom.Quests.Master;

import be.ac.umons.sgl.mom.Enums.Lesson;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Quests.Under.BattleForPlace;
import be.ac.umons.sgl.mom.Quests.Under.GoToLesson;
import be.ac.umons.sgl.mom.Quests.Under.MeetManyPeople;
import be.ac.umons.sgl.mom.Quests.Under.UnderQuest;

/**
 *This class define the fist year in the University of Mons with the goals.
 *@author Randy Dauchot (étudiant en Sciences informatique)
 */

public class Bachelor1 extends MasterQuest
{
    /*This tab is a list of the course of the people for this year*/
    final Lesson[] lesson ={Lesson.MI1,Lesson.MI2,Lesson.algo1,Lesson.algo2,Lesson.ftOrdi,Lesson.projet1};
    /*This is the goals of this MasterQuest*/
    final UnderQuest[] underQuest = {new GoToLesson(this,33),new MeetManyPeople(this,34),new BattleForPlace(this,33)};

    /**
     * This contructor define a Bachelor 1 of Umons
     * @param people who is the people goes to course
     * @param before who is the MasterQuest before them, (null or MasterQuest)
     */
    public Bachelor1(People people, MasterQuest before)
    {
        super(before,people);
        ObligationLesson(lesson);
        addUnderQuest(underQuest);
    }

    /**
     * This method allows to define to next Quest
     */
    public void nextQuest()
    {
        newQuest(new Bachelor2(people,this));
    }


    /**
     *This method allows to say the ask of this MasterQuest
     * @return a question who is a string
     */
    public String question()
    {
        return "Ta quête si tu l'accepte sera de te faire quelques amis et de participer à un mininum de X cours. Tu devras aussi partciper à tous tes examnens";
    }

    /**
     * This method allows to return the name of MasterQuest
     * @return name who is a string
     */
    public String getName()
    {
        return "Bachelor1";
    }
}
