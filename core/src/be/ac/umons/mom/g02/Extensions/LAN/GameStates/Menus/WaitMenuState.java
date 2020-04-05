package be.ac.umons.mom.g02.Extensions.LAN.GameStates.Menus;

import be.ac.umons.mom.g02.Extensions.Dual.Logic.Regulator.SupervisorDual;
import be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.Menus.DualChooseMenu;
import be.ac.umons.mom.g02.Extensions.LAN.GameStates.PlayingState;
import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.GameStates.Dialogs.OutGameDialogState;
import be.ac.umons.mom.g02.GameStates.Menus.MainMenuState;
import be.ac.umons.mom.g02.GameStates.Menus.MenuState;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.MenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.TextMenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.TitleMenuItem;
import be.ac.umons.mom.g02.Managers.ExtensionsManager;
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
            nm.whenMessageReceivedDo("Loaded", (objects1) -> {}); // Can't be null, else stored
            if (ExtensionsManager.getInstance().getExtensionsMap().get("Dual").activated) {
                SupervisorDual.initDual();
                if (nm.isTheServer())
                    gsm.removeAllStateAndAdd(DualChooseMenu.class);
                else
                    gsm.removeAllStateAndAdd(be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.Menus.WaitMenuState.class);
            }
            else
                gsm.removeAllStateAndAdd(PlayingState.class);
            nm.sendOnTCP("Loaded");
        });
        nm.setOnDisconnected(() -> {
            gsm.removeAllStateAndAdd(MainMenuState.class);
            OutGameDialogState ogds = (OutGameDialogState) gsm.setState(OutGameDialogState.class);
            ogds.setText(GraphicalSettings.getStringFromId("disconnected"));
            ogds.addAnswer("OK");
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
