package be.ac.umons.mom.g02.Extensions.Dual.Logic.Quest;

import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Events.Notifications.UseItem;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Enum.TypeDual;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Items.Flag;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Regulator.SupervisorDual;
import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.Items.Items;
import be.ac.umons.mom.g02.Quests.Master.MasterQuest;

import java.awt.*;
import java.util.ArrayList;

/**
 * This class define the first people who take the flag of the adversary
 */
public class TakeFlag extends DualUnderQuest
{

    /**
     * This constructor define the dual of the battle between two people
     * @param people     is the people of this Quest
     * @param master is the difficulty of the game
     */
    public TakeFlag(People people,MasterQuest master)
    {
        super("TakeThreeFlag",master,people);
    }


    /**
     * This method is called when the action occurs
     * @param notify is the notification of the game
     */
    @Override
    public void evenActivity(Notification notify)
    {
        if (notify.getEvents().equals(Events.UseItems) && notify.bufferNotEmpty())
            analyzeUse(((UseItem)notify).getPeople());
        ((DualMasterQuest)master).checkNext();
    }


    /**
     * This method analyses the people who use the object
     * @param people is the people who use item
     */
    private void analyzeUse(People people)
    {
        if (people.equals(this.people))
            addProgress(33.50);
    }


    /***/
    @Override
    public ArrayList<Items> getListItems()
    {
        Point ptFirst  = new Point(11,7);
        Point ptSecond = new Point(10,32);
        ArrayList<Items> list = new ArrayList<>();
        Flag flg;
        for (int i=0; i <3 ;i++)
        {
            list.add(flg = new Flag());flg.setMaps(TypeDual.CatchFlag.getStartMaps());
            if (people.equals(SupervisorDual.getPeople()))
            {
                flg.setPeople(people,"R");
                flg.setPositionOnMap(new Point(ptFirst.x+i,ptFirst.y-i));
            }
            else
            {
                flg.setPeople(people,"B");
                flg.setPositionOnMap(new Point(ptSecond.x+i,ptSecond.y-i));
            }
        }
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