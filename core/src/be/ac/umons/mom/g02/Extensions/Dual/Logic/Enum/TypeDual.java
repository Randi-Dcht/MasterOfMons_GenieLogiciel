package be.ac.umons.mom.g02.Extensions.Dual.Logic.Enum;


import be.ac.umons.mom.g02.Enums.Maps;
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
    DualPlayer(     "", BattlePeople.class    , PlayingStateDual.class,Maps.DualKiosk,new Point(15,15),new Point(15,15)),
    CatchFlag(      "", TakeFlag.class        , PlayingStateDual.class,Maps.DualPark ,new Point(7,24),new Point(8,24)),
    Survivor(       "", SurvivorVsMobile.class, PlayingStateDual.class,Maps.DualKiosk,new Point(15,15),new Point(15,15)),
    OccupationFloor("", MoreCasesMons.class   , PlayingStateDual.class,Maps.DualPark ,new Point(8,24),new Point(8,24));


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
