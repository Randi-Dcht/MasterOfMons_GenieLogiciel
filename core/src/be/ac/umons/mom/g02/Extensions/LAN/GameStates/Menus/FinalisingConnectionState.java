package be.ac.umons.mom.g02.Extensions.LAN.GameStates.Menus;

import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.Extensions.LAN.Regulator.SupervisorLAN;
import be.ac.umons.mom.g02.Extensions.Multiplayer.Objects.Save;
import be.ac.umons.mom.g02.Extensions.Multiplayer.Regulator.SupervisorMultiPlayer;
import be.ac.umons.mom.g02.GameStates.Dialogs.OutGameDialogState;
import be.ac.umons.mom.g02.GameStates.LoadingState;
import be.ac.umons.mom.g02.GameStates.Menus.MainMenuState;
import be.ac.umons.mom.g02.GameStates.Menus.MenuState;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.MenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.TitleMenuItem;
import be.ac.umons.mom.g02.Managers.ExtensionsManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Regulator.Supervisor;
import com.badlogic.gdx.Gdx;

import java.net.SocketException;

/**
 * The state where we wait for a connection / for the acceptation of the connection by the second player.
 */
public class FinalisingConnectionState extends MenuState {

    /**
     * The network manager.
     */
    protected NetworkManager nm;
    /**
     * If we must send the player's characteristics to the second player
     */
    protected boolean sendPlayer;

    /**
     * @param gs The game's graphical settings.
     */
    public FinalisingConnectionState(GraphicalSettings gs) {
        super(gs);
        try {
            nm = NetworkManager.getInstance();
        } catch (SocketException e) {
            e.printStackTrace();
            return;
        }

    }

    @Override
    public void init() {
        super.init();

        transparentBackground = false;

        setMenuItems(new MenuItem[]{
                new TitleMenuItem(gs, GraphicalSettings.getStringFromId("finalisingConnection"))
        });

        Gdx.app.postRunnable(() -> {
            if (MasterOfMonsGame.getGameToLoad() != null && ExtensionsManager.getInstance().getExtensionsMap().get("LAN").activated) {
                Save save = SupervisorLAN.getSupervisor().oldGameLAN(MasterOfMonsGame.getGameToLoad());
                if (nm.isTheServer()) {
                    nm.sendMessageOnTCP("SAVE", save);
                    LoadingState ls = (LoadingState) gsm.removeAllStateAndAdd(LoadingState.class);
                    ls.setAfterLoadingState(be.ac.umons.mom.g02.Extensions.LAN.GameStates.Menus.WaitMenuState.class);
                }
            }
        });
        if (sendPlayer)
            nm.sendMessageOnTCP("PI", Supervisor.getPeople());
        if (nm.isTheServer()) {
            goToLoading();
        }
    }

    @Override
    public void update(float dt) {
        super.update(dt);
    }

    /**
     * @param sendPlayer If we must send the player's characteristics to the second player
     */
    public void setSendPlayer(boolean sendPlayer) {
        this.sendPlayer = sendPlayer;
        if (sendPlayer)
            nm.sendMessageOnTCP("PI", Supervisor.getPeople());
    }

    /**
     * Go to the loading state
     */
    public static void goToLoading() {
        GameStateManager gsm = GameStateManager.getInstance();
        try {
            NetworkManager nm = NetworkManager.getInstance();
            LoadingState ls = (LoadingState) gsm.removeAllStateAndAdd(LoadingState.class);
            ls.setOnLoaded(() -> nm.sendOnTCP("Loaded"));
            ls.setAfterLoadingState(be.ac.umons.mom.g02.Extensions.LAN.GameStates.Menus.WaitMenuState.class);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set all the necessary actions in the NetworkManager
     */
    public static void setNetworkManagerRunnable() {
        GameStateManager gsm = GameStateManager.getInstance();
        NetworkManager nm = null;
        try {
            nm = NetworkManager.getInstance();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        nm.whenMessageReceivedDo("PI", objects -> {
            if (MasterOfMonsGame.getGameToLoad() == null && MasterOfMonsGame.getSaveToLoad() == null)
                FinalisingConnectionState.goToLoading();
            SupervisorMultiPlayer.setPlayerTwo((People) objects[0]);
        });
        nm.whenMessageReceivedDo("SPI", objects -> {
            FinalisingConnectionState.goToLoading();
            SupervisorMultiPlayer.setPlayerOne((People) objects[0]);
        });
        nm.whenMessageReceivedDo("SAVE", (objects -> {
            Save save = (Save) objects[0];
            save.invertPlayerOneAndTwo();
            MasterOfMonsGame.setSaveToLoad(save);
            if (ExtensionsManager.getInstance().getExtensionsMap().get("LAN").activated)
                SupervisorLAN.getSupervisor().oldGameLAN(save);
            FinalisingConnectionState.goToLoading();
        }));
        nm.setOnDisconnected(() -> {
            gsm.removeAllStateAndAdd(MainMenuState.class);
            OutGameDialogState ogds = (OutGameDialogState) gsm.setState(OutGameDialogState.class);
            ogds.setText(GraphicalSettings.getStringFromId("disconnected"));
            ogds.addAnswer("OK");
        });
    }
}
