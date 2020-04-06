package be.ac.umons.mom.g02.Quests.Master;

import be.ac.umons.mom.g02.Enums.*;
import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Quests.Under.*;
import be.ac.umons.mom.g02.Regulator.SuperviserNormally;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Objects.Items.Energizing;
import be.ac.umons.mom.g02.Objects.Items.OldExam;
import be.ac.umons.mom.g02.Objects.Items.PaperHelp;
import be.ac.umons.mom.g02.Objects.Items.TheKillBoot;
import be.ac.umons.mom.g02.Regulator.Supervisor;

import java.util.ArrayList;

/**
 *This class define a MasterQuest who is 'SuccessfulYear' in the bachelor 2
 *@author Umons_Group_2_ComputerScience
 */

public class SuccessfulYear extends MasterQuest
{

    /**
     * This is the goals of this Quest
     */
    final UnderQuest[] underQuest = {new HelpMe(this,24,people),new FreeTimeMons(this,23,people),new BattleForPlace(this,30,people),new FollowLesson(this,23,people)};


    /**
     * This is a constructor of this Quest who define a quest in the bachelor 2
     * @param people who is the people who play the game
     * @param before who is the quest before this
     */
    public SuccessfulYear(People people, MasterQuest before, Difficulty difficulty)
    {
        super(before,people, Bloc.BA2,difficulty);
        addUnderQuest(underQuest);
        Supervisor.getEvent().add(this, Events.MeetOther,Events.Answer,Events.Help,Events.PlaceInMons,Events.ChangeHour,Events.UseItems,Events.Attack,Events.Dead);
    }


    /**
     * This is a next Quest after this if the level is up
     */
    public void nextQuest()
    {
        newQuest(new MeetAndLearn(people,this,difficulty));
    }


    /**
     *This method return the question of this Quest
     * The people must succeed this question to up the Quest
     */
    public String question()
    {
        return "answerSuccessFulYear";
    }


    /**
     * This method return the items for this quest
     */
    @Override
    public void createListItems() throws Exception
    {
        listItems = new ArrayList<>();
        listItems.addAll(createListItems(new Class[]{Energizing.class, TheKillBoot.class, OldExam.class,PaperHelp.class},new int[]{6,1,6,3}, new Maps[]{null,null,null,null}));
    }


    /**
     * This method return the mobile for this quest
     */
    @Override
    protected void createListMobiles() throws Exception
    {
        listMobs = new ArrayList<>();
        listMobs.addAll(createRdMobile(new int[]{10,25,25,30,20},
                new MobileType[]{MobileType.Lambda,MobileType.Loser,MobileType.Strong,MobileType.Loser,MobileType.Strong},
                new Actions[]{Actions.Dialog,Actions.Dialog,Actions.Attack,Actions.Attack,Actions.Attack},
                new NameDialog[]{NameDialog.Lambda,NameDialog.Student,NameDialog.Lambda,NameDialog.Lambda,NameDialog.Lambda},
                new Maps[]{null,null,Maps.Mons,Maps.GrandAmphi,Maps.Warocque},
                true));
    }


    /**
     * This method allows to see the maps of the player interact with the other things
     * @return a list of the Maps
     */
    @Override
    public Maps[] getListMaps()
    {
        return new Maps[]{Maps.Poly, Maps.Mons, Maps.Nimy};
    }


    /**
     * This method return the name of this quest
     * @return name of this Quest
     */
    public String getName()
    {
        return GraphicalSettings.getStringFromId("nameSuccessFulYear");
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
