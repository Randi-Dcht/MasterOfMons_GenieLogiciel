package be.ac.umons.sgl.mom.Events;

public enum Events
{
    HourTimer("timer"),                       /*...*/
    ChangeQuest("quest"),                     /*...*/
    ChangeFrame("frame"),                     /*...*/
    PlaceInMons("placeOfPeople"),             /*...*/
    MeetOther("meetOther"),                   /*...*/
    Attack("LaunchAttack"),
    AddFriend("meetFriend"),                  /*...*/
    Answer("answerQuestion"),                 /*...*/
    Dead("characterDead"),                    /*...*/
    UpLevel("UpLevel");                       /*...*/

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
