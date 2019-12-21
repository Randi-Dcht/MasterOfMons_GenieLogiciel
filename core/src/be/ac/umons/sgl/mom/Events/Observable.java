package be.ac.umons.sgl.mom.Events;

public interface Observable
{
    void notify(Events evt);
    void add(Events evt,Observer ... obs);
    void remove(Events evt, Observer ... obs);
    void remove(Events evt);
}
