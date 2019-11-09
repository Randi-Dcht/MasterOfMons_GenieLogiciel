package be.ac.umons.sgl.mom.GameStates;

import be.ac.umons.sgl.mom.Enums.GameStates;
import be.ac.umons.sgl.mom.Enums.KeyStatus;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class MainMenuState extends MenuState {

    public MainMenuState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }

    @Override
    public void init() {
        super.init();
        topMargin = .1;
        betweenItemMargin = .01;
        menuItems = new MenuItem[] { new MenuItem("Master Of Mons", MenuItemType.Title, false),
                new MenuItem("Play"),
                new MenuItem("Settings"),
                new MenuItem("Quit")};
    }

    @Override
    protected void goToSelectedItem() {
        switch (selectedItem) {
            case 1:
                gsm.setState(GameStates.Loading);
                break;
            case 2:
                gsm.setState(GameStates.Settings);
                break;
            case 3:
                Gdx.app.exit();
                break;
            default:
                break;
        }
    }
}
