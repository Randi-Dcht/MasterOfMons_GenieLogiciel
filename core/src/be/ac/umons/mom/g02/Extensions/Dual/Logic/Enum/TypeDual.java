package be.ac.umons.mom.g02.Extensions.Dual.Logic.Enum;


import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Quest.BattlePeople;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Quest.DualUnderQuest;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Quest.MoreCasesMons;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Quest.SurvivorVsMobile;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Quest.TakeFlag;

import java.awt.*;

public enum TypeDual
{
    DualPlayer(     "", BattlePeople.class   ,Maps.DualKiosk,new Point(0,0),new Point(0,0)),
    CatchFlag(      "", TakeFlag.class        ,Maps.DualPark,new Point(0,0),new Point(0,0)),
    Survivor(       "", SurvivorVsMobile.class,Maps.DualKiosk,new Point(0,0),new Point(0,0)),
    OccupationFloor("", MoreCasesMons.class   ,Maps.DualPark,new Point(0,0),new Point(0,0));


    /***/
    final String name;


    /***/
    final Class<? extends DualUnderQuest> start;


    /***/
    final Maps maps;


    /***/
    final Point pointOne;


    /***/
    final Point pointTwo;


    /***/
    TypeDual(String nameQuest, Class<? extends DualUnderQuest> startClass, Maps map, Point playerOnePos, Point playerTwoPos)
    {
        name  = nameQuest;
        start = startClass;
        maps  = map;
        pointOne = playerOnePos;
        pointTwo = playerTwoPos;
    }


    /***/
    public Point getPointPlayerOne()
    {
        return pointOne;
    }


    /***/
    public Point getPointPlayerTwo()
    {
        return pointTwo;
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
