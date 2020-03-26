package be.ac.umons.mom.g02.Extensions.Dual.Logic.Quest;

import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Regulator.SupervisorDual;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Quests.Master.MasterQuest;


/**
 * This class define the battle between two people in the game
 */
public class BattlePeople extends DualUnderQuest
{

    /**
     * This is the other people in the dual
     */
    private People victim;

    /**
     * This constructor define the dual of the battle between two people
     * @param people     is the people of this Quest
     * @param master is the difficulty of the game
     */
    public BattlePeople(People people,MasterQuest master)
    {
        super("BattleDual",master,people);
    }


    /**
     * This method is called when the action occurs
     * @param notify is the notification of the game
     */
    @Override
    public void evenActivity(Notification notify)
    {
        if (notify.getEvents().equals(Events.Attack) && notify.bufferNotEmpty() && notify.getBuffer().getClass().equals(People.class))
            attackOther((People)notify.getBuffer());
        if (notify.getEvents().equals(Events.Dead) && notify.bufferNotEmpty() && notify.getBuffer().getClass().equals(People.class))
            finishQuest((People)notify.getBuffer());
    }

    private void attackOther(People victim)
    {
        if (SupervisorDual.getSupervisorDual().getAdversary(people).equals(victim))
            addProgress(0.1);
    }

    private void finishQuest(People victim)
    {
        if (SupervisorDual.getSupervisorDual().getAdversary(people).equals(victim))
            addProgress(100-progress);
    }
}