package be.ac.umons.sgl.mom.Quests.Under;

import be.ac.umons.sgl.mom.Events.Notifications.Notification;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Quests.Quest;

public class CheckStudy extends UnderQuest
{
    final Quest[] list = {};

    public CheckStudy(Quest master, double max, People people)
    {
        super("CheckStudy", max, master,people);
    }

    @Override
    public void evenActivity(Notification notify) {
    }

    public void studyAtKot(){}
    public void goToStudy(){}

    @Override
    public Quest[] getSubQuests()
    {
        return list;
    }

    @Override
    public int getTotalSubQuestsNumber()
    {
        return list.length;
    }
}
