package be.ac.umons.mom.g02.Extensions.Dual.Graphic.Menu;

import be.ac.umons.mom.g02.Enums.Difficulty;
import be.ac.umons.mom.g02.Enums.Gender;
import be.ac.umons.mom.g02.Enums.Type;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Regulator.SupervisorDual;
import be.ac.umons.mom.g02.GameStates.Menus.CreatePlayerMenuState;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;

public class CreateAPlayer extends CreatePlayerMenuState
{
    private static int playerNumber = 0;

    /**
     * @param gs The game's graphical settings.
     */
    public CreateAPlayer(GraphicalSettings gs)
    {
        super(gs);
    }

    @Override
    public void initGame(String name, String level)
    {//TODO visibility
        //SupervisorDual.getSupervisorDual().createNewPlayer(new People(name,characterType, playerGender, difficulty),playerNumber);
        SupervisorDual.getSupervisorDual().createNewPlayer(new People(name, Type.athletic, Gender.Men, Difficulty.Easy),playerNumber);
        gsm.removeFirstState();
    }

    public static void setPlayerNumber(int player)
    {
        playerNumber = player;
    }
}
