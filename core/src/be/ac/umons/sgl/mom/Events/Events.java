package be.ac.umons.sgl.mom.Events;

public abstract class Events
{
    private static int num = 0;
    final int id;
    final String name;
    protected boolean see = true;

    public Events(String name)
    {
        this.name = name;
        this.id = num;
        num++;
    }

    public abstract void run();
}
