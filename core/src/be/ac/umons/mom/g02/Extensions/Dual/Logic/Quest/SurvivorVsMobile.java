package be.ac.umons.mom.g02.Extensions.Dual.Logic.Quest;

import be.ac.umons.mom.g02.Enums.Actions;
import be.ac.umons.mom.g02.Enums.Bloc;
import be.ac.umons.mom.g02.Enums.MobileType;
import be.ac.umons.mom.g02.Enums.NameDialog;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Enum.TypeDual;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Mobile.ZombiePNJ;
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

    }


    @Override
    public ArrayList<Mobile> getListMobile()
    {
        ArrayList<Mobile> list = new ArrayList<>();
        for (int i=0; i<100;i++)
            list.add(new ZombiePNJ(MobileType.Lambda,TypeDual.Survivor.getStartMaps()));

        return list;
    }
}