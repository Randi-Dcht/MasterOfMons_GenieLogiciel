package be.ac.umons.mom.g02.Extensions.DualLAN.Helpers;

import be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.Menus.DisconnectedMenuState;
import be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.Menus.DualChooseMenu;
import be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.Menus.WaitMenuState;
import be.ac.umons.mom.g02.Extensions.DualLAN.Interfaces.NetworkReady;
import be.ac.umons.mom.g02.Extensions.LAN.Helpers.PlayingLANHelper;
import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;

import java.net.SocketException;

public class PlayingDualLANHelper {

    public static void setNetworkManagerRunnable(NetworkReady ps) {
        try {
            NetworkManager nm = NetworkManager.getInstance();
            GameStateManager gsm = GameStateManager.getInstance();
            PlayingLANHelper.setNetworkManagerRunnable(ps);
            nm.setOnDisconnected(() -> {
                DisconnectedMenuState dms = (DisconnectedMenuState) gsm.setState(DisconnectedMenuState.class);
                dms.setSecondPlayerPosition(ps.getSecondPlayer().getMapPos());
            });
            nm.whenMessageReceivedDo("EndDual", objects ->
                    PlayingDualLANHelper.goToPreviousMenu());
            nm.whenMessageReceivedDo("Death", (objects) -> PlayingDualLANHelper.goToPreviousMenu());
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    /**
     * Go back to the choosing menu or the wait menu
     */
    public static void goToPreviousMenu() {
        try {
            NetworkManager nm = NetworkManager.getInstance();
            GameStateManager gsm = GameStateManager.getInstance();
            if (nm.isTheServer())
                gsm.removeAllStateAndAdd(DualChooseMenu.class);
            else
                gsm.removeAllStateAndAdd(WaitMenuState.class);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
