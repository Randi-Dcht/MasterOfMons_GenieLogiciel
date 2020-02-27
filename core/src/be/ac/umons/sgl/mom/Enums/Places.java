package be.ac.umons.sgl.mom.Enums;

public enum Places
{

    /***/
    ComputerRoom("RoomOfComputer",State.listen),
    /***/
    RoomCourse("RoomOfCourse",State.listen),
    /***/
    Bed("MyBed",State.nap),
    /***/
    StudyRoom("RoomOfStudy",State.study),
    /***/
    OnTheMap("OnTheMaps",State.normal);

    final String name;
    final State state;


    /***/
    private Places(String name,State state)
    {
        this.name  = name;
        this.state = state;
    }


    /***/
    public State getState()
    {
        return state;
    }
}
