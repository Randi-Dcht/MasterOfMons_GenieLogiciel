package be.ac.umons.sgl.mom.Events;

public interface Observable
{
    void notify(Notification notif);
    void add(Events evt,Observer ... obs);
    void remove(Events evt, Observer ... obs);
    void remove(Events evt);
}
