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
import be.ac.umons.sgl.mom.Quests.Quest;
import be.ac.umons.sgl.mom.Quests.Under.ChooseSubject;
import be.ac.umons.sgl.mom.Quests.Under.GoToPriorityLesson;
import be.ac.umons.sgl.mom.Quests.Under.Traineeship;
import be.ac.umons.sgl.mom.Quests.Under.UnderQuest;
import java.util.ArrayList;
import java.util.Random;

/**
 *This class define the four year in the University of Mons with the goals.
 *@author Maxime Renversez  (étudiant en Sciences informatique)
 */
public class PreparedCompany extends MasterQuest
{
    /*This tab is a list of the course of the people for this year*/
    final Lesson[] lesson ={Lesson.res2,Lesson.lecred,Lesson.proglog,Lesson.bda,Lesson.comp,Lesson.algbio,Lesson.sofev,Lesson.angl};
    /*This is the goals of this MasterQuest*/
    final UnderQuest[] underQuest = {/*new Traineeship(this),*/new GoToPriorityLesson(this,50,people),new ChooseSubject(this,people)};


    /**
     * This constructor define a Master 1 of Umons
     * @param people who is the people goes to course
     * @param before who is the MasterQuest before them, (null or MasterQuest)
     */
    public PreparedCompany(People people, MasterQuest before, GraphicalSettings graphic, Difficulty difficulty)
    {
        super(before,people,Bloc.MA1,graphic,difficulty);
        obligationLesson(lesson);
        addUnderQuest(underQuest);
    }

    /**
     * This method allows to define to next Quest
     */
    public void nextQuest()
    {
        newQuest(new FinishUniversity(people,this,graphic,difficulty));
    }

    /**
     * This method returns the list of the lesson
     * @return list of lesson
     */

    public Lesson[] getLesson()
    {
        return lesson;
    }

    /***/
    public Place[] whatPlace()
    {
        return new Place[]{Place.Mons,Place.Nimy,Place.MicrosoftCompany};
    }

    /**
     *This method allows to say the ask of this MasterQuest
     * @return a question who is a string
     */
    public String question()
    {
        return graphic.getStringFromId("answerMasterOne");
    }


    /**
     * This method allows us to know the different items for this quest
     * @return an arraylist of the items
     */
    public ArrayList<Items> whatItem()
    {
        ArrayList<Items> listofitems = new ArrayList<>();
        for (int i=0 ; i < 8; i++) {
            listofitems.add(new Pen());
            listofitems.add(new Gun());
        }
        for (int i=0;i<3;i++)
        {
            listofitems.add(new PaperHelp());
            listofitems.add(new Synthesis());
        }
        listofitems.add(new Sportswear());
        return listofitems;
    }


    /***/
    public ArrayList<Mobile> whatMobile()
    {
        ArrayList<Mobile> list = new ArrayList<>();
        return list;
    }


    @Override
    public double getProgress() {
        return 0;
    }

    @Override
    public double getAdvancement() {
        return 0;
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public Quest[] getSubQuests() {
        return new Quest[0];
    }

    /**
     * This method allows to return the name of MasterQuest
     * @return name who is a string
     */
    public String getName()
    {
        return graphic.getStringFromId("nameMasterOne");
    }

    @Override
    public int getTotalSubQuestsNumber() {
        return 0;
    }

    @Override
    public void addProgress(double many) {

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void update(Notification notify) {

    }


    /**
    @Override
    public void update(Notification notify)
    {
        super.update(notify);
        /*code ici
    }*/
}

