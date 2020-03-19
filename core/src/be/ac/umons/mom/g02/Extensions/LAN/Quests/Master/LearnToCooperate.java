package be.ac.umons.mom.g02.Extensions.LAN.Quests.Master;

import be.ac.umons.mom.g02.Enums.Bloc;
import be.ac.umons.mom.g02.Enums.Difficulty;
import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Extensions.LAN.Quests.Under.EndPuzzle;
import be.ac.umons.mom.g02.Extensions.LAN.Quests.Under.GoToPuzzle;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Quests.Master.MasterQuest;
import be.ac.umons.mom.g02.Regulator.SuperviserNormally;
import be.ac.umons.mom.g02.Regulator.Supervisor;

import java.util.ArrayList;

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
        addUnderQuest(new GoToPuzzle(this, Supervisor.getPeople()),
                new EndPuzzle(this, Supervisor.getPeople()));
        SuperviserNormally.getEvent().add(this, Events.EntryPlace);
    }

    @Override
    public void nextQuest() {
        newQuest(new MyFirstYear(people,this,difficulty));
    }

    @Override
    public String getName() {
        System.out.println("Test" + Supervisor.getGraphic());
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
    protected void createListMobiles() {
        listMobs = new ArrayList<>();
    }

    @Override
    public Maps[] getListMaps() {
        return new Maps[0];
    }
}
