package be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.Menus;

import be.ac.umons.mom.g02.Extensions.Dual.Logic.Regulator.SupervisorDual;
import be.ac.umons.mom.g02.Extensions.Multiplayer.Regulator.SupervisorMultiPlayer;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;

import java.awt.*;

/**
 * Represent the state in which we must be when the second player is disconnected
 */
public class DisconnectedMenuState extends be.ac.umons.mom.g02.Extensions.LAN.GameStates.Menus.DisconnectedMenuState {

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
            gsm.removeAllStateUntil(this);
            nm.sendMessageOnTCP("TC", SupervisorDual.getSupervisorDual().getDual());
            if (secondPlayerPosition != null)
                nm.sendMessageOnTCP("SPP", secondPlayerPosition);
        });
    }

    @Override
    protected void onConnected() {
        nm.stopBroadcastingServerInfo();
        nm.sendMessageOnTCP("SPI", SupervisorMultiPlayer.getPeopleTwo());
        nm.sendMessageOnTCP("PI", SupervisorMultiPlayer.getPeople());
    }

    public void setSecondPlayerPosition(Point secondPlayerPosition) {
        this.secondPlayerPosition = secondPlayerPosition;
    }
}
