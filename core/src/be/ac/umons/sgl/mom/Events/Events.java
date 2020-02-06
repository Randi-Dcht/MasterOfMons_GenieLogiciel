package be.ac.umons.sgl.mom.Events;

public enum Events
{
    HourTimer("timer"),                       /*...*/
    ChangeQuest("quest"),                     /*...*/
    ChangeFrame("frame"),                     /*...*/
    PlaceGrandAmphi("placeNiGA"),             /*...*/
    PlaceDeVinci("placeNiDV"),                /*...*/
    PlaceHoudeng("placeHG"),                  /*...*/
    PlaceCenterMons("placeMonsCenter"),       /*...*/
    PlaceKot("kot"),                          /*...*/
    MeetOther("meetOther"),                   /*...*/
    AddFriend("meetFriend"),                  /*...*/
    Answer("answerQuestion"),                 /*...*/
    Dead("characterDead");                    /*...*/

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
