package be.ac.umons.sgl.mom.Events;

import be.ac.umons.sgl.mom.Enums.Type;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

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
    }

    @Test
    public void checkNewParty()
    {
        SuperviserNormally sp = SuperviserNormally.getSupervisor();
        assertNull(sp.getPeople(),"the people doesn't create");
        assertNotNull(sp.getEvent(),"the event doesn't create");
        sp.newParty("Test", Type.beefy,null); /*doesn't test here the saving of graphic so gs == null*/
        assertNotNull(sp.getPeople(),"check if the people instance is create");
        assertNotNull(sp.getTime(),"check time is create ");
    }
}
