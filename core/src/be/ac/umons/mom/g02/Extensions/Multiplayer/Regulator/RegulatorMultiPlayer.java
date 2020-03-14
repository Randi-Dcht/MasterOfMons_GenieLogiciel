package be.ac.umons.mom.g02.Extensions.Multiplayer.Regulator;

import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Other.TimeGame;
import be.ac.umons.mom.g02.Regulator.Regulator;

/***/
public class RegulatorMultiPlayer extends Regulator
{

    /**
     * This constructor define the regulator class during the game
     * @param people is the player of the game
     * @param time   is the instance of the calculus time game
     */
    public RegulatorMultiPlayer(People people, TimeGame time)
    {
        super(people, time);
    }
}
