package be.ac.umons.mom.g02.Extensions.LAN.Regulator;

import be.ac.umons.mom.g02.Enums.Places;
import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.Extensions.Multiplayer.Regulator.RegulatorMultiPlayer;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.Course;
import be.ac.umons.mom.g02.Other.Date;
import be.ac.umons.mom.g02.Other.TimeGame;
import be.ac.umons.mom.g02.Regulator.Supervisor;
import com.badlogic.gdx.Gdx;

import java.net.SocketException;

public class RegulatorLAN extends RegulatorMultiPlayer { // TODO Same agenda

    protected boolean secondPlayerInCourseRoom;

    protected Course currentCourse;

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
            nm.whenMessageReceivedDo("TS", (objects) -> TimeGame.FASTER = (int) objects[0]);
            nm.whenMessageReceivedDo("PCR", (objects) -> {
                boolean b = (boolean) objects[0];
                secondPlayerInCourseRoom = b;
                if (b)
                    timeOfDay(player.getPlace());
            });
            nm.whenMessageReceivedDo("PWC", (objects) -> currentCourse.goCourse());
        } catch (SocketException e) {
            Gdx.app.error("RegulatorLAN", "Unable to get the instance of NetworkManager", e);
        }
    }

    @Override
    public void timeOfDay(Places place) {

        if((place.equals(Places.RoomCourse) || place.equals(Places.ComputerRoom))) {
            if (!secondPlayerInCourseRoom)
                nm.sendMessageOnTCP("PCR", true);
            currentCourse = manager.getActualCourse();
        } else
            nm.sendMessageOnTCP("PCR", false);

        super.timeOfDay(place);
        nm.sendMessageOnTCP("TS", TimeGame.FASTER);
    }

    @Override
    public boolean advanceTime() {
        return super.advanceTime() && secondPlayerInCourseRoom;
    }

    @Override
    protected void goToCourse() {
        super.goToCourse();
        nm.sendOnTCP("PWC");
    }
}
