package be.ac.umons.mom.g02.Quests.Under;

import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Enums.Places;
import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.EntryPlaces;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Other.Date;
import be.ac.umons.mom.g02.Quests.Quest;
import be.ac.umons.mom.g02.Regulator.Supervisor;


/**
 * This class define the goals of player goes to study lessons
 * @author Umons_Group_2_ComputerScience_RandyDauchot
 */
public class CheckStudy extends UnderQuest
{

    /**
     * This is a memory of the date when the player go to study
     */
    private Date entryDate;


    /**
     * This constructor define the class of check study
     * @param master is the master class of this
     * @param max    is the maximum percent of this class
     * @param people is the player when play the underQuest
     */
    public CheckStudy(Quest master, double max, People people)
    {
        super("CheckStudy", max, master,people);
    }


    /**
     * This method allows to receive the notification of the game
     * @param notify is the notification in the game
     */
    @Override
    public void evenActivity(Notification notify)
    {
        if (notify.getEvents().equals(Events.EntryPlace) && notify.bufferNotEmpty())
        {
            studyAtKot();
            goToStudy(((EntryPlaces)notify).getBuffer());
        }
        if (notify.getEvents().equals(Events.PlaceInMons))
            studyAtKot();
    }


    /**
     * This method checks if the player go to study in this kot
     */
    private void studyAtKot()
    {
        if (people.getMaps().equals(Maps.Kot))
        {
            if (people.getPlace() != null && people.getPlace().equals(Places.StudyRoom))
                addProgress(0.3);
        }
    }


    /**
     * This method check the time of the player study ses course in the specific place different of kot
     * @param place is the place of the player is now
     */
    private void goToStudy(Places place)
    {
        if (entryDate == null && place.equals(Places.StudyRoom))
            entryDate = Supervisor.getSupervisor().getTime().getDate();
        if (entryDate != null && !place.equals(Places.StudyRoom) && !people.getMaps().equals(Maps.Kot))
            calculusTime(Supervisor.getSupervisor().getTime().getDate());
    }


    /**
     * This method calculus the progress with the time, people reste in place study
     * @param outDate is the date of out place
     */
    private void calculusTime(Date outDate)
    {
        int timeIn  = entryDate.getMin()*60 + entryDate.getHour()*60*60;
        int timeOut = outDate.getMin()*60 + outDate.getHour()*60*60;
        int differenceTime = timeOut - timeIn;
        addProgress(differenceTime*0.01);
        entryDate = null;
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
        return "CkStudyExplain";
    }
}
