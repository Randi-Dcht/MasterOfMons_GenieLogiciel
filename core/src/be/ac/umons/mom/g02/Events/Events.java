package be.ac.umons.mom.g02.Events;

/**
 * This class enum the events in this Game
 * Every event is associate to Notification
 * @author Umons_Group_2_ComputerScience_RandyDauchot
 */
public enum Events
{
    ChangeDay("changeDay"),                   /*This event notify when the day changes in the game*/
    ChangeQuest("quest"),                     /*This event notify when the quest changes*/
    ChangeMonth("month"),                     /*This event notify when the month change in the game*/
    ChangeHour("hour"),                       /*This event notify when the hour change in the game*/
    PlaceInMons("PeopleOnMaps"),              /*This event notify when the people change the maps*/
    EntryPlace("PlaceOnMap"),                 /**/
    OtherInformation("OtherInfo"),            /**/
    LowSomething("low"),                      /**/
    DisplayMessage("Display"),
    Shop("shop"),
    MeetOther("meetOther"),                   /*This event notify when the character meet an other character*/
    Attack("LaunchAttack"),                   /*This event notify when the character attack other character*/
    AddFriend("meetFriend"),                  /*This event notify when the people add friend*/
    Dialog("DialogArray"),                    /*This event notify when the class launch a dialog*/
    Answer("answerQuestion"),                 /*This event notify when the player answer to the dialog*/
    Dead("characterDead"),                    /*This event notify when the character is dead*/
    UpLevel("UpLevel"),                       /*This event notify when the people up the level*/
    UseItems("PeopleUseItem"),                /*This event notify when the people uses an item*/
    LifeChanged("LifeChanged"),
    ExperienceChanged("ExperienceChanged"),
    EnergyChanged("EnergyChanged"),
    MoneyChanged("MoneyChanged"),
    Teleport("Teleport"),;


    /**The name of the events*/
    private String name;


    /**
     * The constructor of the event
     * @param name is the name of the event
     */
    Events(String name)
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
