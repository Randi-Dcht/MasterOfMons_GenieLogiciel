package be.ac.umons.mom.g02.Extensions.Dual.Logic.Quest;

import be.ac.umons.mom.g02.Enums.MobileType;
import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.LaunchAttack;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Enum.TypeDual;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Mobile.ZombiePNJ;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Regulator.SupervisorDual;
import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Quests.Master.MasterQuest;
import java.util.ArrayList;


/**
 * This class define the survivor between two people with many Mobile to kill
 */
public class SurvivorVsMobile extends DualUnderQuest
{

    /**
     * This constructor define the dual of the battle between two people
     * @param people is the people of this Quest
     * @param master is the difficulty of the game
     */
    public SurvivorVsMobile(People people,MasterQuest master)
    {
        super("SurvivorInMons",master,people);
    }


    /**
     * This method is called when the action occurs
     * @param notify is the notification of the game
     */
    @Override
    public void evenActivity(Notification notify)
    {
        if (notify.getEvents().equals(Events.Attack) && notify.bufferNotEmpty())
            analyseAttack((LaunchAttack)notify);
        if (notify.getEvents().equals(Events.Dead) && notify.bufferNotEmpty() && notify.getBuffer().equals(SupervisorDual.getSupervisorDual().getAdversary(people)));
            winGame();

    }

    private void winGame()
    {
        addProgress(100-progress);
    }

    private void analyseAttack(LaunchAttack notify)
    {
        if (notify.getBufferSecond().equals(people))
            addProgress(0.1);
    }


    @Override
    public ArrayList<Mobile> getListMobile()
    {
        ArrayList<Mobile> list = new ArrayList<>();
        for (int i=0; i<50;i++)
            list.add(new ZombiePNJ(MobileType.Lambda,TypeDual.Survivor.getStartMaps()));

        return list;
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