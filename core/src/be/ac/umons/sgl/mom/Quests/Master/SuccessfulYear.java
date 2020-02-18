package be.ac.umons.sgl.mom.Quests.Master;

import be.ac.umons.sgl.mom.Enums.*;
import be.ac.umons.sgl.mom.Events.Notifications.Notification;
import be.ac.umons.sgl.mom.Objects.Characters.FightPNJ;
import be.ac.umons.sgl.mom.Objects.Characters.Mobile;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Objects.Characters.StudPNJ;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import be.ac.umons.sgl.mom.Objects.Items.*;
import be.ac.umons.sgl.mom.Quests.Under.FreeTimeMons;
import be.ac.umons.sgl.mom.Quests.Under.HelpMe;
import be.ac.umons.sgl.mom.Quests.Under.SuccesfulYear;
import be.ac.umons.sgl.mom.Quests.Under.UnderQuest;

import java.util.ArrayList;
import java.util.Random;

/**
 *This class define a MasterQuest who is 'SuccessfulYear' in the bachelor 2
 *@author Randy Dauchot (étudiant en Sciences informatique)
 */

public class SuccessfulYear extends MasterQuest
{
    /*This list is the lesson during this Quest*/
    final Lesson[] lesson = {Lesson.nbComplexe,Lesson.calculus2,Lesson.reseau,Lesson.projet2,Lesson.OS,Lesson.anglais,Lesson.opti};
    /*This is the goals of this Quest*/
    final UnderQuest[] underQuest = {new HelpMe(this,34),new FreeTimeMons(this,33)/*, new GoToLesson(this,25)*/, new SuccesfulYear(this,33)};


    /**
     * This is a constructor of this Quest who define a quest in the bachelor 2
     * @param people who is the people who play the game
     * @param before who is the quest before this
     */
    public SuccessfulYear(People people, MasterQuest before, GraphicalSettings graphic, Difficulty difficulty)
    {
        super(before,people,Bloc.BA2,graphic,difficulty);
        ObligationLesson(lesson);
        addUnderQuest(underQuest);
    }


    /**
     * This is a next Quest after this if the level is up
     */
    public void nextQuest()
    {
        newQuest(new MeetAndLearn(people,this,graphic,difficulty));
    }


    /**
     *This method return the question of this Quest
     * The people must succeed this question to up the Quest
     */
    public String question()
    {
        return graphic.getStringFromId("answerSuccessFulYear");
        //return "Ta quête si tu l'accepte sera de te faire quelques amis et de participer à un mininum de X cours. Tu devras aussi partciper à tous tes examnens";
    }


    /**
     * This method return the items for this quest
     * @return list of items
     */
    @Override
    public ArrayList<Items> whatItem()
    {
        ArrayList<Items> list = new ArrayList<>();
        for (int i=0; i < 6; i++)
            list.add(new Energizing());
        for (int i=0 ; i < 3; i++)
            list.add(new OldExam());
        for (int i=0 ; i < 3; i++)
            list.add(new PaperHelp());
        for (int i=0 ; i < 5; i++)
            list.add(new TheKillBoot());

        return list;
    }


    /**
     * This method return the mobile for this quest
     * @return list of mobile
     */
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


    /***/
    @Override
    public Place[] whatPlace()
    {
        return new Place[]{Place.Poly,Place.Mons,Place.Nimy};
    }


    /**
     * This method return the name of this quest
     * @return name of this Quest
     */
    public String getName()
    {
        return graphic.getStringFromId("nameSuccessFulYear");
    }


    /**
     * This method analyse the notification who receive and notify underQuest
     * @param notify who is a notification with events.
     */
    @Override
    public void update(Notification notify)
    {
        super.update(notify);
    }
}
