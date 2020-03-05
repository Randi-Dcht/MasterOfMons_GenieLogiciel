package be.ac.umons.mom.g02.Extensions.Dual.Graphic;

import be.ac.umons.mom.g02.GameStates.Menus.MainMenuState;
import be.ac.umons.mom.g02.GameStates.Menus.MenuState;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
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
     * @param manager is the gameStateManager
     * @param input   is the gameInputManager
     * @param graphic is the graphicalSetting
     */
    public DualChooseMenu(GameStateManager manager, GameInputManager input, GraphicalSettings graphic)
    {
        super(manager,input,graphic);
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
                        new MenuItem("Menu of Choose",MenuItemType.Title, () -> gsm.removeAllStateAndAdd(PlayDual.class)),
                        new MenuItem("Combat"        ,MenuItemType.Button,() -> gsm.removeAllStateAndAdd(PlayDual.class)),
                        new MenuItem("Flag"          ,MenuItemType.Button,() -> gsm.removeAllStateAndAdd(PlayDual.class)),
                        new MenuItem("Survivor"      ,MenuItemType.Button,() -> gsm.removeAllStateAndAdd(PlayDual.class)),
                        new MenuItem("Occupation"    ,MenuItemType.Button,() -> gsm.removeAllStateAndAdd(PlayDual.class)),
                        new MenuItem("Main"          ,MenuItemType.Button,() -> gsm.removeAllStateAndAdd(MainMenuState.class)),
                        new MenuItem("Quit"          ,MenuItemType.Button,() -> Gdx.app.exit())
                });
    }
}
