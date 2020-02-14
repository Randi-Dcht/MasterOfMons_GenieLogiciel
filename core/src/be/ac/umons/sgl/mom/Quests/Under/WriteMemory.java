package be.ac.umons.sgl.mom.Quests.Under;

import be.ac.umons.sgl.mom.Events.Notifications.Notification;
import be.ac.umons.sgl.mom.Quests.Quest;

public class WriteMemory extends UnderQuest
{
    public WriteMemory(Quest master) {
        super("WriteMemory", 95, master);
    }

    @Override
    public void evenActivity(Notification notify)
    {
        /*code ici*/
    }

    @Override
    public Quest[] getSubQuests()
    {
        /*code ici*/
        return new Quest[0];
    }

    @Override
    public int getTotalSubQuestsNumber()
    {
        /*code ici*/
        return 0;
    }
}
