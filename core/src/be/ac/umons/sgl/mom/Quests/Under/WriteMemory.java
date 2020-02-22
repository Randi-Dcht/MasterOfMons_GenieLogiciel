package be.ac.umons.sgl.mom.Quests.Under;

import be.ac.umons.sgl.mom.Events.Notifications.Notification;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Quests.Quest;

public class WriteMemory extends UnderQuest
{
    public WriteMemory(Quest master, People people)
    {
        super("WriteMemory", 95, master,people);
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
