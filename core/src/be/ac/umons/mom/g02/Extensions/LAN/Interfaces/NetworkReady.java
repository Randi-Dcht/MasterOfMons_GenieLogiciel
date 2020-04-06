package be.ac.umons.mom.g02.Extensions.LAN.Interfaces;

import be.ac.umons.mom.g02.Events.Observer;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Character;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.MapObject;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Player;
import be.ac.umons.mom.g02.GraphicalObjects.TimeShower;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

public interface NetworkReady extends Observer {
    /**
     * @param p The second player position
     */
    void setSecondPlayerPosition(Point p);

    /**
     * @param p The player position
     */
    void setPlayerPosition(Point p);

    /**
     * @return The first player
     */
    Player getPlayer();

    /**
     * @return The second player
     */
    Player getSecondPlayer();

    /**
     * What to do when the information of a character has been received
     * @param name The name of the character
     * @param mob The characteristics of the character
     * @param x The horizontal position (in pixel) on the map
     * @param y The vertical position (in pixel) on the map
     * @return The graphical character created
     */
    Character onCharacterDetected(String name, be.ac.umons.mom.g02.Objects.Characters.Character mob, int x, int y);

    /**
     * @return The map associating an id (his name) and a character.
     */
    HashMap<String, Character> getIdCharacterMap();

    /**
     * @return A list of all the objects that will be drawn on all the map.
     */
    List<MapObject> getMapObjects();

    /**
     * @return The <code>TimeShower</code> use in this <code>PlayingState</code>
     */
    TimeShower getTimeShower();

    /**
     * Add an item to the list of item to draw.
     * @param omi The item characteristics.
     */
    void addItemToMap(MapObject.OnMapItem omi);
}
