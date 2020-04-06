package be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.Menus;

import be.ac.umons.mom.g02.Extensions.Dual.Logic.Regulator.SupervisorDual;
import be.ac.umons.mom.g02.Extensions.Multiplayer.Regulator.SupervisorMultiPlayer;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;

import java.awt.*;

/**
 * Represent the state in which we must be when the second player is disconnected
 */
public class DisconnectedMenuState extends be.ac.umons.mom.g02.Extensions.LAN.GameStates.Menus.DisconnectedMenuState {

    /**
     * The second player position to send later
     */
    protected Point secondPlayerPosition;

    /**
     * @param gs The game's graphical settings.
     */
    public DisconnectedMenuState(GraphicalSettings gs) {
        super(gs);
    }

    @Override
    public void init() {
        super.init();
        nm.whenMessageReceivedDo("Loaded", (objects) -> {
            nm.whenMessageReceivedDo("Loaded", null);
            gsm.removeAllStateUntil(this);
            nm.sendOnTCP("Loaded");
            nm.sendMessageOnTCP("DTS", SupervisorDual.getSupervisorDual().getDual());
            if (secondPlayerPosition != null) {
                nm.sendMessageOnTCP("SPP", secondPlayerPosition);
                nm.sendMessageOnTCP("SPL", SupervisorMultiPlayer.getPeopleTwo().getActualLife());
                nm.sendMessageOnTCP("PL", SupervisorMultiPlayer.getPeople().getActualLife());
                nm.sendMessageOnTCP("SPInv", SupervisorMultiPlayer.getPeopleTwo().getInventory()); // Re-sent because each player is re-initialised at the beginning of a dual.
                nm.sendMessageOnTCP("PInv", SupervisorMultiPlayer.getPeople().getInventory());
            }
        });
    }

    @Override
    protected void onConnected() {
        nm.stopBroadcastingServerInfo();
        nm.sendMessageOnTCP("SPI", SupervisorMultiPlayer.getPeopleTwo());
        nm.sendMessageOnTCP("PI", SupervisorMultiPlayer.getPeople());
    }

    /**
     * @param secondPlayerPosition The second player position to send on reconnection
     */
    public void setSecondPlayerPosition(Point secondPlayerPosition) {
        this.secondPlayerPosition = secondPlayerPosition;
    }
}
