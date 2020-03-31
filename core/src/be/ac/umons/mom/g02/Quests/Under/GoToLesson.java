package be.ac.umons.mom.g02.Quests.Under;

import be.ac.umons.mom.g02.Enums.Places;
import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.ChangeDay;
import be.ac.umons.mom.g02.Events.Notifications.ChangeHour;
import be.ac.umons.mom.g02.Events.Notifications.EntryPlaces;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Objects.Course;
import be.ac.umons.mom.g02.Quests.Quest;
import java.util.ArrayList;


/**
 * This class define the goals to go to listen the course in the auditory
 * @author Umons_Group_2_ComputerScience_RandyDauchot
 */
public class GoToLesson extends UnderQuest
{

    /**
     * This is a list of the place when the people have already study
     */
    private ArrayList<Places> listPlaceMemory = new ArrayList<>();
    /**
     * This is a list of the course during this day
     */
    private ArrayList<Course> coursePeople    = new ArrayList<>();


    /**
     * This constructor define the quest to go to the lessons
     * @param quest  is the upper quest of this
     * @param max     is the max percent of progress
     * @param people is the people who play the quest
     */
    public GoToLesson(Quest quest, int max, People people)
    {
        super("GoToLesson",max,quest,people);
    }


    /**
     * This method allows to receive the notification of the game
     * @param notify is the notification in the game
     */
    @Override
    public void evenActivity(Notification notify)
    {
        if (notify.getEvents().equals(Events.EntryPlace) && notify.bufferNotEmpty())
            goToPlaceStudy(((EntryPlaces)notify).getBuffer());
        if (notify.getEvents().equals(Events.ChangeDay) && notify.bufferNotEmpty())
            checkPlanning(((ChangeDay)notify).getBuffer());
        if (notify.getEvents().equals(Events.ChangeHour) && notify.bufferNotEmpty() && ((ChangeHour)notify).getBuffer() >= 18)
            goToLesson();
    }


    /**
     * This method allows to check if the people when go to the place to study the lessons
     * @param place is the place of the people
     */
    private void goToPlaceStudy(Places place)
    {
        if (place.equals(Places.StudyRoom) && !listPlaceMemory.contains(place))
        {
            addProgress(7);
            listPlaceMemory.add(place);
        }
        if (place.equals(Places.StudyRoom) && listPlaceMemory.contains(place))
            addProgress(0.1);
    }


    /**
     * This method refresh all day the planning of the people
     * @param day is the day now in the game
     */
    private void checkPlanning(int day)
    {
        coursePeople = people.getPlanning().get(day);
    }


    /**
     * This method allows to check if the people go to the lesson during the first years
     * This method is called during the end of the day
     */
    private void goToLesson()
    {
        for (Course course : coursePeople)
        {
            if (course.isGo())
                addProgress(3);
            else
                addProgress(-1);
        }
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
