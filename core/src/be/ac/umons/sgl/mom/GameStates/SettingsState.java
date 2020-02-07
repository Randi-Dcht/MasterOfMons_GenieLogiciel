package be.ac.umons.sgl.mom.GameStates;

import be.ac.umons.sgl.mom.GameStates.Menus.MenuState;
import be.ac.umons.sgl.mom.GraphicalObjects.Controls.TextBox;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import be.ac.umons.sgl.mom.Objects.Settings;

/**
 * The state where the user can choose settings influencing the game.
 * @author Guillaume Cardoen
 */
public class SettingsState extends MenuState {

    public SettingsState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }

    @Override
    public void init() {
        super.init();
        topMargin = .1;
        transparentBackground = false;
        setMenuItems(new MenuItem[] {
                new MenuItem(gs.getStringFromId("settings"), MenuItemType.Title),
                new MenuItem(gs.getStringFromId("gameResolutionWidth"), MenuItemType.NumberTextBox, "TXT_Game_Resolution_Width"),
                new MenuItem(gs.getStringFromId("gameResolutionHeight"), MenuItemType.NumberTextBox, "TXT_Game_Resolution_Height"),
                new MenuItem(gs.getStringFromId("save"), MenuItemType.Button, this::save)
        });
        Settings settings = MasterOfMonsGame.settings;
        for (MenuItem mi : menuItems) {
            switch (mi.id) {
                case "TXT_Game_Resolution_Width":
                    ((TextBox)mi.control).setText("" + settings.getGameResolutionWidth());
                    break;
                case "TXT_Game_Resolution_Height":
                    ((TextBox)mi.control).setText("" + settings.getGameResolutionHeight());
                    break;
            }
        }
    }

    public void save() {
        Settings settings = new Settings();
        for (MenuItem mi : menuItems) {
            switch (mi.id) {
                case "TXT_Game_Resolution_Width":
                    settings.setGameResolutionWidth(Integer.parseInt(((TextBox)mi.control).getText()));
                    break;
                case "TXT_Game_Resolution_Height":
                    settings.setGameResolutionHeight(Integer.parseInt(((TextBox)mi.control).getText()));
                    break;
            }
        }
        // TODO : Save the settings object
        MasterOfMonsGame.settings = settings;
        gsm.removeFirstState();
    }
}
