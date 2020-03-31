package be.ac.umons.mom.g02.Quests.Under;

import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Objects.Characters.SaoulMatePNJ;
import be.ac.umons.mom.g02.Objects.FrameTime;
import be.ac.umons.mom.g02.Other.Date;
import be.ac.umons.mom.g02.Quests.Quest;
import be.ac.umons.mom.g02.Regulator.Supervisor;


/**
 * This class define the goals if the people have a soul mate in the game
 * @author Umons_Group_2_ComputerScience_RandyDauchot
 */
public class LookSoulMate extends UnderQuest implements FrameTime
{
    /**
     * This is the first date of the couple
     */
    private Date coupleFirstDate;


    /**
     * This constructor define the class of check if player have soul mate
     * @param master is the master class of this
     * @param max    is the maximum percent of this class
     * @param people is the player when play the underQuest
     */
    public LookSoulMate(Quest master, int max, People people)
    {
        super("LookSoulMate",max,master,people);
    }


    /**
     * This method allows to receive the notification of the game
     * @param notify is the notification in the game
     */
    @Override
    public void evenActivity(Notification notify)
    {
        if (notify.getEvents().equals(Events.MeetOther) && notify.bufferNotEmpty())
            meetSoulMate((Mobile)notify.getBuffer());
    }


    /**
     * This method check if the player meet the soul mate in the game
     * @param mob is the mobile to meet
     */
    private void meetSoulMate(Mobile mob)
    {
        if (mob.getClass().equals(SaoulMatePNJ.class))
            addProgress(6);
    }


    /**
     * This method add in the memory the date of the first date of couple
     */
    private void coupleIn()//TODO
    {
        coupleFirstDate = Supervisor.getSupervisor().getTime().getDate();
    }


    /**
     * This method add the progress with the advance of time of the couple
     */
    private void timeForSoulMate()
    {

    }


    /**
     * This method allows to give the time between two frames
     * This method allows to refresh the class every call
     * @param dt us the time between two frame
     */
    @Override
    public void update(double dt)
    {
        timeForSoulMate();
    }


    /**
     * This method return the list of the under quest of this
     * @return list of under quest
     */
    public Quest[] getSubQuests()
    {
        return new Quest[]{};
    }


    /**
     * This method return the number of recursion on under this
     * @return number recursion
     */
    public int getTotalSubQuestsNumber()
    {
        return 0;
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
