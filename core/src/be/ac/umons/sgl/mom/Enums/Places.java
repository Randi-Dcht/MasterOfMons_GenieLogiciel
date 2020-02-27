package be.ac.umons.sgl.mom.Enums;

public enum Places
{

    ComputerRoom("RoomOfComputer"),
    RoomCourse("RoomOfCourse"),
    Bed("MyBed"),
    StudyRoom("RoomOfStudy");

    final String name;


    private Places(String name)
    {
        this.name = name;
    }
}
