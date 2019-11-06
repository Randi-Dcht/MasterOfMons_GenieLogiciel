package be.ac.umons.sgl.mom.Interfaces;

public interface Animation {
    void update(double dt);

    double getActual();

    boolean isFinished();

    Runnable getEndingAction();
    Runnable getRunningAction();
    void setEndingAction(Runnable action);
    void setRunningAction(Runnable action);
}
