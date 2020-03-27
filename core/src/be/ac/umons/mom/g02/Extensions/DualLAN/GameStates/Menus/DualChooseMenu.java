package be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.Menus;

import be.ac.umons.mom.g02.Extensions.Dual.Logic.Enum.TypeDual;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Regulator.SupervisorDual;
import be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.PlayingState;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;

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
        SupervisorDual.getSupervisorDual().init(what);
        gsm.removeAllStateAndAdd(PlayingState.class);
    }
}
