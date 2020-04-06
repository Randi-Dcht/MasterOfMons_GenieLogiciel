package be.ac.umons.mom.g02.Extensions.LAN.GameStates.Menus;

import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.GameStates.Dialogs.OutGameDialogState;
import be.ac.umons.mom.g02.GameStates.GameState;
import be.ac.umons.mom.g02.GameStates.Menus.MainMenuState;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;

import java.net.SocketException;

public class InGameMenuState extends be.ac.umons.mom.g02.GameStates.Menus.InGameMenuState {

    /**
     * @param gs The game's graphical settings.
     */
    public InGameMenuState(GraphicalSettings gs) {
        super(gs);
    }

    @Override
    public void goMainMenu() {
        GameState g = gsm.setState(OutGameDialogState.class);
        ((OutGameDialogState)g).setText(GraphicalSettings.getStringFromId("sureQuitGame"));
        ((OutGameDialogState)g).addAnswer("yes", () -> {
            gsm.removeAllStateAndAdd(MainMenuState.class);
            NetworkManager nm;
            try {
                nm = NetworkManager.getInstance();
                nm.setOnDisconnected(null);
                nm.close();
            } catch (SocketException e) {
                Gdx.app.error("InGameMenuState(LAN)", "Unable to gte the instance of the NetworkManager", e);
                e.printStackTrace();
            }
        });
        ((OutGameDialogState)g).addAnswer("no");
    }
}
