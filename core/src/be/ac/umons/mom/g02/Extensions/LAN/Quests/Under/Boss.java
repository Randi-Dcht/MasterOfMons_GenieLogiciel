package be.ac.umons.mom.g02.Extensions.LAN.Quests.Under;

import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Extensions.LAN.Quests.Master.LearnToCooperate;
import be.ac.umons.mom.g02.Extensions.LAN.Regulator.SupervisorLAN;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Quests.Quest;
import be.ac.umons.mom.g02.Quests.Under.UnderQuest;

/**
 * Represent the under-quest where the player have to kill all the mobile.
 */
public class Boss extends UnderQuest {
    /**
     * This constructor allows define the underQuest
     *
     * @param master is the quest who call this
     * @param people is the people who play the game
     */
    public Boss(LearnToCooperate master, People people) {
        super("boss", master.getMobileNumber(), master, people);
    }

    /**
     * This method is called when the action occurs
     *
     * @param notify is the notification of the game
     */
    @Override
    public void evenActivity(Notification notify) {
        if (notify.getEvents().equals(Events.Dead) && notify.getBuffer() != null && ! notify.getBuffer().equals(SupervisorLAN.getPeople()) && ! notify.getBuffer().equals(SupervisorLAN.getPeopleTwo()))
            addProgress(1);
    }

    /**
     * This method return the underQuest of this Quest
     *
     * @return list of the Quest
     */
    @Override
    public Quest[] getSubQuests() {
        return new Quest[0];
    }

    /**
     * This method return the number of underQuest of this Quest
     *
     * @return number of quest
     */
    @Override
    public int getTotalSubQuestsNumber() {
        return 0;
    }
}
