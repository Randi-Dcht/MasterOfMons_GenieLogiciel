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


    @Override
    public void deadMobile(Mobile mb) {
        if (deathToIgnore.contains(mb)) {
            listMobile.get(mb.getMaps()).remove(mb);
            deadMobile.add(mb);
            listUpdate.remove(mb);

            if (mb.equals(memoryMobile))
                memoryMobile = null;
        }
        else {
            super.deadMobile(mb);
            deathToIgnore.remove(mb);
        }
    }

    public void addADeathToIgnore(Mobile mb) {
        deathToIgnore.add(mb);
    }

}
