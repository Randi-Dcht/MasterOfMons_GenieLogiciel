package be.ac.umons.mom.g02.Extensions.DualLAN.Interfaces;

import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Character;

import java.util.List;

/**
 * An interface representing all the necessary methods that a <code>PlayingState</code> must implement to be "Network-Ready"
 */
public interface NetworkReady extends be.ac.umons.mom.g02.Extensions.LAN.Interfaces.NetworkReady {
    /**
     * @return The list of PNJ that is drawn
     */
    List<Character> getPNJs();
    /**
     * @return The map making the link between a character's name and its graphical object
     */
    Character onCharacterDetected(String name, be.ac.umons.mom.g02.Objects.Characters.Character mob, int x, int y, int posInList);
}
