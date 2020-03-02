package be.ac.umons.mom.g02.Enums;

/**
 * This enum define the bloc in the five years (University) :
 * BA1 = bachelor 1
 * BA2 = bachelor 2
 * BA3 = bachelor 3
 * MA1 = master 1
 * MA2 = master 2
 * @author Umons_Group_2_ComputerScience
 */
public enum Bloc
{
    BA1(1,5,1,7),
    BA2(5,10,8,10),
    BA3(10,15,12,15),
    MA1(17,22,20,22),
    MA2(27,35,25,28),
    Extend(0,0,0,0);


    /*this is a min for a mobile (computer) */
    private int minMob;
    /*this is a max for a mobile (computer) */
    private int maxMob;
    /*this is a min for a people (human) */
    private int minPeople;
    /*this is a max for a people (human) */
    private int maxPeople;


    /**
     * This is a constructor for this enum
     * @param maxMob is the maximum for a mobile
     * @param minMob is the minimum for a mobile
     * @param maxPeople is the maximum for a people
     * @param minPeople is the minimum for a people
     */
    private Bloc(int minMob,int maxMob,int minPeople,int maxPeople)
    {
        this.minMob    = minMob;
        this.maxMob    = maxMob;
        this.minPeople = minPeople;
        this.maxPeople = maxPeople;
    }


    /**
     * This method returns the minimum for a mobile
     * @return number of minimum
     */
    public int getMinMob()
    {
        return minMob;
    }


    /**
     * This method returns the maximum for a mobile
     * @return number of maximum
     */
    public int getMaxMob()
    {
        return maxMob;
    }


    /**
     * This method returns the minimum for a people
     * @return number of minimum
     */
    public int getMinPeople()
    {
        return minPeople;
    }


    /**
     * This method returns the maximum for a people
     * @return number of maximum
     */
    public int getMaxPeople()
    {
        return maxPeople;
    }
}
