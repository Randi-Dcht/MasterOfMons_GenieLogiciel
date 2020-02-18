package be.ac.umons.sgl.mom.Quests.Master;

import be.ac.umons.sgl.mom.Enums.*;
import be.ac.umons.sgl.mom.Events.Notifications.Notification;
import be.ac.umons.sgl.mom.Objects.Characters.FightPNJ;
import be.ac.umons.sgl.mom.Objects.Characters.Mobile;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Objects.Characters.StudPNJ;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import be.ac.umons.sgl.mom.Objects.Items.Gun;
import be.ac.umons.sgl.mom.Objects.Items.Items;
import be.ac.umons.sgl.mom.Objects.Items.Pen;
import be.ac.umons.sgl.mom.Quests.Under.GoToLastLesson;
import be.ac.umons.sgl.mom.Quests.Under.UnderQuest;
import be.ac.umons.sgl.mom.Quests.Under.WriteMemory;

import java.util.ArrayList;
import java.util.Random;

/**
 *This class define the five year in the University of Mons with the goals.
 *@author Randy Dauchot (étudiant en Sciences informatique)
 */
public class FinishUniversity extends MasterQuest
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
    public FinishUniversity(People people, MasterQuest before, GraphicalSettings grahic, Difficulty difficulty)
    {
        super(before,people,Bloc.MA2,grahic,difficulty);
        ObligationLesson(lesson);
        addUnderQuest(underQuest);
    }


    /**
     * This method allows to define to next Quest
     */
    public void nextQuest(){/*END*/}


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
        return new Place[]{Place.Nimy,Place.Mons};
    }


    /***/
    @Override
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
    @Override
    public ArrayList<Mobile> whatMobile()
    {
        ArrayList<Mobile> list = new ArrayList<>();
        MobileType[] type = MobileType.values();
        for(int i=0; i < 30;i++)
            list.add(new StudPNJ(getBloc(), type[new Random().nextInt(type.length)]));
        for(int i = 30; i < 40; i++)
            list.add(new FightPNJ(getBloc(), MobileType.Athletic));

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


    /***/
    @Override
    public void update(Notification notify)
    {
        super.update(notify);
        /*code ici*/
    }
}
