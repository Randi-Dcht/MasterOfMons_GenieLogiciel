package be.ac.umons.mom.g02.Objects.Items;

import be.ac.umons.mom.g02.Enums.Difficulty;
import be.ac.umons.mom.g02.Enums.Gender;
import be.ac.umons.mom.g02.Enums.Type;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Regulator.SuperviserNormally;
import be.ac.umons.mom.g02.Regulator.Supervisor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestItems
{
    @Test
    public void TestTheItem()
    {
        SuperviserNormally.initNormallyGame();
        Supervisor.getSupervisor().setMustPlaceItem(false);
        Supervisor.getSupervisor().newParty("Test",Type.athletic,Gender.Men,Difficulty.Easy);
        Items phone = new Phone();
        People pp = Supervisor.getPeople();
        phone.used(pp);
        assertTrue(pp.getFriend()>0,"Check if the phone give the things to the people");
        for (int i=0;i<3;i++)
            phone.used(pp);
        assertTrue(phone.getObsolete(),"Check if the phone battery is lose");
    }
}
