package be.ac.umons.mom.g02.Quests.Under;

import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Quests.Quest;
import java.io.Serializable;


/**
 * The UnderQuest is a subclass of a MasterQuest or of itself.
 * Each underQuest is a goal that the player must do.
 * The player does not necessarily have to pass 100% an underQuest to pass the MasterQuest
 * Each underQuest are independent and exist at the same time in the MasterQuest.
 * @author Umons_Group_2_ComputerScience
 */
public abstract class UnderQuest implements Quest,Serializable
{
    /**
     * Name of this UnderQuest
     */
    final String name;
    /**
     * The percent maximum to succeed the quest
     */
    final double percentMax;
    /**
     * The percent of progress of this Quest
     */
    protected double progress = 0;
    /**
     * This variable allows to know if finish
     */
    protected boolean finish = false;
    /**
     * The Quest overhead
     */
    protected Quest master;
    /**
     * The people who play this game
     */
    protected People people;


    /**
     * This constructor allows define the underQuest
     * @param name is the name of this Quest
     * @param max is the maximum percent of progress
     * @param master is the quest who call this
     * @param people is the people who play the game
     */
    public UnderQuest(String name, double max, Quest master, People people)
    {
        this.name   = name;
        percentMax  = max;
        this.master = master;
        this.people = people;
    }


    /**
     * This method return the actual progress of this Quest
     * @return number between 0 et 0.99
     */
    public double getProgress()
    {
        return (progress/percentMax);
    }


    /**
     * This method allows to pass this Quest for the particular item or #DEBUG#
     */
    public void passQuest()
    {
        progress = percentMax;
        finish   = true;
    }


    /**
     * This method check if the actual Quest is finish with the percent
     */
    private void finished()
    {
        if(progress >= percentMax)
            finish = true;
    }


    /**
     * This method allows to add the progress of advancement this quest
     * @param many is the percent to succeed of quest
     */
    public void addProgress(double many)
    {
        if (progress+many<0)
            progress = 0;
        else
        {
            if (!finish)
            {
                progress += many;
                master.addProgress(many);
            }
            finished();
        }
    }


    /**
     * This method return the advancement of this Quest
     * @return number between 0 and maximum percent
     */
    public double getAdvancement()
    {
        return progress;
    }


    /**
     * This method allows to say of the Quest isn't finish or not
     * @return boolean
     */
    public boolean isActive()
    {
        return !finish;
    }


    /**
     * This method allows to say of the Quest is finish or not
     * @return boolean
     */
    public boolean isFinished()
    {
        return finish;
    }


    /**
     * This method is called when the action occurs
     * @param notify is the notification of the game
     */
    public abstract void evenActivity(Notification notify);


    /**
     * This method return the underQuest of this Quest
     * @return list of the Quest
     */
    public abstract Quest[] getSubQuests();


    /**
     * This method return the name of this Quest
     * @return name of this Quest
     */
    public String getName()
    {
        try
        {
            return GraphicalSettings.getStringFromId(name);
        }
        catch (Exception i)
        {
            return "ERROR";
        }
    }


    /**
     * Doesn't translate the name of the underQuest
     * @param translate if the text must be translate
     */
    public String getName(boolean translate)
    {
        if (translate)
            return getName();
        return name;
    }


    /**
     * This method return the number of underQuest of this Quest
     * @return number of quest
     */
    public abstract int getTotalSubQuestsNumber();


    /**
     * Explain the goal of this underQuest to succeed this or not
     * @return the explication of underQuest
     */
    public /*abstract*/ String explainGoal(){return "NONE";}
}
