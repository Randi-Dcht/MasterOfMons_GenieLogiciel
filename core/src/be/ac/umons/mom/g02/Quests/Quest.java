package be.ac.umons.mom.g02.Quests;

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
    double getProgress();


    /**
     * This method allows to know if the Quest is active or not
     * @return boolean of active
     */
    boolean isActive();


    /**
     * This method returns the under of this Quest
     * @return the under Quest of this
     */
    Quest[] getSubQuests();


    /**
     * This method returns the name of the Quest
     * @return the name of the Quest
     */
    String getName();


    /**
     * This method returns the number of the depth of the recursion
     * @return the number of depth
     */
    int getTotalSubQuestsNumber();


    /**
     * This method allows to give the percent to the progress of Quest
     */
    void addProgress(double many);


    /**
     * This method allows to kown if the Quest is finish
     * @return the boolean of finish
     */
    boolean isFinished();
}
