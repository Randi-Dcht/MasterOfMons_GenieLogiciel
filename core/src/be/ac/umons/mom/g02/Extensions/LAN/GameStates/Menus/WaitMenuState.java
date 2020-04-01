package be.ac.umons.mom.g02.Extensions.LAN.GameStates.Menus;

import be.ac.umons.mom.g02.Extensions.LAN.GameStates.PlayingState;
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
        try {
            nm = NetworkManager.getInstance();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        nm.whenMessageReceivedDo("Loaded", (objects) -> {
            gsm.removeAllStateAndAdd(PlayingState.class);
            nm.sendOnTCP("Loaded");
            nm.whenMessageReceivedDo("Loaded", null);
        });
    }

    @Override
    public void init() {
        super.init();
        setMenuItems(new MenuItem[]{
                new TitleMenuItem(gs, GraphicalSettings.getStringFromId("waiting")),
                new TextMenuItem(gs, GraphicalSettings.getStringFromId("waitingForSecondPlayerToLoad"))
        });
    }
}
