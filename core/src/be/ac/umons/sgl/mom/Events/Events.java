package be.ac.umons.sgl.mom.Events;

public enum Events
{
    HourTimer("timer"),                       /*...*/
    ChangeQuest("quest"),                     /*...*/
    PlaceGrandAmphi("placeNiGA"),             /*...*/
    PlaceDeVinci("placeNiDV"),                /*...*/
    PlaceHoudeng("placeHG"),                  /*...*/
    PlaceCenterMons("placeMonsCenter"),       /*...*/
    PlaceKot("kot"),                          /*...*/
    MeetOther("meetOther"),                   /*...*/
    AddFriend("meetFriend");                  /*...*/

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
