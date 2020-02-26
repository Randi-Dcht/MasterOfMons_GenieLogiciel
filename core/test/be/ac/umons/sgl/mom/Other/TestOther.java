package be.ac.umons.sgl.mom.Other;

import be.ac.umons.sgl.mom.Events.Notifications.ChangeQuest;
import be.ac.umons.sgl.mom.Other.Date;
import be.ac.umons.sgl.mom.Other.TimeGame;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class allows to test the class of the package Other
 */
public class TestOther
{
    @Test
    void TimeGameTest()
    {
        TimeGame tg = new TimeGame(new Date(15,9,2019,8,0));
        tg.update(new ChangeQuest());
        assertEquals(1, tg.getValueTest()[0]);
        for(int i = 0; i <= 60; i++)
            tg.update(new ChangeQuest());
        System.out.println(tg.getValueTest()[1]);
        //assertEquals(9, tg.getValueTest()[1]);
    }
}
