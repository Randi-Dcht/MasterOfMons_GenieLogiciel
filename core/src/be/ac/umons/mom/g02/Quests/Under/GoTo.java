package be.ac.umons.mom.g02.Quests.Under;

import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Quests.Quest;


public class GoTo extends UnderQuest
{
    /***/
    public GoTo(Quest q, int nb, People people)
    {
        super("GoTo",nb,q,people);
    }

    @Override
    public void evenActivity(Notification notify) {
    }


    /***/
    public Quest[] getSubQuests()
    {
        return new Quest[]{};
    }


    /***/
    public int getTotalSubQuestsNumber()
    {
        return 0;
    }
}
