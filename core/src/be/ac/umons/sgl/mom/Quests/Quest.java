package be.ac.umons.sgl.mom.Quests;

/**
 *This interface define the MasterQuest and UnderQuest with the same method
 *@author Umons_Group_2_ComputerScience_RandyDauchot
 */

public interface Quest
{
    /**
     *This method return the progress for the graphic
     * @return number between 0 and 1
     */
    public double getProgress();


    /**
     * This method allows to know if the Quest is active or not
     * @return boolean of active
     */
    public boolean isActive();


    /**
     * This method returns the under of this Quest
     * @return the under Quest of this
     */
    public Quest[] getSubQuests();


    /**
     * This method returns the name of the Quest
     * @return the name of the Quest
     */
    public String getName();


    /**
     * This method returns the number of the depth of the recursion
     * @return the number of depth
     */
    public int getTotalSubQuestsNumber();


    /**
     * This method allows to give the percent to the progress of Quest
     */
    public void addProgress(double many);


    /**
     * This method allows to kown if the Quest is finish
     * @return the boolean of finish
     */
    public boolean isFinished();
}
