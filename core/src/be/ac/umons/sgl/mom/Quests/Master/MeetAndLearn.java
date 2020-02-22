package be.ac.umons.sgl.mom.Quests.Master;

import be.ac.umons.sgl.mom.Enums.Bloc;
import be.ac.umons.sgl.mom.Enums.Difficulty;
import be.ac.umons.sgl.mom.Enums.Lesson;
import be.ac.umons.sgl.mom.Enums.MobileType;
import be.ac.umons.sgl.mom.Enums.Place;
import be.ac.umons.sgl.mom.Events.Notifications.Notification;
import be.ac.umons.sgl.mom.Objects.Characters.Mobile;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Objects.Characters.SaoulMatePNJ;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import be.ac.umons.sgl.mom.Objects.Items.Flower;
import be.ac.umons.sgl.mom.Objects.Items.Gun;
import be.ac.umons.sgl.mom.Objects.Items.Items;
import be.ac.umons.sgl.mom.Objects.Items.OldExam;
import be.ac.umons.sgl.mom.Objects.Items.TheKillBoot;
import be.ac.umons.sgl.mom.Quests.Under.GoToPriorityLesson;
import be.ac.umons.sgl.mom.Quests.Under.LookSoulMate;
import be.ac.umons.sgl.mom.Quests.Under.UnderQuest;
import java.util.ArrayList;


/**
 *This class define a MasterQuest who is 'Meet and Learn' in the bachelor 3
 *@author Randy Dauchot (étudiant en Sciences informatique Umons)
 */
public class MeetAndLearn extends MasterQuest
{

    /*this is the goals of this quest*/
    final UnderQuest[] underQuest = {new LookSoulMate(this,50,people), new GoToPriorityLesson(this,50,people)};


    /**
     * This is a constructor of this Quest who define a quest in the bachelor 3
     * @param people who is the people who play the game
     * @param before who is the quest before this
     */
    public MeetAndLearn(People people, MasterQuest before, GraphicalSettings graphic, Difficulty difficulty)
    {
        super(before,people,Bloc.BA3,graphic,difficulty);
        //ObligationLesson(lesson);
        addUnderQuest(underQuest);
    }


    /**
     * This method allows to switch with the after masterQuest
     */
    public void nextQuest()
    {
        /*newQuest(new PreparedCompany(people,this,graphic,difficulty));*/ //TODO raccorder
    }


    /**
     *This method return the question of this Quest
     * The people must succeed this question to up the Quest
     */
    public String question()
    {
        return graphic.getStringFromId("answerMeetLearn");
    }


    /**
     * This method return the items for this quest
     * @return list of items
     */
    @Override
    public ArrayList<Items> whatItem()
    {
        ArrayList<Items> list = new ArrayList<>();
        for (int i=0; i < 4; i++)
            list.add(new Flower());
        for (int i=0 ; i < 6; i++)
            list.add(new OldExam());
        list.add(new Gun());
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

        list.add(new SaoulMatePNJ(getBloc(),MobileType.Lambda));

        return list;
    }


    /***/
    @Override
    public Place[] whatPlace()
    {
        return new Place[]{Place.Nimy,Place.Mons,Place.Poly};
    }


    /**
     * This method return the name of this quest
     * @return name of this Quest
     */
    public String getName()
    {
        return graphic.getStringFromId("nameMeetLearn");
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
