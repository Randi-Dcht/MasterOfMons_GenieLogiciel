package be.ac.umons.sgl.mom.Superviser;

public interface Observable
{
    public void add(Observer obs);
    public void remove(Observer obs);
    public void notif();
}
