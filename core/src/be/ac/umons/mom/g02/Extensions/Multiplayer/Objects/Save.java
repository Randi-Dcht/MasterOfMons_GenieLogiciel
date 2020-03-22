package be.ac.umons.mom.g02.Extensions.Multiplayer.Objects;

import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.MapObject;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Other.Date;
import be.ac.umons.mom.g02.Other.LogicSaving;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Save extends LogicSaving implements Serializable {
    /**
     * The second player's characteristics
     */
    People secondPlayer;
    /**
     * The second player position
     */
    Point positionTwo;
    /**
     * The first player map
     */
    String mapOne;
    /**
     * The second player map
     */
    String mapTwo;

    /**
     * If we must show informations about the energizing item
     */
    protected boolean informEnergizing=true;
    /**
     * If we must display informations about places or not
     */
    protected boolean informPlace=true;
    /**
     * If the is the first time the player go to a course
     */
    protected boolean firstCourse=true;
    /**
     * If this is the first time the user study
     */
    protected boolean firstStudy=true;
    /**
     * The all maps of the maps
     */
    protected ArrayList<Maps> maps;

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

    /**
     * Invert the informations of the first and the second player
     */
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

    /**
     * @return The second player's characteristics
     */
    public People getSecondPlayer() {
        return secondPlayer;
    }

    /**
     * @return The second player's position
     */
    public Point getSecondPlayerPosition() {
        return positionTwo;
    }

    /**
     * @return The first player's map
     */
    public String getFirstPlayerMap() {
        return mapOne;
    }

    /**
     * @return The second player's map
     */
    public String getSecondPlayerMap() {
        return mapTwo;
    }

    /**
     * @param positionTwo The second player's position
     */
    public void setPositionTwo(Point positionTwo) {
        this.positionTwo = positionTwo;
    }

    /**
     * @param mapOne The first player's map
     */
    public void setFirstPlayerMap(String mapOne) {
        this.mapOne = mapOne;
    }

    /**
     * @param mapTwo The second player's map
     */
    public void setSecondPlayerMap(String mapTwo) {
        this.mapTwo = mapTwo;
    }

    /**
     * @param secondPlayer The second player's characteristics
     */
    public void setSecondPlayer(People secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    /**
     * @return If the is the first time the player go to a course
     */
    public boolean isTheFirstCourse() {
        return firstCourse;
    }

    /**
     * @param firstCourse If the is the first time the player go to a course
     */
    public void setFirstCourse(boolean firstCourse) {
        this.firstCourse = firstCourse;
    }

    /**
     * @return If this is the first time the user study
     */
    public boolean isTheFirstStudy() {
        return firstStudy;
    }

    /**
     * @param firstStudy If this is the first time the user study
     */
    public void setFirstStudy(boolean firstStudy) {
        this.firstStudy = firstStudy;
    }

    /**
     * @return If we must show informations about the energizing item
     */
    public boolean mustShowEnergizingInformation() {
        return informEnergizing;
    }

    /**
     * @param informEnergizing If we must show informations about the energizing item
     */
    public void setShowEnergizingInformation(boolean informEnergizing) {
        this.informEnergizing = informEnergizing;
    }

    /**
     * @return If we must display informations about places or not
     */
    public boolean mustDisplayPlaceInformations() {
        return informPlace;
    }

    /**
     * @param informPlace If we must display informations about places or not
     */
    public void setDisplayPlaceInformations(boolean informPlace) {
        this.informPlace = informPlace;
    }

    /**
     * @return The maps where we need to display informations about them.
     */
    public ArrayList<Maps> getRemainingMaps() {
        return maps;
    }

    /**
     * @param maps The maps where we need to display informations about them.
     */
    public void setRemainingMaps(ArrayList<Maps> maps) {
        this.maps = maps;
    }
}
