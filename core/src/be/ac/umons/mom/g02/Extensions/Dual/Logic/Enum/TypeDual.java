package be.ac.umons.mom.g02.Extensions.Dual.Logic.Enum;


import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Extensions.Dual.Graphic.PlayCases;
import be.ac.umons.mom.g02.Extensions.Dual.Graphic.PlayingStateDual;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Quest.BattlePeople;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Quest.DualUnderQuest;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Quest.MoreCasesMons;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Quest.SurvivorVsMobile;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Quest.TakeFlag;
import be.ac.umons.mom.g02.GameStates.PlayingState;
import java.awt.Point;


/***/
public enum TypeDual
{
    DualPlayer(     "", BattlePeople.class    , PlayingStateDual.class,Maps.DualKiosk,new Point(11,20),new Point(1690,586)),
    CatchFlag(      "", TakeFlag.class        , PlayingStateDual.class,Maps.DualPark ,new Point(10,33) ,new Point(1674,476)),
    Survivor(       "", SurvivorVsMobile.class, PlayingStateDual.class,Maps.DualKiosk,new Point(14,16),new Point(1570,496)),
    OccupationFloor("", MoreCasesMons.class   , PlayCases.class,       Maps.DualPark ,new Point(10,33) ,new Point(1674,476));


    /***/
    final String name;


    /***/
    final Class<? extends DualUnderQuest> start;


    final Class<? extends PlayingState> graphic;


    /***/
    final Maps maps;


    /***/
    final Point pointOne;


    /***/
    final Point pointTwo;


    /***/
    TypeDual(String nameQuest, Class<? extends DualUnderQuest> startClass,Class<? extends PlayingState> graphic,Maps map, Point playerOnePos, Point playerTwoPos)
    {
        name  = nameQuest;
        start = startClass;
        maps  = map;
        pointOne = playerOnePos;
        pointTwo = playerTwoPos;
        this.graphic = graphic;
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


    public Class<? extends PlayingState> getGraphic()
    {
        return graphic;
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
