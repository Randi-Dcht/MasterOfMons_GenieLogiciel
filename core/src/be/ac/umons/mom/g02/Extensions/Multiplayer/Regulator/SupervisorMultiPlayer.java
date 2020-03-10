package be.ac.umons.mom.g02.Extensions.Multiplayer.Regulator;

import be.ac.umons.mom.g02.Enums.Gender;
import be.ac.umons.mom.g02.Enums.Type;
import be.ac.umons.mom.g02.Events.SuperviserNormally;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Other.Date;
import be.ac.umons.mom.g02.Regulator.Supervisor;


/**
 * This class defines the logic for two players on a map that can help or compete
 * This class allows to integrate the Dual and network extensions
 * @author Umons_Group_2_ComputerScience_RandyDauchot_GuillaumeCardoen
 */
public class SupervisorMultiPlayer extends Supervisor
{
    private static SupervisorMultiPlayer instance;

    /**
     * This method to give the only instance of <code>SuperviserNormaly</code>
     */
    public static SupervisorMultiPlayer getSupervisor()
    {
        if(instance == null)
            instance = new SupervisorMultiPlayer();
        return instance;
    }


    /**
     * This is the instance of the second player
     */
    protected People playerTwo;


    /**
     * This is the constructor of the supervisor for two player in the game
     */
    protected SupervisorMultiPlayer()
    {
        super();
    }


    /**
     * This method allows to load a old player in the extension game
     * @param name   is the name of the saving
     * @param player is the number of player
     */
    public void loadPlayer(String name, int player)//TODO approche naïve
    {
        if (player == 1)
            playerOne = Saving.getPlayer(name);
        else
            playerTwo = Saving.getPlayer(name);
    }


    /**
     * This methods allows to create the second player on the maps
     * @param namePlayer is the name of player
     * @param type       is the type of the character
     * @param gender     is the gender of the player (Men or Women)
     */
    public void createPlayerTwo(String namePlayer, Type type, GraphicalSettings graphic, Gender gender)
    {
        playerTwo = new People(namePlayer,type,gender,playerOne.getDifficulty());
    }


    /**
     * This method returns the second player in the game
     * @return the second player
     */
    public People getPlayerTwo()
    {
        return playerTwo;
    }
}
