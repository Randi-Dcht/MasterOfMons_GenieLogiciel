package be.ac.umons.mom.g02.Extensions.Dual.Logic.Quest;

import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Quests.Master.MasterQuest;


/**
 * This class define the more cases of the people in the city
 */
public class MoreCasesMons extends DualUnderQuest
{

    /**
     * This constructor define the dual of the battle between two people
     * @param people     is the people of this Quest
     * @param master is the difficulty of the game
     */
    public MoreCasesMons(People people,MasterQuest master)
    {
        super("MoreCasesMons",master,people);
    }


    /**
     * This method is called when the action occurs
     *
     * @param notify is the notification of the game
     */
    @Override
    public void evenActivity(Notification notify)
    {
    }


    public void callMe(int cb)
    {
        if (cb < 0)
            addProgress(-0.05);
        else
            addProgress(0.08);
    }


    /**
     * Explain the goal of this underQuest to succeed this or not
     * @return the explication of underQuest
     */
    @Override
    public String explainGoal()
    {
        return "NONE";
    }
}