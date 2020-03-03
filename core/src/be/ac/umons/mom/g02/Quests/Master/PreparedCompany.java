package be.ac.umons.mom.g02.Quests.Master;

import be.ac.umons.mom.g02.Enums.*;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Objects.Items.*;
import be.ac.umons.mom.g02.Quests.Quest;
import be.ac.umons.mom.g02.Quests.Under.ChooseSubject;
import be.ac.umons.mom.g02.Quests.Under.GoToPriorityLesson;
import be.ac.umons.mom.g02.Quests.Under.UnderQuest;

import java.util.ArrayList;
import java.util.Random;

/**
 *This class define the four year in the University of Mons with the goals.
 *@author Maxime Renversez  (étudiant en Sciences informatique)
 */
public class PreparedCompany extends MasterQuest
{
    /*This tab is a list of the course of the people for this year*/
   //final Lesson[] lesson ={Lesson.res2,Lesson.lecred,Lesson.proglog,Lesson.bda,Lesson.comp,Lesson.algbio,Lesson.sofev,Lesson.angl};
    /*This is the goals of this MasterQuest*/
    final UnderQuest[] underQuest = {/*new Traineeship(this),*/new GoToPriorityLesson(this,50,people),new ChooseSubject(this,people)};


    /**
     * This constructor define a Master 1 of Umons
     * @param people who is the people goes to course
     * @param before who is the MasterQuest before them, (null or MasterQuest)
     */
    public PreparedCompany(People people, MasterQuest before, GraphicalSettings graphic, Difficulty difficulty)
    {
        super(before,people, Bloc.MA1,graphic,difficulty);
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
     *This method allows to say the ask of this MasterQuest
     * @return a question who is a string
     */
    public String question()
    {
        return graphic.getStringFromId("answerMasterOne");
    }

    @Override
    protected void createListItems() {
        listItems = new ArrayList<>();
        for (int i=0 ; i < 8; i++) {
            listItems.add(new Pen());
            listItems.add(new Gun());
        }
        for (int i=0;i<3;i++)
        {
            listItems.add(new PaperHelp());
            listItems.add(new Synthesis());
        }
        listItems.add(new Sportswear());
    }

    @Override
    protected void createListMobiles() {
        listMobs = new ArrayList<>();
        MobileType[] type = MobileType.values();
        for(int i=0; i < 30;i++)
            listMobs.add(new Mobile("Student",getBloc(),type[new Random().nextInt(type.length)], Actions.Dialog,NameDialog.Lambda));
        for(int i = 30; i < 50; i++)
            listMobs.add(new Mobile("Fight",getBloc(),MobileType.Athletic, Actions.Attack,NameDialog.Lambda));
    }

    /**
     * This method return all of the place for this quest
     * @return the different place for this quest
     */
    @Override
    public Maps[] getListMaps() {
        return new Maps[]{Maps.Nimy, Maps.DeVinci, Maps.Mons};
    }


    /**
     * This method allows us to know the different items for this quest
     * @return an arraylist of the items
     */



    @Override
    public double getProgress() {
        return percent;
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
}

