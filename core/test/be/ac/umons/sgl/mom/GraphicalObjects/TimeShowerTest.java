package be.ac.umons.sgl.mom.GraphicalObjects;

import be.ac.umons.sgl.mom.Other.Date;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TimeShowerTest{

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
