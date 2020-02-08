package be.ac.umons.sgl.mom.Quests.Master;

import be.ac.umons.sgl.mom.Enums.Bloc;
import be.ac.umons.sgl.mom.Enums.Lesson;
import be.ac.umons.sgl.mom.Events.Notifications.Notification;
import be.ac.umons.sgl.mom.Objects.Characters.Mobile;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Objects.Items.Items;
import be.ac.umons.sgl.mom.Quests.Under.GoToPriorityLesson;
import be.ac.umons.sgl.mom.Quests.Under.LookSoulMate;
import be.ac.umons.sgl.mom.Quests.Under.UnderQuest;

/**
 *This class define a MasterQuest who is 'Meet and Learn' in the bachelor 3
 *@author Randy Dauchot (étudiant en Sciences informatique Umons)
 */

public class MeetAndLearn extends MasterQuest
{
    /*This is the lessons to follow*/
    final Lesson[] lesson = {};
    /*this is the goals of this quest*/
    final UnderQuest[] underQuest = {new LookSoulMate(this,50), new GoToPriorityLesson(this,50)};


    /**
     * This is a constructor of this Quest who define a quest in the bachelor 3
     * @param people who is the people who play the game
     * @param before who is the quest before this
     */
    public MeetAndLearn(People people, MasterQuest before)
    {
        super(before,people,Bloc.BA3);
        ObligationLesson(lesson);
        addUnderQuest(underQuest);
    }


    /**
     * This method allows to switch with the after masterQuest
     */
    public void nextQuest()
    {
        newQuest(new Master1(people,this));
    }


    /**
     *This method return the question of this Quest
     * The people must succeed this question to up the Quest
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
     * This method return the name of this quest
     * @return name of this Quest
     */
    public String getName()
    {
        return "Meet & Learn ";
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
