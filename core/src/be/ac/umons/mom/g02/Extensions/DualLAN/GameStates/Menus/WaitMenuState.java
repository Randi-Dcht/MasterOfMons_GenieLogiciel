package be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.Menus;

import be.ac.umons.mom.g02.Extensions.Dual.Logic.Enum.TypeDual;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Regulator.SupervisorDual;
import be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.CasesPlayingState;
import be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.PlayingState;
import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.GameStates.Menus.MenuState;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.MenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.TextMenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.TitleMenuItem;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;

import java.net.SocketException;

public class WaitMenuState extends MenuState {

    protected NetworkManager nm;

    public WaitMenuState(GraphicalSettings gs) {
        super(gs);
    }

    @Override
    public void init() {
        super.init();
        try {
            nm = NetworkManager.getInstance();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        transparentBackground = true;
        setNetworkManagerRunnables();
        setMenuItems(new MenuItem[]{
                new TitleMenuItem(gs, gs.getStringFromId("waiting")),
                new TextMenuItem(gs, gs.getStringFromId("waitingForSecondPlayer"))
        });
    }

    /**
     * Set all the necessary action in the NetworkManager
     */
    protected void setNetworkManagerRunnables() {
        nm.whenMessageReceivedDo("TC", (objects -> {
            SupervisorDual.getSupervisorDual().init((TypeDual) objects[0]);
            if (objects[0] == TypeDual.OccupationFloor)
                gsm.removeAllStateAndAdd(CasesPlayingState.class);
            else
                gsm.removeAllStateAndAdd(PlayingState.class);
        }));
    }
}
