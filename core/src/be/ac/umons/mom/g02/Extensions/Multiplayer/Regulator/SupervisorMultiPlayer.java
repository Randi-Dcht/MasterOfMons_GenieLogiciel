package be.ac.umons.mom.g02.Extensions.Multiplayer.Regulator;

import be.ac.umons.mom.g02.Enums.Difficulty;
import be.ac.umons.mom.g02.Enums.Gender;
import be.ac.umons.mom.g02.Enums.Type;
import be.ac.umons.mom.g02.GameStates.PlayingState;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Other.LogicSaving;
import be.ac.umons.mom.g02.Quests.Master.MasterQuest;
import be.ac.umons.mom.g02.Regulator.Supervisor;

/**
 * This class defines the logic for two players on a map that can help or compete
 * This class allows to integrate the Dual and network extensions
 * @author Umons_Group_2_ComputerScience_RandyDauchot
 */
public abstract class SupervisorMultiPlayer extends Supervisor
{

    protected static People playerTwo;

    public static People getPeopleTwo() {
        return playerTwo;
    }

    public static void setPlayerTwo(People playerTwo)
    {
        SupervisorMultiPlayer.playerTwo = playerTwo;
    }


    /*-----------------------------------------------------------------------------------------------*/


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


    /***/
    @Override
    public MasterQuest actualQuest()
    {
        return playerOne.getQuest();
    }


    /**
     * @param pathAndFile
     * @param play
     * @param graphic
     */
    @Override
    public abstract void oldGame(String pathAndFile, PlayingState play, GraphicalSettings graphic);


    /**
     * @param pathAndFile
     */
    @Override
    public abstract void saveGame(String pathAndFile);


    /**
     * This method allows to load a old player in the extension game
     * @param name   is the name of the saving
     * @param player is the number of player
     */
    public void loadPlayer(String name, int player)//TODO implement
    {

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
