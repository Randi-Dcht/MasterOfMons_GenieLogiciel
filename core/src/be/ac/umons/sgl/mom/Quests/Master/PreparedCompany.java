package be.ac.umons.sgl.mom.Quests.Master;

import be.ac.umons.sgl.mom.Enums.Bloc;
import be.ac.umons.sgl.mom.Enums.Difficulty;
import be.ac.umons.sgl.mom.Enums.Lesson;
import be.ac.umons.sgl.mom.Enums.Place;
import be.ac.umons.sgl.mom.Events.Notifications.Notification;
import be.ac.umons.sgl.mom.Objects.Characters.Mobile;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import be.ac.umons.sgl.mom.Objects.Items.*;
import be.ac.umons.sgl.mom.Quests.Under.Traineeship;
import be.ac.umons.sgl.mom.Quests.Under.UnderQuest;

import java.util.ArrayList;

/**
 *This class define the four year in the University of Mons with the goals.
 *@author Randy Dauchot (étudiant en Sciences informatique)
 */
public class PreparedCompany extends MasterQuest
{
    /*This tab is a list of the course of the people for this year*/
    final Lesson[] lesson ={/*code ici*/};
    /*This is the goals of this MasterQuest*/
    final UnderQuest[] underQuest = {new Traineeship(this)};


    /**
     * This constructor define a Master 1 of Umons
     * @param people who is the people goes to course
     * @param before who is the MasterQuest before them, (null or MasterQuest)
     */
    public PreparedCompany(People people, MasterQuest before, GraphicalSettings graphic, Difficulty difficulty)
    {
        super(before,people,Bloc.MA1,graphic,difficulty);
        ObligationLesson(lesson);
        addUnderQuest(underQuest);
    }


    /**
     * This method allows to define to next Quest
     */
    public void nextQuest()
    {
        newQuest(new FinishUniversity(people,this,graphic,difficulty));
    }


    /***/
    @Override
    public Place[] whatPlace()
    {
        return new Place[]{Place.Mons,Place.Nimy};
    }


    /**
     *This method allows to say the ask of this MasterQuest
     * @return a question who is a string
     */
    public String question()
    {
        return graphic.getStringFromId("answerMasterOne");
    }


    /***/
    @Override
    public ArrayList<Items> whatItem()
    {
        ArrayList<Items> list = new ArrayList<>();
        for (int i=0 ; i < 8; i++)
            list.add(new Pen());
        for (int i=0 ; i < 8; i++)
            list.add(new Gun());

        return list;
    }


    /***/
    @Override
    public ArrayList<Mobile> whatMobile()
    {
        return null;/*code ici*/
    }


    /**
     * This method allows to return the name of MasterQuest
     * @return name who is a string
     */
    public String getName()
    {
        return graphic.getStringFromId("nameMasterOne");
    }


    /***/
    @Override
    public void update(Notification notify)
    {
        super.update(notify);
        /*code ici*/
    }
}

