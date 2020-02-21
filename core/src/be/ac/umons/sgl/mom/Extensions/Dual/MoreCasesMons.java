package be.ac.umons.sgl.mom.Extensions.Dual;

import be.ac.umons.sgl.mom.Events.Notifications.Notification;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Quests.Quest;
import be.ac.umons.sgl.mom.Quests.Under.UnderQuest;

public class MoreCasesMons extends UnderQuest {
    public MoreCasesMons(String name, double max, Quest master, People people) {
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