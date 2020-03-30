package be.ac.umons.mom.g02.Quests.Master;

import be.ac.umons.mom.g02.Enums.*;
import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Objects.Items.Energizing;
import be.ac.umons.mom.g02.Objects.Items.OldExam;
import be.ac.umons.mom.g02.Objects.Items.TheKillBoot;
import be.ac.umons.mom.g02.Quests.Under.*;
import be.ac.umons.mom.g02.Regulator.Supervisor;

import java.util.ArrayList;

/**
 *This class define a MasterQuest who is 'My first Year' in the bachelor 1
 *@author Umons_Group_2_ComputerScience_RandyDauchot
 */
public class MyFirstYear extends MasterQuest
{

    /**
     * This is the goals of this MasterQuest
     */
    final UnderQuest[] underQuest = {new LowEnergizing(this,28,people),new GoToLesson(this,28,people),new MeetManyPeople(this,29,people),new ReadInformation(this,15,people)};


    /**
     * This is a constructor of this Quest who define a quest in the bachelor 1
     * @param people who is the people who play the game
     * @param before who is the quest before this
     */
    public MyFirstYear(People people, MasterQuest before, Difficulty difficulty)
    {
        super(before,people, Bloc.BA1,difficulty);
        addUnderQuest(underQuest);
        Supervisor.getEvent().add(this, Events.MeetOther,Events.Dialog,Events.Attack,Events.UseItems,Events.LowSomething,Events.EntryPlace,Events.ChangeHour,Events.ChangeDay,Events.AddFriend,Events.Dead,Events.OtherInformation);
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
        listItems.addAll(createListItems(new Class[]{Energizing.class, TheKillBoot.class, OldExam.class},new int[]{10,1,6}, new Maps[]{null,null,null}));
    }


    /**
     * This method return the mobile for this quest
     */
    @Override
    protected void createListMobiles() throws Exception
    {
        listMobs = new ArrayList<>();
        listMobs.addAll(createRdMobile(new int[]{10,10,25,45},
                new MobileType[]{MobileType.Lambda,MobileType.Athletic,MobileType.Loser,MobileType.Strong},
                new Actions[]{Actions.Dialog,Actions.Attack,Actions.Dialog,Actions.Attack},
                new NameDialog[]{NameDialog.Lambda,NameDialog.Lambda,NameDialog.Student,NameDialog.Lambda},
                new Maps[]{null,null,null,Maps.GrandAmphi},
                true));
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
        return GraphicalSettings.getStringFromId("nameMyFirstYear");
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
