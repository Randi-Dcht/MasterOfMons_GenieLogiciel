package be.ac.umons.mom.g02.Extensions.Dual.Logic.Regulator;

import be.ac.umons.mom.g02.Extensions.Dual.Graphic.Menu.CreatePlayerDual;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Enum.TypeDual;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Quest.DualMasterQuest;
import be.ac.umons.mom.g02.Extensions.Multiplayer.Regulator.RegulatorMultiPlayer;
import be.ac.umons.mom.g02.Extensions.Multiplayer.Regulator.SupervisorMultiPlayer;
import be.ac.umons.mom.g02.GameStates.PlayingState;
import be.ac.umons.mom.g02.Objects.Characters.Attack;
import be.ac.umons.mom.g02.Objects.Characters.Character;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Objects.Saving;
import be.ac.umons.mom.g02.Other.Date;
import be.ac.umons.mom.g02.Other.LogicSaving;
import be.ac.umons.mom.g02.Other.TimeGame;
import be.ac.umons.mom.g02.Quests.Master.MasterQuest;
import java.util.HashMap;


/**
 * @author Umons_Group_2_ComputerScience_RandyDauchot
 */
public class SupervisorDual extends SupervisorMultiPlayer
{

    /***/
    public static void initDual()
    {
        instance = new SupervisorDual();
    }


    /***/
    public static SupervisorDual getSupervisorDual()
    {
        if (instance.getClass().equals(SupervisorDual.class))
            return (SupervisorDual) instance;
        return null;
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    /**
     * This is the actual MasterQuest of the extension Dual
     */
    private DualMasterQuest dualQuest;
    /***/
    private TypeDual dual;
    /***/
    private HashMap<People,People> adv = new HashMap<>();


    /**
     * This constructor define this class
     */
    public SupervisorDual()
    {
        super();
    }

    /**
     * @param pathAndFile
     * @param play
     * @param graphic
     */
    @Override
    public void oldGame(String pathAndFile, PlayingState play, GraphicalSettings graphic)
    {

    }

    /**
     * @param pathAndFile
     */
    @Override
    public void saveGame(String pathAndFile)
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


    public void createNewPlayer(People people,int number)
    {
        if (number == 1)
            playerOne = people;
        else
            playerTwo = people;
    }

    public boolean startLoading()
    {
        if (playerOne == null)
        {
            CreatePlayerDual.setError("Player 1 doesn't create");
            return false;
        }
        if (playerTwo == null)
        {
            CreatePlayerDual.setError("Player 2 doesn't create");
            return false;
        }
        return true;
    }

    public People getAdversary(People people)
    {
        return adv.get(people);
    }

    @Override
    public MasterQuest actualQuest()
    {
        return dualQuest;
    }

    public void init(TypeDual dual)
    {
        this.dual = dual;

        if (playerTwo != null && playerOne != null)
        {
            adv.put(playerOne,playerTwo);
            adv.put(playerTwo,playerOne);
            dualQuest = new DualMasterQuest(dual,playerOne,playerTwo);
            time = new TimeGame(new Date(16,9,2019,8,15));
            regulator = new RegulatorMultiPlayer(playerOne,playerTwo,time,this);
            refreshQuest();
        }
    }

    public TypeDual getDual()
    {
        return dual;
    }

    @Override
    public void attackMethod(Attack attacker, Attack victim)
    {
        if (attacker != null && victim != null)//TODO
            super.attackMethod(attacker, victim);
    }


    /**
     * This method allows to load the old player in the extension Dual
     * @param pathAndFile is the path with the name of the file saving
     * @param why         is the number of the player
     */
    public void loadPeople(String pathAndFile,int why)
    {
        if (why == 1)
        {
            playerOneSave = (LogicSaving) Saving.getSaveObject(pathAndFile);
            playerOne     = playerOneSave.getPlayer();
        }
        else
        {
            playerTwoSave = (LogicSaving) Saving.getSaveObject(pathAndFile);
            playerTwo     = playerTwoSave.getPlayer();
        }
    }
}