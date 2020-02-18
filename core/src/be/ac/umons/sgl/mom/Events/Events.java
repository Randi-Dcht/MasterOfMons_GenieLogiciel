package be.ac.umons.sgl.mom.Events;

public enum Events
{
    ChangeDay("changeDay"),                       /*...*/
    ChangeQuest("quest"),                     /*...*/
    ChangeMonth("month"),                     /*...*/
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
