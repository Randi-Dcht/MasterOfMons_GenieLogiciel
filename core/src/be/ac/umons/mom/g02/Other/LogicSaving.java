package be.ac.umons.mom.g02.Other;

import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.MapObject;
import be.ac.umons.mom.g02.Objects.Characters.People;
import java.awt.Point;
import java.io.Serializable;


/**
 * This class allows to save the object as player, position, quest, item, etc
 * @author Umons_Group_2_ComputerScience
 */
public class LogicSaving implements Serializable
{

    /**
     * The player of the game
     */
    private People playerGame;
    /**
     * The date of the game
     */
    private Date dateGame;
    /**
     * The position of the player on the maps
     */
    private Point playerPosition;
    /**
     * The positions of all items on the maps
     */
    private MapObject.OnMapItem[] itemPosition;
    /**
     * The location of the people (TMX)
     */
    private Maps actualMap;


    /**
     * This constructor allows to save the object of the game
     * @param player    is the player of the game
     * @param date      is the actually date on the game
     * @param position  is the position of the player
     * @param list      is a list of position of item
     */
    public LogicSaving(People player, Date date, Point position, MapObject.OnMapItem[] list)
    {
        this(player,player.getMaps(),date,position,list);
    }


    /**
     * This constructor allows to save the object of the game
     * @param player    is the player of the game
     * @param date      is the actually date on the game
     * @param position  is the position of the player
     * @param list      is a list of position of item
     * @param map       is the actual maps of player
     */
    public LogicSaving(People player, Maps map, Date date, Point position, MapObject.OnMapItem[] list)
    {
        playerGame     = player;
        dateGame       = date;
        playerPosition = position;
        itemPosition   = list;
        actualMap      = map;

    }


    /**
     * This method allows to give the actual date of the ame
     * @return date in game
     */
    public Date getDate()
    {
        return dateGame;
    }


    /***/
    public Maps getMap()
    {
        return actualMap;
    }

    /**
     * This method return a list of position of item in the game
     * @return list of position
     */
    public MapObject.OnMapItem[] getItemPosition()
    {
        return itemPosition;
    }


    /**
     * The method return the player of the game
     * @return player of the game
     */
    public People getPlayer()
    {
        return playerGame;
    }


    /**
     * This method return the position of the player */
    public Point getPlayerPosition()
    {
        return playerPosition;
    }

    public void setPlayer(People playerGame) {
        this.playerGame = playerGame;
    }

    public void setPlayerPosition(Point playerPosition) {
        this.playerPosition = playerPosition;
    }
}
