package be.ac.umons.mom.g02.Extensions.Duals;

import be.ac.umons.mom.g02.Enums.Difficulty;
import be.ac.umons.mom.g02.Enums.Gender;
import be.ac.umons.mom.g02.Enums.Type;
import be.ac.umons.mom.g02.Events.Notifications.LaunchAttack;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Enum.TypeDual;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Quest.DualMasterQuest;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Quest.DualUnderQuest;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Regulator.SupervisorDual;
import be.ac.umons.mom.g02.Objects.Characters.People;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestExtensionDual
{
    @BeforeAll
    static void beforeAll()
    {
        SupervisorDual.initDual();
        SupervisorDual.getSupervisorDual().createNewPlayer(new People("Test", Type.athletic,Gender.Men, Difficulty.Easy),1);
        SupervisorDual.getSupervisorDual().createNewPlayer(new People("Test2", Type.athletic,Gender.Men, Difficulty.Easy),2);
    }


    @Test
    public void testBattle()
    {
        SupervisorDual.getSupervisorDual().init(TypeDual.DualPlayer);
        People p1 = SupervisorDual.getPeople();
        People p2 = SupervisorDual.getPeopleTwo();
        DualUnderQuest d1 = (DualUnderQuest)((DualMasterQuest)SupervisorDual.getSupervisor().actualQuest()).getUnderQuest(p1);
        DualUnderQuest d2 = (DualUnderQuest)((DualMasterQuest)SupervisorDual.getSupervisor().actualQuest()).getUnderQuest(p2);
        double db1 = d1.getAdvancement();
        double db2 = d2.getAdvancement();
        SupervisorDual.getSupervisor().actualQuest().eventMaps(new LaunchAttack(p1,p2));
        assertEquals(d1.getAdvancement(), db1);
        assertEquals(d2.getAdvancement(), db2);
    }


    @Test
    public void catchFlag()
    {}
}
