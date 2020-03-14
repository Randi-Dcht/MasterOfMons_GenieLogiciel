package be.ac.umons.mom.g02.Extensions.LAN.Quests.Under;

import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Quests.Quest;
import be.ac.umons.mom.g02.Quests.Under.UnderQuest;

public class GoToPuzzle extends UnderQuest {
    /**
     * This constructor allows define the underQuest
     *
     * @param master is the quest who call this
     * @param people is the people who play the game
     */
    public GoToPuzzle(Quest master, People people) {
        super("goToPuzzle", 1, master, people);
    }

    @Override
    public void evenActivity(Notification notify) {
        if (notify.getEvents().equals(Events.EntryPlace) && notify.bufferNotEmpty()) {
            if (notify.getBuffer().equals("Tmx/LAN_Puzzle.tmx"))
                addProgress(1);
        }
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
