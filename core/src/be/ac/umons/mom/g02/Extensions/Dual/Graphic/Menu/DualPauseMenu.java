package be.ac.umons.mom.g02.Extensions.Dual.Graphic.Menu;

import be.ac.umons.mom.g02.GameStates.Menus.MenuState;
import be.ac.umons.mom.g02.GameStates.PlayingState;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.ButtonMenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.MenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.TitleMenuItem;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


/***
 * This class allows to display the pause menu for the extension dual
 */
public class DualPauseMenu extends MenuState
{

    /**
     * @param gs The game's graphical settings.
     */
    public DualPauseMenu(GraphicalSettings gs)
    {
        super(gs);
    }


    /**
     * Allows to init the menu of pause in the dual extension
     */
    @Override
    public void init()
    {
        super.init();
        transparentBackground = true;
        setMenuItems(new MenuItem[]
        {
             new TitleMenuItem(gs, GraphicalSettings.getStringFromId("dualPause")),
             new ButtonMenuItem(gim, gs, GraphicalSettings.getStringFromId("continue"), () -> gsm.removeFirstState())
        });
    }
}
