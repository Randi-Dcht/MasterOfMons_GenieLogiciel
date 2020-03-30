package be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.Menus;

import be.ac.umons.mom.g02.Extensions.Dual.Logic.Enum.TypeDual;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Regulator.SupervisorDual;
import be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.PlayingCasesState;
import be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.PlayingState;
import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.GameStates.Menus.MainMenuState;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;

import java.net.SocketException;

public class DualChooseMenu extends be.ac.umons.mom.g02.Extensions.Dual.Graphic.Menu.DualChooseMenu {

    NetworkManager nm;

    /**
     * This constructor define the menu to choose the dual
     *
     * @param graphic is the graphicalSetting
     */
    public DualChooseMenu(GraphicalSettings graphic) {
        super(graphic);
    }

    @Override
    public void init() {
        super.init();
        try {
            nm = NetworkManager.getInstance();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void checkChoose(TypeDual what) {
        nm.sendMessageOnTCP("TC", what);
        SupervisorDual.getSupervisorDual().init(what);
        if (what == TypeDual.OccupationFloor)
            gsm.removeAllStateAndAdd(PlayingCasesState.class);
        else
            gsm.removeAllStateAndAdd(PlayingState.class);
    }

    @Override
    public void getFocus() {
        super.getFocus();
        nm.setOnDisconnected(() -> gsm.setState(MainMenuState.class, true));
    }
}
