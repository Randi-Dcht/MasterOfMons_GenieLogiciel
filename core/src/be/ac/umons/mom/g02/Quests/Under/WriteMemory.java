package be.ac.umons.mom.g02.Quests.Under;

import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Quests.Quest;

public class WriteMemory extends UnderQuest
{
    public WriteMemory(Quest master, People people)
    {
        super("WriteMemory", 80, master,people);
    }

    @Override
    public void evenActivity(Notification notify)
    {
        /*code ici*/
    }

    @Override
    public Quest[] getSubQuests()
    {
        Quest[] q = {};
        return q;
    }

    @Override
    public int getTotalSubQuestsNumber()
    {
        return getSubQuests().length;
    }
}
