package be.ac.umons.mom.g02.Animations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DoubleAnimationTests {
    private int endActionExecuted = 0;
    private int runningActionExecuted = 0;

    /**
     * Test if each actions is executed the expected number of times and if the returned value are correct
     */
    @Test
    public void test() {
        endActionExecuted = 0;
        runningActionExecuted = 0;
        DoubleAnimation da = new DoubleAnimation(0, 2, 1000);
        da.setEndingAction(() -> endActionExecuted += 1);
        da.setRunningAction(() -> runningActionExecuted += 1);
        da.update(.5); //Simule un espacement de 500ms entre 2 mise-à-jour.
        Assertions.assertEquals(0, endActionExecuted);
        Assertions.assertEquals(1, runningActionExecuted);
        Assertions.assertEquals(1, da.getActual());
        da.update(.5); //Simule un espacement de 500ms entre 2 mise-à-jour.
        Assertions.assertEquals(1, endActionExecuted);
        Assertions.assertEquals(2, runningActionExecuted);
        Assertions.assertEquals(2, da.getActual());
        da.update(.5); //Simule un espacement de 500ms entre 2 mise-à-jour.
        Assertions.assertEquals(1, endActionExecuted);
        Assertions.assertEquals(2, runningActionExecuted);
        Assertions.assertEquals(2, da.getActual());
    }

    /**
     * Test if the actions are executed the expected number of times and if the returned value is correct
     */
    @Test
    public void test2() {
        endActionExecuted = 0;
        runningActionExecuted = 0;
        DoubleAnimation da = new DoubleAnimation(2, 0, 1000);
        da.setEndingAction(() -> endActionExecuted += 1);
        da.setRunningAction(() -> runningActionExecuted += 1);
        da.update(.5); //Simule un espacement de 500ms entre 2 mise-à-jour.
        Assertions.assertEquals(0, endActionExecuted);
        Assertions.assertEquals(1, runningActionExecuted);
        Assertions.assertEquals(1, da.getActual());
        da.update(.5); //Simule un espacement de 500ms entre 2 mise-à-jour.
        Assertions.assertEquals(1, endActionExecuted);
        Assertions.assertEquals(2, runningActionExecuted);
        Assertions.assertEquals(0, da.getActual());
        da.update(.5); //Simule un espacement de 500ms entre 2 mise-à-jour.
        Assertions.assertEquals(1, endActionExecuted);
        Assertions.assertEquals(2, runningActionExecuted);
        Assertions.assertEquals(0, da.getActual());
    }
}
