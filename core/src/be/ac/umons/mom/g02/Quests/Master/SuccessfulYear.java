package be.ac.umons.mom.g02.Quests.Master;

import be.ac.umons.mom.g02.Enums.Bloc;
import be.ac.umons.mom.g02.Enums.Difficulty;
import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Enums.MobileType;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Regulator.SuperviserNormally;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Objects.Items.Energizing;
import be.ac.umons.mom.g02.Objects.Items.OldExam;
import be.ac.umons.mom.g02.Objects.Items.PaperHelp;
import be.ac.umons.mom.g02.Objects.Items.TheKillBoot;
import be.ac.umons.mom.g02.Quests.Under.FreeTimeMons;
import be.ac.umons.mom.g02.Quests.Under.HelpMe;
import be.ac.umons.mom.g02.Quests.Under.SuccesfulYear;
import be.ac.umons.mom.g02.Quests.Under.UnderQuest;

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
    final UnderQuest[] underQuest = {new HelpMe(this,34,people),new FreeTimeMons(this,33,people)/*, new GoToLesson(this,25)*/, new SuccesfulYear(this,33,people)};


    /**
     * This is a constructor of this Quest who define a quest in the bachelor 2
     * @param people who is the people who play the game
     * @param before who is the quest before this
     */
    public SuccessfulYear(People people, MasterQuest before, Difficulty difficulty)
    {
        super(before,people, Bloc.BA2,difficulty);
        addUnderQuest(underQuest);
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
    protected void createListMobiles() //TODO optimiser cela
    {
        listMobs = new ArrayList<>();
        MobileType[] type = MobileType.values();
        /*for(int i=0; i < 30;i++)
            list.add(new StudPNJ(getBloc(), type[new Random().nextInt(type.length)]));
        for(int i = 30; i < 40; i++)
            list.add(new FightPNJ(getBloc(), MobileType.Athletic));*/
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
        return SuperviserNormally.getGraphic().getStringFromId("nameSuccessFulYear");
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
