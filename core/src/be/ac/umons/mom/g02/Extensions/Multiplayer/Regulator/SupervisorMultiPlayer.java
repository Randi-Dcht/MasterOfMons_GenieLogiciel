package be.ac.umons.mom.g02.Extensions.Multiplayer.Regulator;

import be.ac.umons.mom.g02.GameStates.PlayingState;
import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Other.LogicSaving;
import be.ac.umons.mom.g02.Quests.Master.MasterQuest;
import be.ac.umons.mom.g02.Regulator.Supervisor;
import java.util.LinkedList;
import java.util.List;

/**
 * This class defines the logic for two players on a map that can help or compete
 * This class allows to integrate the Dual and network extensions
 * @author Umons_Group_2_ComputerScience_RandyDauchot
 */
public abstract class SupervisorMultiPlayer extends Supervisor
{

    /**
     * This is the second player on the maps
     */
    protected static People playerTwo;


    /**
     * This method returns the second player
     * @return the second player
     */
    public static People getPeopleTwo()
    {
        return playerTwo;
    }


    /**
     * This method allows to give a second player
     * @param playerTwo is the second player
     */
    public static void setPlayerTwo(People playerTwo)
    {
        SupervisorMultiPlayer.playerTwo = playerTwo;
    }

    public static SupervisorMultiPlayer getSupervisor() {
        if (instance instanceof SupervisorMultiPlayer)
            return (SupervisorMultiPlayer)instance;
        return null;
    }


    /*-----------------------------------------------------------------------------------------------*/


    /**
     * This is the saving of the first player
     */
    protected LogicSaving playerOneSave;
    /**
     * This is the saving of second player
     */
    protected LogicSaving playerTwoSave;
    /**
     * The list of the death of the second player to ignore while calculating the win of XP
     */
    protected List<Mobile> deathToIgnore;


    /**
     * This is the constructor of the supervisor for two player in the game
     */
    protected SupervisorMultiPlayer()
    {
        super();
        deathToIgnore = new LinkedList<>();
    }


    /**
     * This method allows to analyse the id to receive
     * @param id
     */
    @Override
    public void analyseIdMap(String id) throws Exception {}


    /**
     * This method allows to give the actual quest of the player one
     * @return the masterQuest of player one
     */
    @Override
    public MasterQuest actualQuest()
    {
        return playerOne.getQuest();
    }


    /**
     * This method allows to charge an old game
     * @param pathAndFile is the name of file and the path
     * @param play        is the playingState
     * @param graphic     is the graphicalSettings
     */
    @Override
    public abstract void oldGame(String pathAndFile, PlayingState play, GraphicalSettings graphic);


    /**
     * This method allows to save the actual game
     * @param pathAndFile is the name of file and the path
     */
    @Override
    public abstract void saveGame(String pathAndFile);


    /**
     * This method allows to remove the dead mobile on the maps
     * @param mb is the mobile dead
     */
    @Override
    public void deadMobile(Mobile mb)
    {
        if (deathToIgnore.contains(mb)) {
            listMobile.get(mb.getMaps()).remove(mb);
            deadMobile.add(mb);
            listUpdate.remove(mb);
            mb.setLiving(false);

            if (mb.equals(memoryMobile))
                memoryMobile = null;
            while (deathToIgnore.size() > 5)
                deathToIgnore.remove(0); // Remove the old one (now useless)
        }
        else {
            super.deadMobile(mb);
            deathToIgnore.remove(mb);
        }
    }


    /***/
    public void addADeathToIgnore(Mobile mb)
    {
        deathToIgnore.add(mb);
    }

}
