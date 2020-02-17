package be.ac.umons.sgl.mom.Events;

import be.ac.umons.sgl.mom.Enums.Difficulty;
import be.ac.umons.sgl.mom.Enums.Type;
import be.ac.umons.sgl.mom.Events.Notifications.ChangeQuest;
import be.ac.umons.sgl.mom.Events.Notifications.Dead;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        SuperviserNormally sp = SuperviserNormally.getSupervisor();
        assertNull(sp.getPeople(),"the people doesn't create");
        assertNotNull(sp.getEvent(),"the event doesn't create");
        sp.newParty("Test", Type.beefy,null, Difficulty.Easy); /*doesn't test here the saving of graphic so gs == null*/
        assertNotNull(sp.getPeople(),"check if the people instance is create");
        assertNotNull(sp.getTime(),"check time is create ");
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
}
