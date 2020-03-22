package be.ac.umons.mom.g02.Extensions.LAN.Regulator;

import be.ac.umons.mom.g02.Extensions.LAN.GameStates.PlayingState;
import be.ac.umons.mom.g02.Extensions.Multiplayer.Objects.Save;
import be.ac.umons.mom.g02.Extensions.Multiplayer.Regulator.RegulatorMultiPlayer;
import be.ac.umons.mom.g02.Extensions.Multiplayer.Regulator.SupervisorMultiPlayer;
import be.ac.umons.mom.g02.Managers.GameMapManager;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Objects.Saving;
import be.ac.umons.mom.g02.Other.Date;
import be.ac.umons.mom.g02.Other.TimeGame;
import be.ac.umons.mom.g02.Quests.Master.MasterQuest;
import be.ac.umons.mom.g02.Regulator.Regulator;
import be.ac.umons.mom.g02.Regulator.Supervisor;
import com.badlogic.gdx.Gdx;

import java.io.*;
import java.util.ArrayList;

public class SupervisorLAN extends SupervisorMultiPlayer {

    protected Save save;

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

    @Override
    public void oldGame(String pathAndFile, be.ac.umons.mom.g02.GameStates.PlayingState play, GraphicalSettings graphic) {
        //TODO implement this abstract method
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
        playerTwo.newQuest(firstQuest, false);
        refreshQuest();
    }

    @Override
    public void saveGame(String path) {

        try (ObjectOutputStream sortie = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File(path))))) {
            sortie.writeObject(createSave());
        } catch (IOException e) {
            Gdx.app.error("Error in the saving the game (out)", e.getMessage());
        }
    }

    public Save createSave() {
        PlayingState ps = ((PlayingState)playGraphic); // Not in the same package
        Save save = new Save(playerOne, playerTwo,
                GameMapManager.getInstance().getActualMapName(), ps.getSecondPlayerMap(),
                time.getDate(),
                ps.getPlayerPosition(), ps.getSecondPlayerPosition(),
                ps.getItemsOnMap());

        save.setDisplayPlaceInformations(regulator.mustDisplayPlaceInformations());
        save.setFirstCourse(regulator.isTheFirstCourse());
        save.setFirstStudy(regulator.isTheFirstStudy());
        save.setRemainingMaps(regulator.getRemainingMaps());
        save.setShowEnergizingInformation(regulator.mustShowEnergizingInformation());
        return save;
    }

    public Save oldGameLAN(String path, PlayingState play, GraphicalSettings graphic) {
        if (save == null)
            save =  (Save) Saving.getSaveObject(path);
        if (save != null)
            oldGameLAN(save, play, graphic);
        return save;
    }

    public Save oldGameLAN(String path) {
        if (save == null)
            save =  (Save) Saving.getSaveObject(path);
        if (save != null)
            oldGameLAN(save);
        return save;
    }

    public void oldGameLAN(Save save) {

        listUpdate = new ArrayList<>();
        time = new TimeGame(save.getDate());
        playerOne = save.getPlayer();
        playerOne.setMaps(Supervisor.getSupervisor().getMaps(save.getFirstPlayerMap()));
        playerTwo = save.getSecondPlayer();
        playerTwo.setMaps(Supervisor.getSupervisor().getMaps(save.getSecondPlayerMap()));
        listCourse = playerOne.getPlanning().get(time.getDate().getDay());
        regulator= new Regulator(playerOne,time);
        regulator.setFirstStart(false);
        regulator.setChangeQuest(false);
        regulator.setDisplayPlaceInformations(save.mustDisplayPlaceInformations());
        regulator.setFirstCourse(save.isTheFirstCourse());
        regulator.setFirstStudy(save.isTheFirstStudy());
        regulator.setRemainingMaps(save.getRemainingMaps());
        regulator.setShowEnergizingInformation(save.mustShowEnergizingInformation());
    }

    public void oldGameLAN(Save save, PlayingState play, GraphicalSettings gs) {
        play.setNewParty(false);
        Supervisor.setGraphic(gs);
        oldGameLAN(save);
        refreshQuest();
        checkPlanning();

        play.initMap(save.getFirstPlayerMap());
        play.setPlayerPosition(save.getPlayerPosition());
        play.setSecondPlayerMap(save.getSecondPlayerMap());
        play.setSecondPlayerPosition(save.getSecondPlayerPosition());
        play.addItemsToMap(save.getItemPosition());
    }
}
