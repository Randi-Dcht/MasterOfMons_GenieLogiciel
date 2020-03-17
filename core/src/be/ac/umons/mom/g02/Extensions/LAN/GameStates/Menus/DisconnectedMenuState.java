package be.ac.umons.mom.g02.Extensions.LAN.GameStates.Menus;

import be.ac.umons.mom.g02.Extensions.LAN.GameStates.PlayingState;
import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.GameStates.Menus.MenuState;
import be.ac.umons.mom.g02.GameStates.Menus.SaveMenuState;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameMapManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class DisconnectedMenuState extends MenuState {

    protected NetworkManager nm;
    protected PlayingState ps;

    /**
     * @param gsm The game's state manager
     * @param gim The game's input manager
     * @param gs The game's graphical settings.
     */
    public DisconnectedMenuState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }

    @Override
    public void init() {
        super.init();
        handleEscape = false;
        transparentBackground = true;
        List<MenuItem> menuItemList = new ArrayList<>();
        menuItemList.add(new MenuItem(gs.getStringFromId("disconnected"), MenuItemType.Title));

        menuItemList.add(new MenuItem(gs.getStringFromId("waitingReconnection")));

        menuItemList.add(new MenuItem(gs.getStringFromId("saveTheGame"), MenuItemType.Button, () -> gsm.setState(SaveMenuState.class)));
        menuItemList.add(new MenuItem(gs.getStringFromId("quit"), MenuItemType.Button));
        setMenuItems(menuItemList.toArray(new MenuItem[0]));

        try {
            nm = NetworkManager.getInstance();
            nm.close();
            nm.acceptConnection();
            nm.startBroadcastingMessage("Game begun");
            nm.setOnConnected(() -> {
                nm.sendPlayerInformation((People)ps.getPlayer().getCharacteristics());
                nm.stopBroadcastingServerInfo();
                gsm.removeFirstState();
            });
            nm.setOnPlayerDetected((player) -> {
                ps.setSecondPlayerCharacteristics(player);
                nm.sendMapChanged(GameMapManager.getInstance().getActualMapName());
            });
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void setPlayingState(PlayingState ps) {
        this.ps = ps;
    }
}
