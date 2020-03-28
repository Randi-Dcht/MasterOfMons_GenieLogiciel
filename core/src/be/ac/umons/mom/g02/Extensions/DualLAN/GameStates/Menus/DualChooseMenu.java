package be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.Menus;

import be.ac.umons.mom.g02.Extensions.Dual.Logic.Enum.TypeDual;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Regulator.SupervisorDual;
import be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.PlayingState;
import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;

import java.net.SocketException;

public class DualChooseMenu extends be.ac.umons.mom.g02.Extensions.Dual.Graphic.Menu.DualChooseMenu {
    /**
     * This constructor define the menu to choose the dual
     *
     * @param graphic is the graphicalSetting
     */
    public DualChooseMenu(GraphicalSettings graphic) {
        super(graphic);
    }

    @Override
    protected void checkChoose(TypeDual what) {
        try {
            NetworkManager.getInstance().sendMessageOnTCP("TC", what);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        SupervisorDual.getSupervisorDual().init(what);
        gsm.removeAllStateAndAdd(PlayingState.class);
    }
}
