package be.ac.umons.mom.g02.Extensions.LAN.GameStates.Menus;

import be.ac.umons.mom.g02.Extensions.LAN.GameStates.PlayingState;
import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.GameStates.Dialogs.OutGameDialogState;
import be.ac.umons.mom.g02.GameStates.Menus.MainMenuState;
import be.ac.umons.mom.g02.GameStates.Menus.MenuState;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.MenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.TextMenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.TitleMenuItem;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;

import java.net.SocketException;

/**
 * The state in which the user is while the second player is still loading.
 */
public class WaitMenuState extends MenuState {

    /**
     * The network manager of the game
     */
    protected NetworkManager nm;

    /**
     * @param gs The graphical settings to use
     */
    public WaitMenuState(GraphicalSettings gs) {
        super(gs);
        try {
            nm = NetworkManager.getInstance();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        nm.whenMessageReceivedDo("Loaded", this::onLoaded);
        nm.setOnDisconnected(() -> {
            gsm.removeAllStateAndAdd(MainMenuState.class);
            OutGameDialogState ogds = (OutGameDialogState) gsm.setState(OutGameDialogState.class);
            ogds.setText(GraphicalSettings.getStringFromId("disconnected"));
            ogds.addAnswer("OK");
        });
    }

    protected void onLoaded(Object[] objects) {
        nm.whenMessageReceivedDo("Loaded", (objects1) -> {}); // Can't be null, else stored
        nm.sendOnTCP("Loaded");
        gsm.removeAllStateAndAdd(PlayingState.class);
    }

    @Override
    public void init() {
        super.init();
        setMenuItems(new MenuItem[]{
                new TitleMenuItem(gs, GraphicalSettings.getStringFromId("waiting")),
                new TextMenuItem(gs, GraphicalSettings.getStringFromId("waitingForSecondPlayer"))
        });
    }
}
