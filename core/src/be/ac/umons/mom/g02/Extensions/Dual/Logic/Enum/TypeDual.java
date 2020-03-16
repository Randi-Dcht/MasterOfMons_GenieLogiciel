package be.ac.umons.mom.g02.Extensions.Dual.Logic.Enum;


import be.ac.umons.mom.g02.Quests.Master.MasterQuest;

public enum TypeDual
{
    DualPlayer(     "",null),
    CatchFlag(      "",null),
    Survivor(       "",null),
    OccupationFloor("",null);


    /***/
    final String name;


    /***/
    final Class<MasterQuest> start;


    /***/
    TypeDual(String nameQuest, Class<MasterQuest> startClass)
    {
        name  = nameQuest;
        start = startClass;
    }


    /***/
    public String getName()
    {
        return name;
    }


    /***/
    public Class<MasterQuest> getStart()
    {
        return start;
    }
}
