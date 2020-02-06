package be.ac.umons.sgl.mom.Enums;

public enum Bloc
{
    BA1(1,5,1,7),
    BA2(5,10,8,10),
    BA3(10,15,12,15),
    MA1(17,22,20,22),
    MA2(27,35,25,28),
    Extend(0,0,0,0);


    private int minMob;
    private int maxMob;
    private int minPeople;
    private int maxPeople;

    private Bloc(int minMob,int maxMob,int minPeople,int maxPeople)
    {
        this.minMob    = minMob;
        this.maxMob    = maxMob;
        this.minPeople = minPeople;
        this.maxPeople = maxPeople;
    }

    public int getMinMob()
    {
        return minMob;
    }

    public int getMaxMob()
    {
        return maxMob;
    }

    public int getMinPeople()
    {
        return minPeople;
    }

    public int getMaxPeople()
    {
        return maxPeople;
    }
}
