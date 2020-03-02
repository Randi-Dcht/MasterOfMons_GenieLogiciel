package be.ac.umons.mom.g02.Quests.Master;

import be.ac.umons.mom.g02.Enums.Bloc;
import be.ac.umons.mom.g02.Enums.Difficulty;
import be.ac.umons.mom.g02.Enums.Lesson;
import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Objects.Items.Gun;
import be.ac.umons.mom.g02.Objects.Items.Items;
import be.ac.umons.mom.g02.Objects.Items.Pen;
import be.ac.umons.mom.g02.Quests.Under.GoToLesson;
import be.ac.umons.mom.g02.Quests.Under.UnderQuest;
import be.ac.umons.mom.g02.Quests.Under.WriteMemory;

import java.util.ArrayList;

/**
 *This class define the five year in the University of Mons with the goals.
 *@author Randy Dauchot (étudiant en Sciences informatique)
 */
public class FinishUniversity extends MasterQuest
{
    /*This tab is a list of the course of the people for this year*/
    final Lesson[] lesson ={Lesson.mang,Lesson.mem,Lesson.stage,Lesson.ss,Lesson.ps};
    /*This is the goals of this MasterQuest*/
    final UnderQuest[] underQuest = {new GoToLesson(this,5,people),new WriteMemory(this,people)}; //il manquait le joueur
    //TODO a quoi correspond le nb dans gotolesson ?

    /**
     * This constructor define a Master 1 of Umons
     * @param people who is the people goes to course
     * @param before who is the MasterQuest before them, (null or MasterQuest)
     */
    public FinishUniversity(People people, MasterQuest before, GraphicalSettings grahic, Difficulty difficulty)
    {
        super(before,people, Bloc.MA2,grahic,difficulty);
        addUnderQuest(underQuest);
    }


    /**
     * This method allows to define to next Quest
     */
    public void nextQuest(){/*END*/}


    /*public Lesson[] getLesson() {
        return new Lesson[0];
    }*/


    /**
     *This method allows to say the ask of this MasterQuest
     * @return a question who is a string
     */
    public String question()
    {
        return graphic.getStringFromId("answerMasterSecond");
    }

    @Override
    protected void createListItems() {
        // TODO
    }

    @Override
    protected void createListMobiles() {
        // TODO

    }

    @Override
    public Maps[] getListMaps() {
        // TODO
        return new Maps[0];
    }


    /**
     * @return*/
    public Maps[] whatPlace()
    {
        return new Maps[]{Maps.Nimy,Maps.Mons};
    }


    /***/
    public ArrayList<Items> whatItem()
    {
        ArrayList<Items> list = new ArrayList<>();
        for (int i=0 ; i < 7; i++)
            list.add(new Pen());
        for (int i=0 ; i < 2; i++)
            list.add(new Gun());

        return list;
    }


    /***/
    public ArrayList<Mobile> whatMobile()
    {
        ArrayList<Mobile> list = new ArrayList<>();
        return list;
    }


    /**
     * This method allows to return the name of MasterQuest
     * @return name who is a string
     */
    public String getName()
    {
        return graphic.getStringFromId("nameMasterSecond");
    }

    @Override
    public void update(Notification notify) {

    }


    /***/
    /*@Override
    public void update(Notification notify)
    {
        super.update(notify);
        /*code ici
    }*/
}
