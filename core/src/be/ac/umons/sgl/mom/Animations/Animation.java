package be.ac.umons.sgl.mom.Animations;

/**
 * Abstract class representing the base of an animation.
 * @author Guillaume Cardoen
 */
public abstract class Animation<T> {

    /**
     * Action to execute each time the value is updated
     */
    protected Runnable runningAction;
    /**
     * Action to execute when the animation is finished.
     */
    protected Runnable endingAction;

    /**
     * Actual value
     */
    protected T actual;

    /**
     * Ending value
     */
    protected T to;

    /**
     * Animation's duration
     */
    protected double time;

    /**
     * Create a new animation
     * @param from Starting value
     * @param to Ending value
     * @param time Duration
     */
    public Animation(T from, T to, double time) {
        this.actual = from;
        this.to = to;
        this.time = time;
    }

    /**
     * Create a new animation
     * @param from Starting value
     * @param to Ending value
     * @param time Duration
     * @param runningAction Action to execute each time the value is updated
     * @param endCallback Action to execute when the animation is finished.
     */
    public Animation(T from, T to, double time, Runnable runningAction, Runnable endCallback) {
        this(from, to, time);
        this.runningAction = runningAction;
        this.endingAction = endCallback;
    }

    /**
     * Update the actual value.
     * @param dt Delta Time
     */
    public abstract void update(double dt);
    /**
     * Return if the animation is finished
     * @return If the animation is finished
     */
    public abstract boolean isFinished();
    /**
     * Return the actual value
     * @return The actual value
     */
    public T getActual() {
        return actual;
    }
    /**
     * Set the action to execute each time the value is updated
     * @param run The action to execute each time the value is updated
     */
    public void setRunningAction(Runnable run) {
        this.runningAction = run;
    }

    /**
     * Set the action to execute when the animation is finished.
     * @param run The action to execute when the animation is finished.
     */
    public void setEndingAction(Runnable run) {
        this.endingAction = run;
    }

    /**
     * @return The action to execute each time the value is updated
     */
    public Runnable getRunningAction() {
        return runningAction;
    }

    /**
     * @return The action to execute each time the value is updated
     */
    public Runnable getEndingAction() {
        return endingAction;
    }

    /**
     * End the animation and set the actual value to the ending value.
     */
    public void finishNow() {
        actual = to;
    }
}
