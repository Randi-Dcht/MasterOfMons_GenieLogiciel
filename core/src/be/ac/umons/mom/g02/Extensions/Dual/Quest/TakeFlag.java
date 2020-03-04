package be.ac.umons.mom.g02.Extensions.Dual.Quest;

import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Quests.Quest;
import be.ac.umons.mom.g02.Quests.Under.UnderQuest;

public class TakeFlag extends UnderQuest
{
    public TakeFlag(String name, double max, Quest master, People people)
    {
        super(name, max, master,people);
    }

    @Override
    public void evenActivity(Notification notify) {

    }


    @Override
    public Quest[] getSubQuests() {
        return new Quest[0];
    }

    @Override
    public int getTotalSubQuestsNumber() {
        return 0;
    }
}