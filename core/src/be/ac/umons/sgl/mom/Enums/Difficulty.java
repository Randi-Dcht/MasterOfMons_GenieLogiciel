package be.ac.umons.sgl.mom.Enums;

/**
 * This class define the difficulty of the game
 * @author Umons_Group_2_ComputerScience
 */
public enum Difficulty
{
    Easy("Easy",4,85,5,3),
    Medium("Medium",2,93,4,5),
    Hard("Hard",0,99,3,8);

    /**The difference to have a MasterQuest*/
    private int upLevel;
    /**The percent maximum of the MasterQuest*/
    private int percent;
    /**The maximum of the items in the bag*/
    private int object;
    /**The name of the level*/
    private String name;
    /**The number of the the moving pnj in the all map*/
    private int numberPNJ;


    /**
     * This constructor define a level of the game
     * @param upLevel is the difference of level to have a quest
     * @param percent is the maximum of MasterQuest
     * @param object is the maximum of item in the bag
     */
    private Difficulty(String name,int upLevel,int percent, int object,int numberMovingPNJ)
    {
        this.name    = name;
        this.object  = object;
        this.percent = percent;
        this.upLevel = upLevel;
        numberPNJ    = numberMovingPNJ;
    }


    /**
     * This method return the difference of level
     * @return number of difference
     */
    public int getUpLevel()
    {
        return upLevel;
    }


    /**
     * This method return the maximum of percent Quest
     * @return  number of maximum
     */
    public int getMaxPercent()
    {
        return percent;
    }


    /**
     * This method return the number of Items in the bag
     * @return number of item in the bag
     */
    public int getManyItem()
    {
        return object;
    }


    /**
     * This method return the name of the level
     * @return name of level
     */
    public String getName()
    {
        return name;
    }


    /**
     * This method return the number of the mobile pnj in the all maps of the game
     * @return  the number of the moving pnj
     */
    public int getNumberPNJ()
    {
        return numberPNJ;
    }
}
