package be.ac.umons.mom.g02.Extensions.Dual.Logic.Enum;


import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Extensions.Dual.Graphic.PlayCases;
import be.ac.umons.mom.g02.Extensions.Dual.Graphic.PlayingFlag;
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
    DualPlayer(     "combat", BattlePeople.class    , PlayingStateDual.class,Maps.DualKiosk,new Point(11,20),new Point(1690,586),true),
    CatchFlag(      "flag", TakeFlag.class        , PlayingFlag.class     ,Maps.DualPark ,new Point(10,33) ,new Point(2026,653),true),
    Survivor(       "survivor", SurvivorVsMobile.class, PlayingStateDual.class,Maps.DualKiosk,new Point(14,16),new Point(1570,496),false),
    OccupationFloor("occupation", MoreCasesMons.class   , PlayCases.class,       Maps.DualPark ,new Point(5,40) ,new Point(1920,865),true);


    /**
     * The name of the dual
     */
    final String name;


    /**
     * The underQuest of the dual
     */
    final Class<? extends DualUnderQuest> start;


    /**
     * The playingState of the dual
     */
    final Class<? extends PlayingState> graphic;


    /**
     * The maps of the dual
     */
    final Maps maps;


    /**
     * The init position of player one
     */
    final Point pointOne;


    /**
     * The init position of player second
     */
    final Point pointTwo;


    /**
     * If the player can relive
     */
    final boolean relife;


    /**
     * This constructor define the type of the dual
     */
    TypeDual(String nameQuest, Class<? extends DualUnderQuest> startClass,Class<? extends PlayingState> graphic,
             Maps map, Point playerOnePos, Point playerTwoPos,boolean relife)
    {
        name  = nameQuest;
        start = startClass;
        maps  = map;
        pointOne = playerOnePos;
        pointTwo = playerTwoPos;
        this.graphic = graphic;
        this.relife  = relife;
    }


    /**
     * @return the init point of player one
     */
    public Point getPointPlayerOne()
    {
        return pointOne;
    }


    /**
     * @return the init point of player second
     */
    public Point getPointPlayerTwo()
    {
        return pointTwo;
    }


    /**
     * @return the playingState to play the dual
     */
    public Class<? extends PlayingState> getGraphic()
    {
        return graphic;
    }


    /**
     * @return the name of the dual
     */
    public String getName()
    {
        return name;
    }


    /**
     * @return the underQuest of dual
     */
    public Class<? extends DualUnderQuest> getStart()
    {
        return start;
    }


    /**
     * @return the maps of the dual
     */
    public Maps getStartMaps()
    {
        return maps;
    }


    /**
     * if the player can relive
     */
    public boolean canReLife()
    {
        return relife;
    }
}
