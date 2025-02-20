package be.ac.umons.mom.g02.Events;

import be.ac.umons.mom.g02.Enums.Difficulty;
import be.ac.umons.mom.g02.Enums.Gender;
import be.ac.umons.mom.g02.Enums.Type;
import be.ac.umons.mom.g02.Events.Notifications.ChangeQuest;
import be.ac.umons.mom.g02.Events.Notifications.Dead;
import be.ac.umons.mom.g02.Other.Date;
import be.ac.umons.mom.g02.Regulator.SuperviserNormally;
import be.ac.umons.mom.g02.Regulator.Supervisor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class allows to test the class:
 * events
 * event
 * SuperviserNormally
 */

public class TestEvents
{
    @Test
    public void sameInstance()
    {
        assertSame(SuperviserNormally.getSupervisor(),SuperviserNormally.getSupervisor(),"check the design parrtern single/same instance");
        assertTrue(Events.AddFriend.equals(Events.AddFriend));
        assertFalse(Events.Answer.equals(Events.AddFriend));
    }

    @Test
    public void checkNewParty()
    {
        SuperviserNormally.initNormallyGame();
        SuperviserNormally sp = SuperviserNormally.getSupervisor();
        sp.setMustPlaceItem(false);
        assertNotNull(Supervisor.getEvent(),"the event doesn't create");
        sp.newParty("Test", Type.beefy,Gender.Men,Difficulty.Easy); /*doesn't test here the saving of graphic so gs == null*/
        assertNotNull(Supervisor.getPeople(),"check if the people instance is create");
        Assertions.assertNotNull(sp.getTime(),"check time is create ");
    }

    @Test
    public void checkTheEvent()
    {
        Event evt = new Event();
        assertEquals(0, evt.getList().size(),"check if the size of list is null");
        TestObserver obs = new TestObserver("tets1");
        TestObserver obs2 = new TestObserver("test2");
        evt.add(Events.ChangeQuest,obs);
        assertEquals(1, evt.getList().size(),"check if one events is create");
        evt.add(Events.ChangeQuest,obs2);
        assertEquals(1, evt.getList().size(),"check if the event isn't create");
        assertEquals(2,evt.getList().get(Events.ChangeQuest).size(),"check if the second observer is add to same list");
        evt.notify(new Dead());
        assertFalse(obs.value,"the observer does't notify");
        evt.notify(new ChangeQuest());
        assertTrue(obs.value,"the observer is notify");
        assertTrue(obs2.value,"the observer is notify");
    }

    @Test
    public void checkNotification()
    {
        SuperviserNormally.initNormallyGame();
        Supervisor.getSupervisor().setMustPlaceItem(false);
        SuperviserNormally.getSupervisor().newParty("TestNotif",Type.athletic,Gender.Men,Difficulty.Easy);
        Date oldDate = Supervisor.getSupervisor().getTime().getDate();
        Supervisor.getEvent().notify(new ChangeQuest());
        assertTrue(oldDate.getYear() < Supervisor.getSupervisor().getTime().getDate().getYear(),"Check if the date changed when the quest pass");
    }
}
