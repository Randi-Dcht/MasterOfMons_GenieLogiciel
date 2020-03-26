package be.ac.umons.mom.g02.Extensions.Dual.Graphic.Menu;

import be.ac.umons.mom.g02.Extensions.Dual.Logic.Regulator.SupervisorDual;
import be.ac.umons.mom.g02.GameStates.Menus.LoadMenuState;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;

public class LoadPlayerDual extends LoadMenuState
{

    private static int playerNumber = 0;

    /**
     * @param gs  The game's graphical settings.
     */
    public LoadPlayerDual(GraphicalSettings gs)
    {
        super(gs);
    }

    @Override
    protected void load(String loadFilePath)
    {
        SupervisorDual.getSupervisorDual().loadPeople(loadFilePath,playerNumber);
        gsm.removeFirstState();
    }

    public static void setPlayer(int player)
    {
        playerNumber = player;
    }
}
