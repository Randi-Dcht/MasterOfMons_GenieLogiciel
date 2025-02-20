package be.ac.umons.mom.g02.Extensions.Dual.Graphic.Menu;

import be.ac.umons.mom.g02.Extensions.Dual.Logic.Enum.TypeDual;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Regulator.SupervisorDual;
import be.ac.umons.mom.g02.GameStates.Menus.MainMenuState;
import be.ac.umons.mom.g02.GameStates.Menus.MenuState;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.ButtonMenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.MenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.TitleMenuItem;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;


/***
 * This class define the menu to choose in the Dual extension
 * @author Umons_Group_2_ComputerScience_RandyDauchot
 */
public class DualChooseMenu extends MenuState
{

    /**
     * This constructor define the menu to choose the dual
     * @param graphic is the graphicalSetting
     */
    public DualChooseMenu(GraphicalSettings graphic)
    {
        super(graphic);
    }


    /**
     * This method allows to display the menu of choose the type of dual
     */
    @Override
    public void init()
    {
        super.init();
        transparentBackground = false;

        setMenuItems(new MenuItem[]
                {
                        new TitleMenuItem(gs, GraphicalSettings.getStringFromId("menuDual")      ),
                        new ButtonMenuItem(gim,gs,GraphicalSettings.getStringFromId("combat" )       ,() -> checkChoose(TypeDual.DualPlayer)),
                        new ButtonMenuItem(gim,gs,GraphicalSettings.getStringFromId("flag")          ,() -> checkChoose(TypeDual.CatchFlag)),
                        new ButtonMenuItem(gim,gs,GraphicalSettings.getStringFromId("survivor")      ,() -> checkChoose(TypeDual.Survivor)),
                        new ButtonMenuItem(gim,gs,GraphicalSettings.getStringFromId("occupation")    ,() -> checkChoose(TypeDual.OccupationFloor)),
                        new ButtonMenuItem(gim,gs,GraphicalSettings.getStringFromId("return")        ,() -> gsm.removeAllStateAndAdd(MainMenuState.class), true, "BTN_Go_Back"),
                        new ButtonMenuItem(gim,gs,GraphicalSettings.getStringFromId("quit")          ,() -> Gdx.app.exit())
                });
    }

    protected void checkChoose(TypeDual what)
    {
        SupervisorDual.getSupervisorDual().init(what);
        gsm.removeAllStateAndAdd(what.getGraphic());
    }
}
