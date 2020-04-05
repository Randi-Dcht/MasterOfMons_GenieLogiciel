package be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.Menus;

import be.ac.umons.mom.g02.Extensions.DualLAN.Helpers.PlayingDualLANHelper;
import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.GameStates.Dialogs.OutGameDialogState;
import be.ac.umons.mom.g02.GameStates.Menus.MainMenuState;
import be.ac.umons.mom.g02.GraphicalObjects.Controls.Button;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;

import java.net.SocketException;

public class WinMenu extends be.ac.umons.mom.g02.Extensions.Dual.Graphic.Menu.WinMenu {
    public WinMenu(GraphicalSettings gs) {
        super(gs);
    }

    @Override
    public void init() {
        super.init();

        ((Button)menuItems[2].getControl()).setOnClick(PlayingDualLANHelper::goToPreviousMenu);
        NetworkManager nm = null;
        try {
            nm = NetworkManager.getInstance();
        } catch (SocketException e) {
            Gdx.app.error("WinMenu", "Unable to get the instance of NetworkManager", e);
            return;
        }

        nm.setOnDisconnected(() -> {
            gsm.removeAllStateAndAdd(MainMenuState.class);
            OutGameDialogState ogds = (OutGameDialogState) gsm.setState(OutGameDialogState.class);
            ogds.setText(GraphicalSettings.getStringFromId("disconnected"));
            ogds.addAnswer("OK");
        });
    }
}
