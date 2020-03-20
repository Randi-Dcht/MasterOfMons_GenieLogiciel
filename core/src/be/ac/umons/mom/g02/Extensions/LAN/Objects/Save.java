package be.ac.umons.mom.g02.Extensions.LAN.Objects;

import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.MapObject;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Other.Date;
import be.ac.umons.mom.g02.Other.LogicSaving;

import java.awt.*;
import java.io.Serializable;

public class Save extends LogicSaving implements Serializable {
    People secondPlayer;
    Point positionTwo;
    String mapOne;
    String mapTwo;

    /**
     * This constructor allows to save the object of the game
     *
     * @param player   is the player of the game
     * @param secondPlayer is the second player of the game
     * @param map      is the current map of player
     * @param date     is the current date on the game
     * @param positionOne is the position of the first player
     * @param positionTwo is the position of the second player
     * @param list     is a list of position of item
     */
    public Save(People player, People secondPlayer,
                String map, String mapTwo,
                Date date,
                Point positionOne, Point positionTwo,
                MapObject.OnMapItem[] list) {
        super(player, date, positionOne, list);
        this.secondPlayer = secondPlayer;
        this.positionTwo = positionTwo;
        this.mapOne = map;
        this.mapTwo = mapTwo;
    }

    public void invertPlayerOneAndTwo() {
        String fs = getFirstPlayerMap();
        People p = getPlayer();
        Point pos = getPlayerPosition();
        setFirstPlayerMap(getSecondPlayerMap());
        setPlayer(getSecondPlayer());
        setPlayerPosition(getSecondPlayerPosition());
        setSecondPlayer(p);
        setPositionTwo(pos);
        setSecondPlayerMap(fs);
    }

    public People getSecondPlayer() {
        return secondPlayer;
    }

    public Point getSecondPlayerPosition() {
        return positionTwo;
    }

    public String getFirstPlayerMap() {
        return mapOne;
    }

    public String getSecondPlayerMap() {
        return mapTwo;
    }

    public void setPositionTwo(Point positionTwo) {
        this.positionTwo = positionTwo;
    }

    public void setFirstPlayerMap(String mapOne) {
        this.mapOne = mapOne;
    }

    public void setSecondPlayerMap(String mapTwo) {
        this.mapTwo = mapTwo;
    }

    public void setSecondPlayer(People secondPlayer) {
        this.secondPlayer = secondPlayer;
    }
}
