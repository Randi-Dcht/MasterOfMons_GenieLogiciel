package be.ac.umons.mom.g02.Regulator;

import be.ac.umons.mom.g02.Enums.Difficulty;
import be.ac.umons.mom.g02.Enums.Gender;
import be.ac.umons.mom.g02.Enums.Type;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Regulator.SupervisorDual;
import be.ac.umons.mom.g02.Extensions.Multiplayer.Regulator.SupervisorMultiPlayer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SupervisorTest extends SuperviserNormally
{

    @Test
    public void TestInitSupervisor()
    {
        SuperviserNormally.initNormallyGame();
        assertEquals(Supervisor.getSupervisor().getClass(), SuperviserNormally.class,"Check if the supervisor class is the single people");

        SupervisorDual.initDual();
        assertEquals(Supervisor.getSupervisor().getClass(), SupervisorMultiPlayer.class,"Check if the supervisor class is the two peoples");
    }

    @Test
    public void TestNewParty()
    {
        /*before new party*/
        assertNull(playerOne,"There isn't player instance");
        assertNull(listItems);
        assertNull(listMobile);
        assertNull(listMoving);
        /*after init new party*/
        SuperviserNormally.initNormallyGame();
        newParty("Test", Type.athletic,Gender.Men, Difficulty.Easy);
        assertNotNull(playerOne);
        assertNotNull(listItems);
        assertNotNull(listMobile);
        assertNotNull(listMoving);
    }
}
