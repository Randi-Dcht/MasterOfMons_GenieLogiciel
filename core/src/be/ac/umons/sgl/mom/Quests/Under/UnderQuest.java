package be.ac.umons.sgl.mom.Quests.Under;

import be.ac.umons.sgl.mom.Events.Notifications.Notification;
import be.ac.umons.sgl.mom.Events.SuperviserNormally;
import be.ac.umons.sgl.mom.Quests.Quest;
import java.io.Serializable;

/**
 *La UnderQuest est une sous classe d'une MasterQuest ou d'elle-même.
 *Chaque underQuest est un objectif que le joueur doit faire.
 *Le joueur ne doit pas forcément réussir à 100% une underQuest pour réussir la MasterQuest
 *Chaque underQuest sont indépendant et existe en même temps dans la MasterQuest.
 *@author Randy Dauchot (étudiant en Sciences informatique)
 */

public abstract class UnderQuest implements Quest,Serializable
{
    /*name of this UnderQuest*/
    final String name;
    /*the percent maximum to succeed the quest */
    final double percentMax;
    /*the percent of progress of this Quest*/
    protected double progress = 0;
    /*this variable allows to know if finish*/
    protected boolean finish = false;
    /*The Quest overhead */
    protected Quest master;


    /**
     * This constructor allows define the underQuest
     * @param name is the name of this Quest
     * @param max is the maximum percent of progress
     * @param master is the quest who call this
     */
    public UnderQuest(String name,double max,Quest master)
    {
        this.name   = name;
        percentMax  = max;
        this.master = master;
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
     * This method check if the actual Quest is finish with the percent
     */
    private void finished()
    {
        if(progress >= percentMax)
            finish = true;
    }


    /**
     * This method allows to add the progress of advancement this quest
     */
    public void addProgress(double many)
    {
        progress = progress + many;
        master.addProgress(many);
        finished();
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
     * @param notify
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
        return SuperviserNormally.getSupervisor().getGraphic().getStringFromId(name);
    }


    /**
     * This method return the number of underQuest of this Quest
     * @return number of quest
     */
    public abstract int getTotalSubQuestsNumber();
}
