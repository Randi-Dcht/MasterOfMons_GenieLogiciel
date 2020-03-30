package be.ac.umons.mom.g02.Extensions.LAN.Quests.Master;

import be.ac.umons.mom.g02.Enums.*;
import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.Teleport;
import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.Extensions.LAN.Quests.Under.Boss;
import be.ac.umons.mom.g02.Extensions.LAN.Quests.Under.EndPuzzle;
import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Quests.Master.MasterQuest;
import be.ac.umons.mom.g02.Regulator.Supervisor;
import com.badlogic.gdx.Gdx;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.Random;

public class LearnToCooperate extends MasterQuest {

    /**
     * If we already sent the end of this MasterQuest or not
     */
    protected boolean alreadySent = false;
    /**
     * The number of mobiles to spawn on this map
     */
    protected int mobileNumber;

    /**
     * This constructor allows to define a masterQuest
     *
     * @param before     who is the before masterQuest
     * @param people     who is the people who play this masterQuest
     * @param difficulty
     */
    public LearnToCooperate(MasterQuest before, People people, Difficulty difficulty) {
        super(before, people, Bloc.BA1, difficulty);
        Supervisor.getEvent().add(this, Events.PlaceInMons, Events.Dead);
        mobileNumber = 22;
        if (difficulty.equals(Difficulty.Medium))
            mobileNumber = 28;
        else if (difficulty.equals(Difficulty.Hard))
            mobileNumber = 36;
        maxPercent = mobileNumber * 2; // finish puzzle + 36 mobs
        addUnderQuest(new EndPuzzle(this, Supervisor.getPeople()),
                new Boss(this, Supervisor.getPeople()));
    }

    @Override
    public void nextQuest() {
        newQuest(new MyFirstYear(people,this, difficulty));
        Supervisor.getEvent().notify(new Teleport("Tmx/Umons_Nimy.tmx"));
        if (! alreadySent) {
            try {
                NetworkManager.getInstance().sendOnTCP("EMQ");
            } catch (SocketException e) {
                Gdx.app.error("MasterQuest", "Unable to get the NetworkManager", e);
            }
            alreadySent = true;
        }
    }

    @Override
    public String getName() {
        return GraphicalSettings.getStringFromId("learnToCooperate");
    }

    @Override
    public String question() {
        return "questionLearnToCooperate";
    }

    @Override
    protected void createListItems() {
        listItems = new ArrayList<>();
    }

    @Override
    protected void createListMobiles() { // 36 positions on map
        Random rand = new Random();
        listMobs = new ArrayList<>();
        MobileType[] mobileTypes = MobileType.values();

        for (int i = 0; i < mobileNumber - 2; i++ ) {
            listMobs.add(new Mobile(Bloc.BA1,
                    mobileTypes[rand.nextInt(mobileTypes.length)],
                    Actions.Attack, NameDialog.Lambda));
        }
        for (int i = 0; i < 2; i++)
            listMobs.add(new Mobile(Bloc.BA2,
                    mobileTypes[rand.nextInt(mobileTypes.length)],
                    Actions.Attack, NameDialog.Lambda));
    }

    @Override
    public Maps[] getListMaps() {
        return new Maps[] {Maps.LAN_Boss};
    }

    /**
     * @return The number of mobiles to spawn on this map
     */
    public int getMobileNumber() {
        return mobileNumber;
    }
}
