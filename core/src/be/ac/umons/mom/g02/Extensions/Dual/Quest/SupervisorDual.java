package be.ac.umons.mom.g02.Extensions.Dual.Quest;

import be.ac.umons.mom.g02.Enums.Difficulty;
import be.ac.umons.mom.g02.Enums.Gender;
import be.ac.umons.mom.g02.Enums.Type;
import be.ac.umons.mom.g02.Regulator.SuperviserNormally;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Other.Date;
import be.ac.umons.mom.g02.Other.TimeGame;
import be.ac.umons.mom.g02.Quests.Master.MasterQuest;


/**
 * @author Umons_Group_2_ComputerScience_RandyDauchot
 */
public class SupervisorDual extends SuperviserNormally
{

    /**
     * This us an instance of the player Two
     */
    private People playerTwo;
    /**
     * This is the actual MasterQuest of the extension Dual
     */
    private MasterQuest dualQuest;


    /**
     * This constructor define this class
     */
    public SupervisorDual()
    {
        super();
    }


    public void newParty(String namePlayerOne,String namePlayerTwo,Type typeOne,Type typeTwo, GraphicalSettings graphic, Gender genderOne, Gender genderTwo, Difficulty difficulty)
    {
        playerOne = new People(namePlayerOne,typeOne,genderOne,difficulty);
        playerTwo = new People(namePlayerTwo,typeTwo,genderTwo,difficulty);
        dualQuest = new MovingInMons(playerOne,playerTwo,difficulty);
        time      = new TimeGame(new Date(1,1,2020,9,0));
        playerOne.newQuest(dualQuest);
        playerTwo.newQuest(dualQuest);
        save.setLogic(playerOne);
        save.setLogic(playerTwo);
        this.graphic = graphic;

        refreshQuest();
    }

    private void launchNewQuest(People people)
    {}

    private void addThisQuest(People people)
    {}

    private void finishQuest()
    {}

}