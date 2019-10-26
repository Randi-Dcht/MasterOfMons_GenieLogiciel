package be.ac.umons.sgl.mom.Interfaces;

public interface Animation {
    void update(double dt);

    double getActual();

    boolean isFinished();
}
