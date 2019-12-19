package be.ac.umons.sgl.mom.Quests.Master;

import be.ac.umons.sgl.mom.Enums.Lesson;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Quests.Under.UnderQuest;

/**
 *This class define the five year in the University of Mons with the goals.
 *@author Randy Dauchot (étudiant en Sciences informatique)
 */

public class Master2 extends MasterQuest
{
    /*This tab is a list of the course of the people for this year*/
    final Lesson[] lesson ={};
    /*This is the goals of this MasterQuest*/
    final UnderQuest[] underQuest = {};

    /**
     * This contructor define a Bachelor 1 of Umons
     * @param people who is the people goes to course
     * @param before who is the MasterQuest before them, (null or MasterQuest)
     */
    public Master2(People people, MasterQuest before)
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
    }


    /**
     *This method allows to say the ask of this MasterQuest
     * @return a question who is a string
     */
    public String question()
    {
        return "...";
    }

    /**
     * This method allows to return the name of MasterQuest
     * @return name who is a string
     */
    public String getName()
    {
        return "Master2";
    }
}
