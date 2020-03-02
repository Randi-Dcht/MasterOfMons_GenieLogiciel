package be.ac.umons.mom.g02.Enums;

public enum Places
{

    /***/
    ComputerRoom("Computer",State.listen),
    /***/
    RoomCourse("Course",State.listen),
    /***/
    Bed("Bed",State.nap),
    /***/
    StudyRoom("Study",State.study),
    /***/
    OnTheMap("Maps",State.normal);

    final String name;
    final State state;


    /***/
    private Places(String name,State state)
    {
        this.name  = name;
        this.state = state;
    }

    /***/
    public String toString()
    {
        return name;
    }


    /***/
    public String getIdName()
    {
        return "ID"+name;
    }


    /***/
    public String getIdUse()
    {
        return "ID"+name+"_Use";
    }


    /***/
    public State getState()
    {
        return state;
    }
}
