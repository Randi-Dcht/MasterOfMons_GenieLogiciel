package be.ac.umons.mom.g02.Extensions.IA.Graphic;

import be.ac.umons.mom.g02.Extensions.Dual.Graphic.PlayDual;
import be.ac.umons.mom.g02.GameStates.Menus.MenuState;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;

public class IaExplicationMenu extends MenuState {
    /**
     * This constructor define the menu to choose the dual
     * @param manager is the gameStateManager
     * @param input   is the gameInputManager
     * @param graphic is the graphicalSetting
     */
    public IaExplicationMenu(GameStateManager manager, GameInputManager input, GraphicalSettings graphic)
    {
        super(manager,input,graphic);
    }


    /**
     * This method allows to explain the different type of Ia and start the game
     */
    @Override
    public void init()
    {
        super.init();
        transparentBackground = false;

        setMenuItems(new MenuItem[]
                {
                        //TODO mettre les explications des differents types d'ia qu'on peut trouver sur la map s
                        new MenuItem("Welcome in the IA's extension ! "+"\n\n"+"I'll explain you the different type of ia",MenuItemType.Text, () -> gsm.removeAllStateAndAdd(PlayDual.class)),
                        new MenuItem("Play",MenuItemType.Button, () ->gsm.removeAllStateAndAdd(PlayIa.class)) //TODO mettre ma classe qui lancera le jeu avec mon extension
                });
    }
}


