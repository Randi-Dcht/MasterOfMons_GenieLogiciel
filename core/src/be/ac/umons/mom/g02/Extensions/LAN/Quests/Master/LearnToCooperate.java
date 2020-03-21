package be.ac.umons.mom.g02.Extensions.LAN.Quests.Master;

import be.ac.umons.mom.g02.Enums.*;
import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Extensions.LAN.Quests.Under.Boss;
import be.ac.umons.mom.g02.Extensions.LAN.Quests.Under.EndPuzzle;
import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Quests.Master.MasterQuest;
import be.ac.umons.mom.g02.Regulator.Supervisor;

import java.util.ArrayList;
import java.util.Random;

public class LearnToCooperate extends MasterQuest {

    /**
     * This constructor allows to define a masterQuest
     *
     * @param before     who is the before masterQuest
     * @param people     who is the people who play this masterQuest
     * @param difficulty
     */
    public LearnToCooperate(MasterQuest before, People people, Difficulty difficulty) {
        super(before, people, Bloc.BA1, difficulty);
        addUnderQuest(new EndPuzzle(this, Supervisor.getPeople()),
                new Boss(this, Supervisor.getPeople()));
        Supervisor.getEvent().add(this, Events.PlaceInMons);
        maxPercent = 2;
    }

    @Override
    public void nextQuest() {
        newQuest(new MyFirstYear(people,this,difficulty));
    }

    @Override
    public String getName() {
        return Supervisor.getGraphic().getStringFromId("learnToCooperate");
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
        for (int i = 0; i < 34; i++ ) { // TODO Check the difficulty of that
            listMobs.add(new Mobile(Bloc.BA1,
                    mobileTypes[rand.nextInt(mobileTypes.length)],
                    Actions.Attack, NameDialog.Lambda));
        }
        for (int i = 0; i < 2; i++)
            listMobs.add(new Mobile(Bloc.MA2,
                    mobileTypes[rand.nextInt(mobileTypes.length)],
                    Actions.Attack, NameDialog.Lambda));
    }

    @Override
    public Maps[] getListMaps() {
        return new Maps[] {Maps.LAN_Boss};
    }
}
