package be.ac.umons.mom.g02.Extensions.LAN.Interfaces;

import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Character;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.MapObject;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Player;
import be.ac.umons.mom.g02.GraphicalObjects.TimeShower;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

public interface NetworkReady {
    void setSecondPlayerPosition(Point p);
    void setPlayerPosition(Point p);
    Player getPlayer();
    Player getSecondPlayer();
    Character onCharacterDetected(String name, be.ac.umons.mom.g02.Objects.Characters.Character mob, int x, int y);
    HashMap<String, Character> getIdCharacterMap();
    List<MapObject> getMapObjects();
    TimeShower getTimeShower();
    void addItemToMap(MapObject.OnMapItem omi);
}
