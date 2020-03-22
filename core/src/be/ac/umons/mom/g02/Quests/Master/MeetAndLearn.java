package be.ac.umons.mom.g02.Quests.Master;

import be.ac.umons.mom.g02.Enums.Bloc;
import be.ac.umons.mom.g02.Enums.Difficulty;
import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Enums.MobileType;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Objects.Items.*;
import be.ac.umons.mom.g02.Quests.Under.CheckStudy;
import be.ac.umons.mom.g02.Regulator.SuperviserNormally;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.Characters.SaoulMatePNJ;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Quests.Under.GoToPriorityLesson;
import be.ac.umons.mom.g02.Quests.Under.LookSoulMate;
import be.ac.umons.mom.g02.Quests.Under.UnderQuest;

import java.util.ArrayList;


/**
 *This class define a MasterQuest who is 'Meet and Learn' in the bachelor 3
 *@author Umons_Group_2_ComputerScience
 */
public class MeetAndLearn extends MasterQuest
{

    /**
     * This is the goals of this quest
     */
    final UnderQuest[] underQuest = {new LookSoulMate(this,25,people),new CheckStudy(this,25,people), new GoToPriorityLesson(this,50,people)};


    /**
     * This is a constructor of this Quest who define a quest in the bachelor 3
     * @param people who is the people who play the game
     * @param before who is the quest before this
     */
    public MeetAndLearn(People people, MasterQuest before, Difficulty difficulty)
    {
        super(before,people, Bloc.BA3,difficulty);
        addUnderQuest(underQuest);
    }


    /**
     * This method allows to switch with the after masterQuest
     */
    public void nextQuest()
    {
        newQuest(new PreparedCompany(people,this,difficulty));
    }


    /**
     *This method return the question of this Quest
     * The people must succeed this question to up the Quest
     */
    public String question()
    {
        return "answerMeetLearn";
    }


    /**
     * This method return the items for this quest
     * @return list of items
     */
    @Override
    protected void createListItems() throws Exception
    {
        listItems = new ArrayList<>();
        listItems.addAll(createListItems(new Class[]{Energizing.class, Flower.class, OldExam.class},new int[]{10,1,6}, new Maps[]{null,null,null}));

    }


    /**
     * This method return the mobile for this quest
     */
    @Override
    protected void createListMobiles() //TODO optimiser cela
    {
        listMobs = new ArrayList<>();
        MobileType[] type = MobileType.values();
        listMobs.add(new SaoulMatePNJ(getBloc(),MobileType.Lambda));
    }


    /**
     * This method allows to see the maps of the player interact with the other things
     * @return a list of the Maps
     */
    @Override
    public Maps[] getListMaps()
    {
        return new Maps[]{Maps.Nimy, Maps.Mons, Maps.Poly};
    }


    /**
     * This method return the name of this quest
     * @return name of this Quest
     */
    public String getName()
    {
        return SuperviserNormally.getGraphic().getStringFromId("nameMeetLearn");
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
