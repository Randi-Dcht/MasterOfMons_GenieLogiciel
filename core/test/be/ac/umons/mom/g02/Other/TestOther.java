package be.ac.umons.mom.g02.Other;

import be.ac.umons.mom.g02.Enums.Difficulty;
import be.ac.umons.mom.g02.Enums.Gender;
import be.ac.umons.mom.g02.Enums.Type;
import be.ac.umons.mom.g02.Events.Notifications.ChangeQuest;
import be.ac.umons.mom.g02.Regulator.SuperviserNormally;
import be.ac.umons.mom.g02.Regulator.Supervisor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class allows to test the class of the package Other
 */
public class TestOther
{
    @Test
    void TimeGameTest()
    {
        SuperviserNormally.initNormallyGame();Supervisor.getSupervisor().setMustPlaceItem(false);
        Supervisor.getSupervisor().newParty("Test",Type.athletic,Gender.Men,Difficulty.Easy);
        TimeGame tg = Supervisor.getSupervisor().getTime();
        int old = tg.getDate().getYear();
        tg.update(new ChangeQuest());
        for(int i = 0; i < 60; i++)
            tg.update(new ChangeQuest());
        assertEquals(tg.getDate().getYear(), (old + 61));
    }


    @Test
    public void testPushTime()
    {
        SuperviserNormally.initNormallyGame();
        Supervisor.getSupervisor().setMustPlaceItem(false);
        Supervisor.getSupervisor().newParty("Test", Type.athletic, Gender.Men, Difficulty.Easy);
        TimeGame time = new TimeGame((new Date(15,1,2020,9,15)));
        time.refreshTime(1,5,46,false);
        assertEquals(new Date(16,1,2020,15,1),time.getDate(),"Check if the time refresh push the good time");
    }

}
