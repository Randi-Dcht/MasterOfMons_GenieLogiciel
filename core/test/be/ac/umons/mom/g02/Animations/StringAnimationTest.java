package be.ac.umons.mom.g02.Animations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringAnimationTest {
    private int endActionExecuted = 0;
    private int runningActionExecuted = 0;

    /**
     * Test si les actions s'éxécute le nombre de fois attendue ainsi que si la valeur retourné par StringAnimation est celle attendue.
     */
    @Test
    public void test() {
        endActionExecuted = 0;
        runningActionExecuted = 0;
        StringAnimation da = new StringAnimation("aaaa", 1000);
        da.setEndingAction(() -> endActionExecuted += 1);
        da.setRunningAction(() -> runningActionExecuted += 1);
        da.update(.5); //Simule un espacement de 500ms entre 2 mise-à-jour.
        Assertions.assertEquals(0, endActionExecuted);
        Assertions.assertEquals(1, runningActionExecuted);
        Assertions.assertEquals("aa", da.getActual());
        da.update(.5); //Simule un espacement de 500ms entre 2 mise-à-jour.
        Assertions.assertEquals(1, endActionExecuted);
        Assertions.assertEquals(2, runningActionExecuted);
        Assertions.assertEquals("aaaa", da.getActual());
        da.update(.5); //Simule un espacement de 500ms entre 2 mise-à-jour.
        Assertions.assertEquals(1, endActionExecuted);
        Assertions.assertEquals(2, runningActionExecuted);
        Assertions.assertEquals("aaaa", da.getActual());
    }

    /**
     * Test si les actions s'éxécute le nombre de fois attendue ainsi que si la valeur retourné par DoubleAnimation est celle attendue.
     */
    @Test
    public void test2() {
        endActionExecuted = 0;
        runningActionExecuted = 0;
        StringAnimation da = new StringAnimation("aa", 1000);
        da.setEndingAction(() -> endActionExecuted += 1);
        da.setRunningAction(() -> runningActionExecuted += 1);
        da.update(.5); //Simule un espacement de 500ms entre 2 mise-à-jour.
        Assertions.assertEquals(0, endActionExecuted);
        Assertions.assertEquals(1, runningActionExecuted);
        Assertions.assertEquals("a", da.getActual());
        da.update(.5); //Simule un espacement de 500ms entre 2 mise-à-jour.
        Assertions.assertEquals(1, endActionExecuted);
        Assertions.assertEquals(2, runningActionExecuted);
        Assertions.assertEquals("aa", da.getActual());
        da.update(.5); //Simule un espacement de 500ms entre 2 mise-à-jour.
        Assertions.assertEquals(1, endActionExecuted);
        Assertions.assertEquals(2, runningActionExecuted);
        Assertions.assertEquals("aa", da.getActual());
    }

}
