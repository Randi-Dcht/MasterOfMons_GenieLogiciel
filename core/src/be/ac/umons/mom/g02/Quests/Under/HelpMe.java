package be.ac.umons.mom.g02.Quests.Under;

import be.ac.umons.mom.g02.Enums.Places;
import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.Answer;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Quests.Quest;


/**
 * This class define the goals when the player go to help the other PNJ
 * @author Umons_Group_2_ComputerScience_RandyDauchot
 */
public class HelpMe extends UnderQuest
{
    /**
     * This constructor allows to define a goals of help an other people (PNJ)
     * @param master is the above this
     * @param max    is the maximum percent to succeed this
     * @param people is the player plays the game
     */
    public HelpMe(Quest master, int max, People people)
    {
        super("HelpMe",max,master,people);
    }


    /**
     * This method allows to give the activity of the player in the game
     * @param notify is the notification in the game
     */
    @Override
    public void evenActivity(Notification notify)
    {
        if (notify.getEvents().equals(Events.MeetOther) && notify.bufferNotEmpty() && notify.getBuffer().getClass().equals(Mobile.class))
            goToPnj((Mobile) notify.getBuffer());

        if (notify.getEvents().equals(Events.Answer) && notify.bufferNotEmpty() && !notify.getBuffer().equals("ESC"))
            percentPassPnj(((Answer)notify).getBuffer());
    }


    /**
     * This method check if the people go to speak with a PNJ who have help
     * @param mobile is the mobile who have help
     */
    private void goToPnj(Mobile mobile)
    {
        addProgress(0.1);
    }



    /**
     * This method check if the people give a syntheses to help the PNJ in the game
     */
    private void percentPassPnj(String answer)
    {
        if (answer.equals("HELPMe"))
            addProgress(0.3);
        if (answer.equals("PASSITEMYES"))
            addProgress(1);
    }


    /**
     * Ask to the PNJ if he want to study with the people
     */
    private void studyWithPnj(Mobile mobile)
    {
        if (people.getPlace().equals(Places.StudyRoom))
            addProgress(0.7);
    }


    /**
     * This method allows to give the goals Quest of this UnderQuest
     * @return list of underQuest
     */
    public Quest[] getSubQuests()
    {
        return new Quest[]{};
    }


    /**
     * This method allows to say the number of the underQuest under this
     * @return the number of under
     */
    public int getTotalSubQuestsNumber()
    {
        return 0;
    }


    /**
     * Explain the goal of this underQuest to succeed this or not
     * @return the explication of underQuest
     */
    @Override
    public String explainGoal()
    {
        return "NONE";
    }
}
