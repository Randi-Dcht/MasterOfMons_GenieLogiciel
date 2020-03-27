package be.ac.umons.mom.g02.Extensions.Dual.Logic.Quest;

import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Enum.TypeDual;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Items.Flag;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Regulator.SupervisorDual;
import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.Items.Items;
import be.ac.umons.mom.g02.Quests.Master.MasterQuest;

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
     *
     * @param notify is the notification of the game
     */
    @Override
    public void evenActivity(Notification notify)
    {

    }

    @Override
    public ArrayList<Items> getListItems()
    {
        ArrayList<Items> list = new ArrayList<>();
        Flag flg;
        for (int i=0; i <3 ;i++)
        {
            list.add(flg = new Flag());flg.setMaps(TypeDual.CatchFlag.getStartMaps());
            if (people.equals(SupervisorDual.getPeople()))
                flg.setPeople(people,"FlagR");
            else
                flg.setPeople(people,"FlagB");
        }
        return list;
    }
}