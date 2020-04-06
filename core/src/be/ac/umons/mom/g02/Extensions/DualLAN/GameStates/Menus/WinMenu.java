package be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.Menus;

import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.GameStates.Dialogs.OutGameDialogState;
import be.ac.umons.mom.g02.GameStates.Menus.MainMenuState;
import be.ac.umons.mom.g02.GraphicalObjects.Controls.Button;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.MenuItem;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;

import java.net.SocketException;

/**
 * Let the user know which player wins.
 */
public class WinMenu extends be.ac.umons.mom.g02.Extensions.Dual.Graphic.Menu.WinMenu {

    /**
     * @param gs The graphical settings to use.
     */
    public WinMenu(GraphicalSettings gs) {
        super(gs);
    }

    @Override
    public void init() {
        super.init();
        try {
            NetworkManager nm = NetworkManager.getInstance();

            for (MenuItem mi : menuItems) {
                if (mi.getId().equals("BTN_Continue")) {
                    ((Button)mi.getControl()).setOnClick(() -> {
                        gsm.setState(WaitMenuState.class);
                        nm.sendOnTCP("Loaded");
                    });
                }
            }

            nm.setOnDisconnected(() -> {
                gsm.removeAllStateAndAdd(MainMenuState.class);
                OutGameDialogState ogds = (OutGameDialogState) gsm.setState(OutGameDialogState.class);
                ogds.setText(GraphicalSettings.getStringFromId("disconnected"));
                ogds.addAnswer("OK");
            });
        } catch (SocketException e) {
            Gdx.app.error("WinMenu", "Unable to get the instance of NetworkManager", e);
        }
    }
}
