package be.ac.umons.mom.g02.Quests.Under;

import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.OtherInformation;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Quests.Quest;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import java.util.ArrayList;


/**
 * This class define the goals when the people go to read the information of the place
 * @author Umons_Group_2_ComputerScience_RandyDauchot
 */
public class ReadInformation extends UnderQuest
{

    /**
     * This is the number of the place with the information
     */
    private static final int NUMBER = 2;
    /**
     * This list contains the id when the people already read on the maps
     */
    private ArrayList<String> memoryIdRead = new ArrayList<>();


    /**
     * This constructor define the quest of battle place
     * @param quest  is the upper quest of this
     * @param nb     is the max percent of progress
     * @param people is the people who play the quest
     */
    public ReadInformation(Quest quest, int nb, People people)
    {
        super("ReadInfo",nb,quest,people );
    }


    /**
     * This method allows to receive the notification of the game
     * @param notify is the notification in the game
     */
    @Override
    public void evenActivity(Notification notify)
    {
        if (notify.getEvents().equals(Events.OtherInformation) && notify.bufferNotEmpty())
            checkRead(((OtherInformation)notify).getBuffer());
    }


    /**
     * This method allows to check if the people go to read the information in the all Maps
     * @param id is the id of the other information
     */
    private void checkRead(String id)//TODO analyse id
    {
        if (!people.getMaps().equals(Maps.Kot) && !memoryIdRead.contains(id))
        {
            memoryIdRead.add(id);
            addProgress(percentMax/NUMBER);
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
}
