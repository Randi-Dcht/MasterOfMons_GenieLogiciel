package be.ac.umons.mom.g02.Extensions.Multiplayer.Regulator;

import be.ac.umons.mom.g02.Enums.Difficulty;
import be.ac.umons.mom.g02.Enums.Gender;
import be.ac.umons.mom.g02.Enums.Type;
import be.ac.umons.mom.g02.GameStates.PlayingState;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Other.LogicSaving;
import be.ac.umons.mom.g02.Regulator.Supervisor;


/**
 * This class defines the logic for two players on a map that can help or compete
 * This class allows to integrate the Dual and network extensions
 * @author Umons_Group_2_ComputerScience_RandyDauchot
 */
public class SupervisorMultiPlayer extends Supervisor
{

    /**
     * This method allows to initialize the instance of multiPlayer
     */
    public static void initMultiPlayerGame()
    {
        instance = new SupervisorMultiPlayer();
    }


    /**
     * This method to give the only instance of <code>SuperviserMultiPlayer</code>
     */
    public static SupervisorMultiPlayer getSupervisor()
    {
        if (instance == null)
            initMultiPlayerGame();
        if(instance.getClass().equals(SupervisorMultiPlayer.class))
            return (SupervisorMultiPlayer) instance;
        return null;
    }


    /*-----------------------------------------------------------------------------------------------*/

    /**
     * This is the instance of the second player
     */
    protected People playerTwo;
    /***/
    protected LogicSaving playerOneSave;
    /***/
    protected LogicSaving playerTwoSave;


    /**
     * This is the constructor of the supervisor for two player in the game
     */
    protected SupervisorMultiPlayer()
    {
        super();
    }

    /**
     * @param id
     */
    @Override
    public void analyseIdMap(String id) throws Exception
    {

    }

    @Override
    public void newParty(String namePlayer, Type type, GraphicalSettings graphic, Gender gender, Difficulty difficulty)
    {
        super.newParty(namePlayer, type, graphic, gender, difficulty);
        regulator = new RegulatorMultiPlayer(playerOne,playerTwo,time);
    }

    /**
     * @param pathAndFile
     * @param play
     * @param graphic
     */
    @Override
    public void oldGame(String pathAndFile, PlayingState play, GraphicalSettings graphic)
    {
        //TODO
    }


    /**
     * @param pathAndFile
     */
    @Override
    public void saveGame(String pathAndFile)//TODO upgrade simple player
    {
        LogicSaving saveOne,saveTwo=null;

        if (playerOneSave != null)
            saveOne = new LogicSaving(playerOne,playerOneSave.getMap(),playerOneSave.getDate(),playerOneSave.getPlayerPosition(),playerOneSave.getItemPosition());
        else//TODO update
            saveOne = new LogicSaving(playerOne,time.getDate(),playGraphic.getPlayerPosition(),playGraphic.getItemsOnMap());

        if (playerTwoSave != null)
            saveTwo = new LogicSaving(playerTwo,playerTwoSave.getMap(),playerTwoSave.getDate(),playerTwoSave.getPlayerPosition(),playerTwoSave.getItemPosition());
        else//TODO update
            saveOne = new LogicSaving(playerTwo,time.getDate(),playGraphic.getPlayerPosition(),playGraphic.getItemsOnMap());

        be.ac.umons.mom.g02.Objects.Saving.setSaveObject(pathAndFile,saveOne);
        be.ac.umons.mom.g02.Objects.Saving.setSaveObject(pathAndFile,saveTwo);
    }


    /**
     * This method allows to load a old player in the extension game
     * @param name   is the name of the saving
     * @param player is the number of player
     */
    public void loadPlayer(String name, int player)//TODO approche naïve
    {

    }


    /**
     * This methods allows to create the second player on the maps
     * @param namePlayer is the name of player
     * @param type       is the type of the character
     * @param gender     is the gender of the player (Men or Women)
     */
    public void createPlayerTwo(String namePlayer, Type type, GraphicalSettings graphic, Gender gender)
    {
        if (playerOne != null)
            playerTwo = new People(namePlayer,type,gender,playerOne.getDifficulty());
        else
            playerTwo = new People(namePlayer,type,gender, Difficulty.Easy);
    }


    /**
     * This method returns the second player in the game
     * @return the second player
     */
    public People getSecondPeople()
    {
        return playerTwo;
    }
}
