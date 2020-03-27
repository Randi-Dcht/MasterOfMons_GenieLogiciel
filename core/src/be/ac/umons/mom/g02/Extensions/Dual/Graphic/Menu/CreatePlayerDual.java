package be.ac.umons.mom.g02.Extensions.Dual.Graphic.Menu;

import be.ac.umons.mom.g02.Extensions.Dual.Logic.Regulator.SupervisorDual;
import be.ac.umons.mom.g02.GameStates.GameState;
import be.ac.umons.mom.g02.GameStates.LoadingState;
import be.ac.umons.mom.g02.GameStates.Menus.MenuState;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.*;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;

public class CreatePlayerDual extends MenuState
{

    private static String seeError = " ";
    /**
     * The state to launch when the creation is done.
     */
    protected Class<? extends GameState> afterCreationState;


    public static void setError(String error)
    {
        seeError = error;
    }

    /**
     * @param gs The game's graphical settings.
     */
    public CreatePlayerDual(GraphicalSettings gs)
    {
        super(gs);
        afterCreationState = LoadingState.class;
    }

    @Override
    public void init()
    {
        SupervisorDual.initDual();
        super.init();

        setMenuItems(new MenuItem[]
                {
                        new TitleMenuItem(gs,"Player One"),

                        new ButtonMenuItem(gim,gs,"Create Player One", () ->
                        {
                            CreateAPlayer.setPlayerNumber(1);
                            gsm.setState(CreateAPlayer.class);
                        }),

                        new ButtonMenuItem(gim,gs,"Load Player One", () ->
                        {
                            LoadPlayerDual.setPlayer(1);
                            gsm.setState(LoadPlayerDual.class);
                        }),

                        new TitleMenuItem(gs,"Player Two"),


                        new ButtonMenuItem(gim,gs,"Create Player Two", () ->
                        {
                            CreateAPlayer.setPlayerNumber(2);
                            gsm.setState(CreateAPlayer.class);
                        }),


                        new ButtonMenuItem(gim,gs,"Load Player Two", () ->
                        {
                            LoadPlayerDual.setPlayer(2);
                            gsm.setState(LoadPlayerDual.class);
                        }),

                        new TextMenuItem(gs,seeError),

                        new ButtonMenuItem(gim,gs,gs.getStringFromId("newGame"), () ->
                        {
                            if (SupervisorDual.getSupervisorDual().startLoading())
                            {
                                gsm.removeAllStateAndAdd(LoadingState.class);
                                GameState a = gsm.setState(afterCreationState);
                                ((LoadingState)a).setAfterLoadingState(DualChooseMenu.class);
                            }
                            else
                                init();
                        }),
                });
    }

}
