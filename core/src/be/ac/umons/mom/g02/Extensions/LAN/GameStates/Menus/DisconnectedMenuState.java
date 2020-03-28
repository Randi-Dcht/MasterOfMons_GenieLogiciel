package be.ac.umons.mom.g02.Extensions.LAN.GameStates.Menus;

import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.Extensions.LAN.Regulator.SupervisorLAN;
import be.ac.umons.mom.g02.Extensions.Multiplayer.Objects.Save;
import be.ac.umons.mom.g02.GameStates.Dialogs.OutGameDialogState;
import be.ac.umons.mom.g02.GameStates.GameState;
import be.ac.umons.mom.g02.GameStates.Menus.MenuState;
import be.ac.umons.mom.g02.GameStates.Menus.SaveMenuState;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.ButtonMenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.MenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.TextMenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.TitleMenuItem;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represent the state in which the game must be if a disconnection happens
 */
public class DisconnectedMenuState extends MenuState {
    /**
     * The network manager of the game.
     */
    protected NetworkManager nm;

    /**
     * @param gs The game's graphical settings.
     */
    public DisconnectedMenuState(GraphicalSettings gs) {
        super(gs);
    }

    @Override
    public void init() {
        super.init();
        handleEscape = false;
        transparentBackground = true;
        List<MenuItem> menuItemList = new ArrayList<>();
        menuItemList.add(new TitleMenuItem(gs, gs.getStringFromId("disconnected")));

        menuItemList.add(new TextMenuItem(gs, gs.getStringFromId("waitingReconnection")));
        menuItemList.add(new TextMenuItem(gs, gs.getStringFromId("searchInLoad")));

        menuItemList.add(new ButtonMenuItem(gim, gs, gs.getStringFromId("saveTheGame"), () -> gsm.setState(SaveMenuState.class)));
        menuItemList.add(new ButtonMenuItem(gim, gs, gs.getStringFromId("quit"), this::exit));
        setMenuItems(menuItemList.toArray(new MenuItem[0]));

        try {
            nm = NetworkManager.getInstance();
            nm.close();
            nm.acceptConnection();
            nm.startBroadcastingMessage("Game begun");
            nm.whenMessageReceivedDo("PI", null);
            nm.whenMessageReceivedDo("Loaded", (objects) -> gsm.removeAllStateUntil(this));
            nm.setOnMagicNumberSent((magicNumber) -> {
                OutGameDialogState ogds = (OutGameDialogState) gsm.setState(OutGameDialogState.class);
                ogds.setText(String.format(gs.getStringFromId("magicNumber"), magicNumber));
                ogds.addAnswer("OK");
            });
            nm.setOnConnected(this::onConnected);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    /**
     * Executed when a connection is established with the second player
     */
    protected void onConnected() {
        Save save = SupervisorLAN.getSupervisor().createSave();
        nm.sendMessageOnTCP("SAVE", save);
        nm.stopBroadcastingServerInfo();
    }

    /**
     * Executed when the "quit" option is chosen
     */
    public void exit() {
        GameState g = gsm.setState(OutGameDialogState.class);
        ((OutGameDialogState)g).setText(gs.getStringFromId("sureQuitGame"));
        ((OutGameDialogState)g).addAnswer("yes", () -> Gdx.app.exit());
        ((OutGameDialogState)g).addAnswer("no", () -> gsm.removeFirstState());
    }
}
