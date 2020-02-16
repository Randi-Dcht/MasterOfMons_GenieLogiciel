package be.ac.umons.sgl.mom.Quests.Master;

import be.ac.umons.sgl.mom.Enums.Bloc;
import be.ac.umons.sgl.mom.Enums.Difficulty;
import be.ac.umons.sgl.mom.Enums.Lesson;
import be.ac.umons.sgl.mom.Enums.Place;
import be.ac.umons.sgl.mom.Events.Notifications.Notification;
import be.ac.umons.sgl.mom.Objects.Characters.Mobile;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import be.ac.umons.sgl.mom.Objects.Items.Items;
import be.ac.umons.sgl.mom.Quests.Under.GoToLastLesson;
import be.ac.umons.sgl.mom.Quests.Under.UnderQuest;
import be.ac.umons.sgl.mom.Quests.Under.WriteMemory;

/**
 *This class define the five year in the University of Mons with the goals.
 *@author Randy Dauchot (étudiant en Sciences informatique)
 */
public class Master2 extends MasterQuest
{
    /*This tab is a list of the course of the people for this year*/
    final Lesson[] lesson ={/*code ici*/};
    /*This is the goals of this MasterQuest*/
    final UnderQuest[] underQuest = {new GoToLastLesson(this),new WriteMemory(this)};


    /**
     * This constructor define a Master 1 of Umons
     * @param people who is the people goes to course
     * @param before who is the MasterQuest before them, (null or MasterQuest)
     */
    public Master2(People people, MasterQuest before, GraphicalSettings grahic, Difficulty difficulty)
    {
        super(before,people,Bloc.MA2,grahic,difficulty);
        ObligationLesson(lesson);
        addUnderQuest(underQuest);
    }


    /**
     * This method allows to define to next Quest
     */
    public void nextQuest()
    {
        /*code ici*/
    }


    /**
     *This method allows to say the ask of this MasterQuest
     * @return a question who is a string
     */
    public String question()
    {
        return graphic.getStringFromId("answerMasterSecond");
    }


    /***/
    @Override
    public Place[] whatPlace()
    {
        return null;
    }


    /***/
    @Override
    public Items[] whatItem() {
        return null;/*code ici*/
    }


    /***/
    @Override
    public Mobile[] whatMobile()
    {
        return null;/*code ici*/
    }


    /**
     * This method allows to return the name of MasterQuest
     * @return name who is a string
     */
    public String getName()
    {
        return graphic.getStringFromId("nameMasterSecond");
    }


    /***/
    @Override
    public void update(Notification notify)
    {
        super.update(notify);
        /*code ici*/
    }
}
