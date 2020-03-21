package be.ac.umons.mom.g02.Extensions.LAN.Quests.Under;

import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Quests.Quest;
import be.ac.umons.mom.g02.Quests.Under.UnderQuest;

public class EndPuzzle extends UnderQuest {
    /**
     * This constructor allows define the underQuest
     *
     * @param master is the quest who call this
     * @param people is the people who play the game
     */
    public EndPuzzle(Quest master, People people) {
        super("endPuzzle", 1, master, people);
    }

    @Override
    public void evenActivity(Notification notify) {
        if (notify.getEvents().equals(Events.PlaceInMons) && notify.getBuffer() != null && notify.getBuffer().equals(Maps.LAN_Boss))
            addProgress(1);
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
