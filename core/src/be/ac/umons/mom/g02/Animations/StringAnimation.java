package be.ac.umons.mom.g02.Animations;

/**
 * Animate a String in a given time.
 * @author Guillaume Cardoen
 */
public class StringAnimation extends Animation<String> {

    /**
     * The number of chars to add each second.
     */
    double toAddBySecond;
    /**
     * The number of char actually added.
     */
    double actualNbr = 0;

    /**
     * Create a new animation.
     * @param s The string to animate.
     * @param time The duration
     */
    public StringAnimation(String s, double time) {
        super("", s, time);
        toAddBySecond = (to.length()) / (time / 1000);
    }

    /**
     * Create a new animation.
     * @param s The string to animate.
     * @param time The duration
     * @param runningAction Action to execute each time the value is updated
     * @param endCallback Action to execute when the animation is finished.
     */
    public StringAnimation(String s, double time, Runnable runningAction, Runnable endCallback) {
        super("", s, time, runningAction, endCallback);
    }

    @Override
    public void update(double dt) {
        if ((toAddBySecond > 0 && actual.length() >= to.length()) || (toAddBySecond < 0 && actual.length() == 0)) // if < 0, inverted => actual = ""
            return;

        actualNbr += dt * toAddBySecond;
        if (actualNbr > to.length())
            actualNbr = to.length();
        if (actualNbr < 1)
            actual = "";
        else
            actual = to.substring(0, (int)actualNbr);
        if (runningAction != null)
            runningAction.run();
        if ((toAddBySecond > 0 && actual.length() >= to.length()) || (toAddBySecond < 0 && actual.length() == 0)) {
            if (toAddBySecond > 0)
                actual = to;
            if (endingAction != null)
                endingAction.run();
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    /**
     * Invert the animation ((add -> | <- remove) character by character)
     */
    public void invert() {
        toAddBySecond = -toAddBySecond;
        actualNbr = to.length() - 1;
        actual = to;
    }
}
