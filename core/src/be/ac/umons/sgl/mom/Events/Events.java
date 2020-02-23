package be.ac.umons.sgl.mom.Events;

/**
 * This class enum the events in this Game
 * Every event is associate to Notification
 * @author Umons_Group_2_ComputerScience
 */
public enum Events
{
    ChangeDay("changeDay"),                   /*...*/
    ChangeQuest("quest"),                     /*...*/
    ChangeMonth("month"),                     /*...*/
    PlaceInMons("placeOfPeople"),             /*...*/
    MeetOther("meetOther"),                   /*...*/
    Attack("LaunchAttack"),                   /*...*/
    AddFriend("meetFriend"),                  /*...*/
    Dialog("DialogArray"),                    /*...*/
    Answer("answerQuestion"),                 /*...*/
    Dead("characterDead"),                    /*...*/
    UpLevel("UpLevel"),                       /*...*/
    UseItems("PeopleUseItem");                /*...*/


    /**The name of the events*/
    private String name;


    /**
     * The constructor of the event
     * @param name is the name of the event
     */
    private Events(String name)
    {
        this.name = name;
    }


    /**
     * This method allows to know if the same events
     * @return boolean, true if same else false
     */
    public boolean equals(Events evt)
    {
        return evt.name.equals(this.name);
    }
}
