package be.ac.umons.mom.g02.Quests.Under;

import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.PlaceInMons;
import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Objects.Course;
import be.ac.umons.mom.g02.Quests.Quest;
import be.ac.umons.mom.g02.Regulator.SuperviserNormally;


/**
 * This class define the goals when the people go to mons during the free time
 * @author Umons_Group_2_ComputerScience_RandyDauchot
 */
public class FreeTimeMons extends UnderQuest
{

    /**
     * This is the actual course on the planning of the player
     */
    private Course actual;
    /**
     * If the player is in Mons during the free time
     */
    private boolean nothing;


    /**
     * This constructor define the class of check free time in mons
     * @param master is the master class of this
     * @param max    is the maximum percent of this class
     * @param people is the player when play the underQuest
     */
    public FreeTimeMons(Quest master, int max, People people)
    {
        super("FreeTimeMons",max,master,people);
        nothing = false;
    }


    /**
     * This method allows to receive the notification of the game
     * @param notify is the notification in the game
     */
    @Override
    public void evenActivity(Notification notify)
    {
        if (notify.getEvents().equals(Events.MeetOther) && notify.bufferNotEmpty() && notify.getBuffer().getClass().equals(Mobile.class))
            friendPnj((Mobile)notify.getBuffer());

        if (notify.getEvents().equals(Events.PlaceInMons) && notify.bufferNotEmpty())
            goToMons(((PlaceInMons)notify).getBuffer());

        if (notify.getEvents().equals(Events.ChangeHour))
            actual = SuperviserNormally.getSupervisor().getActualCourse();
    }


    /**
     * This method check if the people go in mons during the free time
     * the player haven't course now
     */
    private void goToMons(Maps map)
    {
        if (map.equals(Maps.Mons) && actual == null)
        {
            nothing = true;
            addProgress(0.1);
        }
        else
            nothing = false;
    }


    /**
     * This method check if the player have a friend during the free time in Mons
     * @param mobile is the mobile to meet in Mons
     */
    private void friendPnj(Mobile mobile)
    {
        if (nothing)
            addProgress(0.5);
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
}
