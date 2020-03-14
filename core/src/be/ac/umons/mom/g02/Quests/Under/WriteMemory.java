package be.ac.umons.mom.g02.Quests.Under;

import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Quests.Quest;


/**
 * This class define the goals if the player write correctly
 * @author Umons_Group_2_ComputerScience_RandyDauchot
 */
public class WriteMemory extends UnderQuest
{

    /**
     * This constructor define the class of check to write memory
     * @param master is the master class of this
     * @param max    is the maximum percent of this class
     * @param people is the player when play the underQuest
     */
    public WriteMemory(Quest master,int max, People people)
    {
        super("WriteMemory", max, master,people);
    }


    /**
     * This method allows to receive the notification of the game
     * @param notify is the notification in the game
     */
    @Override
    public void evenActivity(Notification notify)
    {
        /*code ici*/
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
