package be.ac.umons.mom.g02.Quests.Master;

import be.ac.umons.mom.g02.Enums.*;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Objects.Items.Energizing;
import be.ac.umons.mom.g02.Objects.Items.OldExam;
import be.ac.umons.mom.g02.Objects.Items.TheKillBoot;
import be.ac.umons.mom.g02.Quests.Under.BattleForPlace;
import be.ac.umons.mom.g02.Quests.Under.GoToLesson;
import be.ac.umons.mom.g02.Quests.Under.MeetManyPeople;
import be.ac.umons.mom.g02.Quests.Under.UnderQuest;

import java.util.ArrayList;
import java.util.Random;

/**
 *This class define a MasterQuest who is 'My first Year' in the bachelor 1
 *@author Umons_Group_2_ComputerScience
 */
public class MyFirstYear extends MasterQuest
{

    /**
     * This is the goals of this MasterQuest
     */
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
        return "answerMyFirstYear";
    }


    /**
     * This method return the items for this quest
     */
    @Override
    public void createListItems() //TODO optimiser cela
    {
        listItems = new ArrayList<>();
        for (int i=0; i < 10; i++)
            listItems.add(new Energizing());
        for (int i=0 ; i < 6; i++)
            listItems.add(new OldExam());
        listItems.add(new TheKillBoot());
        //createListItems(new Class[]{Energizing.class, TheKillBoot.class, OldExam.class},new int[]{10,1,6});
    }


    /**
     * This method return the mobile for this quest
     */
    @Override
    protected void createListMobiles() //TODO optimiser cela
    {
        listMobs = new ArrayList<>();
        MobileType[] type = MobileType.values();
        for(int i=0; i < 30;i++)
            listMobs.add(new Mobile("Student",getBloc(),type[new Random().nextInt(type.length)], Actions.Dialog,NameDialog.Lambda));
        for(int i = 30; i < 40; i++)
            listMobs.add(new Mobile("Fight",getBloc(),MobileType.Athletic, Actions.Attack,NameDialog.Lambda));
        for (int i = 0; i < 35;i++)
        {
            Mobile mb = new Mobile("Fight",getBloc(),MobileType.Athletic, Actions.Attack,NameDialog.Lambda);
            mb.setMaps(Maps.GrandAmphi);
            listMobs.add(mb);
        }
    }


    /**
     * This method allows to see the maps of the player interact with the other things
     * @return a list of the Maps
     */
    @Override
    public Maps[] getListMaps()
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

<<<<<<< HEAD


=======
>>>>>>> 44ad2dd012c401de94f8617f4e097b651acccb79

    /**
     * This method analyse the notification who receive and notify underQuest
     * @param notify who is a notification with events.
     */
    @Override
    public void update(Notification notify)
    {

        //super.update(notify);
    }
}
