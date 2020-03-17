package be.ac.umons.mom.g02.Extensions.LAN.Quests.Master;

import be.ac.umons.mom.g02.Enums.Difficulty;
import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Quests.Master.MasterQuest;
import com.badlogic.gdx.Gdx;

import java.net.SocketException;

public class MyFirstYear extends be.ac.umons.mom.g02.Quests.Master.MyFirstYear {

    /**
     * If we already sent the end of this MasterQuest or not
     */
    protected boolean alreadySent = false;

    /**
     * This is a constructor of this Quest who define a quest in the bachelor 1
     *
     * @param people     who is the people who play the game
     * @param before     who is the quest before this
     * @param difficulty
     */
    public MyFirstYear(People people, MasterQuest before, Difficulty difficulty) {
        super(people, before, difficulty);
    }

    @Override
    public void nextQuest() {
        newQuest(new SuccessfulYear(people,this,difficulty));
        if (! alreadySent) {
            try {
                NetworkManager.getInstance().sendEndOfMasterQuest();
            } catch (SocketException e) {
                Gdx.app.error("MasterQuest", "Unable to get the NetworkManager", e);
            }
            alreadySent = true;
        }
    }
}
