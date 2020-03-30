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
                        new TitleMenuItem(gs,String.format("Finish this dual (%s)", SupervisorDual.getSupervisorDual().getDual().toString())),// TODO bundle
                        new TextMenuItem(gs,String.format("\n \n you are win : %s \n \n",SupervisorDual.getSupervisorDual().getNameWin())),//TODO bundle
                        new ButtonMenuItem(gim,gs,"continue",() -> gsm.removeAllStateAndAdd(DualChooseMenu.class))//TODO bundle
                });
    }
}
