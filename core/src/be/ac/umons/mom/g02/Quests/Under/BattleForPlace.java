package be.ac.umons.mom.g02.Quests.Under;

import be.ac.umons.mom.g02.Enums.Actions;
import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.LaunchAttack;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.Characters.Character;
import be.ac.umons.mom.g02.Quests.Quest;

/**
 * This class define the goals of the battle to have a place in the auditory of lesson
 * @author Umons_Group_2_ComputerScience_RandyDauchot
 */
public class BattleForPlace extends UnderQuest
{

    /**
     * The memory of last victim during attack
     */
    private Character memory;


    /**
     * This constructor define the quest of battle place
     * @param quest  is the upper quest of this
     * @param nb     is the max percent of progress
     * @param people is the people who play the quest
     */
    public BattleForPlace(Quest quest, int nb, People people)
    {
        super("BattleForPlace",nb,quest,people );
    }


    /**
     * This method allows to receive the notification of the game
     * @param notify is the notification in the game
     */
    @Override
    public void evenActivity(Notification notify)
    {
        if (notify.getEvents().equals(Events.Attack) && notify.bufferNotEmpty())
            attackOther(((LaunchAttack)notify).getBuffer());
        if (notify.getEvents().equals(Events.Dead) && notify.bufferNotEmpty() && ((Character)notify.getBuffer()).getType().equals(Character.TypePlayer.Computer))
            onlyOnPlace((Mobile) notify.getBuffer());
    }


    /**
     * This method allows to see when the people attacks the other characters in the auditory
     * @param victim is the victim of the attack
     */
    private void attackOther(Character victim)
    {
        if ((people.getMaps().equals(Maps.GrandAmphi) || people.getMaps().equals(Maps.DeVinci)) && (memory == null || !victim.equals(memory)))
        {
            addProgress(0.1);
            memory = victim;
        }
    }


    /**
     * This method allows to see when the other characters on the maps is dead
     * @param mob is the mobile on the maps who dead
     */
    private void onlyOnPlace(Mobile mob)
    {
        if (people.getMaps().equals(Maps.GrandAmphi) && mob.getAction().equals(Actions.Attack))
            addProgress(0.5);
        if (people.getMaps().equals(Maps.GrandAmphi))
            addProgress(0.1);
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
