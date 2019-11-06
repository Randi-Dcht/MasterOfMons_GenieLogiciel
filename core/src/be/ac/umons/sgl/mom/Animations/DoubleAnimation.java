package be.ac.umons.sgl.mom.Animations;

import be.ac.umons.sgl.mom.Interfaces.Animation;

public class DoubleAnimation implements Animation {
    protected double time;
    protected double actualState;
    protected double from;
    protected double to;
    protected double toAddBySecond;
    protected Runnable runningAction;
    protected Runnable endingAction;

    public DoubleAnimation(double from, double to, double time) {
        this.time = time;
        this.from = from;
        this.actualState = from;
        this.to = to;

        toAddBySecond = (to - from) / (time / 1000);
    }
    public DoubleAnimation(double from, double to, double time, Runnable runningAction, Runnable endCallback) {
        this(from, to, time);
        this.runningAction = runningAction;
        this.endingAction = endCallback;
    }

    public void update(double dt) {
        if (actualState >= to)
            return;

        actualState = actualState + toAddBySecond * dt;
        if (actualState > to) {
            actualState = to;
            if (endingAction != null)
                endingAction.run();
            return;
        }
        if (runningAction != null)
            runningAction.run();

    }

    public double getActual() {
        return actualState;
    }

    public boolean isFinished() {
        return Double.compare(actualState, to) == 0;
    }

    public void setRunningAction(Runnable run) {
        this.runningAction = run;
    }

    public void setEndingAction(Runnable run) {
        this.endingAction = run;
    }

    public Runnable getRunningAction() {
        return runningAction;
    }

    public Runnable getEndingAction() {
        return endingAction;
    }
}
