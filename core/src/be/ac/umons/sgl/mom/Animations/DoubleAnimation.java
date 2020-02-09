package be.ac.umons.sgl.mom.Animations;

/**
 * Animate a double in a given time.
 * @author Guillaume Cardoen
 */
public class DoubleAnimation extends Animation<Double> {
    /**
     * Value to add each second
     */
    private double toAddBySecond;


    /**
     * Create a new animation
     * @param from Starting value
     * @param to Ending value
     * @param time Duration
     */
    public DoubleAnimation(double from, double to, double time) {
        super(from, to, time);

        toAddBySecond = (to - from) / (time / 1000);
    }

    /**
     * Create a new animation
     * @param from Starting value
     * @param to Ending value
     * @param time Duration
     * @param runningAction Action to execute each time the value is updated
     * @param endCallback Action to execute when the animation is finished.
     */
    public DoubleAnimation(double from, double to, double time, Runnable runningAction, Runnable endCallback) {
        this(from, to, time);
        this.runningAction = runningAction;
        this.endingAction = endCallback;
    }

    @Override
    public void update(double dt) {
        if ((toAddBySecond > 0 && actual >= to) || (toAddBySecond < 0 && actual <= to))
            return;

        actual = actual + toAddBySecond * dt;
        if (runningAction != null)
            runningAction.run();
        if ((toAddBySecond > 0 && actual >= to) || (toAddBySecond < 0 && actual <= to)) {
            actual = to;
            if (endingAction != null)
                endingAction.run();
        }
    }

    @Override
    public boolean isFinished() {
        return Double.compare(actual, to) == 0;
    }
}
