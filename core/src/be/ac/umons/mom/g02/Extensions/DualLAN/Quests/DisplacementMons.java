package be.ac.umons.mom.g02.Extensions.DualLAN.Quests;

import be.ac.umons.mom.g02.Enums.Difficulty;
import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Quests.Master.MasterQuest;
import com.badlogic.gdx.Gdx;

import java.net.SocketException;

public class DisplacementMons extends be.ac.umons.mom.g02.Extensions.Dual.Logic.Quest.DisplacementMons {
    /**
     * If we already sent the end of this MasterQuest or not
     */
    protected boolean alreadySent = false;

    /**
     * @param people The player of this quest
     * @param before The MasterQuest before this one
     * @param difficulty The difficulty to use
     */
    public DisplacementMons(People people, MasterQuest before, Difficulty difficulty) {
        super(people, before, difficulty);
    }


    @Override
    public void nextQuest() {
        super.nextQuest();
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
