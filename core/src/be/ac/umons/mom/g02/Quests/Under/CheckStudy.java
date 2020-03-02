package be.ac.umons.mom.g02.Quests.Under;

import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Quests.Quest;

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
