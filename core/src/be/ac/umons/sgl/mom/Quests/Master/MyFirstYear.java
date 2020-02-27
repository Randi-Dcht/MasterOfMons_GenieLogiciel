package be.ac.umons.sgl.mom.Quests.Master;

import be.ac.umons.sgl.mom.Enums.*;
import be.ac.umons.sgl.mom.Enums.Maps;
import be.ac.umons.sgl.mom.Events.Events;
import be.ac.umons.sgl.mom.Events.Notifications.Notification;
import be.ac.umons.sgl.mom.Events.SuperviserNormally;
import be.ac.umons.sgl.mom.Objects.Characters.Mobile;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import be.ac.umons.sgl.mom.Objects.Items.Energizing;
import be.ac.umons.sgl.mom.Objects.Items.Items;
import be.ac.umons.sgl.mom.Objects.Items.OldExam;
import be.ac.umons.sgl.mom.Objects.Items.TheKillBoot;
import be.ac.umons.sgl.mom.Quests.Under.BattleForPlace;
import be.ac.umons.sgl.mom.Quests.Under.GoToLesson;
import be.ac.umons.sgl.mom.Quests.Under.MeetManyPeople;
import be.ac.umons.sgl.mom.Quests.Under.UnderQuest;
import java.util.ArrayList;
import java.util.Random;

/**
 *This class define a MasterQuest who is 'My first Year' in the bachelor 1
 *@author Umons_Group_2_ComputerScience
 */
public class MyFirstYear extends MasterQuest
{

    /*This is the goals of this MasterQuest*/
    final UnderQuest[] underQuest = {new GoToLesson(this,33,people),new MeetManyPeople(this,34,people),new BattleForPlace(this,33,people)};


    /**
     * This is a constructor of this Quest who define a quest in the bachelor 1
     * @param people who is the people who play the game
     * @param before who is the quest before this
     */
    public MyFirstYear(People people, MasterQuest before, GraphicalSettings graphic, Difficulty difficulty)
    {
        super(before,people, Bloc.BA1,graphic,difficulty);
        addUnderQuest(underQuest);
    }


    /**
     * This method allows to define to next Quest
     */
    public void nextQuest()
    {
        newQuest(new SuccessfulYear(people,this,graphic,difficulty));
    }


    /**
     *This method allows to say the ask of this MasterQuest
     * @return a question who is a string
     */
    public String question()
    {
        return graphic.getStringFromId("answerMyFirstYear");
    }


    /**
     * This method return the items for this quest
     * @return list of items
     */
    @Override
    public ArrayList<Items> whatItem() //TODO optimiser cela
    {
        ArrayList<Items> list = new ArrayList<>();
        for (int i=0; i < 10; i++)
            list.add(new Energizing());
        for (int i=0 ; i < 6; i++)
            list.add(new OldExam());
        list.add(new TheKillBoot());

        return list;
    }


    /**
     * This method return the mobile for this quest
     * @return list of mobile
     */
    @Override
    public ArrayList<Mobile> whatMobile() //TODO optimiser cela
    {
        ArrayList<Mobile> list = new ArrayList<>();
        MobileType[] type = MobileType.values();
        for(int i=0; i < 30;i++)
            list.add(new Mobile("Student",getBloc(),type[new Random().nextInt(type.length)], Actions.Dialog));
        for(int i = 30; i < 40; i++)
            list.add(new Mobile("Fight",getBloc(),MobileType.Athletic, Actions.Attack));

        return list;
    }


    /***/
    @Override
    public Maps[] whatPlace()
    {
        return new Maps[]{Maps.Nimy, Maps.DeVinci, Maps.Mons};
    }


    /**
     * This method allows to return the name of MasterQuest
     * @return name who is a string
     */
    public String getName()
    {
        return graphic.getStringFromId("nameMyFirstYear");
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
