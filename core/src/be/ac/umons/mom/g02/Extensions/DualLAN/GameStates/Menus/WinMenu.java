package be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.Menus;

import be.ac.umons.mom.g02.Extensions.DualLAN.Helpers.PlayingDualLANHelper;
import be.ac.umons.mom.g02.GraphicalObjects.Controls.Button;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;

public class WinMenu extends be.ac.umons.mom.g02.Extensions.Dual.Graphic.Menu.WinMenu {
    public WinMenu(GraphicalSettings gs) {
        super(gs);
    }

    @Override
    public void init() {
        super.init();

        ((Button)menuItems[2].getControl()).setOnClick(PlayingDualLANHelper::goToPreviousMenu);
    }
}
