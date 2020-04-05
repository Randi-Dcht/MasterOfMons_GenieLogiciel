package be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.Menus;

import be.ac.umons.mom.g02.Extensions.Dual.Logic.Enum.TypeDual;
import be.ac.umons.mom.g02.Extensions.DualLAN.Helpers.PlayingDualLANHelper;
import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.GameStates.Dialogs.OutGameDialogState;
import be.ac.umons.mom.g02.GameStates.Menus.MainMenuState;
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
        setNetworkManagerRunnables();
        setMenuItems(new MenuItem[]{
                new TitleMenuItem(gs, GraphicalSettings.getStringFromId("waiting")),
                new TextMenuItem(gs, GraphicalSettings.getStringFromId("waitingForSecondPlayer"))
        });
    }

    /**
     * Set all the necessary action in the NetworkManager
     */
    protected void setNetworkManagerRunnables() {
        nm.whenMessageReceivedDo("DTS", (objects) -> PlayingDualLANHelper.onTypeSelected((TypeDual) objects[0]));
        nm.setOnDisconnected(() -> {
            gsm.removeAllStateAndAdd(MainMenuState.class);
            OutGameDialogState ogds = (OutGameDialogState) gsm.setState(OutGameDialogState.class);
            ogds.setText(GraphicalSettings.getStringFromId("disconnected"));
            ogds.addAnswer("OK");
        });

        nm.processMessagesNotRan(true);
    }
}
