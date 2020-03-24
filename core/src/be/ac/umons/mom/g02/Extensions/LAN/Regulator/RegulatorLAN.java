package be.ac.umons.mom.g02.Extensions.LAN.Regulator;

import be.ac.umons.mom.g02.Enums.Places;
import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.Extensions.Multiplayer.Regulator.RegulatorMultiPlayer;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Other.TimeGame;
import be.ac.umons.mom.g02.Regulator.Supervisor;
import com.badlogic.gdx.Gdx;

import java.net.SocketException;

public class RegulatorLAN extends RegulatorMultiPlayer {

    NetworkManager nm;

    /**
     * This constructor define the regulator class during the game
     *
     * @param people  is the player of the game
     * @param second
     * @param time    is the instance of the calculus time game
     * @param manager
     */
    public RegulatorLAN(People people, People second, TimeGame time, Supervisor manager) {
        super(people, second, time, manager);
        try {
            nm = NetworkManager.getInstance();
            nm.setOnTimeSpeedReceived((speed) -> TimeGame.FASTER = speed);
        } catch (SocketException e) {
            Gdx.app.error("RegulatorLAN", "Unable to get the instance of NetworkManager", e);
        }
    }

    @Override
    public void timeOfDay(Places place) {
        super.timeOfDay(place);
        nm.sendTimeSpeed(TimeGame.FASTER);
    }
}
