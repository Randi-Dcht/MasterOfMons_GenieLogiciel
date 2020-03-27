package be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.Menus;

import be.ac.umons.mom.g02.Extensions.Dual.Logic.Regulator.SupervisorDual;
import be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.PlayingState;
import be.ac.umons.mom.g02.Extensions.DualLAN.Managers.NetworkManager;
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

    protected void setNetworkManagerRunnables() {
        nm.setOnTypeChosen((type -> {
            SupervisorDual.getSupervisorDual().init(type);
            gsm.removeAllStateAndAdd(PlayingState.class);
        }));
    }
}
