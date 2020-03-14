package be.ac.umons.mom.g02.Quests.Under;

import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.Dialog;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Events.Notifications.UseItem;
import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.Items.Items;
import be.ac.umons.mom.g02.Objects.Items.Phone;
import be.ac.umons.mom.g02.Quests.Quest;
import java.util.ArrayList;


/**
 * This class define the goals to meet other character on the all maps in the game
 * @author Umons_Group_2_ComputerScience_RandyDauchot
 */
public class MeetManyPeople extends UnderQuest
{

    /**
     * This is a list with the all character already meet on the all maps
     */
    private ArrayList<Mobile> myList = new ArrayList<>();


    /**
     * This constructor define the quest to meet the other character
     * @param quest  is the upper quest of this
     * @param nb     is the max percent of progress
     * @param people is the people who play the quest
     */
    public MeetManyPeople(Quest quest, int nb, People people)
    {
        super("MeetManyPeople",nb,quest,people);
    }


    /**
     * This method allows to receive the notification of the game
     * @param notify is the notification in the game
     */
    @Override
    public void evenActivity(Notification notify)
    {
        if (notify.getEvents().equals(Events.Dialog) && notify.bufferNotEmpty())
            goToSpeak(((Dialog)notify).getBuffer().get(0));
        if (notify.getEvents().equals(Events.MeetOther))
            meetPNJ((Mobile)notify.getBuffer());
        if (notify.getEvents().equals(Events.UseItems))
            helpByPhone(((UseItem)notify).getBuffer());
    }


    /**
     * This method see when the player meet other if the other isn't in list
     * @param mobile is the other character to meet
     */
    private void meetPNJ(Mobile mobile)
    {
        if (!myList.contains(mobile))
        {
            myList.add(mobile);
            addProgress(0.5);
        }
    }


    /**
     * This method checks if the player go to speak with the other character
     * @param answer is the answer of the player in the dialog
     */
    private void goToSpeak(String answer)
    {
        if (answer.equals("Hello"))
            addProgress(2);
    }


    /**
     * This method check if the people use the phone to meet the other character in the game
     * @param items is the items (phone) to use
     */
    private void helpByPhone(Items items)
    {
        if (items.getClass().equals(Phone.class) /*&& items.getObsolete() != 0*/)
            addProgress(2);
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
        return getSubQuests().length;
    }
}
