package be.ac.umons.mom.g02.Extensions.DualLAN.Helpers;

import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.LifeChanged;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Enum.TypeDual;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Regulator.SupervisorDual;
import be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.CasesPlayingState;
import be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.FlagPlayingState;
import be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.Menus.DisconnectedMenuState;
import be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.Menus.DualChooseMenu;
import be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.Menus.WaitMenuState;
import be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.PlayingState;
import be.ac.umons.mom.g02.Extensions.DualLAN.Interfaces.NetworkReady;
import be.ac.umons.mom.g02.Extensions.LAN.Helpers.PlayingLANHelper;
import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.Extensions.Multiplayer.Regulator.SupervisorMultiPlayer;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Regulator.Supervisor;

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
            nm.whenMessageReceivedDo("SPL", (objects) -> SupervisorMultiPlayer.getPeopleTwo().setActualLife((int) objects[0]));
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

    public static void update(NetworkReady ps, Notification notify) {
        PlayingLANHelper.update(ps, notify);
        NetworkManager nm = null;
        try {
            nm = NetworkManager.getInstance();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        if (notify.getEvents().equals(Events.Dead) && notify.bufferNotEmpty() && notify.getBuffer().getClass().equals(People.class)) {
            nm.sendOnTCP("Death");
            PlayingDualLANHelper.goToPreviousMenu();
        } else if (notify.getEvents().equals(Events.LifeChanged) && ((LifeChanged)notify).getConcernedOne().equals(Supervisor.getPeople())) {
            nm.sendMessageOnUDP("PL", Supervisor.getPeople().getActualLife());
        }else if (notify.getEvents().equals(Events.LifeChanged) && ((LifeChanged)notify).getConcernedOne().equals(SupervisorMultiPlayer.getPeopleTwo())) {
            nm.sendMessageOnUDP("SPL", SupervisorMultiPlayer.getPeopleTwo().getActualLife());
        }
    }

    public static void handleInput() {
        PlayingLANHelper.handleInput();
    }

    public static void getFocus() {
        PlayingLANHelper.getFocus();
    }

    public static void onTypeSelected(TypeDual type) {
        GameStateManager gsm = GameStateManager.getInstance();
        if (SupervisorDual.getSupervisorDual() == null)
            SupervisorDual.initDual();
        SupervisorDual.getSupervisorDual().init(type);
        if (type == TypeDual.OccupationFloor)
            gsm.removeAllStateAndAdd(CasesPlayingState.class);
        else if (type == TypeDual.CatchFlag)
            gsm.removeAllStateAndAdd(FlagPlayingState.class);
        else
            gsm.removeAllStateAndAdd(PlayingState.class);
    }
}
