package be.ac.umons.sgl.mom.Enums;

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
    public State getState()
    {
        return state;
    }
}
