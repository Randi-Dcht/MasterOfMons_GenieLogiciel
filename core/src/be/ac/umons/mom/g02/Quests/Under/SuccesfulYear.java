package be.ac.umons.mom.g02.Quests.Under;

import be.ac.umons.mom.g02.Enums.Places;
import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.EntryPlaces;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Objects.Course;
import be.ac.umons.mom.g02.Quests.Quest;
import be.ac.umons.mom.g02.Regulator.Supervisor;


/**
 * This class define the goals if the player succeed the all course in this year
 * @author Umons_Group_2_ComputerScience_RandyDauchot
 */
public class SuccesfulYear extends UnderQuest
{
    /***/
    private int oldSuccess;


    /**
     * This constructor define the class of check succeed full year
     * @param master is the master class of this
     * @param max    is the maximum percent of this class
     * @param people is the player when play the underQuest
     */
    public SuccesfulYear(Quest master, int max, People people)
    {
        super("SuccesfulYear",max,master, people);
        oldSuccess = people.getLesson().size();
    }


    /**
     * This method allows to receive the notification of the game
     * @param notify is the notification in the game
     */
    @Override
    public void evenActivity(Notification notify)
    {
        if (notify.getEvents().equals(Events.ChangeDay))
            checkingCourse();

        if (notify.getEvents().equals(Events.EntryPlace))
        {
            goToStudy(((EntryPlaces)notify).getBuffer());
            goToLearn(((EntryPlaces)notify).getBuffer());
        }
    }


    /***/
    private void checkingCourse()
    {
        if (oldSuccess != people.getLesson().size())
        {
            oldSuccess = people.getLesson().size();
            addProgress(5);
        }
    }


    /**
     * This method check if the people go to study in the specific place
     */
    private void goToStudy(Places place)
    {
        addProgress(0.05);
    }


    /**
     * This method allows to see if the player go follow the lesson in the specific room of University
     */
    private void goToLearn(Places place)
    {
        addProgress(0.08);
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
        return "SuccessExplain";
    }
}
