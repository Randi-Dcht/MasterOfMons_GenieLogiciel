package be.ac.umons.mom.g02.Extensions.LAN.Regulator;

import be.ac.umons.mom.g02.Extensions.Multiplayer.Regulator.RegulatorMultiPlayer;
import be.ac.umons.mom.g02.Extensions.Multiplayer.Regulator.SupervisorMultiPlayer;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Other.Date;
import be.ac.umons.mom.g02.Other.TimeGame;
import be.ac.umons.mom.g02.Quests.Master.MasterQuest;

public class SupervisorLAN extends SupervisorMultiPlayer {


    /**
     * This method to give the only instance of <code>SupervisorLAN</code> if no other instance of <code>Supervisor</code> exists.
     */
    public static SupervisorLAN getSupervisor()
    {
        if (instance == null)
            instance = new SupervisorLAN();
        return (SupervisorLAN) instance;
    }


    protected SupervisorLAN() {
        super();
    }

    /**
     * Create a new game
     * @param firstQuest The first quest of the game.
     * @param playerOne The first player
     * @param playerTwo The second player
     */
    public void newParty(MasterQuest firstQuest, People playerOne, People playerTwo) {
        time      = new TimeGame(new Date(1,1,2020,9,0));
        regulator = new RegulatorMultiPlayer(playerOne,playerTwo,time);
        playerOne.newQuest(firstQuest);
        playerTwo.newQuest(firstQuest);
        save.setLogic(playerOne);
        save.setLogic(playerTwo);
        refreshQuest();
    }
}
