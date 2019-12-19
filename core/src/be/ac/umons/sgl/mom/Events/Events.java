package be.ac.umons.sgl.mom.Events;

public enum Events
{
    HourTimer("timer"),
    ChangeQuest("quest");

    private String name;

    private Events(String name)
    {
        this.name = name;
    }

    public boolean equals(Events evt)
    {
        return evt.name.equals(this.name);
    }
}
