package be.ac.umons.mom.g02.Extensions.LAN.Quests.Master;

import be.ac.umons.mom.g02.Enums.Difficulty;
import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Quests.Master.MasterQuest;
import be.ac.umons.mom.g02.Regulator.Supervisor;
import com.badlogic.gdx.Gdx;

import java.net.SocketException;

public class MeetAndLearn extends be.ac.umons.mom.g02.Quests.Master.MeetAndLearn {

    /**
     * If we already sent the end of this MasterQuest or not
     */
    protected boolean alreadySent = false;

    /**
     * This is a constructor of this Quest who define a quest in the bachelor 3
     *
     * @param people     who is the people who play the game
     * @param before     who is the quest before this
     * @param difficulty
     */
    public MeetAndLearn(People people, MasterQuest before, Difficulty difficulty) {
        super(people, before, difficulty);
    }

    @Override
    public void nextQuest() {
        Supervisor.getSupervisor().getRegale().finishQuest();
        if (! alreadySent) {
            try {
                NetworkManager.getInstance().sendOnTCP("EMQ");
            } catch (SocketException e) {
                Gdx.app.error("MasterQuest", "Unable to get the NetworkManager", e);
            }
            alreadySent = true;
        }
    }
}
