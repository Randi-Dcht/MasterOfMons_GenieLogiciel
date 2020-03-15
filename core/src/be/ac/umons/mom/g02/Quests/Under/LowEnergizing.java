package be.ac.umons.mom.g02.Quests.Under;

import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.LowSomething;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Quests.Quest;
import java.util.ArrayList;

/**
 * This class define the goal of the low energizing of the player during the attack
 * @author Umons_Group_2_ComputerScience_RandyDauchot
 */
public class LowEnergizing extends UnderQuest//TODO new to implement
{

    /**
     * This list memorise the old victim of the player
     */
    private ArrayList<Mobile> oldVictim = new ArrayList<>();


    /**
     * This constructor define the goal when the player have a low of energizing player
     * @param master is the master quest of this
     * @param people is the player who play the game
     * @param max    is the maximum of percent progress
     */
    public LowEnergizing(Quest master, int max, People people)
    {
        super("GoTo",max,master,people);
    }


    /**
     * This method allows to receive the notification of the game
     * @param notify is the notification in the game
     */
    @Override
    public void evenActivity(Notification notify)
    {
        if (notify.getEvents().equals(Events.LowSomething) && notify.bufferNotEmpty())
            analyseLow(((LowSomething)notify).getBuffer());
        if (notify.getEvents().equals(Events.Attack) && notify.bufferNotEmpty() && notify.getBuffer().getClass().equals(Mobile.class))
            attackMobile((Mobile)notify.getBuffer());
    }


    /**
     * This method checks if the player energizing is under ten percents
     * @param what is the type of the low
     */
    private void analyseLow(LowSomething.TypeLow what)
    {
        if (what.equals(LowSomething.TypeLow.Energy))
        {
            if (people.getEnergy() <= 10)
                addProgress(2);
        }
    }


    /**
     * This method see if the player attack the mobile on the maps
     * @param victim is the mobile who victims of the attack
     */
    private void attackMobile(Mobile victim)
    {
        if (!oldVictim.contains(victim))
        {
            addProgress(0.1);
            oldVictim.add(victim);
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
