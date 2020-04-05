package be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.Menus;

import be.ac.umons.mom.g02.Extensions.Dual.Logic.Enum.TypeDual;
import be.ac.umons.mom.g02.Extensions.DualLAN.Helpers.PlayingDualLANHelper;
import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.GameStates.Menus.MainMenuState;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;

import java.net.SocketException;

/**
 * Inherited from <code>be.ac.umons.mom.g02.Extensions.Dual.Graphic.Menu.DualChooseMenu</code>. Let the user choose which type of dual he wants to play.
 */
public class DualChooseMenu extends be.ac.umons.mom.g02.Extensions.Dual.Graphic.Menu.DualChooseMenu {

    /**
     * The network manager of the game.
     */
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
        nm.sendMessageOnTCP("DTS", what);
        PlayingDualLANHelper.onTypeSelected(what);
    }

    @Override
    public void getFocus() {
        super.getFocus();
        nm.setOnDisconnected(() -> gsm.setState(MainMenuState.class, true));
    }
}
