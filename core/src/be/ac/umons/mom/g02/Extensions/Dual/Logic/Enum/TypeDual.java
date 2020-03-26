package be.ac.umons.mom.g02.Extensions.Dual.Logic.Enum;


import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Quest.*;
import be.ac.umons.mom.g02.Quests.Under.UnderQuest;

public enum TypeDual
{
    DualPlayer(     "", BattlePeople.class   ,Maps.DualKiosk),
    CatchFlag(      "", TakeFlag.class        ,Maps.DualPark),
    Survivor(       "", SurvivorVsMobile.class,Maps.DualKiosk),
    OccupationFloor("", MoreCasesMons.class   ,Maps.DualPark);


    /***/
    final String name;


    /***/
    final Class<? extends DualUnderQuest> start;


    /***/
    final Maps maps;


    /***/
    TypeDual(String nameQuest, Class<? extends DualUnderQuest> startClass, Maps map)
    {
        name  = nameQuest;
        start = startClass;
        maps  = map;
    }


    /***/
    public String getName()
    {
        return name;
    }


    /***/
    public Class<? extends DualUnderQuest> getStart()
    {
        return start;
    }


    public Maps getStartMaps()
    {
        return maps;
    }
}
