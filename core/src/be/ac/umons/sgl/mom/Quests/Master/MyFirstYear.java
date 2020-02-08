package be.ac.umons.sgl.mom.Quests.Master;

import be.ac.umons.sgl.mom.Enums.Bloc;
import be.ac.umons.sgl.mom.Enums.Lesson;
import be.ac.umons.sgl.mom.Events.Notifications.Notification;
import be.ac.umons.sgl.mom.Objects.Characters.Mobile;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Objects.Items.Items;
import be.ac.umons.sgl.mom.Quests.Under.BattleForPlace;
import be.ac.umons.sgl.mom.Quests.Under.GoToLesson;
import be.ac.umons.sgl.mom.Quests.Under.MeetManyPeople;
import be.ac.umons.sgl.mom.Quests.Under.UnderQuest;

/**
 *This class define a MasterQuest who is 'My first Year' in the bachelor 1
 *@author Randy Dauchot (étudiant en Sciences informatique Umons)
 */

public class MyFirstYear extends MasterQuest
{
    /*This tab is a list of the course of the people for this year*/
    final Lesson[] lesson ={Lesson.MI1,Lesson.MI2,Lesson.algo1,Lesson.algo2,Lesson.ftOrdi,Lesson.projet1};
    /*This is the goals of this MasterQuest*/
    final UnderQuest[] underQuest = {new GoToLesson(this,33),new MeetManyPeople(this,34),new BattleForPlace(this,33)};


    /**
     * This is a constructor of this Quest who define a quest in the bachelor 1
     * @param people who is the people who play the game
     * @param before who is the quest before this
     */
    public MyFirstYear(People people, MasterQuest before)
    {
        super(before,people, Bloc.BA1);
        ObligationLesson(lesson);
        addUnderQuest(underQuest);
    }


    /**
     * This method allows to define to next Quest
     */
    public void nextQuest()
    {
        newQuest(new SuccessfulYear(people,this));
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
     * This method return the items for this quest
     * @return list of items
     */
    @Override
    public Items[] whatItem()
    {
        return new Items[0];
    }


    /**
     * This method return the mobile for this quest
     * @return list of mobile
     */
    @Override
    public Mobile[] whatMobile()
    {
        return new Mobile[0];
    }


    /**
     * This method allows to return the name of MasterQuest
     * @return name who is a string
     */
    public String getName()
    {
        return "MyFirstYear";
    }


    /**
     * This method analyse the notification who receive and notify underQuest
     * @param notify who is a notification with events.
     */
    @Override
    public void update(Notification notify)
    {
    }
}
