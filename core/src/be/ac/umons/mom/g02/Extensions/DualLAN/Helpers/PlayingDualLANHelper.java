package be.ac.umons.mom.g02.Extensions.DualLAN.Helpers;

import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Enums.MobileType;
import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.LifeChanged;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Events.Observer;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Enum.TypeDual;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Mobile.ZombiePNJ;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Regulator.SupervisorDual;
import be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.CasesPlayingState;
import be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.FlagPlayingState;
import be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.Menus.DisconnectedMenuState;
import be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.Menus.DualChooseMenu;
import be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.Menus.WaitPlayerMenuState;
import be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.Menus.WinMenu;
import be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.PlayingState;
import be.ac.umons.mom.g02.Extensions.DualLAN.Interfaces.NetworkReady;
import be.ac.umons.mom.g02.Extensions.LAN.Helpers.PlayingLANHelper;
import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.Extensions.Multiplayer.Regulator.SupervisorMultiPlayer;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Character;
import be.ac.umons.mom.g02.Managers.GameMapManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Regulator.Supervisor;

import java.net.SocketException;

/**
 * The class where all the (possible) redundant code for the extension LAN combined with Dual is.
 */
public class PlayingDualLANHelper {

    /**
     * Set all the necessary variables and events.
     * @param observer The <code>PlayingState</code> which use this class.
     */
    public static void init(Observer observer) {
        PlayingLANHelper.init(observer);
    }

    /**
     * Set all the necessary <code>Runnable</code> in the <code>NetworkManager</code>
     * @param ps The playing state which use this class
     */
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
                    PlayingDualLANHelper.goToChoosingMenu());
            nm.whenMessageReceivedDo("Death", (objects) -> gsm.setState(WinMenu.class, true));
            nm.whenMessageReceivedDo("SPL", (objects) -> SupervisorMultiPlayer.getPeople().setActualLife((double) objects[0], false));
            nm.whenMessageReceivedDo("ZPNJ", (objects) ->
            {
                Character c;
                ZombiePNJ zpnj = new ZombiePNJ((String)objects[1], (MobileType) objects[2], (Maps) objects[3], (int) objects[4]);
                zpnj.initialisation(c = ps.onCharacterDetected((String) objects[1], zpnj, (int) objects[5], (int) objects[6], (int) objects[0]),
                        ps.getPlayer());
                Supervisor.getSupervisor().addMobile(zpnj, Supervisor.getSupervisor().getMaps(GameMapManager.getInstance().getActualMapName()), c);
            });
            nm.whenMessageReceivedDo("getZPNJsPos", (objects) -> {
                for (int i = 0; i < ps.getPNJs().size(); i++) { // The order of the list is important
                    Character c = ps.getPNJs().get(i);
                    Mobile mob = (Mobile) c.getCharacteristics();
                    nm.sendMessageOnTCP("ZPNJ", i, mob.getName(), mob.getMobileType(), mob.getMaps(), mob.getLevel(), c.getPosX(), c.getPosY());
                }
            });
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    /**
     * Go back to the choosing menu or the wait menu
     */
    public static void goToChoosingMenu() {
        try {
            NetworkManager nm = NetworkManager.getInstance();
            GameStateManager gsm = GameStateManager.getInstance();
            if (nm.isTheServer())
                gsm.setState(DualChooseMenu.class, true);
            else
                gsm.setState(WaitPlayerMenuState.class, true);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    /**
     * Monitor and process all the events received by the <code>Observer</code> given before.
     * @param ps The <code>PlayingState</code> which uses this class.
     * @param notify The notification to process
     */
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
//            PlayingDualLANHelper.goToPreviousMenu();
        } else if (notify.getEvents().equals(Events.LifeChanged) && ((LifeChanged)notify).getInvolvedOne().equals(SupervisorMultiPlayer.getPeopleTwo())) {
            nm.sendMessageOnUDP("SPL", SupervisorMultiPlayer.getPeopleTwo().getActualLife());
        }
    }

    /**
     * Monitor all the changing due to a player input.
     * @param ps The <code>PlayingState</code> which uses this class.
     */
    public static void handleInput(NetworkReady ps) {
        NetworkManager nm = null;
        try {
            nm = NetworkManager.getInstance();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        PlayingLANHelper.handleInput();
        nm.sendMessageOnUDP("PP", ps.getPlayer().getMapPos());
        nm.sendMessageOnUDP("PO", ps.getPlayer().getOrientation());
    }

    /**
     * What to do when a <code>PlayingState</code> get the focus
     */
    public static void getFocus() {
        PlayingLANHelper.getFocus();
    }

    /**
     * What to do when the user selected the type of dual.
     * @param type The chosen type of dual.
     */
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
