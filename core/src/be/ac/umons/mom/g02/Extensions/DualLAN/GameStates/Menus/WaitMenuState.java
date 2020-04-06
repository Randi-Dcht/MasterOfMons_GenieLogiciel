package be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.Menus;

import be.ac.umons.mom.g02.Extensions.Dual.Logic.Regulator.SupervisorDual;
import be.ac.umons.mom.g02.Extensions.DualLAN.Helpers.PlayingDualLANHelper;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;

public class WaitMenuState extends be.ac.umons.mom.g02.Extensions.LAN.GameStates.Menus.WaitMenuState {
    /**
     * @param gs The graphical settings to use
     */
    public WaitMenuState(GraphicalSettings gs) {
        super(gs);
    }

    @Override
    protected void onLoaded(Object[] objects) {
        nm.whenMessageReceivedDo("Loaded", (objects1) -> {}); // Can't be null, else message stored
        nm.sendOnTCP("Loaded");
        SupervisorDual.initDual();
        PlayingDualLANHelper.goToChoosingMenu();
    }


}
