package be.ac.umons.mom.g02.GraphicalObjects;

import be.ac.umons.mom.g02.Other.Date;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Testing class for <code>TimeShower</code>
 */
public class TimeShowerTest {

    /**
     * Test if the method mustExpend returns the expected value
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Test
    public void mustExpendTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Method m = TimeShower.class.getDeclaredMethod("mustExpend", Date.class, Date.class);

        Date d1 = new Date(0,0,0,0,0);
        Date d2 = new Date(0,0,0,0,0);
        Assertions.assertFalse((boolean)m.invoke(new TimeShower(), d1, d2));
        Assertions.assertFalse((boolean)m.invoke(new TimeShower(), d2, d1));
        d2 = new Date(0,0,0,0,1);
        Assertions.assertFalse((boolean)m.invoke(new TimeShower(), d1, d2));
        Assertions.assertFalse((boolean)m.invoke(new TimeShower(), d2, d1));
        d1 = new Date(1,1,1,1,0);
        d2 = new Date(1,1,1,1,2);
        Assertions.assertTrue((boolean)m.invoke(new TimeShower(), d1, d2));
        Assertions.assertTrue((boolean)m.invoke(new TimeShower(), d2, d1));
        d2 = new Date(1,2,1,1,0);
        Assertions.assertTrue((boolean)m.invoke(new TimeShower(), d1, d2));
        Assertions.assertTrue((boolean)m.invoke(new TimeShower(), d2, d1));
        d2 = new Date(2,1,1,1,0);
        Assertions.assertTrue((boolean)m.invoke(new TimeShower(), d1, d2));
        Assertions.assertTrue((boolean)m.invoke(new TimeShower(), d2, d1));
        d2 = new Date(1,1,2,1,0);
        Assertions.assertTrue((boolean)m.invoke(new TimeShower(), d1, d2));
        Assertions.assertTrue((boolean)m.invoke(new TimeShower(), d2, d1));
    }

}
