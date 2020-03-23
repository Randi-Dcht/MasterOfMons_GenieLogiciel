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
import be.ac.umons.mom.g02.Quests.Under.LowEnergizing;
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
import be.ac.umons.mom.g02.Regulator.Supervisor;
import java.util.ArrayList;
import java.util.Random;

/**
 *This class define a MasterQuest who is 'My first Year' in the bachelor 1
 *@author Umons_Group_2_ComputerScience_RandyDauchot
 */
public class MyFirstYear extends MasterQuest
{

    /**
     * This is the goals of this MasterQuest
     */
    final UnderQuest[] underQuest = {new LowEnergizing(this,33,people),new GoToLesson(this,33,people),new MeetManyPeople(this,34,people)};


    /**
     * This is a constructor of this Quest who define a quest in the bachelor 1
     * @param people who is the people who play the game
     * @param before who is the quest before this
     */
    public MyFirstYear(People people, MasterQuest before, Difficulty difficulty)
    {
        super(before,people, Bloc.BA1,difficulty);
        addUnderQuest(underQuest);
        Supervisor.getEvent().add(this, Events.MeetOther,Events.Dialog,Events.Attack,Events.UseItems,Events.AddFriend,Events.Dead);
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
                new Maps[]{null,null,null,Maps.GrandAmphi}));

        for (Mobile m : listMobs)//TODO delete
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
        return SuperviserNormally.getGraphic().getStringFromId("nameMyFirstYear");
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
