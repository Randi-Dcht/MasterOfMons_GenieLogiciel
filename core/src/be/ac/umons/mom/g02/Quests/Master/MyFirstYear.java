package be.ac.umons.mom.g02.Quests.Master;

import be.ac.umons.mom.g02.Enums.Actions;
import be.ac.umons.mom.g02.Enums.Bloc;
import be.ac.umons.mom.g02.Enums.Difficulty;
import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Enums.MobileType;
import be.ac.umons.mom.g02.Enums.NameDialog;
import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Quests.Under.BattleForPlace;
import be.ac.umons.mom.g02.Quests.Under.GoTo;
import be.ac.umons.mom.g02.Quests.Under.GoToLesson;
import be.ac.umons.mom.g02.Quests.Under.MeetManyPeople;
import be.ac.umons.mom.g02.Quests.Under.UnderQuest;
import be.ac.umons.mom.g02.Regulator.SuperviserNormally;
import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.Items.Energizing;
import be.ac.umons.mom.g02.Objects.Items.OldExam;
import be.ac.umons.mom.g02.Objects.Items.Pen;
import be.ac.umons.mom.g02.Objects.Items.TheKillBoot;
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
    final UnderQuest[] underQuest = {new GoTo(this,10,people),new GoToLesson(this,23,people),new MeetManyPeople(this,34,people),new BattleForPlace(this,33,people)};


    /**
     * This is a constructor of this Quest who define a quest in the bachelor 1
     * @param people who is the people who play the game
     * @param before who is the quest before this
     */
    public MyFirstYear(People people, MasterQuest before, Difficulty difficulty)
    {
        super(before,people, Bloc.BA1,difficulty);
        addUnderQuest(underQuest);
        SuperviserNormally.getSupervisor().getEvent().add(this, Events.MeetOther,Events.Dialog,Events.Attack,Events.UseItems,Events.AddFriend,Events.Dead);
    }


    /**
     * This method allows to define to next Quest
     */
    public void nextQuest()
    {
        newQuest(new SuccessfulYear(people,this,difficulty));
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
    public void createListItems() throws Exception
    {
        listItems = new ArrayList<>();
        listItems.addAll(createListItems(new Class[]{Energizing.class, TheKillBoot.class, OldExam.class},new int[]{10,1,6}));
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
        //TODO delete this:
        for (Mobile m : listMobs)
            m.addObject(new Pen(),new Pen(),new Pen());
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
        return SuperviserNormally.getSupervisor().getGraphic().getStringFromId("nameMyFirstYear");
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
