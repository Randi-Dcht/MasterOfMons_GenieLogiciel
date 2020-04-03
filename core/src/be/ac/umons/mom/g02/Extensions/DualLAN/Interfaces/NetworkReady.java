package be.ac.umons.mom.g02.Extensions.DualLAN.Interfaces;

import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Character;

import java.util.List;

public interface NetworkReady extends be.ac.umons.mom.g02.Extensions.LAN.Interfaces.NetworkReady {
    List<Character> getPNJs();
    Character onCharacterDetected(String name, be.ac.umons.mom.g02.Objects.Characters.Character mob, int x, int y, int posInList);
}
