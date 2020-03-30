package be.ac.umons.mom.g02.Quests;

import be.ac.umons.mom.g02.Enums.Difficulty;
import be.ac.umons.mom.g02.Enums.Gender;
import be.ac.umons.mom.g02.Enums.Type;
import be.ac.umons.mom.g02.Regulator.SuperviserNormally;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Quests.Master.MasterQuest;
import be.ac.umons.mom.g02.Quests.Master.MyFirstYear;
import be.ac.umons.mom.g02.Quests.Master.SuccessfulYear;
import be.ac.umons.mom.g02.Regulator.Supervisor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestQuest
{
    @Test
    void TestnextQuest()
    {
        Supervisor.getSupervisor().newParty("Test", Type.beefy, Gender.Men, Difficulty.Easy);
        People p = SuperviserNormally.getPeople();
        MasterQuest mq = new MyFirstYear(p,null,Difficulty.Easy);
        mq.nextQuest();
        assertNull(mq.getChildren(),"quest after is null");
        assertNull(mq.getParent(),"quest before is null");
        mq.addProgress(100);
        for (int i = 0; i < 40; i++){p.upLevel();}
        assertNotNull(mq.getChildren(),"quest1 after isn't null");
        Assertions.assertNotNull(p.getQuest().getParent(),"quest2 before isn't null");
        Assertions.assertNull(p.getQuest().getChildren(),"quest2 after is null");
    }

    @Test
    void TestNextWithLevel()
    {
        Supervisor.getSupervisor().newParty("Test",Type.beefy,Gender.Men,Difficulty.Easy);
        People p = SuperviserNormally.getPeople();
        People pTest = new People("Tesst", Type.normal, Gender.Men ,Difficulty.Easy);
        SuccessfulYear quest = new SuccessfulYear(pTest,null,Difficulty.Easy);
        quest.addProgress(100);
        assertNull(quest.getChildren());
        for (int i = 0; i < 60;i++){pTest.upLevel();}
        Assertions.assertNotSame(pTest.getQuest(),quest,"The level of people is goog to give the child quest");
    }
}
