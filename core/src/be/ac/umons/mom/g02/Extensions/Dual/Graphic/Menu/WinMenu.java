package be.ac.umons.mom.g02.Extensions.Dual.Graphic.Menu;

import be.ac.umons.mom.g02.Extensions.Dual.Logic.Regulator.SupervisorDual;
import be.ac.umons.mom.g02.GameStates.Menus.MenuState;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.ButtonMenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.MenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.TextMenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.TitleMenuItem;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Regulator.Supervisor;

public class WinMenu extends MenuState
{
    public WinMenu(GraphicalSettings gs)
    {
        super(gs);
    }


    @Override
    public void init()
    {
        super.init();
        transparentBackground = true;

        setMenuItems(new MenuItem[]
                {
                        new TitleMenuItem(gs,String.format(GraphicalSettings.getStringFromId("Finish"), SupervisorDual.getSupervisorDual().getDual().toString())),
                        new TextMenuItem(gs,String.format(GraphicalSettings.getStringFromId("winDual"),SupervisorDual.getSupervisorDual().getNameWin())),
                        new ButtonMenuItem(gim,gs,GraphicalSettings.getStringFromId("continue"),() -> gsm.removeAllStateAndAdd(DualChooseMenu.class), true, "BTN_Continue")
                });
    }
}
