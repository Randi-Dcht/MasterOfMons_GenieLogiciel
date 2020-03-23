package be.ac.umons.mom.g02.Extensions.Multiplayer.Regulator;

import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Other.TimeGame;
import be.ac.umons.mom.g02.Regulator.Regulator;
import be.ac.umons.mom.g02.Regulator.Supervisor;

/***/
public class RegulatorMultiPlayer extends Regulator
{

    protected People peopleTwo;

    /**
     * This constructor define the regulator class during the game
     * @param people is the player of the game
     * @param time   is the instance of the calculus time game
     */
    public RegulatorMultiPlayer(People people, People second, TimeGame time, Supervisor manager)
    {
        super(people, time, manager);
        peopleTwo = second;
    }

    @Override
    protected void questionPlace(Maps maps)
    {
        if (firstStart)
        {
            firstStart = false;
//            push("HelloExt");
        }
    }
}
