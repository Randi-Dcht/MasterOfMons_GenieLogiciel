package be.ac.umons.mom.g02.Extensions.LAN.Regulator;

import be.ac.umons.mom.g02.Extensions.LAN.GameStates.PlayingState;
import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.Extensions.LAN.Objects.Save;
import be.ac.umons.mom.g02.Extensions.Multiplayer.Regulator.RegulatorMultiPlayer;
import be.ac.umons.mom.g02.Extensions.Multiplayer.Regulator.SupervisorMultiPlayer;
import be.ac.umons.mom.g02.Managers.GameMapManager;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Objects.Saving;
import be.ac.umons.mom.g02.Other.Date;
import be.ac.umons.mom.g02.Other.LogicSaving;
import be.ac.umons.mom.g02.Other.TimeGame;
import be.ac.umons.mom.g02.Quests.Master.MasterQuest;
import be.ac.umons.mom.g02.Regulator.Regulator;
import be.ac.umons.mom.g02.Regulator.Supervisor;
import com.badlogic.gdx.Gdx;

import java.io.*;
import java.net.SocketException;
import java.util.ArrayList;

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

    @Override
    public void saveGame(String path) {
        PlayingState ps = ((PlayingState)playGraphic); // Not in the same package
        Save save = new Save(playerOne, playerTwo,
                GameMapManager.getInstance().getActualMapName(), ps.getSecondPlayerMap(),
                time.getDate(),
                ps.getPlayerPosition(), ps.getSecondPlayerPosition(),
                ps.getItemsOnMap());
        try (ObjectOutputStream sortie = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File(path))))) {
            sortie.writeObject(save);
        } catch (IOException e) {
            Gdx.app.error("Error in the saving the game (out)", e.getMessage());
        }
    }

    public void oldGame(String path, PlayingState play, GraphicalSettings graphic) {
        Save saving =  (Save) Saving.getSaveObject(path);
        if (saving != null)
        {
            listUpdate = new ArrayList<>();
            time = new TimeGame(saving.getDate());
            playerOne = saving.getPlayer();
            playerOne.setMaps(Supervisor.getSupervisor().getMaps(saving.getFirstPlayerMap()));
            playerTwo = saving.getSecondPlayer();
            playerTwo.setMaps(Supervisor.getSupervisor().getMaps(saving.getSecondPlayerMap()));
            listCourse = playerOne.getPlanning().get(time.getDate().getDay());
            Supervisor.graphic = graphic;
            regulator= new Regulator(playerOne,time);
            refreshQuest();
            checkPlanning();

            play.initMap(saving.getMap().getMaps());
            play.setPlayerPosition(saving.getPlayerPosition());
            play.setSecondPlayerPosition(saving.getSecondPlayerPosition());
            play.addItemsToMap(saving.getItemPosition());
            try {
                NetworkManager.getInstance().sendSecondPlayerMapChanged(saving.getSecondPlayerMap());
                NetworkManager.getInstance().sendSecondPlayerPosition(saving.getSecondPlayerPosition());
            } catch (SocketException e) {
                Gdx.app.error("SupervisorLAN", "Error while getting the NetworkManager", e);
            }
        }
    }
}
