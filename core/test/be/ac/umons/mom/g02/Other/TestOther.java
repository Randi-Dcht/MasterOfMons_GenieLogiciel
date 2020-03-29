package be.ac.umons.mom.g02.Other;

import be.ac.umons.mom.g02.Events.Notifications.ChangeQuest;
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
        TimeGame tg = new TimeGame(new Date(15,9,2019,8,0));
        tg.update(new ChangeQuest());
        assertEquals(1, tg.getValueTest()[0]);
        for(int i = 0; i <= 60; i++)
            tg.update(new ChangeQuest());
        System.out.println(tg.getValueTest()[1]);//TODO
        //assertEquals(9, tg.getValueTest()[1]);
    }


    @Test
    public void testPushTime()
    {
        TimeGame time = new TimeGame((new Date(15,1,2020,9,15)));
        time.refreshTime(1,5,46);
        assertSame(new Date(16,1,2020,15,1),time.getDate(),"Check if the time refresh push the good time");
    }

}
